package edu.cs3500.spreadsheets.provider.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A WorksheetView that uses Java Swing to display the model and provides functionality for updating
 * the view as changes to the model are made.
 */
public class SpreadsheetGraphicalView extends JFrame implements WorksheetView {

  private final GridPanel grid;
  private JButton confirmButton;
  private JButton rejectButton;
  private JButton saveButton;
  private JButton loadButton;
  private JTextField textBox;
  private JTextField saveBox;
  private KeyListener keyListener;
  private Map<Coord, String> rawValues;

  /**
   * Constructor for a WorksheetView that uses Java Swing to display a model graphically.
   */
  public SpreadsheetGraphicalView() {
    super();
    MouseAdapter clickListener = new ClickListener(this);
    this.getContentPane().addMouseListener(clickListener);
    this.setTitle("Spreadsheet");
    this.setSize(1000, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    grid = new GridPanel(new HashMap<>());
    grid.select(new Coord(0, 0));

    rawValues = new HashMap<>();

    JPanel sheetPanel = new ProviderSpreadsheetPanel(grid);
    this.add(sheetPanel);

    JPanel editPanel = new JPanel();
    editPanel.setLayout(new BorderLayout());

    confirmButton = new JButton("\u2713");
    confirmButton.setActionCommand("confirm");
    rejectButton = new JButton("\u2717");
    rejectButton.setActionCommand("reject");
    JPanel westLayout = new JPanel();
    westLayout.add(confirmButton);
    westLayout.add(rejectButton);

    textBox = new JTextField(rawValues.get(grid.getSelected()));
    textBox.setPreferredSize(new Dimension(500, 30));
    westLayout.add(textBox);
    editPanel.add(westLayout, BorderLayout.WEST);

    JPanel eastLayout = new JPanel();
    saveButton = new JButton("SAVE");
    saveButton.setActionCommand("save");
    loadButton = new JButton("LOAD");
    saveBox = new JTextField("Enter Pathname Here");
    saveBox.setPreferredSize(new Dimension(150, 30));
    eastLayout.add(saveBox);
    eastLayout.add(loadButton);
    eastLayout.add(saveButton);
    editPanel.add(eastLayout, BorderLayout.EAST);
    this.add(editPanel, BorderLayout.NORTH);
  }

  @Override
  public void display() {
    this.setVisible(true);
    this.setFocusable(true);
    this.requestFocusInWindow();
  }

  @Override
  public void updateView(String cell, String value) {
    if (value.length() != 0) {
      if (value.charAt(0) == '=') {
        rawValues.put(cellParse(cell), value);
      } else {
        grid.updateCells(cell, value);
        rawValues.put(cellParse(cell), value);
      }
    } else {
      grid.updateCells(cell, value);
      rawValues.put(cellParse(cell), value);
    }
    display();
    repaint();
  }

  @Override
  public void addFeatures(Features features) {
    confirmButton.addActionListener(evt -> features.addCell(grid.getSelected().toString(),
        textBox.getText()));
    rejectButton.addActionListener(evt -> textBox.setText(rawValues.get(grid.getSelected())));
    saveButton.addActionListener(evt -> features.save(saveBox.getText()));
    loadButton.addActionListener(evt -> features.load(saveBox.getText()));
    keyListener = new KeyboardListener(this, features);
    this.addKeyListener(keyListener);
  }

  @Override
  public Coord getSelectedCell() {
    return grid.getSelected();
  }

  //Turns a string into a Coord for a cell.
  private static Coord cellParse(String cell) {
    if (!cell.matches("([A-Z])*\\d+")) {
      throw new IllegalArgumentException("Incorrect cell coordinate");
    } else {
      String[] s = cell.split("(?<=[A-Z])(?=[0-9])");
      return new Coord(Coord.colNameToIndex(s[0]), Integer.parseInt(s[1]));
    }
  }

  //Updates the view to show the newly selected cell.
  void updateSelectedCell(Coord c) {
    grid.select(c);
    textBox.setText(rawValues.get(grid.getSelected()));
    display();
    repaint();
  }

  //Updates the view to show the newly selected cell.
  void moveSelectedByKey(KeyEvent e) {
    grid.moveBySelectedKey(e);
    textBox.setText(rawValues.get(grid.getSelected()));
    display();
    repaint();
  }
}
