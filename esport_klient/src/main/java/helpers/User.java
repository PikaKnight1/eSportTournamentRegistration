package helpers;

/**
 * Klasa zawierająca dane użytkownika Typ użytkownika oznaczony jest cyfrą: 0 =
 * niezalogowany, 1 = zwykły, 2 = administrator
 */
public class User {

    String Username;
    int TypeOfUser;

    /**
     * Getter dla nazwy użytkownika
     *
     * @return nazwa użytkownika jako String
     */
    public String getUsername() {
        return Username;
    }

    /**
     * Getter dla typu użytkownika
     *
     * @return typ użytkownika jako int (0 = niezalogowany, 1 = zalogowany, 2 =
     * administrator)
     */
    public int getTypeOfUser() {
        return TypeOfUser;
    }

    public User(String Username, int TypeOfUser) {
        this.Username = Username;
        this.TypeOfUser = TypeOfUser;
    }
}
