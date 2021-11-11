package edu.cs3500.spreadsheets.model.cell.function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * Object representing if operation to see if first argument less than second.
 */
public class FirstLessThan extends Function {
  Data first;
  Data second;

  /**
   * Public constructor for this object.
   *
   * @param first  first argument in inequality.
   * @param second second argument in inequality.
   */
  public FirstLessThan(Data first, Data second) {
    super(new HashSet());
    this.first = first;
    this.second = second;
  }

  /**
   * Constructor for specified dependents list.
   * @param first first argument in inequality.
   * @param second second argument in inequality.
   * @param d dependents list.
   */
  public FirstLessThan(Data first, Data second, HashSet<Coord> d) {
    super(d);
    this.first = first;
    this.second = second;
  }

  /**
   * Copy constructor for a FirstLessThan object.
   *
   * @param f the FirstLessThan to be copied.
   */
  public FirstLessThan(FirstLessThan f) {
    super(new HashSet<Coord>());
    this.first = f.first;
    this.second = f.second;
  }

  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return visitor.visitFirstLessThan(first, second, cells); //TODO
  }

  @Override
  public boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells) {
    return this.first.isCyclic(cells, linkedCells)
            || this.second.isCyclic(cells, linkedCells);
  }

  @Override
  public void makeDependent(Coord d, HashMap<Coord, Data> cells) {
    this.first.makeDependent(d, cells);
    this.second.makeDependent(d, cells);
  }

  @Override
  public String toString() {
    String result = "=(< ";
    result = result + this.first.toString() + " ";
    result = result + this.second.toString() + ")";
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof FirstLessThan)) {
      return false;
    } else {
      FirstLessThan other = (FirstLessThan) o;
      return (this.first.equals((other.first)) && this.second.equals(other.second));
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.first) + Objects.hash(this.second);
  }
}
