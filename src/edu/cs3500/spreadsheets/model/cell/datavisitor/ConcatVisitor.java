package edu.cs3500.spreadsheets.model.cell.datavisitor;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.function.Concat;
import edu.cs3500.spreadsheets.model.cell.reference.Reference;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;

/**
 * Visitor for Concat function.
 */
public class ConcatVisitor implements DataVisitor<StringValue> {
  @Override
  public StringValue visitBooleanValue(boolean b) {
    return new StringValue("");
  }

  @Override
  public StringValue visitNumberValue(double d) {
    return new StringValue("");
  }

  @Override
  public StringValue visitStringValue(String s) {
    return new StringValue(s);
  }

  @Override
  public StringValue visitSum(List<Data> args, HashMap<Coord, Data> cells) {
    return new StringValue("");
  }

  @Override
  public StringValue visitProduct(List<Data> args, HashMap<Coord, Data> cells) {
    return new StringValue("");
  }

  @Override
  public StringValue visitFirstLessThan(Data first, Data second, HashMap<Coord, Data> cells) {
    return new StringValue("");
  }

  @Override
  public StringValue visitConcat(List<Data> args, HashMap<Coord, Data> cells) {
    return new Concat(args).accept(new EvaluateVisitor(), cells).accept(new ConcatVisitor(), cells);
  }

  @Override
  public StringValue visitReference(List<Coord> references, HashMap<Coord, Data> cells) {
    return new Reference(references)
            .accept(new EvaluateVisitor(), cells).accept(new ConcatVisitor(), cells);
  }
}
