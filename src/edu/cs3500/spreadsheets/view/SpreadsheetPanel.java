package edu.cs3500.spreadsheets.view;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JScrollPane;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ReadOnlyModel;
import edu.cs3500.spreadsheets.model.WorkSheet;

/**
 * A type of JPanel that is used to render a Spreadsheet-like table.
 */
class SpreadsheetPanel extends JPanel {

  private WorkSheet<?> model;
  private JTable table;

  /**
   * Constructor for SpreadsheetPanel.
   * @param model the model to be rendered
   */
  public SpreadsheetPanel(WorkSheet<?> model) {
    this.model = new ReadOnlyModel(model);
    this.fillTable();
  }

  private void fillTable() {
    this.table = new SpreadsheetTable(this.getData(), this.getColNames());
    this.table.getTableHeader().setReorderingAllowed(false);

    this.centerColumns();
    this.setColumnWidths();

    JScrollPane scrollPane = new JScrollPane(this.table,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    this.table.getColumnModel().getColumn(0).setCellRenderer(new Col1CellRenderer());
    add(scrollPane);
  }

  private String[][] getData() {
    String[][] ans =  new String[this.model.getRows()][this.model.getCols() + 1];
    Coord c;
    for (int i = 0; i < this.model.getRows(); ++i) {
      for (int j = 0; j < this.model.getCols() + 1; ++j) {
        if (j == 0) {
          ans[i][j] = Integer.toString(i + 1);
        }
        else {
          c = new Coord(j, i + 1);
          if (this.model.getCells().get(c) == null) {
            ans[i][j] = "";
          }
          else {
            //ans[i][j] = this.model.getCells().get(c).toString();
            //this displays the formula (use for future assignment?)
            try {
              ans[i][j] = this.model.getDataAt(j, i + 1).toString();
            } catch (NullPointerException e) {
              ans[i][j] = "ERROR";
            }
          }
        }
      }
    }
    return ans;
  }

  private String[] getColNames() {
    String[] ans = new String[this.model.getCols() + 1];
    ans[0] = "";
    for (int i = 1; i <= this.model.getCols(); ++i) {
      ans[i] = Coord.colIndexToName(i);
    }
    return ans;
  }

  private void centerColumns() {
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    this.table.setDefaultRenderer(String.class, centerRenderer);
    for (int i = 0; i <= this.model.getCols(); ++i) {
      this.table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
  }

  private void setColumnWidths() {
    this.table.getColumnModel().getColumn(0).setMinWidth(30);
    this.table.getColumnModel().getColumn(0).setMaxWidth(30);
    for (int i = 1; i <= this.model.getCols(); ++i) {
      this.table.getColumnModel().getColumn(i).setMinWidth(60);
    }
  }

  /**
   * Finds the coordinates of the cell that is selected in this panel.
   * @return coordinates of the selected cell
   */
  public Coord getSelectedCell() {
    return new Coord(this.table.getSelectedColumn(), this.table.getSelectedRow() + 1);
  }
}
