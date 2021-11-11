package edu.cs3500.spreadsheets.model.cell.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * A Class representing a boolean type of Value.
 */
public class BooleanValue extends Value {
  private boolean val;

  /**
   * Public constructor to create a BooleanValue.
   *
   * @param b input boolean to be stored.
   */
  public BooleanValue(boolean b) {
    super(new HashSet<Coord>());
    this.val = b;
  }

  /**
   * Public constructor to create BooleanValue with specified dependents.
   * @param b input boolean to be stored.
   * @param d list of dependents.
   */
  public BooleanValue(boolean b, HashSet<Coord> d) {
    super(d);
    this.val = b;
  }

  /**
   * Copy constructor for a BooleanValue.
   *
   * @param b the BooleanValue to be copied.
   */
  public BooleanValue(BooleanValue b) {
    super(new HashSet<Coord>());
    this.val = b.val;
  }


  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return visitor.visitBooleanValue(this.val);
  }

  @Override
  public boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells) {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BooleanValue)) {
      return false;
    } else {
      BooleanValue other = (BooleanValue) o;
      return this.val == other.val;
    }
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.val);
  }

  @Override
  public String toString() {
    return Boolean.toString(this.val);
  }
}
