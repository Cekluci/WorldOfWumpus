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

    public GameBoard loadBoardFromDB(String mapName) {
        List<Tile> tiles = dbRepository.selectTilesByMapName(mapName);
        List<BoardDetails> boardDetails = null;
        GameBoard gameBoard = new GameBoard();
        //int size = calculateBoardSize(tiles);
        return null;
    }


}

