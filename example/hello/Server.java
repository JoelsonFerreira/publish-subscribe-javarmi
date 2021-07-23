package example.hello;
	
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;
import java.util.HashSet;
	
public class Server implements ServerInterface {

    private HashMap<Integer, HashSet<Cell>> objects = new HashMap<Integer, HashSet<Cell>>();
    private HashMap<Integer, HashSet<String>> clients = new HashMap<Integer, HashSet<String>>();
	
    public Server() {}

    public void publish(Integer key, Cell o) {
        HashSet<Cell> cellList = objects.get(key);

        if(cellList == null) cellList = new HashSet<Cell>();

        cellList.add(o);

        objects.put(key, cellList);

        HashSet<String> clis = clients.get(key);

        if(clis != null) {
            for(String cli : clis) {
                try {
                    Registry registry = LocateRegistry.getRegistry();
                    ClientInterface stub = (ClientInterface) registry.lookup(cli);
                    stub.receive(key, o);
                } catch (Exception e) {
                    System.err.println("Server exception: " + e.toString());
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void subscribe(Integer key, String clientName) {
        HashSet<String> clientList = clients.get(key);

        if(clientList == null) clientList = new HashSet<String>();

        clientList.add(clientName);

        clients.put(key, clientList);

        HashSet<Cell> objs = objects.get(key);

        if(objs != null) {
            for(Cell o : objs) {
                try{
                    System.out.println(clientName);
                    Registry registry = LocateRegistry.getRegistry();
                    ClientInterface stub = (ClientInterface) registry.lookup(clientName);
                    stub.receive(key, o);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
	
    public static void main(String args[]) {
        try {
            Server obj = new Server();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("server", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
