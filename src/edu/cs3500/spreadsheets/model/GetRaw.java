package edu.cs3500.spreadsheets.model;

import java.util.List;

import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

// MADE REDUNDANT SINCE THIS IS JUST MIRROR OF Sexp toString().... :(
/**
 * Visitor to return the raw String read from a file to result in the given Sexp.
 */
public class GetRaw implements SexpVisitor<String> {
  @Override
  public String visitBoolean(boolean b) {
    if (b) {
      return "true";
    } else {
      return "false";
    }
  }

  @Override
  public String visitNumber(double d) {
    return Double.toString(d);
  }

  @Override
  public String visitSList(List<Sexp> l) {
    String result = "(";
    for (int i = 0; i < l.size(); i++) {
      result += l.get(i).accept(new GetRaw()) + " ";
    }
    if (result.length() > 1) {
      if (result.substring(result.length() - 1).equals(" ")) {
        result = result.substring(0, result.length() - 1);
      }
    }
    result += ")";
    return result;
  }

  @Override
  public String visitSymbol(String s) {
    return s;
  }

  @Override
  public String visitString(String s) {
    String result = "\"";
    result += s;
    result += "\"";
    return result;
  }
}
