package helpers;

import java.io.*;
import javax.swing.JOptionPane;

/**
 * Klasa dla danych konfiguracyjnych serwera
 */
public class ConnectionInfo {

    private static final String FILENAME = "config.txt";
    private String host;
    private int port;

    /**
     * getter dla adresu IP
     *
     * @return adres IP/host serwera
     */
    public String getHost() {
        return host;
    }

    /**
     * setter dla adresu IP/hosta
     *
     * @param host typu String
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * getter dla portu serwera
     *
     * @return port (int)
     */
    public int getPort() {
        return port;
    }

    /**
     * setter dla portu serwera
     *
     * @param port (int)
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * *
     * Konstruktor bezargumentowy dla klasy ConnectionInfo, Wczytuje plik
     * konfiguracyjny, trzymający adres IP i port serwera
     */
    public ConnectionInfo() {
        try {
            FileReader fileReader = new FileReader(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            this.host = bufferedReader.readLine();
            this.port = Integer.parseInt(bufferedReader.readLine());

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Brak pliku konfiguracyjnego!", "Błąd", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Wystąpił problem z plikiem konfiguracyjnym!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * *
     * Metoda zapisująca do pliku dane konfiguracyjne
     */
    public void ZapiszConnectionInfoDoPliku() {
        try {
            FileWriter fileWriter = new FileWriter(FILENAME);
            fileWriter.write(host + "\n" + port);
            fileWriter.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Wystąpił błąd przy zapisie do pliku!", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}
