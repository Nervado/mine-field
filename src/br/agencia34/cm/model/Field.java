package br.agencia34.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Field {
  private final int lin;
  private final int col;
  private boolean open = false;
  private boolean mined = false;
  private boolean marked = false;

  private List<Field> neibhs = new ArrayList<>();

  private List<FieldObserver> observers = new ArrayList<>();

  // private List<BiConsumer<Field, FieldEvents>> Observers = new ArrayList<>();

  Field(int lin, int col) {
    this.lin = lin;
    this.col = col;
  }

  public void registerObserver(FieldObserver o) {
    observers.add(o);
  }

  private void notifyObservers(FieldEvent e) {
    observers.stream().forEach(o -> o.eventHappened(this, e));
  }

  boolean addNeibh(Field neibh) {

    boolean isDifLin = lin != neibh.lin;
    boolean isDifCol = col != neibh.col;
    boolean isDiag = isDifLin && isDifCol;

    int deltaLin = Math.abs(lin - neibh.lin);
    int deltaCol = Math.abs(col - neibh.col);
    int deltaSum = deltaCol + deltaLin;

    if (deltaSum == 1 && !isDiag) {
      neibhs.add(neibh);
      return true;
    } else if (deltaSum == 2 && isDiag) {
      neibhs.add(neibh);
      return true;
    } else {
      return false;
    }
  }

  public boolean markToogle() {
    if (!open) {
      marked = !marked;
      if (marked) {
        notifyObservers(FieldEvent.TAG);
      } else {
        notifyObservers(FieldEvent.UNTAG);
      }
    }
    return false;
  }

  public boolean open() {
    if (!open && !marked) {

      if (mined) {

        notifyObservers(FieldEvent.EXPLODE);
        return true;
      }
      setOpen(true);

      if (isNeibhSafe()) {
        neibhs.forEach(n -> n.open());
      }

      return true;
    } else {
      return false;
    }
  }

  public boolean isNeibhSafe() {
    return neibhs.stream().noneMatch(n -> n.mined);
  }

  public boolean isMarked() {
    return marked;
  }

  void undermine() {
    mined = true;
  }

  public boolean isUndermined() {
    return mined;
  }

  public boolean isOpen() {
    return open;
  }

  public boolean isClosed() {
    return !isOpen();
  }

  public int getCol() {
    return col;
  }

  public int getLin() {
    return lin;
  }

  boolean success() {
    boolean solved = !mined && open;
    boolean protectedField = mined && marked;
    return solved || protectedField;
  }

  public int minesCount() {
    return (int) neibhs.stream().filter(n -> n.mined).count();
  }

  void reset() {
    open = false;
    mined = false;
    marked = false;
    notifyObservers(FieldEvent.RESET);
  }

  void setOpen(boolean opened) {
    if (opened) {
      notifyObservers(FieldEvent.OPEN);
    }
    this.open = opened;
  }
}
