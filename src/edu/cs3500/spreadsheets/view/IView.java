package edu.cs3500.spreadsheets.view;

import java.io.IOException;

/**
 * Interface for different views of a Worksheet object.
 */
public interface IView {
  /**
   * Renders a method in some manner, such as text or with graphics.
   * @throws IOException if rendering fails for some reason.
   */
  void render() throws IOException;
}
