package edu.cs3500.spreadsheets.model;

import java.util.Objects;

/**
 * A value type representing coordinates in a {@link BasicWorksheet}.
 */
public class Coord {
  public final int row;
  public final int col;

  /**
   * Public constructor for Coord object, given col and row.
   * @param col given col index.
   * @param row given row index.
   */
  public Coord(int col, int row) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("Coordinates should be strictly positive");
    }
    this.row = row;
    this.col = col;
  }

  /**
   * Turns String into coord.
   * @param s String to turn into a Coord.
   */
  public Coord(String s) {
    String col = "";
    String row = "";
    for (int i = 0; i < s.length(); i++) {
      if (Character.isLetter(s.charAt(i))) {
        col += s.charAt(i);
      }
      else {
        row = s.substring(i);
        break;
      }
    }
    this.col = colNameToIndex(col);
    this.row = Integer.parseInt(row);
  }

  /**
   * Converts from the A-Z column naming system to a 1-indexed numeric value.
   * @param name the column name
   * @return the corresponding column index
   */
  public static int colNameToIndex(String name) {
    name = name.toUpperCase();
    int ans = 0;
    for (int i = 0; i < name.length(); i++) {
      ans *= 26;
      ans += (name.charAt(i) - 'A' + 1);
    }
    return ans;
  }

  /**
   * Converts a 1-based column index into the A-Z column naming system.
   * @param index the column index
   * @return the corresponding column name
   */
  public static String colIndexToName(int index) {
    StringBuilder ans = new StringBuilder();
    while (index > 0) {
      int colNum = (index - 1) % 26;
      ans.insert(0, Character.toChars('A' + colNum));
      index = (index - colNum) / 26;
    }
    return ans.toString();
  }

  @Override
  public String toString() {
    return colIndexToName(this.col) + this.row;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return row == coord.row
        && col == coord.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col);
  }

  /**
   * Getter method for col of a Coord.
   * @return int representing col position.
   */
  public int getCol() {
    return this.col;
  }

  /**
   * Getter method for row of a Coord.
   * @return int representing row position.
   */
  public int getRow() {
    return this.row;
  }

}
