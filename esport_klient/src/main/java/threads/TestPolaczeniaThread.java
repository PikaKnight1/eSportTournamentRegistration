package threads;

import helpers.ConnectionInfo;
import helpers.MessageType;
import helpers.WebMessage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;

/**
 * Klasa dziedzicząca po Thread implementująca ActionListener, Wątek używany do
 * testowania połączenia z serwerem
 *
 * @author pikak
 */
public class TestPolaczeniaThread extends Thread implements ActionListener {

    private ConnectionInfo connectionInfo;
    private JLabel errorLabel;

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public void setErrorLabel(JLabel label) {
        this.errorLabel = label;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public JLabel getErrorLabel() {
        return errorLabel;
    }

    public TestPolaczeniaThread(JLabel label) {
        this.errorLabel = label;
        this.connectionInfo = new ConnectionInfo();

        try {
            this.socket = new Socket(connectionInfo.getHost(), connectionInfo.getPort());
            this.writer = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            init();
        } catch (IOException e) {
            getErrorLabel().setText("Wystąpił błąd przy otwieraniu połączenia z serwerem\n");
        }
    }

    public void init() {
        start();
    }

    public void run() {

        try {
            //wysyłam wiadomość testową
            WebMessage msg = new WebMessage(MessageType.TEST_MESSAGE, new String[]{});
            writer.println(msg.encode());

            //czekam na odpowiedź
            WebMessage newMsg;
            String line;

            var x = getReader().readLine();
            var y = WebMessage.decode(x);
            if (y.getTypeOfMessage() == MessageType.TEST_MESSAGE) {
                getErrorLabel().setText("Pomyślnie nawiązano połączenie z serwerem.");
                getErrorLabel().setForeground(Color.GREEN);
            }

            System.out.println(x);
        } catch (Exception ex) {
            getErrorLabel().setText("Brak połączenia z serwerem.");
            getErrorLabel().setForeground(Color.RED);

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            WebMessage msg = new WebMessage(MessageType.TEST_MESSAGE, new String[]{});
            writer.println(msg.encode());

            //czekam na odpowiedź
            WebMessage newMsg;
            String line;

            var x = getReader().readLine();
            var y = WebMessage.decode(x);
            if (y.getTypeOfMessage() == MessageType.TEST_MESSAGE) {
                getErrorLabel().setText("Pomyślnie nawiązano połączenie z serwerem.");
                getErrorLabel().setForeground(Color.GREEN);
            }

            System.out.println(x);
        } catch (Exception ex) {
            getErrorLabel().setText("Brak połączenia z serwerem.");
            getErrorLabel().setForeground(Color.RED);

        }
    }

}
