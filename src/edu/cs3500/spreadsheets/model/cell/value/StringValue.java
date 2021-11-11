package edu.cs3500.spreadsheets.model.cell.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * Object to represent a String Value.
 */
public class StringValue extends Value {
  private String val;

  /**
   * Public constructor for StringValue object.
   *
   * @param s the String to be stored.
   */
  public StringValue(String s) {
    super(new HashSet<Coord>());
    this.val = s;
  }

  /**
   * Public constructor for StringValue object with specified dependents.
   * @param s String to be stored.
   * @param d dependents list.
   */
  public StringValue(String s, HashSet<Coord> d) {
    super(d);
    this.val = s;
  }

  /**
   * A Copy constructor for StringValue object.
   *
   * @param s the StringValue to be copied.
   */
  public StringValue(StringValue s) {
    super(new HashSet<Coord>());
    this.val = s.val;
  }

  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return visitor.visitStringValue(this.val);
  }

  @Override
  public boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells) {
    return false;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof StringValue)) {
      return false;
    } else {
      StringValue other = (StringValue) o;
      return this.val.equals(other.val);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.val);
  }

  /**
   * Concatenates this StringValue with given other StringValue.
   *
   * @param other other StringValue to concat with.
   * @return StringValue containing both strings concatenated.
   */
  public StringValue concat(StringValue other) {
    return new StringValue(this.val.concat(other.val));
  }

  @Override
  public String toString() {
    String result = "\"";
    for (Character c : this.val.toCharArray()) {
      if (c == '"') {
        result += "\\" + '"';
      } else if (c == '\\') {
        result += "\\\\";
      } else {
        result += c;
      }
    }
    result += "\"";
    return result;
  }
}
