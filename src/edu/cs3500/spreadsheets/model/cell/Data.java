package edu.cs3500.spreadsheets.model.cell;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Interface for Cell objects.
 */
public interface Data {


  /**
   * Accept for accepting visitors.
   * @param visitor The visitor function to be applied to the Data.
   * @param <R> result in Type R.
   * @return
   */
  <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells);

  /**
   * Method to determine if a data is self-referential.
   * @param cells the field containing the spreadsheet data to find out other cells' values etc.
   * @param linkedCells a built list of cells that is passed down during each call, containing
   *                    all the Coords of referenced cells.
   * @return boolean representing whether or not a cell is cyclic.
   */
  boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells);

  /**
   * Adds a list of dependents to a Data.
   * @param d list of dependents to add to Data.
   */
  void addDependents(Set<Coord> d);

  /**
   * Add a Dependent to the Data.
   * @param d the coord of the dependent to be added.
   */
  void addDependents(Coord d);

  /**
   * Gets the list of dependents from a data.
   * @return the list of dependents.
   */
  Set<Coord> getDependents();

  /**
   * Re-evaluates the data.
   * @return a Value representing the re-evaluated value of the data.
   */
  //todo

  /**
   * Method for adding dependents to Data, where the called Data is the dependent.
   * @param d the coord of this Data.
   */
  void makeDependent(Coord d, HashMap<Coord, Data> cells);

  /**
   * Method for updating dependencies.
   * @param cells the cells field of a Worksheet.
   * @param evaluated the EvaluatedCells field of a Worksheet.
   */
  void update(HashMap<Coord, Data> cells, HashMap<Coord, Value> evaluated);


}
