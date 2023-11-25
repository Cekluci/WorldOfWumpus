package nye.progtech;

import nye.progtech.DAO.Tile;
import nye.progtech.interfaces.DBRepositoryInterface;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBRepositoryImpl implements DBRepositoryInterface {
    private DataSource dataSource;

    public DBRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Tile> selectTilesByMapName(String mapName) {
        List<Tile> tiles = new ArrayList<>();
        String sql = "SELECT * FROM tiles WHERE mapName = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mapName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tile tile = new Tile();
                    tile.setRow(rs.getInt("rowindex"));
                    tile.setColumn(rs.getInt("columnindex"));
                    tile.setContent(rs.getString("content").charAt(0));
                    tile.setMapName(rs.getString("mapname"));
                    tiles.add(tile);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiles;
    }

    @Override
    public void insertTile(Tile tile) {
        String sql = "INSERT INTO tiles (rowindex, columnindex, content, mapname) VALUES (?, ?, ?, ?)";
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, tile.getRow());
                ps.setInt(2, tile.getColumn());
                ps.setString(3, String.valueOf(tile.getContent()));
                ps.setString(4, tile.getMapName());
                ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
