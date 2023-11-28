package nye.progtech.controller;

import nye.progtech.db.DBInitializer;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.util.FileUtils;
import nye.progtech.repository.DBRepositoryInterface;

import javax.sql.DataSource;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private static DBRepositoryInterface dbRepository;

    public Menu() {
        this.scanner = new Scanner(System.in);
        //this.dbRepository = new DBRepositoryImpl();
    }

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
        List<String> fileNames = FileUtils.listFilesInDirectory(directoryPath);

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
