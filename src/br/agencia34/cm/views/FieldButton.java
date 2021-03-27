package br.agencia34.cm.views;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import br.agencia34.cm.model.Field;
import br.agencia34.cm.model.FieldEvent;
import br.agencia34.cm.model.FieldObserver;

@SuppressWarnings("serial")
public class FieldButton extends JButton implements FieldObserver, MouseListener {
  private final Color BG_DEFAULT = new Color(184, 184, 184);
  // private final Color BG_TAGED = new Color(8, 179, 247);
  private final Color BG_EXPLODE = new Color(189, 66, 68);
  private final Color TEXT_COLOR = new Color(0, 100, 0);

  private final Icon mark = new ImageIcon(getClass().getResource("/resources/mark.png"));

  private final Icon mine = new ImageIcon(getClass().getResource("/resources/mine.png"));

  private Field field;

  public FieldButton(Field field) {
    this.field = field;

    setBackground(BG_DEFAULT);
    setOpaque(true);
    setBorder(BorderFactory.createBevelBorder(0));
    setIcon(null);

    addMouseListener(this);
    field.registerObserver(this);
  }

  @Override
  public void eventHappened(Field f, FieldEvent e) {

    switch (e) {
    case OPEN:
      applyOpenStyle();
      break;
    case TAG:
      applyTagUnTagStyle();
      break;
    case EXPLODE:
      applyExplodeStyle();
      break;
    default:
      applyDefaultStyle();
      break;
    }

    SwingUtilities.invokeLater(() -> {
      repaint();
      validate();
    });

  }

  private void applyOpenStyle() {

    setBorder(BorderFactory.createLineBorder(Color.GRAY));

    if (field.isUndermined()) {
      // setBackground(BG_EXPLODE);
      setIcon(mine);
      return;
    }

    setBackground(BG_DEFAULT);

    switch (field.minesCount()) {
    case 1:
      setForeground(TEXT_COLOR);
      break;
    case 2:
      setForeground(Color.BLUE);
      break;

    case 3:
      setForeground(Color.YELLOW);
      break;
    case 4:
    case 5:
    case 6:
      setForeground(Color.RED);
      break;
    default:
      setForeground(Color.PINK);
      break;
    }

    String value = !field.isNeibhSafe() ? field.minesCount() + "" : "";

    setText(value);
  }

  private void applyTagUnTagStyle() {
    // setBackground(BG_TAGED);
    // setForeground(Color.BLACK);
    setIcon(mark);
    // setText("T");
  }

  private void applyExplodeStyle() {
    setBackground(BG_EXPLODE);
    // setForeground(Color.WHITE);
    setIcon(mine);
    // setText("X");
  }

  private void applyDefaultStyle() {
    setBackground(BG_DEFAULT);
    setBorder(BorderFactory.createBevelBorder(0));
    setText("");
    setIcon(null);
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (e.getButton() == 1) {
      field.open();
    } else {
      field.markToogle();
    }

  }

  public void mouseClicked(MouseEvent e) {

  }

  public void mouseReleased(MouseEvent e) {

  }

  public void mouseEntered(MouseEvent e) {

  }

  public void mouseExited(MouseEvent e) {

  }
}
