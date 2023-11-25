package nye.progtech;

import nye.progtech.interfaces.DBRepositoryInterface;
import nye.progtech.services.GameBoardService;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;



// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();
    private static DBRepositoryInterface dbRepository;
    private static GameBoardService gameBoardService;
    private static GameBoard gameBoard;

    public static void main(String[] args) throws SQLException {
        //h2 adatbázis létrehozása
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:wumpusgame;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        DBInitializer dbInitializer = new DBInitializer(dataSource);
        dbInitializer.initializeDB();

        dbRepository = new DBRepositoryImpl(dataSource);
        gameBoardService = new GameBoardService(dbRepository);

        //H2 web portal engedélyezése
        org.h2.tools.Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

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
                    gameBoard = performFileLoading();
                    break;
                case 3:
                    System.out.println("Adatbázisból betöltés lesz");
                case 4:
                    System.out.println("Adatbázisba mentés lesz");
                    System.out.println("A jelenlegi tábla:");
                    gameBoard.displayBoard();
                    if (gameBoard != null) {
                        saveCurrentGameBoard(gameBoard);
                    } else {
                        System.out.println("Nem sikerült elmenteni a pályát.");
                    }
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

    private static GameBoard performFileLoading() {
        try {
            String choosenFile = menu.chooseFileFromDirectory("worlds");
            if (choosenFile != null) {
                GameBoard gameBoard = FileLoader.loadBoard(choosenFile);
                gameBoard.displayBoard();
                return gameBoard;
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a file beolvasása során: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static void saveCurrentGameBoard(GameBoard gameBoard) {
        if (gameBoard != null) {
            gameBoardService.saveBoardToDB(gameBoard);
            System.out.println("A játéktábla sikeresen mentve az adatbázisba.");
        } else {
            System.out.println("Nincs mentendő játéktábla, vagy a pálya azonosítója érvénytelen.");
        }
    }
}