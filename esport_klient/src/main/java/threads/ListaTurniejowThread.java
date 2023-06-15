package threads;

import helpers.ConnectionInfo;
import helpers.MessageType;
import helpers.Turniej;
import helpers.WebMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 * Klasa dziedzicząca po Thread implementująca ActionListener, Wątek używany do
 * wyciągania z serwera listy turniejów
 *
 * @author pikak
 */
public class ListaTurniejowThread extends Thread implements ActionListener {

    private ConnectionInfo connectionInfo;
    ArrayList<Turniej> lista = new ArrayList<Turniej>();
    DefaultTableModel tableModel;

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

    public ListaTurniejowThread(DefaultTableModel dtm) {
        this.tableModel = dtm;

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
            WebMessage msg = new WebMessage(MessageType.TOURNAMENT_LIST_MESSAGE, new String[]{});
            writer.println(msg.encode());

            //czekam na odpowiedź
            WebMessage newMsg;
            String line;

            var x = getReader().readLine();
            var y = WebMessage.decode(x);
            if (y.getTypeOfMessage() == MessageType.TOURNAMENT_LIST_MESSAGE) {
                var splittedTournaments = y.getContent();

                for (int i = 0; i < splittedTournaments.length; i = i + 4) {
                    this.tableModel.addRow(new Object[]{splittedTournaments[i], splittedTournaments[i + 1], splittedTournaments[i + 2], splittedTournaments[i + 3]});
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
