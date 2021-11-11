package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.function.Concat;
import edu.cs3500.spreadsheets.model.cell.function.FirstLessThan;
import edu.cs3500.spreadsheets.model.cell.function.Product;
import edu.cs3500.spreadsheets.model.cell.function.Sum;
import edu.cs3500.spreadsheets.model.cell.reference.Reference;
import edu.cs3500.spreadsheets.model.cell.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.value.NumberValue;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;
import edu.cs3500.spreadsheets.sexp.SSymbol;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Visitor for Sexp. Creates a Data based on the Sexp.
 */
public class CreateData implements SexpVisitor<Data> {


  @Override
  public Data visitBoolean(boolean b) {
    return new BooleanValue(b);
  }

  @Override
  public Data visitNumber(double d) {
    return new NumberValue(d);
  }

  @Override
  public Data visitSList(List<Sexp> l) {
    // Determine the functional element. (First Sexp should declare a function type).
    Sexp f = l.get(0);
    // Determines the arguments for the given Function.
    List<Data> args = new ArrayList<Data>();
    // adds each Sexp into list of Data to use as argument for function.
    for (Sexp element : l.subList(1, l.size())) {
      args.add(element.accept(new CreateData()));
    }
    // If block to determine the function
    if (f.equals(new SSymbol("SUM"))) {
      return new Sum(args);
    } else if (f.equals(new SSymbol("<"))) {
      if (args.size() == 2) {
        return new FirstLessThan(args.get(0), args.get(1));
      } else {
        throw new IllegalArgumentException("Must have 2 args only for <");
      }
    } else if (f.equals(new SSymbol("PRODUCT"))) {
      return new Product(args);
    } else if (f.equals(new SSymbol("CONCAT"))) {
      return new Concat(args);
    } else {
      throw new IllegalArgumentException("Invalid function name");
    }

  }

  @Override
  public Data visitSymbol(String s) {
    if (s.equals("SUM")
            || s.equals("PRODUCT")
            || s.equals("<")
            || s.equals("CONCAT")) {
      return new StringValue(s);
    } else {
      return new Reference(becomeReferenceList(s));
    }
  }

  /**
   * Private method to turn a String into a List of References.
   *
   * @param s String input.
   * @return List of Coordinates referenced.
   */
  private List<Coord> becomeReferenceList(String s) {
    List<Coord> result = new ArrayList<Coord>();
    if (s.contains(":")) {
      String refStart = s.substring(0, s.indexOf(":"));
      String refEnd = s.substring(s.indexOf(":"));
      Coord startCoord = new Coord(refStart);
      Coord endCoord = new Coord(refEnd);
      if (startCoord.getCol() > endCoord.getCol()
              || (startCoord.getRow() > endCoord.getRow())) {
        throw new IllegalArgumentException("Start and End are invalid");
      } else {
        for (int i = startCoord.getRow(); i < endCoord.getRow(); i++) {
          for (int k = startCoord.getCol(); k < endCoord.getCol(); k++) {
            result.add(new Coord(i, k));
          }
        }
      }
    } else {
      result.add(new Coord(s));
      return result;
    }
    return result;
  }


  @Override
  public Data visitString(String s) {
    return new StringValue(s);
  }
}
