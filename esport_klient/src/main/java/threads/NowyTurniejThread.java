package threads;

import helpers.ConnectionInfo;
import helpers.MessageType;
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
 * wysyłania wiadomości służącej do dodania nowego turnieju
 *
 * @author pikak
 */
public class NowyTurniejThread extends Thread implements ActionListener {

    String nazwa, gra, data, maxGraczy;

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getGra() {
        return gra;
    }

    public void setGra(String gra) {
        this.gra = gra;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMaxGraczy() {
        return maxGraczy;
    }

    public void setMaxGraczy(String maxGraczy) {
        this.maxGraczy = maxGraczy;
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

    public NowyTurniejThread(String nazwa, String gra, String data, String maxGraczy) {
        this.nazwa = nazwa;
        this.gra = gra;
        this.data = data;
        this.maxGraczy = maxGraczy;

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
            String[] info = new String[]{getNazwa(), getGra(), getMaxGraczy(), getData()};
            WebMessage msg = new WebMessage(MessageType.ADD_TOURNAMENT_MESSAGE, info);
            writer.println(msg.encode());

            //czekam na odpowiedź
            WebMessage newMsg;
            String line;

            var x = getReader().readLine();
            var y = WebMessage.decode(x);
            if (y.getTypeOfMessage() == MessageType.ADD_TOURNAMENT_MESSAGE) {
                JOptionPane.showMessageDialog(null, "Dodano turniej!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
