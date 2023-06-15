package kjprojekt.esport_klient;
import views.LogowanieView;

public class Esport_klient {

    /**
     * Metoda startowa dla programu, otwierająca wyłącznie ekran logowania
     */
    public static void main(String[] args) {
        new LogowanieView().setVisible(true);
    }
}
