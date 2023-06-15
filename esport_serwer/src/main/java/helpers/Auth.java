/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa dla autoryzacji użytkowników
 *
 * @author pikak
 */
public class Auth {

    /**
     * Metoda do autoryzacji użytkownika Zwracana informacja jako int: 0 -> brak
     * autoryzacji 1 -> user zwykły 2 -> administrator
     */
    public static int AuthorizeUser(String username, String password) {
        ArrayList<User> userList = GetAllUsers();
        for (var user : userList) {
            if (user.name.equals(username) && (user.pass.equals(password))) {
                return user.authy;
            }
        }
        return 0;
    }

    /**
     * Prywatna metoda pozwalająca wyciągnąć wszystkich użytkowników z bazy
     *
     * @return listę (ArrayList) wszystkich użytkowników
     */
    private static ArrayList<User> GetAllUsers() {
        String FILENAME = "authdb.txt";
        ArrayList<User> users = new ArrayList<User>();

        try {
            FileReader fileReader = new FileReader(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                var tmp = line.split(";");
                users.add(new User(tmp[0], tmp[1], Integer.parseInt(tmp[2])));
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Brak pliku");
        } catch (IOException ex) {
            System.out.println("Problem z plikiem");
        }
        return users;
    }
}

class User {

    String name;
    String pass;
    int authy;

    User(String n, String p, int a) {
        this.name = n;
        this.pass = p;
        this.authy = a;
    }
}
