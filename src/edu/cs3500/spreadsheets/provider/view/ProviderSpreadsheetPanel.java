package edu.cs3500.spreadsheets.provider.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollBar;


/**
 * A JPanel that houses the grid panel as well as two scrollbars and handles scrolling.
 */
class ProviderSpreadsheetPanel extends JPanel {

  private final GridPanel grid;

  /**
   * Constructs a SpreadsheetPanel that uses Java Swing to display the given GridPanel with the
   * addition of two scrollbars to handle scrolling.
   *
   * @param grid the grid to be displayed.
   */
  ProviderSpreadsheetPanel(GridPanel grid) {
    this.grid = grid;
    this.setLayout(new BorderLayout());
    JScrollBar vertical = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 2000);
    JScrollBar horizontal = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 2000);
    vertical.addAdjustmentListener(e -> {
      if (e.getValueIsAdjusting()) {
        if (e.getValue() % 2 == 0) {
          grid.changeYPostion((int) Math.pow(e.getValue(), 2) / 4000);
          repaint();
        }
      }
    });
    horizontal.addAdjustmentListener(e -> {
      if (e.getValueIsAdjusting()) {
        if (e.getValue() % 2 == 0) {
          grid.changeXPostion((int) Math.pow(e.getValue(), 2) / 4000);
          repaint();
        }
      }
    });
    this.add(grid);
    this.add(vertical, BorderLayout.EAST);
    this.add(horizontal, BorderLayout.SOUTH);
  }

  /**
   * Adds a new value to the sheet at the specified cell coordinate.
   *
   * @param cell  the coordinate of the cell as a string
   * @param value the value of the cell as a string
   */
  void updateCells(String cell, String value) {
    grid.updateCells(cell, value);
  }
}
