package nye.progtech.repository;

import nye.progtech.DAO.BoardDetails;
import nye.progtech.DAO.Tile;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Player;

import java.util.List;

public interface DBRepositoryInterface {
    List<Tile> selectTilesByMapName(String mapName);

    BoardDetails selectBoardDetailsByMapName(String mapName);

    List<String> getAllMapNames();

    void saveGameBoardToDB(GameBoard gameBoard);

    void saveGameBoardDetailsToDB(GameBoard gameBoard);

    void saveScoreBoardToDB(Player player);
}
