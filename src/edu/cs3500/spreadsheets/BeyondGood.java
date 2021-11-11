package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import edu.cs3500.spreadsheets.controller.ProviderController;
import edu.cs3500.spreadsheets.controller.SpreadsheetController;
import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.CreateData;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.cell.reference.Reference;
import edu.cs3500.spreadsheets.model.cell.value.Value;
import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.view.SpreadsheetGraphicalView;
import edu.cs3500.spreadsheets.provider.view.WorksheetView;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.view.EditableSwingView;
import edu.cs3500.spreadsheets.view.SavingView;
import edu.cs3500.spreadsheets.view.SwingView;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   *
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    try {
      BasicWorksheet model;
      //First block to handle reading.
      if (args[0].equals("-in")) {
        File file = new File(args[1]);
        Reader f = new StringReader(Files.readString(Paths.get(file.getPath())));
        if (file.exists()) {
          model = WorksheetReader.read(
                  new BasicWorksheet.BasicWorksheetBuilder(), f);

          //Second block to handle evaluation.
          if (args[2].equals("-eval")) {
            if (Parser.parse(args[3]).accept(new CreateData()) instanceof Reference) {
              Reference r = (Reference) Parser.parse(args[3]).accept(new CreateData());
              Value result = model.getEvaluated().get(r.getReferenced());
              if (result == null) {
                System.out.println("null");
              } else {
                System.out.println(result.toString());
              }
            } else {
              throw new IllegalArgumentException("Not a reference");
            }
          } else if (args[2].equals("-save")) {
            PrintWriter out = new PrintWriter(file);
            new SavingView(model, out).render();
            out.close();
          } else if (args[2].equals("-gui")) {
            new SwingView(model).render();
          } else if (args[2].equals("-edit")) {
            new SpreadsheetController(model, new EditableSwingView(model));
          } else if (args[2].equals("-provider")) {
            //create new provider controller?
            WorksheetView v = new SpreadsheetGraphicalView();
            Features feat = new ProviderController(model, v);
            v.addFeatures(feat);
            feat.setView(v);
            v.display();
            //new SpreadsheetGraphicalView().display();
          } else {
            throw new IllegalArgumentException("Malformed command line. 2nd argument should be "
                    + "either \"-eval\", \"-save\", or \"-gui\". Recieved: \""
                    + args[2] + "\"");
          }
        } else {
          throw new IllegalArgumentException("File not found in directory");
        }
      } else if (args[0].equals("-gui")) {
        // blank model instantiated.
        model = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(),
                new StringReader(""));
        new SwingView(model).render();
      } else if (args[0].equals("-edit")) {
        // blank model instantiated.
        model = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(),
                new StringReader(""));
        new SpreadsheetController(model, new EditableSwingView(model));
      } else if (args[0].equals("-provider")) {
        new SpreadsheetGraphicalView().display();
      }
      else {
        throw new IllegalArgumentException("Invalid command line");
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Malformed file");
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("Malformed command line, missing certain arguments. "
      + e.toString());
    }


  }

}


//  WorkSheet model;
//    for (int i = 0; i < args.length; i++) {
//        // If statement block handles reading a file.
//        if (args[i].equals("-in")) {
//        try {
//        File file = new File(args[i + 1]);
//        Reader f = new StringReader(Files.readString(Paths.get(file.getPath())));
//        model = WorksheetReader.read(
//        new BasicWorksheet.BasicWorksheetBuilder<BasicWorksheet>(), f);
//        } catch (IOException notFound) {
//        throw new IllegalArgumentException("File cannot be read.");
//        }
//        }
//        if (args[i].equals("-eval")) {
//
//        }
//        }
