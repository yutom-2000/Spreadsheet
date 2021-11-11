package edu.cs3500.spreadsheets.provider.view;

import java.awt.BorderLayout;
import java.util.Map;
import javax.swing.JFrame;

import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import javax.swing.JPanel;

/**
 * A WorksheetView that uses Java Swing to display a model graphically.
 */
public class WorksheetGraphicalView extends JFrame implements WorksheetView {

  private final GridPanel grid;

  /**
   * Constructor for a WorksheetView that uses Java Swing to display a model graphically.
   *
   * @param cells the cells to be displayed
   */
  public WorksheetGraphicalView(Map<Coord, Object> cells) {
    super();
    this.setTitle("Spreadsheet");
    this.setSize(1000, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    grid = new GridPanel(cells);
    JPanel sheetPanel = new ProviderSpreadsheetPanel(grid);
    this.add(sheetPanel);
  }

  @Override
  public void display() {
    setVisible(true);
  }

  @Override
  public void updateView(String cell, String value) {
    grid.updateCells(cell, value);
    repaint();
  }

  @Override
  public void addFeatures(Features features) {
    /**
     * This is not an editable view so it doesn't need this.
     */
  }

  @Override
  public Coord getSelectedCell() {
    return new Coord(1, 1);
  }
}
