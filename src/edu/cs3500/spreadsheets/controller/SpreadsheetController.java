package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorkSheet;
import edu.cs3500.spreadsheets.view.EditableSwingView;

/**
 * Implements user-control functionality to a spreadsheet given the model and view.
 */
public class SpreadsheetController extends MouseAdapter implements ActionListener {

  private WorkSheet<?> model;
  private EditableSwingView view;

  /**
   * Constructor for a Spreadsheet Controller.
   *
   * @param model spreadsheet moedel
   * @param view  spreadsheet view
   */
  public SpreadsheetController(WorkSheet<?> model, EditableSwingView view) {
    this.model = model;
    this.view = view;
    view.setListener(this);
    try {
      view.render();
    } catch (IOException e) {
      //nothing to do
    }
  }

  /**
   * Handles action events and performs the appropriate functionality.
   *
   * @param e the action event to be performed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      //rewrite the selected cell to the text in the toolbar
      case "Confirm":
        this.confirm();
        break;
      //reject the text in the toolbar and display the contents of the selected cell
      case "Reject":
        this.reject();
        break;
      //create a new row at the end of the spreadsheet
      case "Add Row":
        this.addRow();
        break;
      //create a new column at the end of the spreadsheet
      case "Add Column":
        this.addColumn();
        break;
      default:
        break;
    }
  }

  private void confirm() {
    Coord c = this.view.getSelectedCell();
    String s = this.view.getToolbarText();
    this.model.writeCell(c, s);
    this.view.updateModel(this.model);
  }

  private void reject() {
    Coord c = this.view.getSelectedCell();
    if (this.model.getCellAt(c) == null) {
      this.view.rewriteToolbar("");
    } else {
      this.view.rewriteToolbar(this.model.getCellAt(c).toString());
    }
  }

  private void addRow() {
    this.model.addRow();
    this.view.updateModel(this.model);
  }

  private void addColumn() {
    this.model.addColumn();
    this.view.updateModel(this.model);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    String s = this.model.getCellAt(this.view.getSelectedCell()).toString();
    this.view.rewriteToolbar(s);
  }
}
