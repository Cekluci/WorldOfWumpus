package nye.progtech;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    private DataSource dataSource;

    public DBInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void initializeDB() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tiles (" +
                                "id IDENTITY NOT NULL," +
                                "rowIndex INT NOT NULL," +
                                "columnIndex INT NOT NULL," +
                                "content CHAR(1)," +
                                "mapName varchar(30)," +
                                "UNIQUE(rowIndex, columnIndex, mapName));";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
