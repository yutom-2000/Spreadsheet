package edu.cs3500.spreadsheets.model.cell.datavisitor;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.function.Product;
import edu.cs3500.spreadsheets.model.cell.function.Sum;
import edu.cs3500.spreadsheets.model.cell.reference.Reference;
import edu.cs3500.spreadsheets.model.cell.value.NumberValue;

/**
 * Visitor for products.
 */
public class ProductVisitor implements DataVisitor<NumberValue> {
  @Override
  public NumberValue visitBooleanValue(boolean b) {
    return new NumberValue(0.0);
  }

  @Override
  public NumberValue visitNumberValue(double d) {
    return new NumberValue(d);
  }

  @Override
  public NumberValue visitStringValue(String s) {
    return new NumberValue(0.0);
  }

  @Override
  public NumberValue visitSum(List<Data> args, HashMap<Coord, Data> cells) {
    NumberValue result = new Sum(args)
            .accept(new EvaluateVisitor(), cells).accept(new ProductVisitor(), cells);
    return result;
  }

  @Override
  public NumberValue visitProduct(List<Data> args, HashMap<Coord, Data> cells) {
    NumberValue result =
            new Product(args)
                    .accept(new EvaluateVisitor(), cells).accept(new ProductVisitor(), cells);
    return result;
  }

  @Override
  public NumberValue visitFirstLessThan(Data first, Data second, HashMap<Coord, Data> cells) {
    return new NumberValue(0.0);
  }

  @Override
  public NumberValue visitConcat(List<Data> args, HashMap<Coord, Data> cells) {
    return new NumberValue(0.0);
  }

  @Override
  public NumberValue visitReference(List<Coord> references, HashMap<Coord, Data> cells) {
    return new Reference(references)
            .accept(new EvaluateVisitor(), cells).accept(new ProductVisitor(), cells);
  }
}
