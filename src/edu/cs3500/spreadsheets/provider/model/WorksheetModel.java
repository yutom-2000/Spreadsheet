package edu.cs3500.spreadsheets.provider.model;

import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * A model of a Spreadsheet containing cells.
 */
public interface WorksheetModel {

  /**
   * Adds a cell to the current Spreadsheet.
   *
   * @param cell the cell's position
   * @param value the value in the cell.
   */
  void addCell(String cell, Object value);

  /**
   * Changes the value of the Cell.
   *
   * @param cell the Cell to be edited.
   * @param value the new value of the Cell.
   */
  void changeCellValue(String cell, Object value);

  /**
   * Gets the evaluated value of a Cell.
   *
   * @param cell the cell to be evaluated.
   * @return the cell's evaluated value.
   */
  Object getCellValue(String cell);

  /**
   * Returns the raw calue of the cell, either a formula or a value, as a String.
   * @param cell the position of the cell as a String.
   * @return the raw value of a cell as a string.
   */
  String getRawCellValue(String cell);

  /**
   * Get a list of all of the coordinates of non-empty cells in the sheet.
   * @return the list of coordinates
   */
  List<Coord> getAllValidCoords();

  /**
   * Get a map of all non-empty cells coordinates to their values.
   * @return the map of the values
   */
  Map<Coord, Object> getAllCellValues();

  /**
   * Get a map of all non-empty cells coordinates to their raw values.
   * @return the map of the raw values
   */
  Map<Coord, String> getAllRawCellValues();

}
