package kjprojekt.esport_serwer;

/**
 * Klasa startowa dla projektu, tylko uruchamia Serwer
 *
 * @author pikak
 */
public class Esport_serwer {

    public static void main(String[] args) {
        System.out.println("Startuje...!");
        new Serwer().Uruchom();
    }
}
