import java.io.File;
import java.sql.*;

public class MainPresenter implements Contract.Presenter {
    private final Contract.View view;

    public MainPresenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void addEntryLog(String when, String id) {
        view.addRow(when, id);
    }

	@Override
	public void loadFile(File selectedFile) {
        String fileName = selectedFile.getAbsolutePath();
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + fileName + (fileName.startsWith(".db", fileName.length() - 3) ? "" : ".db");
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS entry_logs (\n"
                        + "when text NOT NULL,\n"
                        + "id text NOT NULL,\n"
                        + "PRIMARY KEY(when, id)\n"
                        + ");");
                try (ResultSet rs = stmt.executeQuery("SELECT when, id FROM entry_logs")) {
                    while (rs.next()) {
                        addEntryLog(rs.getString("when"), rs.getString("id"));
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
