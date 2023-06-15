package threads;

import helpers.Auth;
import helpers.MessageType;
import helpers.Turnieje;
import helpers.WebMessage;
import helpers.Zapisy;
import java.io.*;
import java.net.Socket;

/**
 * Wątek klienta
 *
 * @author pikak
 */
public class KlientThread extends Thread {

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public KlientThread(Socket socket) {
        this.socket = socket;
        try {
            writer = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Wystąpił błąd przy otwieraniu połączenia z serwerem");
        }

    }

    public void init() {
        start();
    }

    @Override
    public void run() {
        //czytam w nieskończoność od serwera
        String line;
        try {
            while (true) {
                line = getReader().readLine();
                //tutaj operuję na otrzymanych wiadomościach
                System.out.println(line);
                var wm = WebMessage.decode(line);
                switch (wm.getTypeOfMessage()) {
                    case TEST_MESSAGE:
                        getWriter().println(line);

                        break;

                    case AUTH_MESSAGE:
                        if (wm.getContent().length > 0) {
                            int result = Auth.AuthorizeUser(wm.getContent()[0], wm.getContent()[1]);
                            WebMessage webMess = new WebMessage(MessageType.AUTH_MESSAGE, new String[]{result + ""});
                            getWriter().println(webMess.encode());

                        } else {

                        }
                        break;
                    case TOURNAMENT_LIST_MESSAGE:
                        System.out.println(line);

                        WebMessage webMess = new WebMessage(MessageType.TOURNAMENT_LIST_MESSAGE, Turnieje.GetAllTurnieje());
                        getWriter().println(webMess.encode());

                        break;
                    case ADD_TOURNAMENT_MESSAGE:
                        System.out.println(line);
                        String[] daneTurnieju = new String[]{wm.getContent()[0], wm.getContent()[1], wm.getContent()[2], wm.getContent()[3]};
                        Turnieje.AddTurniejToFile(daneTurnieju);
                        WebMessage webMessage = new WebMessage(MessageType.ADD_TOURNAMENT_MESSAGE, Turnieje.GetAllTurnieje());
                        getWriter().println(webMessage.encode());
                        break;
                    case ADD_PLAYER_TOURNAMENT_MESSAGE:
                        System.out.println(line);
                        String[] zapis = new String[]{wm.getContent()[0], wm.getContent()[1], wm.getContent()[2], wm.getContent()[3]};
                        Zapisy.AddZapisToFile(zapis);
                        WebMessage message = new WebMessage(MessageType.ADD_TOURNAMENT_MESSAGE, Turnieje.GetAllTurnieje());
                        getWriter().println(message.encode());

                        break;
                }
            }
        } catch (IOException ex) {
        }

        try {
            getWriter().close();
            getReader().close();
            getSocket().close();
        } catch (Exception ex) {
            System.out.println("Błąd przy zamykaniu połączenia");
        }
    }

}
