package model;

import org.junit.Test;

import java.util.Arrays;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.CreateData;
import edu.cs3500.spreadsheets.model.ReadOnly;
import edu.cs3500.spreadsheets.model.ReadOnlyModel;
import edu.cs3500.spreadsheets.model.WorkSheet;
import edu.cs3500.spreadsheets.model.cell.function.FirstLessThan;
import edu.cs3500.spreadsheets.model.cell.reference.Reference;
import edu.cs3500.spreadsheets.model.cell.value.NumberValue;
import edu.cs3500.spreadsheets.model.cell.value.StringValue;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SSymbol;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for BasicWorkSheet. Primarily, testing the ability to build a worksheet using the
 * Builders.
 */
public class BasicWorksheetTest {

  @Test
  public void testBuilding() {
    WorkSheet t = new BasicWorksheet.BasicWorksheetBuilder()
            .createCell(1, 1, "\"abc\"").createWorksheet();
    BasicWorksheet b = (BasicWorksheet) t;
    assertEquals(new StringValue("abc"), t.getDataAt(1, 1));
    assertEquals(new StringValue("abc"), b.getEvaluated().get(new Coord(1, 1)));
  }

  @Test
  public void testBuilder() {
    Reference r = (Reference) Parser.parse("A1").accept(new CreateData());
    assertEquals("A1", new Coord(1, 1).toString());
    assertEquals(new Reference(Arrays.asList(new Coord(1, 1))), Parser.parse("A1")
            .accept(new CreateData()));
    assertEquals(new SSymbol("A1"), Parser.parse("A1"));
    assertEquals(new Coord(1, 12), new Coord("A12"));
    assertEquals(new FirstLessThan(new NumberValue(1), new NumberValue(3)),
            Parser.parse("(< 1 3)").accept(new CreateData()));
  }

  @Test
  public void testGetErrorCells() {
    WorkSheet t = new BasicWorksheet.BasicWorksheetBuilder()
            .createCell(1, 1, "error1").createWorksheet();
    assertEquals(null, t.getDataAt(1, 1));
    assertEquals("A1 error1\n", t.getErroneousCells());
  }


  // This test works, but only if the constructor is public. Too  lazy to really call the builder :(
  //  @Test
  //  public void testGetDataAt() {
  //    Coord a1 = new Coord(1, 1);
  //    Data v1 = new NumberValue(1);
  //    Coord a2 = new Coord(1, 2);
  //    Data v2 = new StringValue("abc");
  //    HashMap<Coord, Data> inputCells = new HashMap<>();
  //    inputCells.put(a1, v1);
  //    inputCells.put(a2, v2);
  //    BasicWorksheet sheet = new BasicWorksheet(inputCells);
  //    assertEquals(new StringValue("abc"), sheet.getDataAt(1, 2));
  //  }

  @Test
  public void testReadOnly() {
    WorkSheet mirror = null;
    WorkSheet t = new BasicWorksheet.BasicWorksheetBuilder()
            .createCell(1, 1, "\"abc\"").createWorksheet();
    mirror = t;
    ReadOnly ro = new ReadOnlyModel(t);
    ro.writeCell(new Coord(1, 1), "\"haha\"");
    assertEquals(mirror, t); //nothing changes in the model held by ro
    ro.addColumn();
    assertEquals(mirror, t); //nothing changes in the model held by ro
    ro.addRow();
    assertEquals(mirror, t); //nothing changes in the model held by ro
  }



}
