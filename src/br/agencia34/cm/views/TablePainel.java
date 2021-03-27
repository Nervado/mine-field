package br.agencia34.cm.views;

import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.agencia34.cm.model.Table;

@SuppressWarnings("serial")
public class TablePainel extends JPanel {

  private final Icon mineField = new ImageIcon(getClass().getResource("/resources/minefield.png"));

  private final Icon lose = new ImageIcon(getClass().getResource("/resources/lose.png"));

  public TablePainel(Table table) {
    setLayout(new GridLayout(table.getLins(), table.getCols()));

    table.forEachField(f -> add(new FieldButton(f)));

    table.registerObserver(e -> {

      SwingUtilities.invokeLater(() -> {
        if (e.isWinner()) {
          JOptionPane.showMessageDialog(this, "You Win :)", "Mine Field", JOptionPane.ERROR_MESSAGE, mineField);
        } else {
          JOptionPane.showMessageDialog(this, "You Lose :(", "Mine Field", JOptionPane.ERROR_MESSAGE, lose);
        }

        table.reset();
      });
    });

  }
}
