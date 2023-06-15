package kjprojekt.esport_serwer;

/**
 *
 * @author pikak
 */
import java.io.*;
import java.net.ServerSocket;
import threads.KlientThread;

/**
 * Klasa odpowiedzialna za działanie serwera
 * @author pikak
 */
public class Serwer {
    public static final int SERVER_PORT = 2002;
    private ServerSocket serverSocket;

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket socket) {
        this.serverSocket = socket;
    }
    
    public Serwer() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Serwer działa");
        }
        catch (IOException ex) {
            System.out.println("Nie można utworzyć gniazda");
        }
    }
    
    public void Uruchom() {
        try {
            while(true) {
                System.out.println("Czekam na klientów...");
                new KlientThread(getServerSocket().accept()).start();
                
            }
        }
        catch (Exception ex) {
            System.out.println("ERROR");
        }
    }
}
