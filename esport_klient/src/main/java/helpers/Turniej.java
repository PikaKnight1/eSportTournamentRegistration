package helpers;

/**
 * Klasa dla danych turniejów
 */
public class Turniej {

    private String nazwa;
    private String gra;
    private String liczbaGraczy;
    private String data;

    /**
     * Konstruktor przyjmujący
     *
     * @param nazwa nazwa turnieju
     * @param gra gra w jaką gracze będą grali
     * @param liczbaGraczy maksymalna liczab graczy biorąca udział w turnieju
     * @param data data planowanego turnieju
     */
    public Turniej(String nazwa, String gra, String liczbaGraczy, String data) {
        this.nazwa = nazwa;
        this.gra = gra;
        this.liczbaGraczy = liczbaGraczy;
        this.data = data;
    }
}
