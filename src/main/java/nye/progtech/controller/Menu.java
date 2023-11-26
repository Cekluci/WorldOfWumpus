package nye.progtech.controller;

import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.util.FileUtils;
import nye.progtech.repository.DBRepositoryInterface;

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
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Pályaszerkesztő");
        System.out.println("2. Fájl beolvasás");
        System.out.println("3. Betöltés adatbázisból");
        System.out.println("4. Mentés adatbázisba");
        System.out.println("5. Játék");
        System.out.println("6. Kilépés");
    }

    public int getUserChoice() {
        int choice = 0;

        do {
            System.out.print("Kérlek válassz: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Nem érvényes opció. Válassz egy menüpontot.");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > 6);

        return choice;
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

}
