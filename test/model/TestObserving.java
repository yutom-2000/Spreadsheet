package model;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.CreateData;
import edu.cs3500.spreadsheets.model.WorkSheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.cell.Data;
import edu.cs3500.spreadsheets.model.cell.function.Sum;
import edu.cs3500.spreadsheets.model.cell.reference.Reference;
import edu.cs3500.spreadsheets.model.cell.value.BooleanValue;
import edu.cs3500.spreadsheets.model.cell.value.NumberValue;
import edu.cs3500.spreadsheets.sexp.Parser;

import static org.junit.Assert.assertEquals;


/**
 * Test class for the observing behavior of elements in a Spreadsheet.
 */
public class TestObserving {

  @Test
  public void testCreateCell() {
    Data expected = new BooleanValue(true);
    StringReader s = new StringReader("true");
    Data actual = Parser.parse("true").accept(new CreateData());
    assertEquals(expected, actual);
  }


  @Test
  public void testDependencies() {
    Data expectedSum = new Sum(new ArrayList(Arrays.asList(
            new NumberValue(1),
            new Reference(new ArrayList(Arrays.asList(new Coord(1, 3)))))));
    try {
      File file = new File("observing.txt");
      Reader f = new StringReader(Files.readString(Paths.get(file.getPath())));
      WorkSheet t = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(), f);
      assertEquals(expectedSum, t.getCells().get(new Coord(1, 2)));

      //see if A3 has A2 as it's dependent
      HashSet<Coord> expected = new HashSet<Coord>();
      expected.add(new Coord(1, 2)); //create dependent set
      HashMap<Coord, Data> a = t.getCells();
      Data b = a.get(new Coord(1, 3));
      Data c = a.get(new Coord(1, 2));
      assertEquals(expected, b.getDependents()); //check that dependent added correctly to value
      assertEquals(new HashSet<Coord>(), c.getDependents()); //check that the sum has no dependents
      assertEquals(new NumberValue(2), t.getDataAt(1, 2));

      //Testing for writing
      t.writeCell(new Coord(1, 3), "2"); //case where value is updated

      assertEquals(new NumberValue(2), t.getDataAt(1, 3));
      assertEquals(new NumberValue(3), t.getDataAt(1, 2));

      //Testing that newly written cells get dependents
      t.writeCell(new Coord(1, 1), "=(SUM A2 1)");
      HashSet<Coord> expected2 = new HashSet();
      expected2.add(new Coord(1, 1));
      a = t.getCells();
      Data d = a.get(new Coord(1, 2));
      Data e = a.get(new Coord(1, 1));
      assertEquals(expected2, d.getDependents());
      assertEquals(new NumberValue(4), t.getDataAt(1, 1));
      assertEquals(new HashSet(), e.getDependents());

      //Testing that newly Written cells get dependencies passed into them
      t.writeCell(new Coord(1, 3), "5");
      assertEquals(new NumberValue(6), t.getDataAt(1, 2));
      assertEquals(new NumberValue(7), t.getDataAt(1, 1));

    } catch (IOException e) {
      System.out.println(e);
    }


  }

  @Test
  public void testStringToData() {
    Sum expected = new Sum(new ArrayList(Arrays.asList(
            new Reference(new ArrayList(Arrays.asList(new Coord(1, 2)))),
            new NumberValue(1))));
    BasicWorksheet t = null;
    try {
      File file = new File("observing.txt");
      Reader f = new StringReader(Files.readString(Paths.get(file.getPath())));
      t = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(), f);
    } catch (IOException e) {
      System.out.println(e);
    }
    assertEquals(new NumberValue(1), t.stringToData("1"));
    assertEquals(expected, t.stringToData("=(SUM A2 1)"));
  }
}
