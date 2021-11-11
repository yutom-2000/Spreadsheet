package edu.cs3500.spreadsheets.model.cell.function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * Object representing Concat function. Concatenates String Values together. Non string Data concats
 * an empty string on.
 */
public class Concat extends Function {
  List<Data> args;

  /**
   * Public constructor for a Concat Object.
   *
   * @param args List of arguments to be passed into the function.
   */
  public Concat(List<Data> args) {
    super(new HashSet<Coord>());
    this.args = args;
  }

  /**
   * Public constructor for a Concat Object where the dependnecies are specified.
   * @param args arguments to be passed into the function.
   * @param d coord of cells dependent on this cell's value.
   */
  public Concat(List<Data> args, HashSet<Coord> d) {
    super(d);
    this.args = args;
  }

  /**
   * Copy constructor for a Concat object.
   *
   * @param c the Concat object to be copied.
   */
  public Concat(Concat c) {
    super(new HashSet<Coord>());
    this.args = c.args;
  }

  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return visitor.visitConcat(this.args, cells);
  }

  @Override
  public boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells) {
    boolean result = false;
    for (Data element : args) {
      result = result || element.isCyclic(cells, linkedCells);
    }
    return result;
  }

  @Override
  public void makeDependent(Coord d, HashMap<Coord, Data> cells) {
    for (Data e : this.args) {
      e.makeDependent(d, cells);
    }
  }

  @Override
  public String toString() {
    String result = "=(CONCAT ";
    for (Data element : args) {
      result += element.toString() + " ";
    }
    if (result.substring(result.length() - 1).equals(" ")) {
      result = result.substring(0, result.length() - 1);
    }
    result = result + ")";
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Concat)) {
      return false;
    }
    else {
      Concat other = (Concat) o;
      return other.args.equals(other.args);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.args);
  }



}
