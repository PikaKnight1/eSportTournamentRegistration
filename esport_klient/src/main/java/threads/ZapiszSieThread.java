package threads;

import helpers.ConnectionInfo;
import helpers.MessageType;
import helpers.User;
import helpers.WebMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 * Klasa dziedzicząca po Thread implementująca ActionListener, Wątek używany do
 * wysyłania wiadomości służącej do zapisania się na turniej
 *
 * @author pikak
 */
public class ZapiszSieThread extends Thread implements ActionListener {

    User user;
    String turniej, kontakt, graUser;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTurniej() {
        return turniej;
    }

    public void setTurniej(String turniej) {
        this.turniej = turniej;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public String getGraUser() {
        return graUser;
    }

    public void setGraUser(String graUser) {
        this.graUser = graUser;
    }

    private ConnectionInfo connectionInfo;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public ZapiszSieThread(User user, String turniej, String kontakt, String graUser) {
        this.user = user;
        this.turniej = turniej;
        this.kontakt = kontakt;
        this.graUser = graUser;

        this.connectionInfo = new ConnectionInfo();

        try {
            socket = new Socket(connectionInfo.getHost(), connectionInfo.getPort());
            writer = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            init();
        } catch (IOException e) {
        }
    }

    public void init() {
        start();
    }

    public void run() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            String[] info = new String[]{getUser().getUsername(), getTurniej(), getKontakt(), getGraUser()};
            WebMessage msg = new WebMessage(MessageType.ADD_PLAYER_TOURNAMENT_MESSAGE, info);
            writer.println(msg.encode());

            //czekam na odpowiedź
            WebMessage newMsg;
            String line;

            var x = getReader().readLine();
            var y = WebMessage.decode(x);
            if (y.getTypeOfMessage() == MessageType.ADD_PLAYER_TOURNAMENT_MESSAGE) {
                JOptionPane.showMessageDialog(null, "Zapisano!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
