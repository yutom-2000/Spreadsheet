package edu.cs3500.spreadsheets.view;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyModel;
import edu.cs3500.spreadsheets.model.WorkSheet;

/**
 * View that uses Java Swing library to render a GUI view of a spreadsheet
 * in which the cells can be edited.
 */
public class EditableSwingView extends JFrame implements IView {

  private WorkSheet<?> model;
  private edu.cs3500.spreadsheets.view.SpreadsheetPanel sheet;
  private final JTextField toolbar;
  private final JButton confirmButton;
  private final JButton rejectButton;
  private final JButton addRowButton;
  private final JButton addColButton;

  /**
   * Constructor for an EditableSwingView.
   * @param model the model to be rendered
   */
  public EditableSwingView(WorkSheet<?> model) {
    this.model = new ReadOnlyModel(model);
    this.sheet = new edu.cs3500.spreadsheets.view.SpreadsheetPanel(this.model);
    this.setSize(500, 560);
    this.setLocation(600, 250);
    this.setLayout(new FlowLayout());


    //confirm button
    this.confirmButton = new JButton("✔");
    this.add(this.confirmButton);
    this.confirmButton.setActionCommand("Confirm");

    //reject button
    this.rejectButton = new JButton("✖");
    this.add(this.rejectButton);
    this.rejectButton.setActionCommand("Reject");

    //toolbar
    this.toolbar = new JTextField(36);
    this.add(this.toolbar);

    //spreadsheet
    this.add(this.sheet);

    //add row button
    this.addRowButton = new JButton("Add Row");
    this.add(this.addRowButton);
    this.addRowButton.setActionCommand("Add Row");

    //add col button
    this.addColButton = new JButton("Add Column");
    this.add(this.addColButton);
    this.addColButton.setActionCommand("Add Column");

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /**
   * Adds the action listener to all of the buttons in the view.
   * @param listener the action listener
   */
  public void setListener(ActionListener listener) {
    this.confirmButton.addActionListener(listener);
    this.rejectButton.addActionListener(listener);
    this.addRowButton.addActionListener(listener);
    this.addColButton.addActionListener(listener);
  }


  /**
   * Rewrites the text in the toolbar to the given string.
   * @param s given string
   */
  public void rewriteToolbar(String s) {
    this.toolbar.setText(s);
  }

  /**
   * Finds the coordinates of the cell that is selected in this view.
   * @return coordinates of the selected cell
   */
  public Coord getSelectedCell() {
    return this.sheet.getSelectedCell();
  }

  /**
   * Gets the text written into the toolbar.
   * @return the text in the toolbar as a string
   */
  public String getToolbarText() {
    return this.toolbar.getText();
  }

  /**
   * Updates the model for this view to the given model.
   * @param model given model
   */
  public void updateModel(WorkSheet<?> model) {
    this.model = new ReadOnlyModel(model);
    //TODO: ???
    //this.sheet = new SpreadsheetPanel(this.model);
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
