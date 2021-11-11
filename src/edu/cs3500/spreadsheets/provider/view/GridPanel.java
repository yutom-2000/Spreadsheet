package edu.cs3500.spreadsheets.provider.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Map;
import javax.swing.JPanel;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * A JPanel that contains the actual grid containing the data from the model.
 */
class GridPanel extends JPanel {

  private final Map<Coord, Object> cells;
  private Coord position;
  private Coord selectedCell;

  /**
   * Constructor for a GridPanel that uses Java Swing to display s grid containing the model's
   * data.
   *
   * @param cells the cells to be displayed.
   */
  GridPanel(Map<Coord, Object> cells) {
    this.cells = cells;
    position = new Coord(1, 1);
    selectedCell = new Coord(1, 1);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    g.setFont(new Font("Courier", Font.PLAIN, 14));

    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

    g2d.setColor(Color.GRAY);
    g2d.fillRect(0, 0, this.getWidth(), 30);
    g2d.fillRect(0, 0, 50, this.getHeight());

    g2d.setColor(Color.BLACK);
    drawLabels(g2d);
    drawLines(g2d);
    drawCells(g2d);

    g2d.setColor(Color.BLUE);
    g2d.setStroke(new BasicStroke(2));
    if (!selectedCell.equals(new Coord(0, 0))) {
      g2d.drawRect((selectedCell.col - position.col) * 100 + 50,
          (selectedCell.row - position.row + 1) * 30,
          100, 30);
    }
  }

  // draw the row and column labels
  private void drawLabels(Graphics2D g2d) {
    for (int i = 0; i < this.getWidth() + 100; i++) {
      if (i != 0 && i % 100 == 30) {
        int col = (i - 30) / 100 + position.col - 1;
        String colName = Coord.colIndexToName(col);
        g2d.drawString(colName, i - 75, 20);
      }
    }
    for (int i = 0; i < this.getHeight() + 30; i++) {
      if (i != 0 && i % 30 == 0) {
        int row = i / 30 + position.row - 1;
        String rowString = String.valueOf(row);
        int width = rowString.length();
        if (width > 6) {
          String cutOff = rowString.substring(rowString.length() - 3);
          cutOff = "..." + cutOff;
          g2d.drawString(cutOff, 1, i + 20);
        } else {
          g2d.drawString(rowString, 1, i + 20);
        }
      }
    }
  }

  // draw the grid lines
  private void drawLines(Graphics2D g2d) {
    for (int i = 0; i < this.getWidth(); i++) {
      if (i == 0) {
        g2d.drawLine(50, 0, 50, this.getHeight());
      } else if (i % 100 == 50) {
        g2d.drawLine(i, 0, i, this.getHeight());
      }
    }
    for (int i = 0; i < this.getHeight(); i++) {
      if (i % 30 == 0) {
        g2d.drawLine(0, i, this.getWidth(), i);
      }
    }
  }

  // draw the text in the cells
  private void drawCells(Graphics2D g2d) {
    for (Coord c : cells.keySet()) {
      if (inRange(c)) {
        int xpos = (c.col - 1) * 100 + 51 - (position.col - 1) * 100;
        int ypos = (c.row - 1) * 30 + 58 - (position.row - 1) * 30;
        String cellString = cells.get(c).toString();
        int width = cellString.length();
        if (width > 12) {
          String cutOff = cellString.substring(0, 9);
          cutOff += "...";
          g2d.drawString(cutOff, xpos, ypos);
        } else {
          g2d.drawString(cells.get(c).toString(), xpos, ypos);
        }
      }
    }
  }

  // checks to see if a cell is in the range of the grid at its current position
  private boolean inRange(Coord c) {
    Coord outerBound = new Coord(position.col + (this.getWidth() - 50) / 100,
        position.row + (this.getHeight() - 30) / 30);
    return c.col >= position.col && c.row >= position.row
        && c.col <= outerBound.col && c.row <= outerBound.row;
  }

  /**
   * Changed the X coordinate of the position of this grid.
   *
   * @param amount the amount to change the X coordinate by
   */
  void changeXPostion(int amount) {
    position = new Coord(1 + amount, position.row);
  }

  /**
   * Changed the Y coordinate of the position of this grid.
   *
   * @param amount the amount to change the Y coordinate by
   */
  void changeYPostion(int amount) {
    position = new Coord(position.col, 1 + amount);
  }

  /**
   * Adds a new value to the sheet at the specified cell coordinate.
   *
   * @param cell  the coordinate of the cell as a string
   * @param value the value of the cell as a string
   */
  void updateCells(String cell, String value) {
    Coord coord = cellParse(cell);
    try {
      Double d = Double.parseDouble(value);
      cells.put(coord, d);
      return;
    } catch (NumberFormatException e) {
      /**
       * Ignore.
       */
    }
    try {
      int i = Integer.parseInt(value);
      Double d = (double) i;
      cells.put(coord, d);
      return;
    } catch (NumberFormatException e) {
      /**
       * Ignore.
       */
    }
    if (value.equals("true") || value.equals("false")) {
      Boolean b = Boolean.parseBoolean(value);
      cells.put(coord, b);
    } else {
      cells.put(coord, value);
    }
  }

  //Turns a string into a Coord for a cell.
  private static Coord cellParse(String cell) {
    if (!cell.matches("([A-Z])*\\d+")) {
      throw new IllegalArgumentException("Incorrect cell coordinate");
    } else {
      String[] s = cell.split("(?<=[A-Z])(?=[0-9])");
      return new Coord(Coord.colNameToIndex(s[0]), Integer.parseInt(s[1]));
    }
  }

  //Sets the current cell selected to the newly selected cell.
  void select(Coord c) {
    selectedCell = new Coord(c.col + position.col - 1, c.row + position.row - 1);
  }

  //Returns the current selectedCell.
  Coord getSelected() {
    return selectedCell;
  }

  //Changes the selectedCell based on which arrow key is pressed.
  void moveBySelectedKey(KeyEvent e) {
    int keyEvent = e.getKeyCode();
    switch (keyEvent) {
      case KeyEvent.VK_UP:
        if (selectedCell.row > 1) {
          selectedCell = new Coord(selectedCell.col, selectedCell.row - 1);
        }
        break;
      case KeyEvent.VK_DOWN:
        if (selectedCell.row < 1023) {
          selectedCell = new Coord(selectedCell.col, selectedCell.row + 1);
        }
        break;
      case KeyEvent.VK_LEFT:
        if (selectedCell.col > 1) {
          selectedCell = new Coord(selectedCell.col - 1, selectedCell.row);
        }
        break;
      case KeyEvent.VK_RIGHT:
        if (selectedCell.col < 1023) {
          selectedCell = new Coord(selectedCell.col + 1, selectedCell.row);
        }
        break;
      default: //nothing should happen, other key events are simply ignored.
    }
    repaint();
  }
}
