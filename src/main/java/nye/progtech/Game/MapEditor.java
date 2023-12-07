/**
 * Pályaszerkesztés modul.
 */
package nye.progtech.Game;

import nye.progtech.controller.ConsoleController;
import nye.progtech.fileUtils.JSONHandler;
import nye.progtech.model.GameBoard;
import nye.progtech.repository.DBRepositoryImpl;
import nye.progtech.repository.DBRepositoryInterface;

/**
 * Pályaszerkesztő osztály.
 */
public class MapEditor {
    /**
     * ASCII kód, Nagybetű int-re konvertáláshoz ennyit
     * kell hozzáadni az indexhez.
     */
    private static final int ASCII_ADDITION = 64;
    /**
     * Row part.
     */
    private static final int ROW_PART = 1;
    /**
     * Col part.
     */
    private static final int COL_PART = 2;
    /**
     * Content part.
     */
    private static final int CONTENT_PART = 3;
    /**
     * Small board size.
     */
    private static final int BOARD_SM = 8;
    /**
     * Medium board size alsó határ.
     */
    private static final int BOARD_MID_L = 9;
    /**
     * Medium board size felső határ.
     */
    private static final int BOARD_MID_H = 14;
    /**
     * Max wumpus.
     */
    private static final int MAX_WUMPUS = 3;
    /**
     * Gameboard objektum.
     */
    private final GameBoard gameBoard = new GameBoard();
    /**
     * DBRepository példányosítás a mentéshez.
     */
    private final DBRepositoryInterface dbRepository = new DBRepositoryImpl();
    /**
     * JSONHandler példányosítás a file-ba mentéshez.
     */
    private final JSONHandler jsonHandler = new JSONHandler();

    /**
     * Üres konstruktor.
     */
    public MapEditor() {
    }

    /**
     * Pályaszerkesztő elindítása.
     */
    public void startEditor() {

        System.out.println("Üdvözöllek a pályaszerkesztőben!");

        int size = ConsoleController.askForBoardSize();
        int heroRow = ConsoleController.askForHeroRow();
        char heroColC = ConsoleController.askForHeroColumn();
        char heroDir = ConsoleController.askForHeroDirection();
        String mapName = ConsoleController.askForMapName();

        char[][] newBoard = gameBoard.createEmptyBoard(size);

        GameBoard newGameBoard = new GameBoard(size, newBoard, newBoard,
                heroColC, heroRow, heroDir, mapName);

        newGameBoard.displayBoard();
        System.out.println("-----szerkesztés után-----");
        editGameBoard(newGameBoard);
    }

    /**
     * Pálya szerkesztése.
     * @param eGameBoard gameboard
     */
    public void editGameBoard(final GameBoard eGameBoard) {
        int wumpusCount = 0;
        int goldCount = 0;
        while (true) {
            String input = ConsoleController.askForEditorInput();

            if ("kilép".equalsIgnoreCase(input)) {
                System.out.println("Kilépés a szerkesztőből.");
                break;
            }

            String[] parts = input.split(",");
            String command = parts[0].trim();
            int row = Integer.parseInt(parts[ROW_PART].trim());
            char col = parts[COL_PART].trim().charAt(0);
            int colInt = Character.toUpperCase(col) - ASCII_ADDITION;
            char content = parts[CONTENT_PART].trim().charAt(0);

            if (content == 'G') {
                goldCount++;
            } else if (content == 'U') {
                wumpusCount++;
            }

            switch (command) {
                case "add" -> {
                    if (row - 1 == 0 || colInt - 1 == 0) {
                        System.out.println("Nem rakhatsz ide semmit, ez itt fal.");
                    } else if (row - 1 > eGameBoard.getSize() || colInt - 1 > eGameBoard.getSize()) {
                        System.out.println("A pálya határain kívül vagy.");
                    } else if (eGameBoard.getCell(row - 1, colInt - 1) != '_') {
                        System.out.println("Ez a mező már foglalt!");
                    } else {
                        if (eGameBoard.getSize() <= BOARD_SM && wumpusCount > 1) {
                            System.out.println("Nem lehet 1-nél több Wumpus a pályán!");
                        } else if (eGameBoard.getSize() >= BOARD_MID_L && eGameBoard.getSize() <= BOARD_MID_H && wumpusCount > 2) {
                            System.out.println("Nem lehet 2-nél több Wumpus a pályán!");
                        } else if (eGameBoard.getSize() > BOARD_MID_H && wumpusCount > MAX_WUMPUS) {
                            System.out.println("Nem lehet 3-nál több Wumpus a pályán!");
                        } else {
                            if (goldCount > 1) {
                                System.out.println("Nem lehet 1-nél több arany a pályán!");
                            } else {
                                eGameBoard.setCell(row - 1, colInt - 1, content);
                            }
                        }
                    }
                }
                case "delete" -> {
                    if (row - 1 == 0 || colInt - 1 == 0) {
                        System.out.println("Nem törölhetsz falat!");
                    } else if (eGameBoard.getCell(row - 1, colInt - 1) == '_') {
                        System.out.println("Ez a mező már üres!");
                    } else if (eGameBoard.getCell(row - 1, colInt - 1) == 'H') {
                        System.out.println("A Hőst nem törölheted a pályáról!");
                    } else {
                        eGameBoard.setCell(row - 1, colInt - 1, '_');
                    }
                }
                default -> System.out.println("Nem megfelelő parancs.");
            }
            eGameBoard.displayBoard();

        }
        dbRepository.saveGameBoardToDB(eGameBoard);
        dbRepository.saveGameBoardDetailsToDB(eGameBoard);

        jsonHandler.saveToJSON(eGameBoard, eGameBoard.getMapName());

        System.out.println("A tábla mentve az adatbázisba.");
    }

}
