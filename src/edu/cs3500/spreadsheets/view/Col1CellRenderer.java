package edu.cs3500.spreadsheets.view;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;

/**
 * A class used to produce a Component that will render the first column
 * of a JTable to be colored gray instead of white.
 */
class Col1CellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                        boolean hasFocus, int row, int column) {
    Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
            row, column);

    if (row == 1) {
      rendererComp.setBackground(new Color(240, 240, 240));
    }
    return rendererComp ;
  }
}
