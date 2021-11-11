package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorkSheet;
import edu.cs3500.spreadsheets.model.cell.Data;

/**
 * View that renders a model into an appendable, then writes the appendable to the disk. The written
 * format should be the same as how the file is read, so that you can save, and open without
 * issues.
 */
//Note for testing: Read a file, render it back to a file, then read the output as a 2nd model
// to compare with the original.
public class SavingView implements IView {
  private final WorkSheet<?> model;
  private final Appendable out;

  public SavingView(WorkSheet<?> model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  @Override
  public void render() throws IOException {
    try {
      this.out.append(this.toString());
    } catch (Exception e) {
      throw new IOException();
    }
  }

  // There will be an extra newLine character at the end of the string... TODO optional fix
  @Override
  public String toString() {
    String result = "";
    for (Map.Entry<Coord, Data> e : model.getCells().entrySet()) {
      result += e.getKey().toString() + " " + e.getValue().toString() + "\n";
    }
    if (result.length() > 2) {
      if (result.substring(result.length() - 2).equals("\n")) {
        result = result.substring(0, result.length() - 2);
      }
    }
    if (this.model.getErroneousCells().equals("")) {
      return result;
    }
    else {
      result += this.model.getErroneousCells();
      return result;
    }
  }
}
