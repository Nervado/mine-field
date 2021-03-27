package br.agencia34.cm.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldTest {

  private Field field = new Field(3, 3);

  @BeforeEach
  void init() {
    field = new Field(3, 3);

  }

  @Test
  void testNeibhRealDistance1() {
    Field neib = new Field(3, 2);
    boolean result = field.addNeibh(neib);
    assertTrue(result);
  }

  @Test
  void testNeibhRealDistance2() {
    Field neib = new Field(2, 2);
    boolean result = field.addNeibh(neib);
    assertTrue(result);
  }

  @Test
  void testNeibhBelow() {
    Field neib = new Field(4, 3);
    boolean result = field.addNeibh(neib);
    assertTrue(result);
  }

  @Test
  void testNoNeibh() {
    Field neib = new Field(1, 1);
    boolean result = field.addNeibh(neib);
    assertFalse(result);
  }

  @Test
  void testToogleMarkedDefault() {
    assertFalse(field.isMarked());
  }

  @Test
  void testToogleMarked() {
    field.markToogle();
    assertTrue(field.isMarked());
  }

  @Test
  void testToogleMarkedWithSecondCall() {
    field.markToogle();
    field.markToogle();
    assertFalse(field.isMarked());
  }

  @Test
  void testOpenMinedNotMarked() {
    field.undermine();
    assertThrows(Exception.class, () -> {
      field.open();
    });
  }

  @Test
  void testOpenMinedWithNeibrs2() {
    Field nebh1 = new Field(1, 1);
    Field nebh3 = new Field(1, 1);
    Field nebh2 = new Field(2, 2);

    nebh3.undermine();

    nebh2.addNeibh(nebh1);
    nebh2.addNeibh(nebh3);

    field.addNeibh(nebh2);

    assertTrue(nebh2.isOpen() && nebh1.isClosed());
  }
}
