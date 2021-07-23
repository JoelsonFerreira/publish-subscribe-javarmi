package example.hello;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import java.io.Serializable;

public class Client implements ClientInterface, Serializable {
    private static final long serialVersionUID = 7526472295622776147L;

    private String name;
    private HashMap<Integer, HashSet<Cell>> objects = new HashMap<Integer, HashSet<Cell>>();

    public void receive(Integer key, Cell o) {
        HashSet<Cell> cellList = objects.get(key);

        if(cellList == null) cellList = new HashSet<Cell>();

        cellList.add(o);

        objects.put(key, cellList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<Cell> getMessages(Integer key) {
        return objects.get(key);
    }

    public static void main(String[] args) {
        String name = args[0];

        if(args.length == 1) {
            try {
                ClientInterface cli = new Client();
                cli.setName(name);
                System.out.println("A");
                System.out.println(cli.getName());
                System.out.println("B");

                ClientInterface clientStub = (ClientInterface)UnicastRemoteObject.exportObject(cli, 0);
                Registry registry = LocateRegistry.getRegistry();
                registry.bind(name, clientStub);

                registry = LocateRegistry.getRegistry("");
                ServerInterface serverStub = (ServerInterface) registry.lookup("server");

                Scanner keyboard = new Scanner(System.in);
                int opt = 2;

                while(opt != 0) {
                    System.out.println("[1] Publish");
                    System.out.println("[2] Subscribe");
                    System.out.println("[3] Show cells");
                    System.out.println("[0] Exit");

                    System.out.print("Option: ");
                    
                    opt = keyboard.nextInt();

                    Integer key;

                    System.out.print("Enter key: ");
                    key = keyboard.nextInt();

                    switch (opt) {
                        case 1:
                            String message;
                            
                            keyboard.nextLine();
                            System.out.print("Enter your message: ");
                            message = keyboard.nextLine();

                            System.out.println("Sending message: " + message);

                            Cell o = new Cell();
                            o.set(message);

                            serverStub.publish(key, o);
                            break;
                        case 2:
                            serverStub.subscribe(key, cli.getName());
                            break;
                        case 3:
                            HashSet<Cell> messagesCells = cli.getMessages(key);
                            
                            if(messagesCells != null) {
                                for(Cell c : messagesCells) {
                                    System.out.println(c.get());
                                }
                            }

                            break;
                        case 0: 
                            System.out.println("Bye!!");
                            break;
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                }

                keyboard.close();
                
                // System.err.println("Subscribind to: " + A + "\nPublishing: " + B + "\n");

                // if (A != 0){          
                //     Integer sub_key;
                //     sub_key = A;
                //     stub.subscribe(c, sub_key);
                // }
            
                // if (B != 0){
                //     Integer pub_key;
                //     pub_key = B;
                //     Cell o = new Cell(); // Create a fresh object
                //     o.set(A);  // Qual valor irá para a Cell? o valor da key dela ou da key do sub?
                //     stub.publish(pub_key, o);  // Publish the object
                // }

            } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
            }
        } else {

        }        
    }
}