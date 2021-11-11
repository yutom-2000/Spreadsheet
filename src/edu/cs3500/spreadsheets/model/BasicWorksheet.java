package edu.cs3500.spreadsheets.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.EvaluateVisitor;
import edu.cs3500.spreadsheets.model.cell.value.BadData;
import edu.cs3500.spreadsheets.model.cell.value.BlankData;
import edu.cs3500.spreadsheets.model.cell.value.Value;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

// PRINTING ERRORS IMPLEMENTED HERE

/**
 * A basic implementation of a Worksheet.
 */
public class BasicWorksheet implements WorkSheet<Value> {
  private HashMap<Coord, Data> cells;
  private HashMap<Coord, Value> evaluatedCells;
  private HashMap<Coord, Data> errorCells;

  /**
   * Private constructor for a new BasicWorksheet object. To be called by builder.
   *
   * @param cells  the cells.
   * @param errors the erroneous cells.
   */
  private BasicWorksheet(HashMap<Coord, Data> cells, HashMap<Coord, Data> errors) {
    this.cells = cells;
    this.evaluatedCells = new HashMap<>();
    this.evaluateCells();
    this.errorCells = errors;
  }



  @Override
  public Value getDataAt(int col, int row) {
    Coord position = new Coord(col, row);
    return this.evaluatedCells.get(position);
  }

  @Override
  public Data getCellAt(Coord c) {
    return this.cells.get(c);
  }

  @Override
  public int getCols() {
    int result = 0;
    for (Map.Entry<Coord, Data> e : this.cells.entrySet()) {
      if (e.getKey().getCol() > result) {
        result = e.getKey().getCol();
      }
    }
    return result;
  }

  @Override
  public int getRows() {
    int result = 0;
    for (Map.Entry<Coord, Data> e : this.cells.entrySet()) {
      if (e.getKey().getRow() > result) {
        result = e.getKey().getRow();
      }
    }
    return result;
  }

  @Override
  public HashMap<Coord, Data> getCells() {
    HashMap<Coord, Data> result = new HashMap<>();
    for (Map.Entry<Coord, Data> e : this.cells.entrySet()) {
      result.put(e.getKey(), e.getValue());
    }
    return result;
  }

  @Override
  public String getErroneousCells() {
    String result = "";
    for (Map.Entry<Coord, Data> e : this.errorCells.entrySet()) {
      result += e.getKey().toString() + " ";
      result += e.getValue().toString();
      result += "\n";
    }
    return result;
  }

  @Override
  public void writeCell(Coord pos, String d) {
    Data originalData; //store original data
    Data newData; //store the created data from String d
    Set<Coord> dependents; //store the dependents of original data
    Value updated; //variable to store re-evaluated result of dependents to put into evaluatedCells

    newData = this.stringToData(d);
    if (!newData.isCyclic(this.cells, new ArrayList(Arrays.asList(pos)))) {

      originalData = this.cells.get(pos);

      try {
        if (originalData == null) {
          if (newData != null) {
            if (newData.isCyclic(this.cells, new ArrayList(Arrays.asList(pos)))) {
              //don't mutate from original, just print error.
              System.out.println("new data is cyclic. Cannot be added.");
            } else { //case that the new data is not cyclic
              this.cells.put(pos, newData); //add data to cells
              //put evaluated value of new data in the field evaluatedCells
              this.evaluatedCells.put(pos, newData.accept(new EvaluateVisitor(), this.cells));

              //Add own dependents
              newData.makeDependent(pos, this.cells);
              newData.getDependents().remove(pos);
            }
          }
        } else { //in case of overwriting an existing data, thus editing it.
          dependents = originalData.getDependents(); //keep the originalData's dependents,
          // for new one
          this.cells.put(pos, newData); //add the new data in the position
          this.cells.get(pos).addDependents(dependents); //add the original's dependents to the new
          //Put new value over old Value in evaluatedCells
          this.evaluatedCells.put(pos, newData.accept(new EvaluateVisitor(), this.cells));
          newData.makeDependent(pos, this.cells);
          newData.getDependents().remove(pos);
          newData.update(this.cells, this.evaluatedCells);
        }
      } catch (Exception e) { //catch statement for any problems that may arise during the operation
        System.out.println("Invalid Operation");
      }
    }
    else {
      System.out.println("Cannot make a cyclic cell");
    }
  }

