package nye.progtech.controller;

import nye.progtech.db.DBInitializer;
import nye.progtech.fileUtils.FileLoader;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;

import javax.sql.DataSource;
import java.util.List;
import java.util.Scanner;

public class ConsoleController {
    private Scanner scanner;

    public ConsoleController() {
        this.scanner = new Scanner(System.in);
    }

    public void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

    public String promptForUserName() {
        System.out.println("Szia Idegen! Kérlek add meg a neved: ");
        return scanner.nextLine();
    }

    public void greetUser(String userName) {
        System.out.println("Üdvözöllek, " + userName + "! Kezdjünk is neki a játéknak.");
    }

    //Editor
    public int askForBoardSize() {
        System.out.println("Add meg a pálya méretét: ");
        return scanner.nextInt();
    }

    public int askForHeroRow() {
        System.out.println("Add meg a hős sorát: ");
        return scanner.nextInt();
    }
    public char askForHeroColumn() {
        System.out.println("Add meg a hős oszlopát: ");
        return scanner.next().charAt(0);
    }
    public char askForHeroDirection() {
        System.out.println("Add meg a hős irányát (N, E, S, W): ");
        scanner.nextLine();
        return scanner.next().charAt(0);
    }
    public String askForMapName() {
        System.out.println("Add meg a pálya nevét: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    public String askForEditorInput() {
        System.out.println("Add meg a pályaelemet a következő formátumban: [add|delete], sorszám, oszlop, pályaelem");
        System.out.println("Pályaelem lehet a következő: U - Wumpus, P - Verem, G - Arany");
        System.out.println("Befejezéshez gépeld be a 'kilép' parancsot.");
        return scanner.nextLine();
    }

    //Main menu
    public void displayMainMenu() {
        System.out.println("\n--- Főmenü ---");
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getValue() + ". " + option.getDescription());
        }
    }

    public MenuOption getSelectedOption() {
        int selectedOptionIndex = scanner.nextInt();
        return MenuOption.fromInt(selectedOptionIndex);
    }

    public String chooseFileFromDirectory(String directoryPath) {
        List<String> fileNames = FileLoader.listFilesInDirectory(directoryPath);

        if (fileNames.isEmpty()) {
            System.out.println("Nincsenek betöltendő file-ok.");
            return null;
        }

        for (int i = 0; i < fileNames.size(); i++) {
            System.out.println((i + 1) + ". " + fileNames.get(i));
        }

        int choice = -1;
        do {
            System.out.println("Válassz egy világot: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Érvénytelen választás, próbáld újra!");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > fileNames.size());

        return fileNames.get(choice - 1);
    }

    public String chooseFileFromDB() {
        DataSource dataSource = DBInitializer.createDataSource();
        DBRepositoryInterface dbRepository = new DBRepositoryImpl(dataSource);

        List<String> mapNames = dbRepository.getAllMapNames();
        if (mapNames.isEmpty()) {
            System.out.println("Nincsenek elmentett pályák az adatbázisban.");
            return null;
        }

        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ". " + mapNames.get(i));
        }

        int choice = -1;
        do {
            System.out.println("Válassz egy világot az adatbázisból.");
            while (!scanner.hasNextInt()) {
                System.out.println("Érvénytelen választás, próbáld újra!");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > mapNames.size());

        return mapNames.get(choice - 1);
    }
}
