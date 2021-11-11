package edu.cs3500.spreadsheets.model.cell.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.EvaluateVisitor;

/**
 * Abstract class for value type Data, since all hold a list of dependents. Represents either a
 * boolean, double, or a String. Represents the "Atomic" case of our cell data.
 */
public abstract class Value implements Data {
  protected HashSet<Coord> dependents;

  /**
   * Hold a field for dependents.
   *
   * @param d List of Coords representing position of dependents.
   */
  public Value(HashSet<Coord> d) {
    this.dependents = d;
  }

  @Override
  public Set<Coord> getDependents() {
    return this.dependents;
  }

  @Override
  public void addDependents(Coord e) {
    this.dependents.add(e);

  }

  @Override
  public void addDependents(Set<Coord> d) {
    for (Coord e : d) {
      this.addDependents(e);
    }
  }

  @Override
  public void makeDependent(Coord d, HashMap<Coord, Data> cells) {
    this.dependents.add(d);
  }

  @Override
  public void update(HashMap<Coord, Data> cells, HashMap<Coord, Value> evaluated) {
    for (Coord e : this.dependents) {
      Value updated = cells.get(e).accept(new EvaluateVisitor(), cells);
      evaluated.put(e, updated);
      cells.get(e).update(cells, evaluated);
    }
  }

}
