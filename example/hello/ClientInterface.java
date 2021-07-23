package example.hello;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;

public interface ClientInterface extends Remote {
    public void receive(Integer key, Cell o)      throws RemoteException;  
    public String getName()                       throws RemoteException;
    public void setName(String name)              throws RemoteException;
    public HashSet<Cell> getMessages(Integer key) throws RemoteException;
}
