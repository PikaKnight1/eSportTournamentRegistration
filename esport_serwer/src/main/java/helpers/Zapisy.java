/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa dotycząca zapisów do turnieju
 *
 * @author pikak
 */
public class Zapisy {

    /**
     * Metoda pozwalająca wyciągnąć wszystkie zapisu z pliku
     *
     * @return
     */
    public static String[] GetAllZapisy() {
        String FILENAME = "zapisydb.txt";
        ArrayList<String> zapisy = new ArrayList<String>();

        try {
            FileReader fileReader = new FileReader(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.isEmpty()) {
                    zapisy.add(line);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Brak pliku");
        } catch (IOException ex) {
            System.out.println("Problem z plikiem");
        }

        String[] rtn = new String[zapisy.size()];
        rtn = zapisy.toArray(rtn);

        return rtn;
    }

    /**
     * Metoda dodająca zapis do pliku
     *
     * @param dane
     */
    public static void AddZapisToFile(String[] dane) {
        String FILENAME = "zapisydb.txt";

        try {
            FileWriter fileWriter = new FileWriter(FILENAME, true);
            fileWriter.write("\n" + dane[0] + ";" + dane[1] + ";" + dane[2] + ";" + dane[3]);
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Nie udało się dopisać do pliku turnieju");
        }
    }
}
