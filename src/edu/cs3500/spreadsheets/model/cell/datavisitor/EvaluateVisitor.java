package edu.cs3500.spreadsheets.model.cell.datavisitor;

import java.util.HashMap;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.value.NumberValue;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.value.Value;

//TODO double check this cause it's sketchy

/**
 * Visitor for a function that turns any given data into a Value. Values are the new datatype that
 * we like to manipulate, essentially, converting the given Data to an 'atomic' structure (Value).
 */
public class EvaluateVisitor implements DataVisitor<Value> {

  @Override
  public Value visitBooleanValue(boolean b) {
    return new BooleanValue(b);
  }

  @Override
  public Value visitNumberValue(double d) {
    return new NumberValue(d);
  }

  @Override
  public Value visitStringValue(String s) {
    return new StringValue(s);
  }

  @Override
  public Value visitSum(List<Data> args, HashMap<Coord, Data> cells) {
    NumberValue result = new NumberValue(0.0); // default addition value is 0.0
    for (Data element : args) {
      result = result.add(element.accept(new EvaluateVisitor(), cells)
              .accept(new SumVisitor(), cells));
    }
    return result;
  }

  // Notably, if a non-numeric argument is passed in alone, will return NumberValue of 0.
  // If there is a list of arguments, but any argument is a number, will act as a 1.
  @Override
  public Value visitProduct(List<Data> args, HashMap<Coord, Data> cells) {
    NumberValue result = new NumberValue(0.0); // default multiplication value is 0.0
    for (Data element : args) {
      result = result.multiply(element.accept(new EvaluateVisitor(), cells)
              .accept(new ProductVisitor(), cells));
    }
    return result;
  }

  @Override
  public Value visitFirstLessThan(Data first, Data second, HashMap<Coord, Data> cells) {
    if (first == null || second == null) {
      throw new IllegalArgumentException("Invalid. Null arguments");
    }
    NumberValue firstNum = first.accept(new FirstLessThanVisitor(), cells);
    NumberValue secondNum = second.accept(new FirstLessThanVisitor(), cells);
    return firstNum.compare(secondNum);
  }


  @Override
  public Value visitConcat(List<Data> args, HashMap<Coord, Data> cells) {
    StringValue result = new StringValue("");
    for (Data element : args) {
      result = result.concat(element.accept(new EvaluateVisitor(), cells)
              .accept(new ConcatVisitor(), cells));
    }
    return result;
  }

  @Override
  public Value visitReference(List<Coord> references, HashMap<Coord, Data> cells) {
    if (references.size() == 1) {
      if (cells.get(references.get(0)) == null) {
        return new StringValue("null");
      }
      return cells.get(references.get(0)).accept(new EvaluateVisitor(), cells);
    } else {
      throw new IllegalArgumentException("Can't evaluate raw references");
    }
  }
}
