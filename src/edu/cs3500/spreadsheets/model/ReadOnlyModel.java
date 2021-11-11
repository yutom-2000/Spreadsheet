package edu.cs3500.spreadsheets.model;

import java.util.HashMap;

import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.value.Value;

/**
 * A read-only model for the view so that nothing can be changed.
 */
public class ReadOnlyModel implements ReadOnly {
  WorkSheet ws;

  public ReadOnlyModel(WorkSheet w) {
    this.ws = w;
  }

  @Override
  public Object getDataAt(int col, int row) {
    return ws.getDataAt(col, row);
  }

  @Override
  public Data getCellAt(Coord c) {
    return ws.getCellAt(c);
  }

  @Override
  public int getCols() {
    return ws.getCols();
  }

  @Override
  public int getRows() {
    return ws.getRows();
  }

  @Override
  public final HashMap<Coord, Data> getCells() {
    return ws.getCells();
  }

  @Override
  public final String getErroneousCells() {
    return ws.getErroneousCells();
  }

  @Override
  public void writeCell(Coord pos, String d) {
    //Do nothing. Is read-only.
  }

  @Override
  public void addRow() {
    //Do nothing. Is read-only.
  }

  @Override
  public void addColumn() {
    //Do nothing. Is read-only.
  }

  @Override
  public HashMap<Coord, Value> getEvaluated() {
    return ws.getEvaluated();
  }

  @Override
  public Value getEvaluatedAt(Coord c) {
    return ws.getEvaluatedAt(c);
  }

}
