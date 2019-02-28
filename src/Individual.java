import java.rmi.RemoteException;

public class Individual implements Person {
    @Override
    public String greet() throws RemoteException {
        System.out.println("Gotta greet, gonna greet");
        return "Hey you!";
    }
}
