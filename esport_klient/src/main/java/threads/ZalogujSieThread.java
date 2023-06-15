package threads;

import helpers.ConnectionInfo;
import helpers.MessageType;
import helpers.User;
import helpers.WebMessage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import views.ListaTurniejowView;

/**
 * Klasa dziedzicząca po Thread implementująca ActionListener, Wątek używany do
 * wysyłania wiadomości służącej do zalogowania sie
 *
 * @author pikak
 */
public class ZalogujSieThread extends Thread implements ActionListener {

    private ConnectionInfo connectionInfo;
    private JLabel errorLabel;
    private JTextField name;
    private JTextField password;

    public JTextField getUsername() {
        return name;
    }

    public JTextField getPassword() {
        return password;
    }

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

    public ZalogujSieThread(JLabel label, JTextField name, JTextField pass) {
        this.errorLabel = label;
        this.name = name;
        this.password = pass;

        this.connectionInfo = new ConnectionInfo();

        try {
            socket = new Socket(connectionInfo.getHost(), connectionInfo.getPort());
            writer = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            init();
        } catch (IOException e) {
            getErrorLabel().setText("Wystąpił błąd przy otwieraniu połączenia z serwerem\n");
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
            WebMessage msg = new WebMessage(MessageType.AUTH_MESSAGE, new String[]{getUsername().getText(), getPassword().getText()});
            writer.println(msg.encode());

            //czekam na odpowiedź
            WebMessage newMsg;
            String line;

            var x = getReader().readLine();
            var y = WebMessage.decode(x);
            if (y.getTypeOfMessage() == MessageType.AUTH_MESSAGE) {
                switch (Integer.parseInt(y.getContent()[0])) {
                    case 0:
                        getErrorLabel().setText("Zły login lub hasło :D");
                        getErrorLabel().setForeground(Color.RED);
                        break;
                    case 1:
                        User user = new User(getUsername().getText(), 1);
                        new ListaTurniejowView(user).setVisible(true);
                        getErrorLabel().setText("Witaj User :D");
                        getErrorLabel().setForeground(Color.GREEN);
                        break;
                    case 2:
                        User admin = new User(getUsername().getText(), 2);
                        new ListaTurniejowView(admin).setVisible(true);

                        getErrorLabel().setText("Witaj Admin :D");
                        getErrorLabel().setForeground(Color.BLUE);
                        break;
                    default:
                        getErrorLabel().setText("Zły login lub hasło :D");
                        getErrorLabel().setForeground(Color.RED);
                        break;
                }
            }

            System.out.println(x);
        } catch (Exception ex) {
            System.out.println(ex);
            getErrorLabel().setText("Brak połączenia z serwerem.");
            getErrorLabel().setForeground(Color.RED);

        }
    }

}
