package edu.cs3500.spreadsheets.model.cell.datavisitor;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.function.FirstLessThan;
import edu.cs3500.spreadsheets.model.cell.function.Sum;
import edu.cs3500.spreadsheets.model.cell.reference.Reference;
import edu.cs3500.spreadsheets.model.cell.value.NumberValue;

/**
 * Visitor for FirstLessThan Function.
 */
public class FirstLessThanVisitor implements DataVisitor<NumberValue> {
  @Override
  public NumberValue visitBooleanValue(boolean b) {
    throw new IllegalArgumentException("< Function expects Number, but was given boolean.");
  }

  @Override
  public NumberValue visitNumberValue(double d) {
    return new NumberValue(d);
  }

  @Override
  public NumberValue visitStringValue(String s) {
    throw new IllegalArgumentException("< Function expects Number, but was given String.");
  }

  @Override
  public NumberValue visitSum(List<Data> args, HashMap<Coord, Data> cells) {
    return new Sum(args).accept(new EvaluateVisitor(), cells).accept(new SumVisitor(), cells);
  }

  @Override
  public NumberValue visitProduct(List<Data> args, HashMap<Coord, Data> cells) {
    throw new IllegalArgumentException("< Function expects a numeric argument, but recieved"
            + " a boolean.");
  }

  @Override
  public NumberValue visitFirstLessThan(Data first, Data second, HashMap<Coord, Data> cells) {
    return new FirstLessThan(first, second).accept(new EvaluateVisitor(), cells)
            .accept(new FirstLessThanVisitor(), cells);
  }

  @Override
  public NumberValue visitConcat(List<Data> args, HashMap<Coord, Data> cells) {
    throw new IllegalArgumentException("< Function expects Number, but was given String from "
            + "Concat function.");
  }

  @Override
  public NumberValue visitReference(List<Coord> references, HashMap<Coord, Data> cells) {
    return new Reference(references)
            .accept(new EvaluateVisitor(), cells)
            .accept(new FirstLessThanVisitor(), cells);
  }
}
