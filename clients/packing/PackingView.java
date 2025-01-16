package clients.packing;

import catalogue.BetterBasket;
import middle.MiddleFactory;
import middle.OrderProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Packing view with an enhanced design.
 */
public class PackingView implements Observer {

  private static final String PACKED = "Packed";

  private static final int H = 400; // Height of window pixels
  private static final int W = 500; // Width of window pixels

  private final JLabel pageTitle = new JLabel();
  private final JLabel theAction = new JLabel();
  private final JTextArea theOutput = new JTextArea();
  private final JScrollPane theSP = new JScrollPane();
  private final JButton theBtPack = new JButton(PACKED);

  private OrderProcessing theOrder = null;
  private PackingController cont = null;

  /**
   * Construct the view with a black background and improved design.
   *
   * @param rpc Window in which to construct
   * @param mf  Factor to deliver order and stock objects
   * @param x   x-coordinate of position of window on screen
   * @param y   y-coordinate of position of window on screen
   */
  public PackingView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
    try {
      theOrder = mf.makeOrderProcessing(); // Process order
    } catch (Exception e) {
      System.out.println("Exception: " + e.getMessage());
    }

    Container cp = rpc.getContentPane(); // Content Pane
    Container rootWindow = (Container) rpc; // Root Window
    cp.setLayout(null); // No layout manager
    rootWindow.setSize(W, H); // Size of Window
    rootWindow.setLocation(x, y);

    // Set background color to black
    cp.setBackground(Color.BLACK);

    Font f = new Font("SansSerif", Font.PLAIN, 14); // Modern font

    pageTitle.setBounds(150, 20, 200, 20);
    pageTitle.setText("Packing Bought Order");
    pageTitle.setForeground(Color.WHITE); // White text
    cp.add(pageTitle);

    theBtPack.setBounds(20, 60, 120, 40); // Adjusted button position and size
    theBtPack.setBackground(Color.GREEN); // Green button
    theBtPack.setForeground(Color.BLACK); // BLACK text
    theBtPack.addActionListener((ActionListener) e -> cont.doPacked()); // Call back code
    cp.add(theBtPack);

    theAction.setBounds(150, 60, 200, 20); // Adjusted message area position
    theAction.setText("");
    theAction.setForeground(Color.WHITE); // White text
    cp.add(theAction);

    theSP.setBounds(20, 110, W - 60, H - 160); // Adjusted scrolling pane position and size
    theOutput.setText("");
    theOutput.setFont(f);
    theOutput.setBackground(Color.LIGHT_GRAY); // Light gray background for text area
    theOutput.setForeground(Color.BLACK); // Black text
    cp.add(theSP);
    theSP.getViewport().add(theOutput);

    rootWindow.setVisible(true); // Make visible
  }

  public void setController(PackingController c) {
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
    PackingModel model = (PackingModel) modelC;
    String message = (String) arg;
    theAction.setText(message);

    BetterBasket basket = model.getBasket();
    if (basket != null) {
      theOutput.setText(basket.getDetails());
    } else {
      theOutput.setText("");
    }
  }
}