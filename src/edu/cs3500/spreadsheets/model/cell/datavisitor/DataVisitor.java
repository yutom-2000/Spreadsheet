package edu.cs3500.spreadsheets.model.cell.datavisitor;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;

/**
 * Function objects to apply visitor pattern upon cell objects.
 *
 * @param <R> Return type of a function. Note: non-Value types of Data must have a HashMap to be
 *            able to reference a sheet's Data, and evaluate references.
 */
public interface DataVisitor<R> {

  /**
   * Apply function to a boolean Value.
   *
   * @param b the value of the boolean.
   * @return R result.
   */
  R visitBooleanValue(boolean b);

  /**
   * Apply function to a double Value.
   *
   * @param d the value of the Number Value.
   * @return R result.
   */
  R visitNumberValue(double d);

  /**
   * Apply function to a String Value.
   *
   * @param s the value of the String Value.
   * @return R result.
   */
  R visitStringValue(String s);

  /**
   * Apply function to a Sum function object.
   *
   * @param args the List of Data to be visited.
   * @return R result.
   */
  R visitSum(List<Data> args, HashMap<Coord, Data> cells);

  /**
   * Apply function to a visitProduct object.
   *
   * @param args the list of Data to be visited.
   * @return R result.
   */
  R visitProduct(List<Data> args, HashMap<Coord, Data> cells);

  /**
   * Apply function to a FirstLessThan object.
   *
   * @param first  the first argument.
   * @param second the second argument.
   * @return R result.
   */
  R visitFirstLessThan(Data first, Data second, HashMap<Coord, Data> cells);

  /**
   * Apply function to Concat object.
   *
   * @param args the list of Data to be visited.
   * @return R result.
   */
  R visitConcat(List<Data> args, HashMap<Coord, Data> cells);

  /**
   * Apply function to a Reference object.
   *
   * @param references the List of Coords to be visited.
   * @return R result.
   */
  R visitReference(List<Coord> references, HashMap<Coord, Data> cells);


}
