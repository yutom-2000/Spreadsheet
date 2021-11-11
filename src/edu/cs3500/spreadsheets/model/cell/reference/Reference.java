package edu.cs3500.spreadsheets.model.cell.reference;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;
import edu.cs3500.spreadsheets.model.cell.datavisitor.EvaluateVisitor;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * Object that represents an Interface.
 */
public class Reference implements Data {
  private Coord position;
  private List<Coord> references;
  private Set<Coord> dependents;

  /**
   * Public constructor for a Reference.
   * @param r the references this reference points to.
   * @param p the position of this reference.
   */
  public Reference(List<Coord> r, Coord p) {
    this.position = p;
    this.references = r;
    this.dependents = new HashSet<Coord>();
  }

  /**
   * Constructor with specified dependents.
   * @param r list of reference this reference points to
   * @param p the position of this reference.
   * @param d the dependents passed into this reference.
   */
  public Reference(List<Coord> r, Coord p, Set<Coord> d) {
    this.dependents = d;
    this.position = p;
    this.references = r;
  }

  /**
   * Temporary constructor for a reference (to work with createCell and comply with SexpVisitor).
   * @param r the references this reference points to.
   */
  public Reference(List<Coord> r) {
    this.references = r;
    this.position = null;
    this.dependents = new HashSet<Coord>();
  }


  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return visitor.visitReference(this.references, cells);
  }

  @Override //TODO: Implement correctly
  public boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells) {
    for (Coord e : this.references) {
      if (linkedCells.contains(e)) {
        return true;
      }
      else {
        if (cells.get(e) instanceof Reference) {
          Reference other = (Reference) cells.get(e);
          for (Coord d : other.references) {
            if (linkedCells.contains(d)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  @Override
  public void addDependents(Set<Coord> d) {
    this.dependents.addAll(d);
  }

  @Override
  public void addDependents(Coord d) {
    this.dependents.add(d);
  }

  @Override
  public Set<Coord> getDependents() {
    return this.dependents;
  }

  @Override
  public void makeDependent(Coord d, HashMap<Coord, Data> cells) {
    for (Coord e : this.references) {
      Data temp = cells.get(e);
      temp.addDependents(d);
    }
  }

  public Coord getReferenced() {
    return references.get(0);
  }

  @Override
  public boolean equals(Object o) {
    boolean result = true;
    if (!(o instanceof Reference)) {
      return false;
    }
    else {
      Reference other = (Reference) o;
      if (this.references.size() == other.references.size()) {
        for (Coord a : this.references) {
          for (Coord b : other.references) {
            result = result && (a.equals(b));
          }
        }
        return result;
      }
      else {
        return false;
      }
    }
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.references);
  }

  @Override
  public String toString() {
    String result = "(";
    for (Coord e : this.references) {
      result += e.toString() + ", ";
    }
    if (result.substring(result.length() - 2).equals(", ")) {
      result = result.substring(0, result.length() - 2);
    }
    result = result + ")";
    return result;
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
