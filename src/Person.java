import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Person extends Remote {

    String greet() throws RemoteException;
}
