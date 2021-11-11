package edu.cs3500.spreadsheets.model.cell.function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * Class representing a Summation operation.
 */
public class Sum extends Function {
  List<Data> args;

  /**
   * Constructor for a Sum object.
   * @param args the list of arguments to be passed into the sum.
   */
  public Sum(List<Data> args) {
    super(new HashSet<Coord>());
    this.args = args;
  }

  /**
   * Copy constructor for Sum object.
   * @param s the Sum to be copied.
   */
  public Sum(Sum s) {
    super(new HashSet<Coord>());
    this.args = s.args;
  }


  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return visitor.visitSum(args, cells);
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
    String result = "=(SUM ";
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
    if (!(o instanceof Sum)) {
      return false;
    }
    else {
      Sum other = (Sum) o;
      return other.args.equals(other.args);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.args);
  }
}
