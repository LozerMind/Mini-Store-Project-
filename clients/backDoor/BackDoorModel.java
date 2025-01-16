package clients.backDoor;

import catalogue.BetterBasket;
import catalogue.BetterBasket;
import catalogue.Product;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.StockException;
import middle.StockReadWriter;

import java.util.Observable;

/**
 * Implements the Model of the back door client
 */
public class BackDoorModel extends Observable {
  private BetterBasket theBasket = null;
  private String pn = "";

  private StockReadWriter theStock = null;

  /**
   * Construct the model of the back door client
   *
   * @param mf The factory to create the connection objects
   */
  public BackDoorModel(MiddleFactory mf) {
    try {
      theStock = mf.makeStockReadWriter();
    } catch (Exception e) {
      DEBUG.error("CustomerModel.constructor\n%s", e.getMessage());
    }

    theBasket = makeBasket();
  }

  /**
   * Get the BetterBasket of products
   *
   * @return basket
   */
  public BetterBasket getBasket() {
    return theBasket;
  }

  /**
   * Check The current stock level
   *
   * @param productNum The product number
   */
  public void doCheck(String productNum) {
    pn = productNum.trim();
  }

  /**
   * Query
   *
   * @param productNum The product number of the item
   */
  public void doQuery(String productNum) {
    String theAction = "";
    pn = productNum.trim();
    try {
      if (theStock.exists(pn)) {
        Product pr = theStock.getDetails(pn);
        theAction = String.format("%s : %7.2f (%2d)", pr.getDescription(), pr.getPrice(), pr.getQuantity());
      } else {
        theAction = "Unknown product number " + pn;
      }
    } catch (StockException e) {
      theAction = e.getMessage();
    }
    setChanged();
    notifyObservers(theAction);
  }

  /**
   * Re stock
   *
   * @param productNum The product number of the item
   * @param quantity How many to be added
   */
  public void doRStock(String productNum, String quantity) {
    String theAction = "";
    theBasket = makeBasket();
    pn = productNum.trim();
    int amount = 0;
    try {
      String aQuantity = quantity.trim();
      try {
        amount = Integer.parseInt(aQuantity);
        if (amount < 0) {
          throw new NumberFormatException("-ve");
        }
      } catch (Exception err) {
        theAction = "Invalid quantity";
        setChanged();
        notifyObservers(theAction);
        return;
      }

      if (theStock.exists(pn)) {
        theStock.addStock(pn, amount);
        Product pr = theStock.getDetails(pn);
        theBasket.add(pr);
        theAction = "";
      } else {
        theAction = "Unknown product number " + pn;
      }
    } catch (StockException e) {
      theAction = e.getMessage();
    }
    setChanged();
    notifyObservers(theAction);
  }

  /**
   * Clear the product()
   */
  public void doClear() {
    String theAction = "";
    theBasket.clear();
    theAction = "Enter Product Number";
    setChanged();
    notifyObservers(theAction);
  }

  /**
   * Remove stock
   *
   * @param productNum The product number of the item
   * @param quantity How many to be removed
   */
  public void doRemoveStock(String productNum, String quantity) {
    String theAction = "";
    pn = productNum.trim();
    int amount = 0;
    try {
      String aQuantity = quantity.trim();
      try {
        amount = Integer.parseInt(aQuantity);
        if (amount < 0) {
          throw new NumberFormatException("-ve");
        }
      } catch (Exception err) {
        theAction = "Invalid quantity";
        setChanged();
        notifyObservers(theAction);
        return;
      }

      if (theStock.exists(pn)) {
        if (theStock.getDetails(pn).getQuantity() >= amount) {
          theStock.removeStock(pn, amount);
          Product pr = theStock.getDetails(pn);
          theAction = String.format("%s: %7.2f (%2d)", pr.getDescription(), pr.getPrice(), pr.getQuantity());
        } else {
          theAction = "Insufficient stock";
        }
      } else {
        theAction = "Unknown product number " + pn;
      }
    } catch (StockException e) {
      theAction = e.getMessage();
    }
    setChanged();
    notifyObservers(theAction);
  }

  /**
   * return an instance of a BetterBasket
   *
   * @return a new instance of a BetterBasket
   */
  protected BetterBasket makeBasket() {
    return new BetterBasket();
  }
}