  @Override
  public void addRow() {
    int temp = this.getRows();
    this.cells.put(new Coord(1, temp), new BlankData(new HashSet<Coord>()));
    this.evaluatedCells.put(new Coord(1, temp), new BlankData(new HashSet<Coord>()));
  }

  @Override
  public void addColumn() {
    int temp = this.getCols();
    this.cells.put(new Coord(temp, 1), new BlankData(new HashSet<Coord>()));
    this.evaluatedCells.put(new Coord(temp, 1), new BlankData(new HashSet<Coord>()));
  }



  /**
   * Builder for BasicWorksheet.
   */
  public static class BasicWorksheetBuilder
          implements WorksheetReader.WorksheetBuilder<BasicWorksheet> {
    private HashMap<Coord, Data> cells;
    private HashMap<Coord, Data> errorCells;

    public BasicWorksheetBuilder() {
      this.cells = new HashMap<Coord, Data>();
      this.errorCells = new HashMap<Coord, Data>();
    }

    @Override
    public WorksheetReader.WorksheetBuilder<BasicWorksheet> createCell(int col, int row,
                                                                       String contents) {

      String input = "";
      if (contents.substring(0, 1).equals("=")) {
        input = contents.substring(1);
      }
      else {
        input = contents;
      }
      //Return Sexp representing parsed inputs.
      Coord position = new Coord(col, row);
      try {
        Sexp expression = Parser.parse(input);
        Data subject = expression.accept(new CreateData());
        if (subject.isCyclic(this.cells, new ArrayList(Arrays.asList(position)))) {
          this.errorCells.put(position, new BadData(contents));
          throw new IllegalArgumentException("Error in " + position.toString() + " Cyclic Data");

        }
        //add dependents to any Data this Data depends upon.
        subject.makeDependent(position, this.cells);
        subject.getDependents().remove(position);
        cells.put(position, subject);
        return this;
      } catch (IllegalArgumentException e) {
        this.errorCells.put(position, new BadData(contents));
        System.out.println("Error in " + position.toString() + " " + e.toString());
        return this;
      }
    }

    @Override
    public BasicWorksheet createWorksheet() {
      BasicWorksheet result = new BasicWorksheet(this.cells, this.errorCells);
      return result;
    }
  }

  /**
   * Getter for Cells.
   *
   * @return this.cells.
   */
  public HashMap<Coord, Value> getEvaluated() {
    return this.evaluatedCells;
  }

  @Override
  public Value getEvaluatedAt(Coord c) {
    return this.evaluatedCells.get(c);
  }

  /**
   * Populates the evaluatedCells field by putting in evaluated cells.
   */
  private void evaluateCells() {
    for (Map.Entry<Coord, Data> e : this.cells.entrySet()) {
      this.evaluatedCells.put(e.getKey(), e.getValue().accept(new EvaluateVisitor(), this.cells));
    }
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof BasicWorksheet)) {
      return false;
    } else {
      BasicWorksheet other = (BasicWorksheet) o;
      return this.cells.equals((other.cells))
              && this.evaluatedCells.equals(other.evaluatedCells)
              && this.errorCells.equals(other.errorCells);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.cells) + Objects.hash(this.evaluatedCells)
            + Objects.hash(this.errorCells);
  }

  /**
   * Private helper method for turning Strings into data while printing errors.
   * @param s String to convert into Data.
   * @return Data representation of the string.
   */
  public Data stringToData(String s) {
    String input;
    if (s.substring(0, 1).equals("=")) {
      input = s.substring(1);
    }
    else {
      input = s;
    }
    try {
      Sexp expression = Parser.parse(input);
      Data subject = expression.accept(new CreateData());
      return subject;
    } catch (IllegalArgumentException e) {
      System.out.println("Cannot write this Data here. Invalid input. " + e);
      return null;
    }
  }
}

