package edu.cs3500.spreadsheets.model;

import java.util.HashMap;

import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Interface for various implementations of a Worksheet.
 */
public interface WorkSheet<T> {

  /**
   * Return the Data at a specific Coord.
   * Need to be careful with this method, because can return null.
   * @param col column index (1-indexed).
   * @param row row index (1-indexed).
   * @return Data matching the Coord key.
   */
  T getDataAt(int col, int row);

  /**
   * Return the Data from cell at given coordinate.
   * @param c given coordinate
   * @return
   */
  Data getCellAt(Coord c);

  /**
   * Return the column count of a Worksheet.
   * @return int representing count of columns in the Worksheet.
   */
  int getCols();

  /**
   * Returns the row count of a Worksheet.
   * @return int representing count of rows in the Worksheet.
   */
  int getRows();

  /**
   * Returns the evaluated cells of a Worksheet in a hashMap.
   * @return the unevaluated cells of a Worksheet in a hashMap.
   */
  HashMap<Coord, Data> getCells();

  /**
   * Returns the raw String input of an erroneous cell in the Worksheet.
   * @return String of the erroneous cell from file.
   */
  String getErroneousCells();

  /**
   * Mutate the cells field to overwrite a position to have a certain data.
   */
  void writeCell(Coord pos, String d);

  /**
   * Adds a BlankData at the row edge of the spreadsheet.
   */
  void addRow();

  /**
   * Adds a BlankData at the column edge of the spreadsheet.
   */
  void addColumn();

  /**
   * Gets the non-evaluated Cells Field.
   */
  HashMap<Coord, Value> getEvaluated();

  /**
   * Get the evaluated Cell at specified coord.
   */
  Value getEvaluatedAt(Coord c);
}
