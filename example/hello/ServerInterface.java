package example.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public void publish(Integer key, Cell o) throws RemoteException;
    public void subscribe(Integer key, String clientName) throws RemoteException;
}
