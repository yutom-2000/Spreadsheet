package edu.cs3500.spreadsheets.provider.controller;

import edu.cs3500.spreadsheets.provider.view.WorksheetView;

/**
 * A controller that provides features, or operations, that can be performed on a
 * Spreadsheet.
 */
public interface Features {

  /**
   * Sets up the given WorksheetView to be editable by doing the following:
   * Passes the controller to add its features to the view.
   * Passes the view the raw and evaluated value of the cells in the current model.
   *
   * @param v the given view to be set.
   */
  void setView(WorksheetView v);

  /**
   * Adds the given value to the specified cell.
   *
   * @param cell  the cell to be added.
   * @param value the value to be put inside the cell.
   */
  void addCell(String cell, Object value);

  /**
   * Changes the value of the specified cell to the value given.
   *
   * @param cell  the cell to be changed.
   * @param value the value to put inside of the specified cell.
   */
  void changeCell(String cell, Object value);

  /**
   * Saves the current Spreadsheet model to the inputted file.
   *
   * @param fileName the file to be saved to.
   */
  void save(String fileName);

  /**
   * Loads the inputted file to be displayed.
   *
   * @param fileName the file to be loaded.
   */
  void load(String fileName);

  /**
   * Deletes the inputted cell from the Spreadsheet.
   *
   * @param cell the cell to be deleted.
   */
  void deleteCell(String cell);
}
