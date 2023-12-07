/**
 * GameBoard service.
 */
package nye.progtech.services;

import nye.progtech.Colors;
import nye.progtech.dao.BoardDetails;
import nye.progtech.dao.Tile;
import nye.progtech.controller.ConsoleController;
import nye.progtech.model.GameBoard;
import nye.progtech.repository.DBRepositoryInterface;
import nye.progtech.fileutils.FileLoader;
import nye.progtech.fileutils.JSONHandler;

import java.io.IOException;
import java.util.List;

public class GameBoardService {
    /**
     * DBRepository interface.
     */
    private final DBRepositoryInterface dbRepository;
    /**
     * ASCII kód, Nagybetű int-re konvertáláshoz ennyit
     * kell hozzáadni az indexhez.
     */
    private static final int ASCII_ADDITION = 65;
    /**
     * GameBoard service konstruktor.
     *
     * @param gbsDbRepository dbRepository
     */
    public GameBoardService(final DBRepositoryInterface gbsDbRepository) {
        this.dbRepository = gbsDbRepository;
    }

    /**
     * File betöltés.
     *
     * @param directory választott könyvtár
     * @return GameBoard objektum
     */
    public GameBoard performFileLoading(final String directory) {
        try {
            String choosenFile =
                    ConsoleController.chooseFileFromDirectory(directory);
            if (choosenFile != null) {
                GameBoard gameBoard = FileLoader.loadBoard(choosenFile);
                return gameBoard;
            }
        } catch (IOException e) {
            System.err.println(Colors.ANSI_RED
                    + "Hiba történt a file beolvasása során: "
                    + e.getMessage()
                    + Colors.ANSI_RESET);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSON file-ból betöltés.
     *
     * @param directory kiválasztott könyvtár
     * @return gameboard objektum
     */
    public GameBoard performJSONLoading(final String directory) {
        String choosenFile =
                ConsoleController.chooseFileFromDirectory(directory);
        if (choosenFile != null) {
            GameBoard gameBoard =
                    JSONHandler.loadGameBoardFromJson(choosenFile);
            return gameBoard;
        }
        return null;
    }

    /**
     * Adatbázisból betöltés.
     *
     * @param tiles Tiles objektum
     * @param bd boardDetails objektum
     * @return gameboard objektum
     */
    public GameBoard loadBoardFromDB(final List<Tile> tiles,
                                     final BoardDetails bd) {

        int boardSize = bd.getBoardSize();
        char[][] board = new char[boardSize][boardSize];
        char[][] originalBoard = new char[boardSize][boardSize];

        for (Tile tile : tiles) {
            board[tile.getRow()][tile.getColumn()] = tile.getContent();
            if (tile.getContent() == 'G'
                    || tile.getContent() == 'H'
                    || tile.getContent() == 'U') {
                originalBoard[tile.getRow()][tile.getColumn()] = '_';
            } else {
                originalBoard[tile.getRow()][tile.getColumn()]
                        = tile.getContent();
            }
        }

        //heroRow
        int heroRow = bd.getHeroRowIndex() + 1;
        //heroColumn
        char heroColumn = (char) (bd.getHeroColIndex() + ASCII_ADDITION);
        //heroDirection
        char heroDirection = bd.getHeroDirection();
        //fileName
        String fileName = bd.getMapName();

        return new GameBoard(boardSize, board, originalBoard, heroColumn,
                heroRow, heroDirection, fileName);

    }
}

