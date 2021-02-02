package Pacman.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileScanner {
    public String readMapFromTxt(){
        try {
            File map = new File("src/resources/map.txt");
            Scanner myReader = new Scanner(map);
            StringBuilder stringMap = new StringBuilder();
            while(myReader.hasNext())
                stringMap.append(myReader.next());
            myReader.close();
            return String.valueOf(stringMap);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }
}
