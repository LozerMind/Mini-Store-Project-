package clients.backDoor;

import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;

/**
 * The standalone BackDoor Client
 */
public class BackDoorClient {
    public static void main(String args[]) {
        String stockURL = args.length < 1 ? Names.STOCK_RW : args[0];
        String orderURL = args.length < 2 ? Names.ORDER : args[1];

        RemoteMiddleFactory mrf = new RemoteMiddleFactory();
        mrf.setStockRWInfo(stockURL);
        mrf.setOrderInfo(orderURL);
        displayGUI(mrf);
    }

    private static void displayGUI(MiddleFactory mf) {
        JFrame window = new JFrame();

        window.setTitle("BackDoor Client (MVC RMI)");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackDoorModel model = new BackDoorModel(mf);
        BackDoorView view = new BackDoorView(window, mf, 0, 0);
        BackDoorController cont = new BackDoorController(model, view);
        view.setController(cont);

        model.addObserver(view);
        window.setVisible(true);
    }
}