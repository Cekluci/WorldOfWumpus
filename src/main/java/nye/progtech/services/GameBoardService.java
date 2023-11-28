package nye.progtech.services;

import nye.progtech.DAO.BoardDetails;
import nye.progtech.DAO.Tile;
import nye.progtech.controller.Menu;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.repository.DBRepositoryInterface;
import nye.progtech.util.FileLoader;

import java.io.IOException;
import java.util.List;

public class GameBoardService {
    private DBRepositoryInterface dbRepository;
    private DBRepositoryInterface tileDAO;
//    private GameBoard board;

    private Hero hero;
    private GameBoard gameBoard;

    public GameBoardService(DBRepositoryInterface dbRepository) {
        this.dbRepository = dbRepository;
    }

    public GameBoard performFileLoading(String directory, Menu menu) {
        try {
            String choosenFile = menu.chooseFileFromDirectory(directory);
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

    public GameBoard loadBoardFromDB(List<Tile> tiles, BoardDetails bd) {

        int boardSize = bd.getBoardSize();
        char[][] board = new char[boardSize][boardSize];

        for (Tile tile : tiles) {
            board[tile.getRow()][tile.getColumn()] = tile.getContent();
        }

        //heroRow
        int heroRow = bd.getHeroRowIndex();
        //heroColumn
        char heroColumn = (char) (bd.getHeroColIndex() + 64);
        //heroDirection
        char heroDirection = bd.getHeroDirection();
        //fileName
        String fileName = bd.getMapName();

        return new GameBoard(boardSize, board, heroColumn, heroRow, heroDirection, fileName);
        //display-ből kimásolva egyelőre
//        System.out.print("   "); // Három szóköz a sorszámozás előtt
//        for (int i = 0; i < 6; i++) {
//            System.out.print((char)('A' + i) + " ");
//        }
//        System.out.println(); // Új sor a tábla tetején
//
//        for (int i = 0; i < 6; i++) {
//            // Sorok számának kiírása
//            System.out.print((i + 1) + " ");
//            if (i < 9) {
//                System.out.print(" ");
//            }
//
//            for (int j = 0; j < 6; j++) {
//                System.out.print(board[i][j] + " "); // Tábla aktuális mezőjének kiírása
//            }
//            System.out.println(); // Új sor minden sor után
//        }
//    }
//        //return null;

    }
}

