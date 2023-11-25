package nye.progtech.services;

import nye.progtech.DAO.Tile;
import nye.progtech.GameBoard;
import nye.progtech.interfaces.DBRepositoryInterface;

public class GameBoardService {
    private DBRepositoryInterface dbRepository;
    private DBRepositoryInterface tileDAO;
//    private GameBoard board;

    public GameBoardService(DBRepositoryInterface dbRepository) {
        this.dbRepository = dbRepository;
    }

    public void saveBoardToDB(GameBoard gameBoard) {
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                char content = gameBoard.getCell(row, col);
                Tile tile = new Tile(row, col, content, gameBoard.getMapName());
                dbRepository.insertTile(tile);
            }
        }
    }
}

