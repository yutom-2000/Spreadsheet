package edu.cs3500.spreadsheets.model.cell.function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * Object representing Product function.
 */
public class Product extends Function {
  List<Data> args;

  /**
   * Public constructor for Product object.
   *
   * @param args Arguments to be passed into the Object.
   */
  public Product(List<Data> args) {
    super(new HashSet());
    this.args = args;
  }

  /**
   * Copy constructor for a Product object.
   *
   * @param p the Product object to be copied.
   */
  public Product(Product p) {
    super(new HashSet<Coord>());
    this.args = p.args;
  }


  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return visitor.visitProduct(args, cells); //TODO
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
    String result = "=(PRODUCT ";
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
