import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws RemoteException, NotBoundException {

        int port = 1337;
        String stubNameOfInstance = "SomePerson";

        if (args.length != 1)
            throw new IllegalArgumentException("Argument needed: server|client");

        switch (args[0]) {
            case "server":
                runServer(port, stubNameOfInstance);
                break;
            case "client":
                runClient(port, stubNameOfInstance);
                break;
            default:
                throw new IllegalArgumentException("Argument not server or client");
        }

    }

    private static void runClient(int port, String stubNameOfInstance) throws RemoteException, NotBoundException {
        Person call;
        Registry registry = null;
        registry = LocateRegistry.getRegistry("127.0.0.1", port);
        System.out.println(Arrays.toString(registry.list()));
        call = (Person) registry.lookup(stubNameOfInstance);
        System.out.println(call.greet());

    }

    private static void runServer(int port, String stubNameOfInstance) {
        Person p = new Individual();
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");

        try {
            Registry l = LocateRegistry.createRegistry(port);
            Person stub = (Person) UnicastRemoteObject.exportObject(p, 0);

            // Bind the remote object's stub in the registry
            l.bind(stubNameOfInstance, stub);

            System.err.println("Server ready");
            System.out.println(Arrays.toString(l.list()));
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
        try {
            sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
