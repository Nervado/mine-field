package br.agencia34.cm.model;

public class EventResult {
  private final boolean win;

  public EventResult(boolean win) {
    this.win = win;
  }

  public boolean isWinner() {
    return win;
  }
}
