package edu.cs3500.spreadsheets.view;

import java.awt.event.FocusListener;

import javax.swing.JTable;

/**
 * A type of JTable used to represent a spreadsheet of data.
 */
public class SpreadsheetTable extends JTable {

  /**
   * Constructor for SpreadsheetTable.
   * @param data Data to fill the spreadsheet
   * @param colHeaders Column header names
   */
  public SpreadsheetTable(String[][] data, String[] colHeaders) {
    super(data, colHeaders);
  }

  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }

  @Override
  public boolean getRowSelectionAllowed() {
    return false;
  }

  @Override
  public synchronized void addFocusListener(FocusListener l) {
    //do nothing - keeps focus on the cell when interacting with a different component
  }
}
