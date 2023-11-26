package nye.progtech;

import nye.progtech.controller.ConsoleController;
import nye.progtech.controller.Menu;
import nye.progtech.db.DBInitializer;
import nye.progtech.repository.DBRepositoryInterface;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.services.GameBoardService;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**.
     * Scanner objektum létrehozása
     */
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();
    private static DBRepositoryInterface dbRepository;

    private static Hero hero;

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        ConsoleController consoleController = new ConsoleController(scanner);
        GameBoardService gameBoardService = new GameBoardService(dbRepository);

        GameBoard gameBoard = null;

        //DB inicializálás és előzetes feltöltés
        DBInitializer.initializeDB();
        DBInitializer.uploadDBWorld();

        DataSource dataSource = DBInitializer.createDataSource();
        DBRepositoryInterface dbRepository = new DBRepositoryImpl(dataSource);


        String userName = consoleController.promptForUserName();
        consoleController.greetUser(userName);

        boolean isRunning = true;

        while (isRunning) {
            menu.displayMainMenu();
            int choice = menu.getUserChoice();

            switch (choice) {
                case 1:
                    System.out.println("Pályaszerkesztés lesz");
                    break;
                case 2:
                    System.out.println("File beolvasás lesz");
                    gameBoard = gameBoardService.performFileLoading("worlds", menu);
                    hero = gameBoard.getHero();
                    if (gameBoard != null) {
                        gameBoard.displayBoard();
                    } else {
                        System.out.println("A file betöltése sikertelen.");
                    }
//                    System.out.println("HeroColumn: "+ hero.getColumn());
//                    System.out.println("HeroRow: " + hero.getRow());
//                    System.out.println("HeroDirection: " + hero.getDirection());
                    break;
                case 3:
                    System.out.println("Adatbázisból betöltés lesz");
                    chooseFileFromDB();
                    break;
                case 4:
                    System.out.println("Adatbázisba mentés lesz");
                    if (gameBoard != null) {
                        dbRepository.saveGameBoardToDB(gameBoard);
                        dbRepository.saveGameBoardDetailsToDB(gameBoard);
                        System.out.println("A tábla sikeresen mentésre került az adatbázisba.");
                    } else {
                        System.out.println("Nem sikerült elmenteni a pályát.");
                    }
                    break;
                case 5:
                    System.out.println("Játszás lesz");
                    break;
                case 6:
                    isRunning = false;
                    System.exit(0);
                    break;
                default:
                    System.out.println("Rossz választás, próbáld újra, kedves " + userName);
            }
        }

        System.out.println("Köszi, hogy játszottál, " + userName + "!");

    }

    private static String chooseFileFromDB() {
        List<String> mapNames = dbRepository.getAllMapNames();

        if (mapNames.isEmpty()) {
            System.out.println("Nincsenek betöltendő map-ok.");
            return null;
        }

        for (int i = 0; i < mapNames.size(); i++) {
            System.out.println((i + 1) + ". " + mapNames.get(i));
        }

        int choice = -1;
        do {
            System.out.println("Válassz egy világot: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Érvénytelen választás, próbáld újra!");
                scanner.next();
            }
            choice = scanner.nextInt();
        } while (choice < 1 || choice > mapNames.size());

        return mapNames.get(choice - 1);
    }
}
