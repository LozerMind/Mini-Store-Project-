package clients.customer;

import catalogue.BetterBasket;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReader;

import javax.swing.*;
import java.util.List;
import java.util.Observable;

/**
 * Implements the Model of the customer client
 */
public class CustomerModel extends Observable
{
  private Product     theProduct = null;          // Current product
  private BetterBasket      theBasket  = null;          // Bought items

  private String      pn = "";                    // Product being processed

  private StockReader     theStock     = null;
  private OrderProcessing theOrder     = null;
  private ImageIcon       thePic       = null;

  /*
   * Construct the model of the Customer
   * @param mf The factory to create the connection objects
   */
  public CustomerModel(MiddleFactory mf)
  {
    try                                          //
    {
      theStock = mf.makeStockReader();           // Database access
    } catch ( Exception e )
    {
      DEBUG.error("CustomerModel.constructor\n" +
              "Database not created?\n%s\n", e.getMessage() );
    }
    theBasket = makeBasket();                    // Initial BetterBasket
  }

  /**
   * return the BetterBasket of products
   * @return the basket of products
   */
  public BetterBasket getBasket()
  {
    return theBasket;
  }

  /**
   * Check if the product is in Stock
   * @param productNum The product number
   */
  public void doCheck(String productNum )
  {
    theBasket.clear();                          // Clear s. list
    String theAction = "";
    pn  = productNum.trim();                    // Product no.
    int    amount  = 1;                         //  & quantity
    try
    {
      if ( theStock.exists( pn ) )              // Stock Exists?
      {                                         // T
        Product pr = theStock.getDetails( pn ); //  Product
        if ( pr.getQuantity() >= amount )       //  In stock?
        {
          theAction =                           //   Display
                  String.format( "%s : %7.2f ", // Removed "(%2d)"
                          pr.getDescription(),              //    description
                          pr.getPrice()                    //    price
                  );
          pr.setQuantity( amount );             //   Require 1
          theBasket.add( pr );                  //   Add to basket
          thePic = theStock.getImage( pn );     //    product
        } else {                                //  F
          theAction =                           //   Inform
                  pr.getDescription() +               //    product not
                          " not in stock" ;                   //    in stock
        }
      } else {                                  // F
        theAction =                             //  Inform Unknown
                "Unknown product number " + pn;       //  product number
      }
    } catch( StockException e )
    {
      DEBUG.error("CustomerClient.doCheck()\n%s",
              e.getMessage() );
    }
    setChanged(); notifyObservers(theAction);
  }

  /**
   * Clear the products from the basket
   */
  public void doClear()
  {
    String theAction = "";
    theBasket.clear();                        // Clear s. list
    theAction = "Enter Product Number";       // Set display
    thePic = null;                            // No picture
    setChanged(); notifyObservers(theAction);
  }

  /**
   * View all products in stock.
   */
  public void doViewAllProducts() {
    String theAction = "";
    try {
      List<Product> allProducts = theStock.getAllProducts();
      StringBuilder sb = new StringBuilder();
      for (Product product : allProducts) {
        sb.append(product.getProductNum())
                .append(": ")
                .append(product.getDescription())
                .append(" - ")
                .append(product.getPrice())
                .append("\n"); // Removed "( in stock)"
      }
      theAction = sb.toString();
    } catch (StockException e) {
      theAction = "Error retrieving products: " + e.getMessage();
      DEBUG.error("CustomerModel.doViewAllProducts(): " + e.getMessage());
    }
    setChanged();
    notifyObservers(theAction);
  }

  /**
   * Return a picture of the product
   * @return An instance of an ImageIcon
   */
  public ImageIcon getPicture()
  {
    return thePic;
  }

  /**
   * ask for update of view callled at start
   */
  private void askForUpdate()
  {
    setChanged(); notifyObservers("START only"); // Notify
  }

  /**
   * Make a new BetterBasket
   * @return an instance of a new BetterBasket
   */
  protected BetterBasket makeBasket()
  {
    return new BetterBasket();
  }
}