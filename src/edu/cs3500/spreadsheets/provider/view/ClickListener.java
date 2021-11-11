package edu.cs3500.spreadsheets.provider.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * A MouseAdapter that provides a MouseListener for a SpreadsheetGraphicalView.
 */
public class ClickListener extends MouseAdapter {

  private final SpreadsheetGraphicalView view;

  /**
   * Constructs a ClickListener that listens to the view, and responds to MouseEvents accordingly.
   *
   * @param view the view to be listened to.
   */
  public ClickListener(SpreadsheetGraphicalView view) {
    this.view = view;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (e.getX() > 50 && e.getY() > 70) {
      Coord c = new Coord((e.getX() - 50) / 100 + 1, (e.getY() - 70) / 30 + 1);
      view.updateSelectedCell(c);
    }
  }
}
