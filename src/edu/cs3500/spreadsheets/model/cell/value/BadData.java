package edu.cs3500.spreadsheets.model.cell.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * A Data representing a malformed input. as a Data object.
 */
public class BadData extends Value {
  private String input;

  public BadData(String in) {
    super(new HashSet<Coord>());
    this.input = in;
  }

  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return null;
  }

  @Override
  public boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells) {
    return false;
  }

  @Override
  public String toString() {
    return input;
  }
}
