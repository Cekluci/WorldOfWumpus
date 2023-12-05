package nye.progtech;

import nye.progtech.DAO.BoardDetails;
import nye.progtech.DAO.Tile;
import nye.progtech.Game.Game;
import nye.progtech.Game.MapEditor;
import nye.progtech.controller.ConsoleController;
import nye.progtech.controller.MenuOption;
import nye.progtech.db.DBInitializer;
import nye.progtech.fileUtils.JSONHandler;
import nye.progtech.repository.DBRepositoryInterface;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.services.GameBoardService;

import java.sql.SQLException;
import java.util.List;

public class Main {

    /**
     * .
     * Scanner objektum létrehozása
     */
    private static DBRepositoryInterface dbRepository;

    private static Hero hero;
    private static GameBoard gameBoard;

    public static void main(String[] args) throws SQLException {

        ConsoleController consoleController = new ConsoleController();
        GameBoardService gameBoardService = new GameBoardService(dbRepository);


        //DB inicializálás és előzetes feltöltés
        DBInitializer.dbSetup();

        DBRepositoryInterface dbRepository = new DBRepositoryImpl();

        String userName = consoleController.promptForUserName();
        consoleController.greetUser(userName);

        boolean isRunning = true;

        while (isRunning) {
            consoleController.displayMainMenu();
            MenuOption selectedOption = consoleController.getSelectedOption();

            if (selectedOption != null) {
                switch (selectedOption) {
                    case PALYASZERKESZTO:
                        System.out.println("Pályaszerkesztés lesz");
                        MapEditor mapEditor = new MapEditor();
                        mapEditor.startEditor();
                        break;
                    case FILEBEOLVASAS: //KÉSZ
                        System.out.println("File beolvasás lesz");
                        gameBoard = gameBoardService.performFileLoading("worlds");
                        hero = gameBoard.getHero();
                        if (gameBoard != null) {
                            gameBoard.displayBoard();
                        } else {
                            System.out.println("A file betöltése sikertelen.");
                        }

                        JSONHandler jsonHandler = new JSONHandler();
                        jsonHandler.saveToJSON(gameBoard, "teszt");
                        jsonHandler.saveToXML(gameBoard, "teszt");

                        GameBoard newGameBoard = jsonHandler.loadGameBoardFromJson("Json/teszt.json");
                        if (newGameBoard != null) {
                            System.out.println("------------------json-ból---------");
                            newGameBoard.displayBoard();
                        }
                        break;
                    case BETOLTESADATBAZISBOL: //KÉSZ
                        System.out.println("Adatbázisból betöltés lesz");
                        String choosenMap = consoleController.chooseFileFromDB();
                        BoardDetails bd = dbRepository.selectBoardDetailsByMapName(choosenMap);
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
