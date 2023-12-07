/**
 * Pályaszerkesztés modul.
 */
package nye.progtech.game;

import nye.progtech.controller.ConsoleController;
import nye.progtech.fileutils.JSONHandler;
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
     *
     * @param editGameBoard gameboard
     */
    public void editGameBoard(final GameBoard editGameBoard) {
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
                    } else if (row - 1 > editGameBoard.getSize() || colInt - 1 > editGameBoard.getSize()) {
                        System.out.println("A pálya határain kívül vagy.");
                    } else if (editGameBoard.getCell(row - 1, colInt - 1) != '_') {
                        System.out.println("Ez a mező már foglalt!");
                    } else {
                        if (editGameBoard.getSize() <= BOARD_SM && wumpusCount > 1) {
                            System.out.println("Nem lehet 1-nél több Wumpus a pályán!");
                        } else if (editGameBoard.getSize() >= BOARD_MID_L && editGameBoard.getSize() <= BOARD_MID_H && wumpusCount > 2) {
                            System.out.println("Nem lehet 2-nél több Wumpus a pályán!");
                        } else if (editGameBoard.getSize() > BOARD_MID_H && wumpusCount > MAX_WUMPUS) {
                            System.out.println("Nem lehet 3-nál több Wumpus a pályán!");
                        } else {
                            if (goldCount > 1) {
                                System.out.println("Nem lehet 1-nél több arany a pályán!");
                            } else {
                                editGameBoard.setCell(row - 1, colInt - 1, content);
                            }
                        }
                    }
                }
                case "delete" -> {
                    if (row - 1 == 0 || colInt - 1 == 0) {
                        System.out.println("Nem törölhetsz falat!");
                    } else if (editGameBoard.getCell(row - 1, colInt - 1) == '_') {
                        System.out.println("Ez a mező már üres!");
                    } else if (editGameBoard.getCell(row - 1, colInt - 1) == 'H') {
                        System.out.println("A Hőst nem törölheted a pályáról!");
                    } else {
                        editGameBoard.setCell(row - 1, colInt - 1, '_');
                    }
                }
                default -> System.out.println("Nem megfelelő parancs.");
            }
            editGameBoard.displayBoard();

        }
        dbRepository.saveGameBoardToDB(editGameBoard);
        dbRepository.saveGameBoardDetailsToDB(editGameBoard);

        jsonHandler.saveToJSON(editGameBoard, editGameBoard.getMapName());

        System.out.println("A tábla mentve az adatbázisba.");
    }

}
