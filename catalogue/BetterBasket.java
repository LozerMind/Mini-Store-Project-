package catalogue;

import java.io.Serializable;
import java.util.*;

/**
 * A collection of products,
 * used to record the products that are to be wished to be purchased.
 * Overrides add in basket
 * @author  Your Name
 * @version 1.0
 */
public class BetterBasket extends Basket implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * Add a product to the Basket.
   * Overrides add in basket.java
   * Checks if product is already in basket and increments quantity
   * if it is, if not appends product to list then sorts by order num
   * @param pr A product to be added to the basket
   * @return true if successfully adds the product
   */
  @Override
  public boolean add(Product pr) {

    // Checks if product being added is already in basket
    // if it is increments quantity by 1
    for (Product basketItem : this) {
      if (basketItem.getProductNum().equals(pr.getProductNum())) {
        basketItem.setQuantity(basketItem.getQuantity() + 1);
        return true;
      }
    }

    // If product is not already in basket it is added
    // if adding succeeds the basket is sorted by order num before returning
    if (super.add(pr)) {
      this.sort(Comparator.comparing(Product::getProductNum));
      return true;
    }

    // If product is not in already basket and adding fails
    return false;

  }
}