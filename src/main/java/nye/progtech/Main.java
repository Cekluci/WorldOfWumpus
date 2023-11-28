package nye.progtech;

import nye.progtech.DAO.BoardDetails;
import nye.progtech.DAO.Tile;
import nye.progtech.Game.Game;
import nye.progtech.controller.ConsoleController;
import nye.progtech.controller.Menu;
import nye.progtech.controller.MenuOption;
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

    /**
     * .
     * Scanner objektum létrehozása
     */
    private static Scanner scanner = new Scanner(System.in);
    private static Menu menu = new Menu();
    private static DBRepositoryInterface dbRepository;

    private static Hero hero;
    private static GameBoard gameBoard;

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        ConsoleController consoleController = new ConsoleController(scanner);
        GameBoardService gameBoardService = new GameBoardService(dbRepository);

        //GameBoard gameBoard = null;

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
            MenuOption selectedOption = menu.getSelectedOption();

            if (selectedOption != null) {
                switch (selectedOption) {
                    case PALYASZERKESZTO:
                        System.out.println("Pályaszerkesztés lesz");
                        break;
                    case FILEBEOLVASAS: //KÉSZ
                        System.out.println("File beolvasás lesz");
                        gameBoard = gameBoardService.performFileLoading("worlds", menu);
                        hero = gameBoard.getHero();
                        if (gameBoard != null) {
                            gameBoard.displayBoard();
                        } else {
                            System.out.println("A file betöltése sikertelen.");
                        }
                        break;
                    case BETOLTESADATBAZISBOL: //KÉSZ
                        System.out.println("Adatbázisból betöltés lesz");
                        String choosenMap = menu.chooseFileFromDB();
                        BoardDetails bd = (BoardDetails) dbRepository.selectBoardDetailsByMapName(choosenMap);
                        List<Tile> tiles = dbRepository.selectTilesByMapName(choosenMap);
                        System.out.println("----------betöltött map-----------");
                        gameBoard = gameBoardService.loadBoardFromDB(tiles, bd);
                        hero = gameBoard.getHero();
                        if (gameBoard != null) {
                            gameBoard.displayBoard();
                        } else {
                            System.out.println("A tábla betöltése sikertelen.");
                        }
                        break;
                    case METESADATBAZISBA: //KÉSZ
                        System.out.println("Adatbázisba mentés lesz");
                        if (gameBoard != null) {
                            dbRepository.saveGameBoardToDB(gameBoard);
                            dbRepository.saveGameBoardDetailsToDB(gameBoard);
                            System.out.println("A tábla sikeresen mentésre került az adatbázisba.");
                        } else {
                            System.out.println("Nem sikerült elmenteni a pályát.");
                        }
                        break;
                    case JATEK:
                        System.out.println("Játszás lesz");
                        Game game = new Game(hero, gameBoard);
                        game.start();
                        break;
                    case KILEPES: //KÉSZ
                        isRunning = false;
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Rossz választás, próbáld újra, kedves " + userName);
                }
                System.out.println("Köszi, hogy játszottál, " + userName + "!");
            }
        }
    }
}
