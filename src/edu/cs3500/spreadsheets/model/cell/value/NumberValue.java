package edu.cs3500.spreadsheets.model.cell.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * Object to represent a Numeric Value (as a double).
 */
public class NumberValue extends Value {
  private double val;

  /**
   * Public constructor for NumberValue.
   *
   * @param d input double to be stored.
   */
  public NumberValue(double d) {
    super(new HashSet<Coord>());
    this.val = d;
  }

  /**
   * Public constructor for NumberValue with specified dependents.
   * @param d input double to be stored.
   * @param e list of dependents.
   */
  public NumberValue(double d, HashSet<Coord> e) {
    super(e);
    this.val = d;
  }

  /**
   * Copy constructor for NumberValue object.
   *
   * @param d the NumberValue to be copied.
   */
  public NumberValue(NumberValue d) {
    super(new HashSet());
    this.val = d.val;
  }


  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return visitor.visitNumberValue(this.val);
  }

  @Override
  public boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells) {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof NumberValue)) {
      return false;
    } else {
      NumberValue other = (NumberValue) o;
      return (0.0001 > Math.abs(this.val - other.val));
    }
  }

  @Override
  public int hashCode() {
    return 1;
  }

  /**
   * Facilitates addition between 2 NumberValues.
   *
   * @param other The other NumberValue.
   * @return NumberValue representing combined value of both NumberValues.
   */
  public NumberValue add(NumberValue other) {
    return new NumberValue(this.val + other.val);
  }

  /**
   * Facilitates multiplication between 2 NumberValues.
   *
   * @param other The other NumberValue.
   * @return NumberValue representing combined value of both NumberValues.
   */
  public NumberValue multiply(NumberValue other) {
    if (Math.abs(this.val) < 0.0001) {
      this.val = 1.0;
    }
    if (Math.abs(other.val) < 0.0001) {
      other.val = 1.0;
    }
    return new NumberValue(this.val * other.val);
  }

  /**
   * Facilitates comparison between 2 NumberValues.
   *
   * @param other the other NumberValue.
   * @return BooleanValue representing the comparison between the NumberValues.
   */
  public BooleanValue compare(NumberValue other) {
    return new BooleanValue(other.val > this.val);
  }

  @Override
  public String toString() {
    return Double.toString(this.val);
  }
}
