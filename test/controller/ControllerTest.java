package controller;

import org.junit.Test;

import java.awt.event.ActionEvent;

import edu.cs3500.spreadsheets.controller.SpreadsheetController;
import edu.cs3500.spreadsheets.model.MockModel;
import edu.cs3500.spreadsheets.view.EditableSwingView;

import static org.junit.Assert.assertEquals;

/**
 * Testing class that uses Mocks to test that the model is receiving the controller's inputs
 * correctly.
 */
public class ControllerTest {

  /**
   * Testing that comparing mocks to expected strings works correctly.
   */
  @Test
  public void testMock() {
    MockModel t = new MockModel();
    t.getErroneousCells();
    StringBuilder expected = new StringBuilder();
    expected.append("getErroneousCells() ");
    assertEquals(expected.toString(), t.log.toString());
    t.getDataAt(1, 2);
    expected.append("getDataAt(1, 2) ");
    assertEquals(expected.toString(), t.log.toString());
  }

  @Test
  public void testController() {
    MockModel t = new MockModel();
    EditableSwingView v = new EditableSwingView(t);
    SpreadsheetController c = new SpreadsheetController(t, v);

    c.actionPerformed(new ActionEvent(null, 1, "Confirm"));
    assertEquals("WriteCell()", t.log.toString());
    c.actionPerformed(new ActionEvent(null, 2, "Reject"));
    assertEquals("WriteCell()", t.log.toString());
    c.actionPerformed(new ActionEvent(null, 3, "Add Row"));
    assertEquals("WriteCell() addRow() ", t.log.toString());
    c.actionPerformed(new ActionEvent(null, 4, "Add Column"));
    assertEquals("WriteCell() addRow() addColumn() ", t.log.toString());
  }

}
