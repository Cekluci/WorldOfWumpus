/**
 * Main package, itt indul az alkalmazás.
 */
package nye.progtech;

import nye.progtech.dao.BoardDetails;
import nye.progtech.dao.ScoreBoard;
import nye.progtech.dao.Tile;
import nye.progtech.game.Game;
import nye.progtech.game.MapEditor;
import nye.progtech.controller.ConsoleController;
import nye.progtech.controller.FileFormat;
import nye.progtech.controller.MenuOption;
import nye.progtech.db.DBInitializer;
import nye.progtech.model.Player;
import nye.progtech.repository.DBRepositoryInterface;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.services.GameBoardService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    /**
     * DBRepository objektum.
     */
    private static DBRepositoryInterface dbRepository;
    /**
     * Hős objektum.
     */
    private static Hero hero;
    /**
     * gameBoard objektum.
     */
    private static GameBoard gameBoard;

    /**
     * A program belépési pontja.
     *
     * @param args argumentumok
     * @throws SQLException sql hiba
     */
    public static void main(final String[] args) throws SQLException {

        ConsoleController consoleController = new ConsoleController();
        GameBoardService gameBoardService = new GameBoardService(dbRepository);
        Player player = new Player();


        //DB inicializálás és előzetes feltöltés
        DBInitializer.dbSetup();

        DBRepositoryInterface dbRepository = new DBRepositoryImpl();

        String userName = consoleController.promptForUserName();
        consoleController.greetUser(userName);

        player.setPlayerName(userName);
        player.setPlayerScore(0);

        boolean isRunning = true;

        while (isRunning) {
            consoleController.displayMainMenu();
            MenuOption selectedOption = ConsoleController.getSelectedOption();

            if (selectedOption != null) {
                switch (selectedOption) {
                    case PALYASZERKESZTO:
                        MapEditor mapEditor = new MapEditor();
                        mapEditor.startEditor();
                        break;
                    case FILEBEOLVASAS:
                        consoleController.displayFormatSelectorMenu();
                        FileFormat selectedFormat =
                                ConsoleController.getSelectedFileFormat();
                        if (selectedFormat != null) {
                            switch (selectedFormat) {
                                case JSON:
                                    gameBoard = gameBoardService.performJSONLoading("Json");
                                    hero = gameBoard.getHero();
                                    if (gameBoard != null) {
                                        gameBoard.displayBoard();
                                    } else {
                                        System.out.println("A file betöltése sikertelen.");
                                    }
                                    break;
                                case XML:
                                    break;
                                case TXT:
                                    gameBoard = gameBoardService.performFileLoading("worlds");
                                    hero = gameBoard.getHero();
                                    if (gameBoard != null) {
                                        gameBoard.displayBoard();
                                    } else {
                                        System.out.println("A file betöltése sikertelen.");
                                    }
                                    break;
                                default:
                                    System.out.println(Colors.ANSI_RED
                                                        + "Nem megfelelő választás. Próbáld újra"
                                                        + Colors.ANSI_RESET);
                            }
                        }
                        break;
                    case BETOLTESADATBAZISBOL:
                        String choosenMap = ConsoleController.chooseFileFromDB();
                        BoardDetails bd = dbRepository.selectBoardDetailsByMapName(choosenMap);
                        List<Tile> tiles = dbRepository.selectTilesByMapName(choosenMap);
                        gameBoard = gameBoardService.loadBoardFromDB(tiles, bd);
                        hero = gameBoard.getHero();
                        if (gameBoard != null) {
                            gameBoard.displayBoard();
                        } else {
                            System.out.println("A tábla betöltése sikertelen.");
                        }
                        break;
                    case METESADATBAZISBA:
                        if (gameBoard != null) {
                            dbRepository.saveGameBoardToDB(gameBoard);
                            dbRepository.saveGameBoardDetailsToDB(gameBoard);
                            System.out.println("A tábla sikeresen mentésre került az adatbázisba.");
                        } else {
                            System.out.println("Nem sikerült elmenteni a pályát.");
                        }
                        break;
                    case JATEK:
                        Game game = new Game(hero, gameBoard, player);
                        game.start();
                        break;
                    case SCOREBOARD:
                        List<ScoreBoard> scoreboard = dbRepository.getScoreBoard();

                        System.out.println("Scoreboard:");
                        for (int i = 0; i < scoreboard.size(); i++) {
                            ScoreBoard entry = scoreboard.get(i);
                            System.out.printf("%d. %s - %d pont\n", i + 1, entry.getPlayerName(), entry.getPlayerScore());
                        }
                        break;
                    case KILEPES:
                        isRunning = false;
                        System.out.println(Colors.ANSI_GREEN
                                + "Köszi, hogy játszottál, "
                                + userName
                                + "!"
                                + Colors.ANSI_RESET);
                        ConsoleController.closeScanner();
                        System.exit(0);
                        break;
                    default:
                        System.out.println(Colors.ANSI_RED
                                + "Rossz választás! Próbáld újra, kedves "
                                + userName
                                + Colors.ANSI_RESET);
                        break;
                }
            }
        }
    }

}
