package edu.cs3500.spreadsheets.model;


import java.util.HashMap;

import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * A Mock model for testing.
 * Methods return String representations of the actions called.
 */
public class MockModel implements WorkSheet {
  public final StringBuilder log;

  /**
   * Public constructor for a MockModel object.
   * Defaults a new StringBuilder for logging actions called upon the model.
   */
  public MockModel() {
    this.log = new StringBuilder();
  }

  @Override
  public Object getDataAt(int col, int row) {
    this.log.append("getDataAt(" + col + ", " + row + ") ");
    return "getDataAt";
  }

  @Override
  public Data getCellAt(Coord c) {
    this.log.append("getCellAt(" + c.toString() + ") ");
    return new StringValue("");
  }

  @Override
  public int getCols() {
    this.log.append("getCols() ");
    return 0;
  }

  @Override
  public int getRows() {
    this.log.append("getRows() ");
    return 0;
  }

  @Override
  public HashMap<Coord, Data> getCells() {
    this.log.append("getCells() ");
    return new HashMap<Coord, Data>();
  }

  @Override
  public String getErroneousCells() {
    this.log.append("getErroneousCells() ");
    return "getErroneousCells()";
  }

  @Override
  public void writeCell(Coord pos, String d) {
    this.log.append("writeCell(" + pos + ", " + d + ") ");
  }

  @Override
  public void addRow() {
    this.log.append("addRow()");
  }

  @Override
  public void addColumn() {
    this.log.append("addColumn()");
  }

  @Override
  public HashMap<Coord, Value> getEvaluated() {
    this.log.append("getEvaluated() ");
    return new HashMap();
  }

  @Override
  public Value getEvaluatedAt(Coord c) {
    this.log.append("getEvaluatedAt(" + c.toString() + ") ");
    return null;
  }


}
