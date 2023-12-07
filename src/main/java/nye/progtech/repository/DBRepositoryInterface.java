/**
 * DBRepository Interface.
 * Adatbázis műveletekhez.
 */
package nye.progtech.repository;

import nye.progtech.DAO.BoardDetails;
import nye.progtech.DAO.ScoreBoard;
import nye.progtech.DAO.Tile;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Player;

import java.util.List;

public interface DBRepositoryInterface {
    /**
     * Pályanév alapján a játéktér mezőinek
     * Tile DAO listába lekérése.
     * @param mapName pálya neve
     * @return lista a mezőelemekről
     */
    List<Tile> selectTilesByMapName(String mapName);

    /**
     * boardDetails adatok lekérése
     * a pályanév alapján.
     * @param mapName pálya neve
     * @return boardDetails objektum.
     */
    BoardDetails selectBoardDetailsByMapName(String mapName);

    /**
     * Lista az összes pályanévről.
     * @return lista a pályanevekből
     */
    List<String> getAllMapNames();

    /**
     * gameboard objektum elmentése adatbázisba.
     * @param gameBoard gameboard objektum
     */
    void saveGameBoardToDB(GameBoard gameBoard);

    /**
     * gameboard details elmentése az
     * adatbázisba.
     * @param gameBoard gameboard objektum
     */
    void saveGameBoardDetailsToDB(GameBoard gameBoard);

    /**
     * scoreboard elmentése az adatbázisba.
     * @param player player objektum
     */
    void saveScoreBoardToDB(Player player);

    /**
     * scoreboard lekérése az adatbázisból.
     * @return lista a pontszámokról
     */
    List<ScoreBoard> getScoreBoard();
}
