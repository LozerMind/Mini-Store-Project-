package clients.backDoor;

import middle.MiddleFactory;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the BackDoor view with an enhanced design.
 */
public class BackDoorView implements Observer {

  private static final String RESTOCK = "Add Stock";
  private static final String CLEAR = "Clear";
  private static final String QUERY = "Query";
  private static final String REMOVE_STOCK = "Remove Stock";

  private static final int H = 400;
  private static final int W = 500;

  private final JLabel pageTitle = new JLabel();
  private final JLabel theAction = new JLabel();
  private final JTextField theInput = new JTextField();
  private final JTextField theInputNo = new JTextField();
  private final JTextArea theOutput = new JTextArea();
  private final JScrollPane theSP = new JScrollPane();
  private final JButton theBtClear = new JButton(CLEAR);
  private final JButton theBtRStock = new JButton(RESTOCK);
  private final JButton theBtQuery = new JButton(QUERY);
  private final JButton theBtRemoveStock = new JButton(REMOVE_STOCK);

  private StockReadWriter theStock = null;
  private BackDoorController cont = null;

  /**
   * Construct the view with a more modern design and layout.
   *
   * @param rpc Window in which to construct
   * @param mf  Factor to deliver order and stock objects
   * @param x   x-coordinate of position of window on screen
   * @param y   y-coordinate of position of window on screen
   */
  public BackDoorView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
    try {
      theStock = mf.makeStockReadWriter();
    } catch (Exception e) {
      System.out.println("Exception: " + e.getMessage());
    }

    Container cp = rpc.getContentPane();
    Container rootWindow = (Container) rpc;
    cp.setLayout(null); // Use null layout for custom positioning
    rootWindow.setSize(W, H);
    rootWindow.setLocation(x, y);

    // Set background color to BLACK
    cp.setBackground(Color.BLACK);

    Font f = new Font("SansSerif", Font.PLAIN, 14); // Modern font

    pageTitle.setBounds(120, 20, 250, 20);
    pageTitle.setText("Staff Stock Management");
    pageTitle.setForeground(Color.WHITE);
    pageTitle.setFont(new Font(f.getName(), Font.BOLD, f.getSize())); // Bold title
    cp.add(pageTitle);

    // Group buttons for better organization
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(theBtQuery);
    buttonGroup.add(theBtRStock);
    buttonGroup.add(theBtClear);
    buttonGroup.add(theBtRemoveStock);

    theBtQuery.setBounds(20, 60, 120, 40);
    theBtQuery.setBackground(Color.BLUE); // Blue button
    theBtQuery.setForeground(Color.BLACK); // White text
    theBtQuery.addActionListener((ActionListener) e -> cont.doQuery(theInput.getText()));
    cp.add(theBtQuery);

    theBtRStock.setBounds(150, 60, 120, 40);
    theBtRStock.setBackground(Color.GREEN); // Green button
    theBtRStock.setForeground(Color.BLACK); // White text
    theBtRStock.addActionListener((ActionListener) e -> cont.doRStock(theInput.getText(), theInputNo.getText()));
    cp.add(theBtRStock);

    theBtClear.setBounds(280, 60, 120, 40);
    theBtClear.setBackground(Color.ORANGE); // Orange button
    theBtClear.setForeground(Color.BLACK); // White text
    theBtClear.addActionListener((ActionListener) e -> cont.doClear());
    cp.add(theBtClear);

    theBtRemoveStock.setBounds(410, 60, 120, 40);
    theBtRemoveStock.setBackground(Color.RED); // Red button
    theBtRemoveStock.setForeground(Color.BLACK); // White text
    theBtRemoveStock.addActionListener((ActionListener) e -> cont.doRemoveStock(theInput.getText(), theInputNo.getText()));
    cp.add(theBtRemoveStock);

    theAction.setBounds(20, 120, 470, 20); // Adjusted message area position
    theAction.setText("");
    theAction.setForeground(Color.WHITE); // white text
    cp.add(theAction);

    theInput.setBounds(20, 150, 150, 30);
    theInput.setText("");
    cp.add(theInput);

    theInputNo.setBounds(180, 150, 100, 30);
    theInputNo.setText("0");
    cp.add(theInputNo);

    theSP.setBounds(20, 190, 470, 180); // Adjusted scrolling pane position and size
    theOutput.setText("");
    theOutput.setFont(f);
    theOutput.setBackground(Color.WHITE); // White background for text area
    theOutput.setForeground(Color.BLACK); // Black text
    cp.add(theSP);
    theSP.getViewport().add(theOutput);

    rootWindow.setVisible(true);
    theInput.requestFocus();
  }

  public void setController(BackDoorController c) {
    cont = c;
  }

  /**
   * Update the view with improved formatting.
   *
   * @param modelC The observed model
   * @param arg    Specific args
   */
  @Override
  public void update(Observable modelC, Object arg) {
    BackDoorModel model = (BackDoorModel) modelC;
    String message = (String) arg;
    theAction.setText(message);

    theOutput.setText(model.getBasket().getDetails());
    theInput.requestFocus();
  }
}