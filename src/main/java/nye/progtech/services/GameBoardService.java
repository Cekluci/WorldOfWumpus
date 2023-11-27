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

    public void loadBoard(List<Tile> tiles) {

        char[][] board = new char[6][6];
        for (Tile tile : tiles) {
            board[tile.getRow()][tile.getColumn()] = tile.getContent();
        }

        System.out.print("   "); // Három szóköz a sorszámozás előtt
        for (int i = 0; i < 6; i++) {
            System.out.print((char)('A' + i) + " ");
        }
        System.out.println(); // Új sor a tábla tetején

        for (int i = 0; i < 6; i++) {
            // Sorok számának kiírása
            System.out.print((i + 1) + " ");
            if (i < 9) {
                System.out.print(" ");
            }

            for (int j = 0; j < 6; j++) {
                System.out.print(board[i][j] + " "); // Tábla aktuális mezőjének kiírása
            }
            System.out.println(); // Új sor minden sor után
        }
    }
        //return null;


}

