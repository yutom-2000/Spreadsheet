package edu.cs3500.spreadsheets.model.cell.value;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.datavisitor.DataVisitor;

/**
 * A Class to represent a placeholder cell. When referenced, will store the dependencies onto
 * it's position, so that when a non-BlankData is written onto it, BlankData can transfer
 * dependencies onto it.
 */
public class BlankData extends Value {

  public BlankData(HashSet<Coord> d) {
    super(d);
  }


  /**
   * Returns null since it is a blankData.
   * @param visitor The visitor function to be applied to the Data.
   * @param cells cells from spreadsheet
   * @param <R> the return type
   * @return null, since is blankData.
   */
  @Override
  public <R> R accept(DataVisitor<R> visitor, HashMap<Coord, Data> cells) {
    return null;
  }

  /**
   * Returns false since blankData cannot be cyclic.
   * @param cells the field containing the spreadsheet data to find out other cells' values etc.
   * @param linkedCells a built list of cells that is passed down during each call, containing
   *                    all the Coords of referenced cells.
   * @return false, since blankData is not cyclic.
   */
  @Override
  public boolean isCyclic(HashMap<Coord, Data> cells, List<Coord> linkedCells) {
    return false;
  }

  @Override
  public String toString() {
    return "";
  }
}
