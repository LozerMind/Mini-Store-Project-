package clients.customer;

import catalogue.BetterBasket;
import catalogue.Product;
import clients.Picture;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the customer view with an enhanced design.
 */
public class CustomerView implements Observer {

  private static final int H = 550; // Increased height for better visibility
  private static final int W = 600; // Increased width for better visibility

  private final JLabel pageTitle = new JLabel();
  private final JLabel theAction = new JLabel();
  private final JTextField theInput = new JTextField();
  // Replace JTextArea with JList for displaying all products
  private final JList<String> productList = new JList<>();
  private final JScrollPane theSP = new JScrollPane(productList);
  private final JButton theBtCheck = new JButton("Check");
  private final JButton theBtClear = new JButton("Clear");
  private final JButton theBtViewAll = new JButton("View All"); // New button

  private final Picture thePicture = new Picture(80, 80);
  private StockReader theStock = null;
  private CustomerController cont = null;

  private List<Product> allProducts; // To store all products

  /**
   * Construct the customer view with a modern design and layout.
   *
   * @param rpc Window in which to construct
   * @param mf  Factor to deliver order and stock objects
   * @param x   x-coordinate of position of window on screen
   * @param y   y-coordinate of position of window on screen
   */
  public CustomerView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
    try {
      theStock = mf.makeStockReader();
    } catch (Exception e) {
      System.out.println("Exception: " + e.getMessage());
    }

    Container cp = rpc.getContentPane();
    Container rootWindow = (Container) rpc;
    cp.setLayout(null); // Use null layout for custom positioning
    rootWindow.setSize(W, H);
    rootWindow.setLocation(x, y);

    // Set background color to a dark color
    cp.setBackground(new Color(32, 32, 32));

    Font f = new Font("SansSerif", Font.PLAIN, 14); // Modern font

    // Title
    pageTitle.setBounds(120, 20, 300, 20); // Adjusted position
    pageTitle.setText("MiniStrore - Customer View");
    pageTitle.setFont(new Font(f.getName(), Font.BOLD, f.getSize())); // Bold title
    pageTitle.setForeground(Color.WHITE); // Set title color to white
    cp.add(pageTitle);

    // Action message
    theAction.setBounds(20, 60, 530, 20); // Adjusted position
    theAction.setText("");
    theAction.setForeground(Color.WHITE); // White text
    cp.add(theAction);

    // Input field
    theInput.setBounds(20, 90, 250, 30); // Adjusted position
    theInput.setText("");
    cp.add(theInput);

    // Buttons
    // Check button
    theBtCheck.setBounds(280, 90, 100, 30); // Adjusted position
    theBtCheck.setBackground(Color.BLUE); // Blue button
    theBtCheck.setForeground(Color.WHITE); // White text
    theBtCheck.addActionListener((ActionListener) e -> cont.doCheck(theInput.getText()));
    cp.add(theBtCheck);

    // Clear button
    theBtClear.setBounds(390, 90, 100, 30); // Adjusted position
    theBtClear.setBackground(Color.ORANGE); // Orange button
    theBtClear.setForeground(Color.WHITE); // White text
    theBtClear.addActionListener((ActionListener) e -> cont.doClear());
    cp.add(theBtClear);

    // View All button
    theBtViewAll.setBounds(400, 20, 100, 30); // Adjusted position
    theBtViewAll.setBackground(Color.GREEN); // Green button
    theBtViewAll.setForeground(Color.WHITE); // White text
    theBtViewAll.addActionListener((ActionListener) e -> cont.doViewAll()); // Add ActionListener
    cp.add(theBtViewAll);

    // Product List
    theSP.setBounds(20, 130, 470, 250); // Adjusted position and size
    cp.add(theSP);
    theSP.setViewportView(productList);

    // Picture
    thePicture.setBounds( 400, 400, 80, 80 );
    cp.add(thePicture);
    thePicture.clear();

    rootWindow.setVisible(true);
    theInput.requestFocus();

    // Load all products initially
    try {
      allProducts = theStock.getAllProducts();
    } catch (Exception e) {
      System.err.println("Error loading all products: " + e.getMessage());
    }
  }

  public void setController(CustomerController c) {
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
    CustomerModel model = (CustomerModel) modelC;
    String message = (String) arg;
    theAction.setText(message);

//    BetterBasket basket = model.getBasket();
//    if (basket == null) {
//      theOutput.setText("Customers order");
//    } else {
//      theOutput.setText(basket.getDetails());
//    }

    ImageIcon image = model.getPicture();  // Image of product
    if ( image == null ) {
      thePicture.clear();                  // Clear picture
    } else {
      thePicture.set( image );             // Display picture
    }

    theInput.requestFocus();

    // Update the JList with all products
    if (arg.equals("View All")) {
      DefaultListModel<String> listModel = new DefaultListModel<>();
      for (Product product : allProducts) {
        listModel.addElement(product.getProductNum() + ": " + product.getDescription() + " - " + product.getPrice());
      }
      productList.setModel(listModel);
    }
  }
}