package br.agencia34.cm.views;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.agencia34.cm.model.Table;

@SuppressWarnings("serial")
public class TablePainel extends JPanel {
  public TablePainel(Table table) {

    setLayout(new GridLayout(table.getLins(), table.getCols()));

    table.forEachField(f -> add(new FieldButton(f)));

    table.registerObserver(e -> {

      SwingUtilities.invokeLater(() -> {
        if (e.isWinner()) {
          JOptionPane.showMessageDialog(this, "You Win :)");
        } else {
          JOptionPane.showMessageDialog(this, "You Lose :(");
        }

        table.reset();
      });
    });

  }
}
