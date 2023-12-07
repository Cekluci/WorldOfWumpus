package nye.progtech.repository;

import nye.progtech.dao.BoardDetails;
import nye.progtech.dao.ScoreBoard;
import nye.progtech.dao.Tile;
import nye.progtech.db.DBInitializer;
import nye.progtech.model.GameBoard;
import nye.progtech.model.Hero;
import nye.progtech.model.Player;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBRepositoryImpl implements DBRepositoryInterface {
    /**
     * A datasource objektum.
     */
    private final DataSource dataSource;
    /**
     * SQL-hez sor paraméter.
     */
    private static final int ROW_PARAM = 1;
    /**
     * SQL-hez oszlop paraméter.
     */
    private static final int COL_PARAM = 2;
    /**
     * SQL-hez mező kontent paraméter.
     */
    private static final int CONTENT_PARAM = 3;
    /**
     * SQL-hez pálya neve paraméter.
     */
    private static final int MAPNAME_PARAM = 4;
    /**
     * BoardDetails size paraméter.
     */
    private static final int BD_SIZE_PARAM = 1;
    /**
     * BoardDetails hero row index paraméter.
     */
    private static final int BD_HERO_ROW_PARAM = 2;
    /**
     * BoardDetails hero col index paraméter.
     */
    private static final int BD_HERO_COL_PARAM = 3;
    /**
     * BoardDetails hero direction paraméter.
     */
    private static final int BD_HERO_DIRECTION_PARAM = 4;
    /**
     * BoardDetails pályanév paraméter.
     */
    private static final int BD_MAPNAME_PARAM = 5;
    /**
     * DbRepositoryImpl konstruktor.
     */
    public DBRepositoryImpl() {
        this.dataSource = DBInitializer.getDataSource();
    }

    /**
     * Tile DAO-ba menti a pályát elemenként, amit
     * beolvasott SQL-ből.
     *
     * @param mapName pálya neve
     * @return list of Tiles
     */
    @Override
    public List<Tile> selectTilesByMapName(final String mapName) {
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

    private void insertTile(final Tile tile) {
        String sql = "INSERT INTO tiles"
                    + "(rowindex, columnindex, content, mapname)"
                    + "VALUES (?, ?, ?, ?)";
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(ROW_PARAM, tile.getRow());
                ps.setInt(COL_PARAM, tile.getColumn());
                ps.setString(CONTENT_PARAM, String.valueOf(tile.getContent()));
                ps.setString(MAPNAME_PARAM, tile.getMapName());
                ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Mentsük el a boarddetails-t az adatbázisba
    private void insertBoardDetails(final BoardDetails boardDetails) {
        String sql = "INSERT INTO boarddetails"
                        + "(boardsize, herorowindex, herocolindex,"
                        + "herodirection, mapname)"
                        + "VALUES (?, ?, ?, ?, ?)";
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
            ) {
                ps.setInt(BD_SIZE_PARAM, boardDetails.getBoardSize());
                ps.setInt(BD_HERO_ROW_PARAM, boardDetails.getHeroRowIndex());
                ps.setInt(BD_HERO_COL_PARAM, boardDetails.getHeroColIndex());
                ps.setString(BD_HERO_DIRECTION_PARAM,
                        String.valueOf(boardDetails.getHeroDirection()));
                ps.setString(BD_MAPNAME_PARAM, boardDetails.getMapName());
                ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Olvassuk be a boarddetails-t az adatbázisból.
     *
     * @param mapName pálya neve
     * @return boarddetails objektum
     */
    @Override
    public BoardDetails selectBoardDetailsByMapName(final String mapName) {
        BoardDetails bd = new BoardDetails();
        String sql = "SELECT * FROM boarddetails WHERE mapname = ?";
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mapName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    bd.setBoardSize(rs.getInt("boardsize"));
                    bd.setHeroRowIndex(rs.getInt("herorowindex"));
                    bd.setHeroColIndex(rs.getInt("herocolindex"));
                    bd.setHeroDirection(rs.getString("herodirection")
                            .charAt(0));
                    bd.setMapName(rs.getString("mapname"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bd;
    }

    /**
     * Kérjük le az összes pályanevet az adatbázisból.
     *
     * @return lista a pályanevekkel.
     */
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

    /**
     * Mentsük el a pályát az adatbázisba.
     *
     * @param gameBoard gameboard objektum
     */
    @Override
    public void saveGameBoardToDB(final GameBoard gameBoard) {
        for (int row = 0; row < gameBoard.getSize(); row++) {
            for (int col = 0; col < gameBoard.getSize(); col++) {
                char content = gameBoard.getCell(row, col);
                Tile tile = new Tile(row, col, content, gameBoard.getMapName());
                insertTile(tile);
            }
        }
    }

    /**
     * GameBoard details feltöltése az adatbázisba.
     *
     * @param gameBoard gameboard objektum
     */
    @Override
    public void saveGameBoardDetailsToDB(final GameBoard gameBoard) {
        Hero hero = gameBoard.getHero();
        BoardDetails boardDetails = new BoardDetails(gameBoard.getSize(),
                hero.getRow(), hero.getColumn(), hero.getDirection(),
                gameBoard.getMapName());
        insertBoardDetails(boardDetails);
    }

    /**
     * Scoreboard elmentése az adatbázisba.
     *
     * @param player játékos objektum.
     */
    @Override
    public void saveScoreBoardToDB(final Player player) {
        String sql = "INSERT INTO scoreboard"
                        + "(playername, playerscore) VALUES (?, ?)";
        try (
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(player.getPlayerName()));
                ps.setInt(2, player.getPlayerScore());
                ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Scoreboard lehívása az adatbázisból.
     *
     * @return lista a pontszámokkal és a játékosokkal
     */
    @Override
    public List<ScoreBoard> getScoreBoard() {
        List<ScoreBoard> scoreboard = new ArrayList<>();
        String sql = "SELECT playername, playerscore "
                        + "FROM scoreboard ORDER BY playerscore DESC";

        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String playerName = rs.getString("playername");
                int playerScore = rs.getInt("playerscore");
                scoreboard.add(new ScoreBoard(playerName, playerScore));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scoreboard;
    }
}
