package nye.progtech;

import java.io.IOException;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();

    public static void main(String[] args) {
        String userName = promptForUserName();
        boolean isRunning = true;

        while (isRunning) {
            menu.displayMainMenu();
            int choice = menu.getUserChoice();

            switch (choice) {
                case 1:
                    System.out.println("Pályaszerkesztés lesz");
                    break;
                case 2:
                    performFileLoading();
                    break;
                case 3:
                    System.out.println("Adatbázisból betöltés lesz");
                case 4:
                    System.out.println("Adatbázisba mentés lesz");
                    break;
                case 5:
                    System.out.println("Játszás lesz");
                    break;
                case 6:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Rossz választás, próbáld újra, kedves " + userName);
            }
        }

        System.out.println("Köszi, hogy játszottál, " + userName + "!");

    }

    private static String promptForUserName() {
        System.out.println("Kérlek add meg a neved: ");
        return scanner.nextLine();
    }

    private static int getUserChoice() {
        System.out.println("Kérlek válassz a menüből: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Nem megfelelő választás. Kérlek próbáld újra.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void performFileLoading() {
        try {
            String choosenFile = menu.chooseFileFromDirectory("worlds");
            if (choosenFile != null) {
                GameBoard gameBoard = FileLoader.loadBoard(choosenFile);
                gameBoard.displayBoard();
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a file beolvasása során: " + e.getMessage());
            e.printStackTrace();
        }
    }
}