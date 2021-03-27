package br.agencia34.cm.views;

import javax.swing.JFrame;

import br.agencia34.cm.model.Table;

public class MainScreen extends JFrame {

  /**
   *
   */
  private static final long serialVersionUID = 1L;

  public MainScreen() {
    Table table = new Table(16, 30, 50);
    TablePainel tablePainel = new TablePainel(table);
    add(tablePainel);

    setTitle("Mine Field");
    setSize(690, 438);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {
    new MainScreen();
  }
}
