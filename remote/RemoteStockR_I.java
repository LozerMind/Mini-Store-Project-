package remote;

import catalogue.Product;
import middle.StockException;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Defines the RMI interface for read access to the stock object.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

public interface RemoteStockR_I
        extends Remote
{
  boolean   exists(String number)
          throws RemoteException, StockException;
  Product   getDetails(String number)
          throws RemoteException, StockException;
  ImageIcon getImage(String number)
          throws RemoteException, StockException;

  /**
   * Returns a list of all products in stock.
   * @return A list of Product objects.
   * @throws RemoteException If a communication error occurs during the remote method invocation.
   * @throws StockException If there is an issue with the stock operation.
   */
  List<Product> getAllProducts() throws RemoteException, StockException;
}