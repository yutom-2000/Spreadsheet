package edu.cs3500.spreadsheets.provider.view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import edu.cs3500.spreadsheets.provider.controller.Features;

/**
 * A KeyAdapter that provides a KeyListener for a SpreadsheetGraphicalView.
 */
public class KeyboardListener extends KeyAdapter {

  private final SpreadsheetGraphicalView view;
  private final Features feat;

  /**
   * Constructs a KeyboardListener that listens to the view, and responds to the KeyEvents
   * accordingly.
   *
   * @param view the view to be listened to.
   * @param feat the feature to be invoked on certain keyEvents.
   */
  KeyboardListener(SpreadsheetGraphicalView view, Features feat) {
    this.view = view;
    this.feat = feat;
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
      feat.deleteCell(view.getSelectedCell().toString());
    }
    view.moveSelectedByKey(e);
  }
}
