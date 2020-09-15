import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

public class MainView implements Contract.View {
    private final DefaultTableModel tableModel;
    private final Contract.Presenter presenter;
    private final JDigitField digitField;
    private final JFileChooser fileChooser;

    public MainView() {
        presenter = new MainPresenter(this);

        final JFrame frame = new JFrame("[학생지원팀] 출입관리시스템");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{"date", "id"}, 0);
        final JTable table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        digitField = new JDigitField();
        digitField.registerKeyboardAction(e -> {
            String digit = getDigit();
            if (digit.trim().length() > 0)
                presenter.addEntryLog(now(), getDigit());
            digitField.setText("");
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), JComponent.WHEN_FOCUSED);
        frame.add(digitField, BorderLayout.NORTH);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("DB 설정");
        menuBar.add(fileMenu);

        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("SQLite DB 파일", "db"));

        JMenuItem loadItem = new JMenuItem("파일 불러오기");
        loadItem.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                presenter.loadFile(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        fileMenu.add(loadItem);

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    @Override
    public void addRow(String date, String id) {
        tableModel.addRow(new String[]{date, id});
    }

    @Override
    public String now() {
        return LocalDateTime.now().toString();
    }

    @Override
    public String getDigit() {
        return digitField.getText();
    }
}
