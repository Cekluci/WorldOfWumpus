package nye.progtech.interfaces;

import nye.progtech.DAO.Tile;

import java.util.List;

public interface DBRepositoryInterface {
    List<Tile> selectTilesByMapName(String mapName);
    void insertTile(Tile tile);
}
