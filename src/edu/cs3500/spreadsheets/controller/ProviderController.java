package edu.cs3500.spreadsheets.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorkSheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.cell.value.Value;
import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.view.WorksheetView;
import edu.cs3500.spreadsheets.view.SavingView;

/**
 * A Controller to implement provider's controller interface, Features. Holds only a model since the
 * view will call upon controller to manipulate model.
 */
public class ProviderController implements Features {
  private WorkSheet model;
  private WorksheetView view;

  /**
   * Public controller for a Features interface.
   * @param model the model to be passed into the Controller
   * @param view the view to be used
   */
  public ProviderController(WorkSheet model, WorksheetView view) {
    this.model = model;
    this.view = view;
  }


  @Override
  public void setView(WorksheetView v) {
    this.view = v;
    Set<Map.Entry<Coord, Value>> cells = this.model.getEvaluated().entrySet();
    int i = 0;
    for (Map.Entry<Coord, Value> e : cells) {
      v.updateView(e.getKey().toString(), e.getValue().toString());
      System.out.println(i);
      i++;
    }
  }

  @Override
  public void addCell(String cell, Object value) {
    //add to model
    Coord temp = cellParse(cell);
    String v = value.toString();
    model.writeCell(temp, v);

    //add to view
    view.updateView(cell, model.getEvaluatedAt(temp).toString());
  }

  @Override
  public void changeCell(String cell, Object value) {
    //add to model
    Coord temp = cellParse(cell);
    model.writeCell(temp, value.toString());

    //add to view
    view.updateView(cell, model.getEvaluatedAt(temp).toString());
  }

  @Override
  public void save(String fileName) {
    try {
      PrintWriter out = new PrintWriter(fileName);
      new SavingView(model, out).render();
      out.close();
      System.out.println("Save success");
    } catch (IOException e) {
      System.out.println("Error in saving: " + e.toString());
      //Do not mutate anything.
    }
  }

  @Override
  public void load(String fileName) {
    try {
      File file = new File(fileName);
      Reader f = new FileReader(Files.readString(Paths.get(file.getPath())));
      this.model = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(), f);
    } catch (IOException e) {
      System.out.println("Can't load: " + e.toString());
      //Do not mutate anything.
    }
  }

  @Override
  public void deleteCell(String cell) {
    this.model.getEvaluated().remove(cellParse(cell));
    this.model.getCells().remove(cellParse(cell));
  }

  //Static method to convert String to Coord.
  private static Coord cellParse(String cell) {
    if (!cell.matches("([A-Z])*\\d+")) {
      throw new IllegalArgumentException("Incorrect cell coordinate");
    } else {
      String[] s = cell.split("(?<=[A-Z])(?=[0-9])");
      return new Coord(Coord.colNameToIndex(s[0]), Integer.parseInt(s[1]));
    }
  }

}
