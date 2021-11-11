package edu.cs3500.spreadsheets.view;

import java.io.IOException;

import javax.swing.JFrame;

import edu.cs3500.spreadsheets.model.WorkSheet;

/**
 * View that uses the Java Swing library to render a GUI view of a spreadsheet.
 */
public class SwingView extends JFrame implements IView {

  /**
   * Constructor for SwingView.
   * @param model the model to be rendered
   */
  public SwingView(WorkSheet<?> model) {
    setSize(500, 500);
    setLocation(200, 200);
    add(new SpreadsheetPanel(model));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void render() throws IOException {
    try {
      this.setVisible(true);
    } catch (Exception e) {
      throw new IOException();
    }
  }
}
