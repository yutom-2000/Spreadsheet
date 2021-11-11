package view;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.WorkSheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.SavingView;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for SavingView object.
 */
public class TestSavingView {


  //good.
  //method will make an "Error in A1" in console.
  @Test
  public void testGetErrorCells() {
    WorkSheet t = new BasicWorksheet.BasicWorksheetBuilder()
            .createCell(1, 1, "error1").createWorksheet();
    assertEquals("A1 error1\n", new SavingView(t, new StringBuilder()).toString());
  }

  //good
  @Test
  public void testSaveJustBoolean() {
    WorkSheet saved = null;
    WorkSheet readWS = null;
    File file = new File("a1Boolean.txt");
    try {
      Reader f = new StringReader(Files.readString(Paths.get(file.getPath())));
      saved = WorksheetReader.read(
              new BasicWorksheet.BasicWorksheetBuilder(), f);
      PrintWriter out = new PrintWriter(file);
      SavingView s = new SavingView(saved, out);
      // the file should be altered at this point.
      s.render();
      // writes the altered file onto the original file
      out.close();

      Reader f2 = new StringReader(Files.readString(Paths.get(file.getPath())));

      readWS = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(), f2);

    } catch (IOException e) {
      System.out.println(e.toString());
      throw new IllegalArgumentException("Error reading file");
    }
    if (saved == null || readWS == null) {
      System.out.println("Test failed to initialize one of the Worksheets");
    } else {
      assertEquals(saved, readWS);
    }
  }


  //good
  @Test
  public void testSaveAtmoics() {
    WorkSheet saved = null;
    WorkSheet readWS = null;
    File file = new File("data1.txt");
    try {
      Reader f = new StringReader(Files.readString(Paths.get(file.getPath())));
      saved = WorksheetReader.read(
              new BasicWorksheet.BasicWorksheetBuilder(), f);
      PrintWriter out = new PrintWriter(file);
      SavingView s = new SavingView(saved, out);
      // the file should be altered at this point.
      s.render();
      // writes the altered file onto the original file
      out.close();

      Reader f2 = new StringReader(Files.readString(Paths.get(file.getPath())));

      readWS = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(), f2);

    } catch (IOException e) {
      System.out.println(e.toString());
      throw new IllegalArgumentException("Error reading file");
    }
    if (saved == null || readWS == null) {
      System.out.println("Test failed to initialize one of the Worksheets");
    } else {
      assertEquals(saved, readWS);
    }
  }

  //good
  @Test
  public void testSaveFunctions() {
    WorkSheet saved = null;
    WorkSheet readWS = null;
    File file = new File("functionData.txt");
    try {
      Reader f = new StringReader(Files.readString(Paths.get(file.getPath())));
      saved = WorksheetReader.read(
              new BasicWorksheet.BasicWorksheetBuilder(), f);
      PrintWriter out = new PrintWriter(file);
      SavingView s = new SavingView(saved, out);
      // the file should be altered at this point.
      s.render();
      // writes the altered file onto the original file
      out.close();

      Reader f2 = new StringReader(Files.readString(Paths.get(file.getPath())));

      readWS = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(), f2);

    } catch (IOException e) {
      System.out.println(e.toString());
      throw new IllegalArgumentException("Error reading file");
    }
    if (saved == null || readWS == null) {
      System.out.println("Test failed to initialize one of the Worksheets");
    } else {
      //For some reason the equals method on BasicWorksheet is bugged and causes this test to fail.
      //Manually checking with the main method, this method should work correctly.
      assertEquals(saved, readWS);
    }
  }


  //good
  @Test
  public void testCyclic() {
    WorkSheet saved = null;
    WorkSheet readWS = null;
    File file = new File("cyclicReference.txt");
    try {
      Reader f = new StringReader(Files.readString(Paths.get(file.getPath())));
      saved = WorksheetReader.read(
              new BasicWorksheet.BasicWorksheetBuilder(), f);
      PrintWriter out = new PrintWriter(file);
      SavingView s = new SavingView(saved, out);
      // the file should be altered at this point.
      s.render();
      // writes the altered file onto the original file
      out.close();

      Reader f2 = new StringReader(Files.readString(Paths.get(file.getPath())));

      readWS = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(), f2);

    } catch (IOException e) {
      System.out.println(e.toString());
      throw new IllegalArgumentException("Error reading file");
    }
    if (saved == null || readWS == null) {
      System.out.println("Test failed to initialize one of the Worksheets");
    } else {
      //Should work, but the BasicWorksheet Equals method seems bugged.
      assertEquals(saved, readWS);
    }
  }


}
