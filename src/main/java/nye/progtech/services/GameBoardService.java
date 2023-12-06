package nye.progtech.services;

import nye.progtech.DAO.BoardDetails;
import nye.progtech.DAO.Tile;
import nye.progtech.controller.ConsoleController;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.repository.DBRepositoryInterface;
import nye.progtech.fileUtils.FileLoader;
import nye.progtech.fileUtils.JSONHandler;

import java.io.IOException;
import java.util.List;

public class GameBoardService {
    private final DBRepositoryInterface dbRepository;
    private DBRepositoryInterface tileDAO;
//    private GameBoard board;

    private Hero hero;
    private GameBoard gameBoard;
    private final ConsoleController consoleController = new ConsoleController();

    public GameBoardService(DBRepositoryInterface dbRepository) {
        this.dbRepository = dbRepository;
    }

    public GameBoard performFileLoading(String directory) {
        try {
            String choosenFile = ConsoleController.chooseFileFromDirectory(directory);
            if (choosenFile != null) {
                GameBoard gameBoard = FileLoader.loadBoard(choosenFile);
                return gameBoard;
            }
        } catch (IOException e) {
            System.err.println("Hiba történt a file beolvasása során: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public GameBoard performJSONLoading(String directory) {
        String choosenFile = ConsoleController.chooseFileFromDirectory(directory);
        if (choosenFile != null) {
            GameBoard gameBoard = JSONHandler.loadGameBoardFromJson(choosenFile);
            return gameBoard;
        }
        return null;
    }

    public GameBoard loadBoardFromDB(List<Tile> tiles, BoardDetails bd) {

        int boardSize = bd.getBoardSize();
        char[][] board = new char[boardSize][boardSize];
        char[][] originalBoard = new char[boardSize][boardSize];

        for (Tile tile : tiles) {
            board[tile.getRow()][tile.getColumn()] = tile.getContent();
            if (tile.getContent() == 'G' || tile.getContent() == 'H' || tile.getContent() == 'U') {
                originalBoard[tile.getRow()][tile.getColumn()] = '_';
            } else {
                originalBoard[tile.getRow()][tile.getColumn()] = tile.getContent();
            }
        }

        //heroRow
        int heroRow = bd.getHeroRowIndex() + 1;
        //heroColumn
        char heroColumn = (char) (bd.getHeroColIndex() + 65);
        //heroDirection
        char heroDirection = bd.getHeroDirection();
        //fileName
        String fileName = bd.getMapName();

        return new GameBoard(boardSize, board, originalBoard, heroColumn, heroRow, heroDirection, fileName);

    }
}

