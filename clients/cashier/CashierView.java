package clients.cashier;

import catalogue.BetterBasket;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * View of the cashier model with an enhanced design.
 */
public class CashierView implements Observer {

  private static final int H = 400;
  private static final int W = 500;

  private static final String CHECK = "Check";
  private static final String BUY = "Buy";
  private static final String BOUGHT = "Bought/Pay";

  private final JLabel pageTitle = new JLabel();
  private final JLabel theAction = new JLabel();
  private final JTextField theInput = new JTextField();
  private final JTextArea theOutput = new JTextArea();
  private final JScrollPane theSP = new JScrollPane();
  private final JButton theBtCheck = new JButton(CHECK);
  private final JButton theBtBuy = new JButton(BUY);
  private final JButton theBtBought = new JButton(BOUGHT);

  private StockReadWriter theStock = null;
  private OrderProcessing theOrder = null;
  private CashierController cont = null;

  /**
   * Construct the cashier view with a modern design and layout.
   *
   * @param rpc Window in which to construct
   * @param mf  Factor to deliver order and stock objects
   * @param x   x-coordinate of position of window on screen
   * @param y   y-coordinate of position of window on screen
   */
  public CashierView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
    try {
      theStock = mf.makeStockReadWriter();
      theOrder = mf.makeOrderProcessing();
    } catch (Exception e) {
      System.out.println("Exception: " + e.getMessage());
    }

    Container cp = rpc.getContentPane();
    Container rootWindow = (Container) rpc;
    cp.setLayout(null); // Use null layout for custom positioning
    rootWindow.setSize(W, H);
    rootWindow.setLocation(x, y);

    // Set background color to a Black
    cp.setBackground(Color.BLACK);

    Font f = new Font("SansSerif", Font.PLAIN, 14); // Modern font

    pageTitle.setBounds(100, 20, 300, 20);
    pageTitle.setText("MiniStrore - Cashier View");
    pageTitle.setForeground(Color.WHITE);
    pageTitle.setFont(new Font(f.getName(), Font.BOLD, f.getSize())); // Bold title
    cp.add(pageTitle);

    // Group buttons for better organization
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(theBtCheck);
    buttonGroup.add(theBtBuy);
    buttonGroup.add(theBtBought);

    theBtCheck.setBounds(20, 60, 120, 40);
    theBtCheck.setBackground(Color.BLUE); // Blue button
    theBtCheck.setForeground(Color.BLACK); // BLACK text
    theBtCheck.addActionListener((ActionListener) e -> cont.doCheck(theInput.getText()));
    cp.add(theBtCheck);

    theBtBuy.setBounds(150, 60, 120, 40);
    theBtBuy.setBackground(Color.GREEN); // Green button
    theBtBuy.setForeground(Color.BLACK); // BLACK text
    theBtBuy.addActionListener((ActionListener) e -> cont.doBuy());
    cp.add(theBtBuy);

    theBtBought.setBounds(280, 60, 120, 40);
    theBtBought.setBackground(Color.ORANGE); // Orange button
    theBtBought.setForeground(Color.BLACK); // BLACK text
    theBtBought.addActionListener((ActionListener) e -> cont.doBought());
    cp.add(theBtBought);

    theAction.setBounds(20, 120, 470, 20); // Adjusted message area position
    theAction.setText("");
    theAction.setForeground(Color.white); // white text
    cp.add(theAction);

    theInput.setBounds(20, 150, 200, 30);
    theInput.setText("");
    cp.add(theInput);

//    theInputNo.setBounds(230, 150, 100, 30); // Added theInputNo field
//    theInputNo.setText("");
//    cp.add(theInputNo);

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

  public void setController(CashierController c) {
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
    CashierModel model = (CashierModel) modelC;
    String message = (String) arg;
    theAction.setText(message);

    BetterBasket basket = model.getBasket();
    if (basket == null) {
      theOutput.setText("Customers order");
    } else {
      theOutput.setText(basket.getDetails());
    }

    theInput.requestFocus();
  }
}