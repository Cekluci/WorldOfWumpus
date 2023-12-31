/**
 * Adatbázis kezdeti inicializálás.
 */
package nye.progtech.db;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public final class DBInitializer {
    /**
     * Data Source.
     */
    private static DataSource dataSource;

    private DBInitializer() {
        throw new UnsupportedOperationException("This is a utility class,"
                + "and cannot be instanciated.");
    }

    /**
     * Program indításakor az adatbázis feltöltése adatokkal.
     *
     * @throws SQLException sql hiba
     */
    public static void dbSetup() throws SQLException {
        createDataSource();
        initializeDB();
        uploadDBWorld();

    }

    /**
     * Data Source objektum létrehozása.
     *
     * @return data source
     */
    public static DataSource getDataSource() {
        if (dataSource == null) {
            createDataSource();
        }
        return dataSource;
    }

    /**
     * Adatbázis létrehozása.
     */
    public static void createDataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:wumpusgame;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");
        //return dataSource;

        dataSource = ds;
    }

    /**
     * Inicializálás, táblák létrehozása.
     *
     * @throws SQLException sql hiba
     */
    public static void initializeDB() throws SQLException {
        //JdbcDataSource dataSource = createDataSource();
        if (dataSource == null) {
            createDataSource();
        }
        //H2 webserver engedélyezése
        Server.createWebServer("-web", "-webAllowOthers",
                "-webPort", "8082").start();

        String createBoardTableSQL = "CREATE TABLE IF NOT EXISTS tiles ("
                                + "id IDENTITY NOT NULL,"
                                + "rowIndex INT NOT NULL,"
                                + "columnIndex INT NOT NULL,"
                                + "content CHAR(1),"
                                + "mapName VARCHAR(30),"
                                + "UNIQUE(rowIndex, columnIndex, mapName));";

        String createBoardDetailsTableSQL = "CREATE TABLE IF NOT EXISTS "
                                            + "boardDetails ("
                                            + "id IDENTITY NOT NULL,"
                                            + "boardSize INT NOT NULL,"
                                            + "HeroRowIndex CHAR(1) NOT NULL,"
                                            + "HeroColIndex INT NOT NULL,"
                                            + "HeroDirection CHAR(1) NOT NULL,"
                                            + "mapName VARCHAR(30) NOT NULL)";

        String createScoreBoardTableSQL = "CREATE TABLE IF NOT EXISTS "
                                            + "scoreBoard ("
                                            + "playerName VARCHAR(30) NOT NULL,"
                                            + "playerScore INT NOT NULL)";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
                stmt.execute(createBoardTableSQL);
                stmt.execute(createBoardDetailsTableSQL);
                stmt.execute(createScoreBoardTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tesztadatok feltöltése.
     */
    public static void uploadDBWorld() {
        if (dataSource == null) {
            createDataSource();
        }
        //JdbcDataSource dataSource = createDataSource();
        String dbWorldTilesSql = "INSERT INTO tiles "
        + "(rowindex, columnindex, content, mapname) VALUES "
                + "(0, 0, 'W', 'dbWorldTest'),"
                + "(0, 1, 'W', 'dbWorldTest'),"
                + "(0, 2, 'W', 'dbWorldTest'),"
                + "(0, 3, 'W', 'dbWorldTest'),"
                + "(0, 4, 'W', 'dbWorldTest'),"
                + "(0, 5, 'W', 'dbWorldTest'),"
                + "(1, 0, 'W', 'dbWorldTest'),"
                + "(1, 1, '_', 'dbWorldTest'),"
                + "(1, 2, 'G', 'dbWorldTest'),"
                + "(1, 3, '_', 'dbWorldTest'),"
                + "(1, 4, 'P', 'dbWorldTest'),"
                + "(1, 5, 'W', 'dbWorldTest'),"
                + "(2, 0, 'W', 'dbWorldTest'),"
                + "(2, 1, '_', 'dbWorldTest'),"
                + "(2, 2, '_', 'dbWorldTest'),"
                + "(2, 3, 'P', 'dbWorldTest'),"
                + "(2, 4, '_', 'dbWorldTest'),"
                + "(2, 5, 'W', 'dbWorldTest'),"
                + "(3, 0, 'W', 'dbWorldTest'),"
                + "(3, 1, '_', 'dbWorldTest'),"
                + "(3, 2, 'U', 'dbWorldTest'),"
                + "(3, 3, '_', 'dbWorldTest'),"
                + "(3, 4, '_', 'dbWorldTest'),"
                + "(3, 5, 'W', 'dbWorldTest'),"
                + "(4, 0, 'W', 'dbWorldTest'),"
                + "(4, 1, '_', 'dbWorldTest'),"
                + "(4, 2, '_', 'dbWorldTest'),"
                + "(4, 3, 'P', 'dbWorldTest'),"
                + "(4, 4, '_', 'dbWorldTest'),"
                + "(4, 5, 'W', 'dbWorldTest'),"
                + "(5, 0, 'W', 'dbWorldTest'),"
                + "(5, 1, 'W', 'dbWorldTest'),"
                + "(5, 2, 'W', 'dbWorldTest'),"
                + "(5, 3, 'W', 'dbWorldTest'),"
                + "(5, 4, 'W', 'dbWorldTest'),"
                + "(5, 5, 'W', 'dbWorldTest')";

        String dbWorldDetailsSql = "INSERT INTO boarddetails"
                                    + "(boardsize, herorowindex, herocolindex,"
                                    + "herodirection, mapname)"
                                    + "VALUES (6, 4, 1, 'N', 'dbWorldTest')";

        try (Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()) {
                    stmt.execute(dbWorldTilesSql);
                    stmt.execute(dbWorldDetailsSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
