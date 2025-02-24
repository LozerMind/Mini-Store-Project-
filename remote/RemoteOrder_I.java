package remote;

import catalogue.BetterBasket;
import middle.OrderException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Defines the RMI interface for the Order object.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

public interface RemoteOrder_I extends Remote
{
  public void newOrder(BetterBasket order)
         throws RemoteException, OrderException;
  public int  uniqueNumber() 
         throws RemoteException, OrderException;
  public BetterBasket getOrderToPack() 
         throws  RemoteException, OrderException;
  public boolean informOrderPacked(int orderNum)
         throws  RemoteException, OrderException;
  public boolean informOrderCollected(int orderNum)
         throws RemoteException, OrderException;
  public Map<String, List<Integer>> getOrderState() 
         throws  RemoteException, OrderException;
}

