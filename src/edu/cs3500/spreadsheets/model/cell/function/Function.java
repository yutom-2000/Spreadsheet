package edu.cs3500.spreadsheets.model.cell.function;

import java.util.HashMap;
import java.util.Set;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.EvaluateVisitor;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Abstract class for Function type Data to hold dependents. Function Data represents an operation
 * as an object, storing the arguments for the function in it's fields.
 */
public abstract class Function implements Data {
  protected Set<Coord> dependents;

  /**
   * Public constructor for a Function. Called in subclasses to store a dependents.
   *
   * @param d the list of dependents.
   */
  public Function(Set<Coord> d) {
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
  public void addDependents(Set<Coord> e) {
    for (Coord d : e) {
      this.addDependents(d);
    }
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
