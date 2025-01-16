/**
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

package remote;

import catalogue.Product;
import middle.StockException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the RMI interface for read/write access to the stock object.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

public interface RemoteStockRW_I
       extends   RemoteStockR_I, Remote
{
  boolean buyStock(String number, int amount)
          throws RemoteException, StockException;
  void    addStock(String number, int amount)
          throws RemoteException, StockException;
  void    modifyStock(Product detail)
          throws RemoteException, StockException;
  /**
   * Removes stock from the store.
   * @param number Product number
   * @param amount Quantity of product to remove
   * @throws RemoteException If a communication error occurs during the remote method invocation.
   * @throws StockException If there is an issue with the stock operation (e.g., insufficient stock).
   */
  void removeStock(String number, int amount)
          throws RemoteException, StockException;
}

