package nye.progtech.db;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static JdbcDataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:wumpusgame;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    public static void initializeDB() throws SQLException {
        JdbcDataSource dataSource = createDataSource();
        //H2 webserver engedélyezése
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();

        String createBoardTableSQL = "CREATE TABLE IF NOT EXISTS tiles (" +
                                "id IDENTITY NOT NULL," +
                                "rowIndex INT NOT NULL," +
                                "columnIndex INT NOT NULL," +
                                "content CHAR(1)," +
                                "mapName varchar(30)," +
                                "UNIQUE(rowIndex, columnIndex, mapName));";

        String createBoardDetailsTableSQL = "CREATE TABLE IF NOT EXISTS boardDetails (" +
                                            "id IDENTITY NOT NULL," +
                                            "boardSize INT NOT NULL," +
                                            "HeroRowIndex CHAR(1) NOT NULL," +
                                            "HeroColIndex INT NOT NULL," +
                                            "HeroDirection CHAR(1) NOT NULL," +
                                            "mapName varchar(30) NOT NULL)";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
                stmt.execute(createBoardTableSQL);
                stmt.execute(createBoardDetailsTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void uploadDBWorld() {
        JdbcDataSource dataSource = createDataSource();
        String dbWorldTilesSql = "INSERT INTO tiles (rowindex, columnindex, content, mapname) VALUES (" +
                "0, 0, \'W\', \'dbWorldTest\')," +
                "(0, 1, \'W\', \'dbWorldTest\')," +
                "(0, 2, \'W\', \'dbWorldTest\')," +
                "(0, 3, \'W\', \'dbWorldTest\')," +
                "(0, 4, \'W\', \'dbWorldTest\')," +
                "(0, 5, \'W\', \'dbWorldTest\')," +
                "(1, 0, \'W\', \'dbWorldTest\')," +
                "(1, 1, \'_\', \'dbWorldTest\')," +
                "(1, 2, \'G\', \'dbWorldTest\')," +
                "(1, 3, \'_\', \'dbWorldTest\')," +
                "(1, 4, \'P\', \'dbWorldTest\')," +
                "(1, 5, \'W\', \'dbWorldTest\')," +
                "(2, 0, \'W\', \'dbWorldTest\')," +
                "(2, 1, \'_\', \'dbWorldTest\')," +
                "(2, 2, \'_\', \'dbWorldTest\')," +
                "(2, 3, \'P\', \'dbWorldTest\')," +
                "(2, 4, \'_\', \'dbWorldTest\')," +
                "(2, 5, \'W\', \'dbWorldTest\')," +
                "(3, 0, \'W\', \'dbWorldTest\')," +
                "(3, 1, \'_\', \'dbWorldTest\')," +
                "(3, 2, \'U\', \'dbWorldTest\')," +
                "(3, 3, \'_\', \'dbWorldTest\')," +
                "(3, 4, \'_\', \'dbWorldTest\')," +
                "(3, 5, \'W\', \'dbWorldTest\')," +
                "(4, 0, \'W\', \'dbWorldTest\')," +
                "(4, 1, \'H\', \'dbWorldTest\')," +
                "(4, 2, \'_\', \'dbWorldTest\')," +
                "(4, 3, \'P\', \'dbWorldTest\')," +
                "(4, 4, \'_\', \'dbWorldTest\')," +
                "(4, 5, \'W\', \'dbWorldTest\')," +
                "(5, 0, \'W\', \'dbWorldTest\')," +
                "(5, 1, \'W\', \'dbWorldTest\')," +
                "(5, 2, \'W\', \'dbWorldTest\')," +
                "(5, 3, \'W\', \'dbWorldTest\')," +
                "(5, 4, \'W\', \'dbWorldTest\')," +
                "(5, 5, \'W\', \'dbWorldTest\')";

        String dbWorldDetailsSql = "INSERT INTO boarddetails (boardsize, herorowindex, herocolindex, herodirection, mapname) VALUES (" +
                "6, 4, 1, \'N\', \'dbWorldTest\')";

        try (Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement()) {
                    stmt.execute(dbWorldTilesSql);
                    stmt.execute(dbWorldDetailsSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
