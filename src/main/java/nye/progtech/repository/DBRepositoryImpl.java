package nye.progtech.repository;

import nye.progtech.DAO.BoardDetails;
import nye.progtech.DAO.Tile;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;

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

    private void insertTile(Tile tile) {
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

    private void insertBoardDetails(BoardDetails boardDetails) {
        String sql = "INSERT INTO boarddetails (boardsize, herorowindex, herocolindex,herodirection, mapname)" +
                        "VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
                ps.setInt(1, boardDetails.getBoardSize());
                ps.setInt(2, boardDetails.getHeroRowIndex());
                ps.setInt(3, boardDetails.getHeroColIndex());
                ps.setString(4, String.valueOf(boardDetails.getHeroDirection()));
                ps.setString(5, boardDetails.getMapName());
                ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<BoardDetails> selectBoardDetailsByMapName(String mapName) {
        List<BoardDetails> boardDetail = new ArrayList<>();
        String sql = "SELECT * FROM boarddetails WHERE mapname = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mapName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BoardDetails boardDetails = new BoardDetails();
                    boardDetails.setBoardSize(rs.getInt("boardsize"));
                    boardDetails.setHeroRowIndex(rs.getInt("herorowindex"));
                    boardDetails.setHeroColIndex(rs.getInt("herocolindex"));
                    boardDetails.setHeroDirection(rs.getString("herodirection").charAt(0));
                    boardDetails.setMapName(rs.getString("mapname"));
                    boardDetail.add(boardDetails);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boardDetail;
    }

    @Override
    public List<String> getAllMapNames() {
        List<String> mapNames = new ArrayList<>();
        String sql = "SELECT mapname FROM boarddetails";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                mapNames.add(rs.getString("mapname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mapNames;
    }

    @Override
    public void saveGameBoardToDB(GameBoard gameBoard) {
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                char content = gameBoard.getCell(row, col);
                Tile tile = new Tile(row, col, content, gameBoard.getMapName());
                insertTile(tile);
            }
        }
    }

    @Override
    public void saveGameBoardDetailsToDB(GameBoard gameBoard) {
        Hero hero = gameBoard.getHero();
        BoardDetails boardDetails = new BoardDetails(gameBoard.getSize(), hero.getRow(), hero.getColumn(), hero.getDirection(), gameBoard.getMapName());
        insertBoardDetails(boardDetails);
    }
}
