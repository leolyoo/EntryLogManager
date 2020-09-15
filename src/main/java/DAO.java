import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public enum DAO {
    INSTANCE;
    private final ArrayList<DBListener> listeners = new ArrayList<>();
    private final Properties propertiesForReadOnly;
    private boolean driverLoaded;
    private boolean tableReady = false;
    private String url = "jdbc:sqlite:default.db";

    DAO() {
        final SQLiteConfig config = new SQLiteConfig();
        config.setReadOnly(true);
        propertiesForReadOnly = config.toProperties();
        try {
            Class.forName("org.sqlite.JDBC");
            driverLoaded = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            driverLoaded = false;
        }
    }

    public void addDBListener(final DBListener listener) {
        this.listeners.add(listener);
    }

    public void setFileName(final String fileName) {
        url = "jdbc:sqlite:" + fileName + (fileName.startsWith(".db", fileName.length() - 3) ? "" : ".db");
        tableReady = false;
    }

    public void createNewTable() {
        if (driverLoaded) {
            try (final Connection conn = DriverManager.getConnection(url);
                 final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS entry_logs (\n"
                        + "date text NOT NULL,\n"
                        + "code text NOT NULL,\n"
                        + "PRIMARY KEY(date, code)\n"
                        + ");");
                tableReady = true;
            } catch (SQLException e) {
                e.printStackTrace();
                tableReady = false;
            }
        }
    }

    public void selectAll() {
        if (driverLoaded && tableReady) {
            try (final Connection conn = DriverManager.getConnection(url, propertiesForReadOnly);
                 final Statement stmt = conn.createStatement();
                 final ResultSet rs = stmt.executeQuery("SELECT date, code FROM entry_logs")) {
                while (rs.next()) {
                    final String date = rs.getString("date");
                    final String code = rs.getString("code");
                    listeners.forEach(listener -> listener.onDataSelected(date, code));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert(final String date, final String code) {
        if (driverLoaded && tableReady) {
            try (final Connection conn = DriverManager.getConnection(url);
                 final PreparedStatement stmt = conn.prepareStatement("INSERT INTO entry_logs (date, code) VALUES (?, ?)")) {
                stmt.setString(1, date);
                stmt.setString(2, code);
                stmt.executeUpdate();
                listeners.forEach(listener -> listener.onDataUpdated(date, code));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}