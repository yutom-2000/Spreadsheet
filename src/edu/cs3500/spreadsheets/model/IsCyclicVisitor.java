package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * Visitor to determine if a data is cyclic.
 */
public class IsCyclicVisitor implements DataVisitor<Boolean> {
  @Override
  public Boolean visitBooleanValue(boolean b) {
    return false;
  }

  @Override
  public Boolean visitNumberValue(double d) {
    return false;
  }

  @Override
  public Boolean visitStringValue(String s) {
    return false;
  }

  @Override
  public Boolean visitSum(List<Data> args, HashMap<Coord, Data> cells) {
    boolean result = false;
    for (Data e : args) {
      result = result || e.accept(new IsCyclicVisitor(), cells);
    }
    return result;
  }

  @Override
  public Boolean visitProduct(List<Data> args, HashMap<Coord, Data> cells) {
    boolean result = false;
    for (Data e : args) {
      result = result || e.accept(new IsCyclicVisitor(), cells);
    }
    return result;
  }

  @Override
  public Boolean visitFirstLessThan(Data first, Data second, HashMap<Coord, Data> cells) {
    return first.accept(new IsCyclicVisitor(), cells)
            || second.accept(new IsCyclicVisitor(), cells);
  }

  @Override
  public Boolean visitConcat(List<Data> args, HashMap<Coord, Data> cells) {
    boolean result = false;
    for (Data e : args) {
      result = result || e.accept(new IsCyclicVisitor(), cells);
    }
    return result;
  }

  @Override
  public Boolean visitReference(List<Coord> references, HashMap<Coord, Data> cells) {
    for (int a = 0; a < references.size(); a++) {
      for (int b = a + 1; b < references.size(); b++) {
        if (references.get(a).equals(references.get(b))) {
          return true;
        }
      }
    }
    return false;
  }
}
