import java.sql.*;

public class MainPresenter implements Contract.Presenter {
    private final Contract.View view;

    public MainPresenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void addEntryLog(String date, String id) {
        view.addRow(date, id);
    }

	@Override
	public void loadFile(String fileName) {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + fileName + (fileName.startsWith(".db", fileName.length() - 3) ? "" : ".db");
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS entry_logs (\n"
                        + "date text NOT NULL,\n"
                        + "id text NOT NULL,\n"
                        + "PRIMARY KEY(date, id)\n"
                        + ");");
                try (ResultSet rs = stmt.executeQuery("SELECT date, id FROM entry_logs")) {
                    while (rs.next()) {
                        addEntryLog(rs.getString("date"), rs.getString("id"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
