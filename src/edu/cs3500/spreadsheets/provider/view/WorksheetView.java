package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.controller.Features;

/**
 * An interface to view a WorksheetModel.
 */
public interface WorksheetView {

  /**
   * Display the view.
   */
  void display();

  /**
   * Update the view after an action has been performed.
   */
  void updateView(String cell, String value);

  /**
   * Add features to the view.
   *
   * @param features the features to be added
   */
  void addFeatures(Features features);

  /**
   * Get the currently selected cell's Coord.
   */
  Coord getSelectedCell();
}
