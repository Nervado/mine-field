package br.agencia34.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Table implements FieldObserver {
  final private int lin;
  final private int col;
  private int mines;

  private final List<Field> fields = new ArrayList<>();
  private final List<Consumer<EventResult>> observers = new ArrayList<>();

  // private final List<Consumer<Boolean>> observers = new ArrayList<>();

  public Table(int lin, int col, int mines) {
    this.col = col;
    this.lin = lin;
    this.mines = mines;

    generateFieds();
    defineNeighbors();
    addMines();
  }

  public void registerObserver(Consumer<EventResult> o) {
    observers.add(o);
  }

  private void notifyObservers(Boolean r) {
    observers.stream().forEach(o -> o.accept(new EventResult(r)));
  }

  private void generateFieds() {
    for (int i = 0; i < lin; i++) {
      for (int j = 0; j < col; j++) {
        Field field = new Field(i, j);
        field.registerObserver(this);
        fields.add(field);
      }
    }
  }

  private void defineNeighbors() {
    for (Field field : fields) {
      for (Field neighb : fields) {
        field.addNeibh(neighb);
      }
    }
  }

  private void addMines() {
    long armedMines = 0;
    Predicate<Field> mined = n -> n.isUndermined();
    do {
      int rand = (int) (Math.random() * fields.size());
      fields.get(rand).undermine();
      armedMines = fields.stream().filter(mined).count();
    } while (armedMines < mines);
  }

  public boolean sucess() {
    return fields.stream().allMatch(n -> n.success());
  }

  public void reset() {
    fields.stream().forEach(f -> f.reset());
    addMines();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();

    sb.append("  ");
    for (int i = 0; i < col; i++) {
      sb.append(" ");
      sb.append(i);
      sb.append(" ");
    }

    sb.append("\n");

    int i = 0;
    for (int l = 0; l < lin; l++) {
      sb.append(l);
      sb.append(" ");
      for (int c = 0; c < col; c++) {
        sb.append(" ");
        sb.append(fields.get(i));
        sb.append(" ");
        i++;
      }
      sb.append("\n");

    }

    return sb.toString();

  }

  public void open(int lin, int col) {

    fields.parallelStream().filter(f -> f.getLin() == lin && f.getCol() == col).findFirst().ifPresent(f -> f.open());

  }

  public void toogleMark(int lin, int col) {
    fields.parallelStream().filter(f -> f.getLin() == lin && f.getCol() == col).findFirst()
        .ifPresent(f -> f.markToogle());
  }

  @Override
  public void eventHappened(Field f, FieldEvent e) {

    if (e == FieldEvent.EXPLODE) {
      showMines();
      notifyObservers(false);
    } else if (sucess()) {

      notifyObservers(true);
    }

  }

  private void showMines() {
    fields.stream().filter(f -> f.isUndermined()).filter(f -> !f.isMarked()).forEach(f -> f.setOpen(true));
  }

  public int getCols() {
    return this.col;
  }

  public int getLins() {
    return this.lin;
  }

  public void forEachField(Consumer<Field> f) {
    fields.forEach(f);
  }
}
