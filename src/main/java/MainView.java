import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

public class MainView implements Contract.View {
    private final DefaultTableModel tableModel;
    private final Contract.Presenter presenter;
    private final JDigitField digitField;

    public MainView() {
        presenter = new MainPresenter(this);

        final JFrame frame = new JFrame("[학생지원팀] 출입관리시스템 [학생지원팀]");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel(new String[]{"when", "id"}, 0);
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
        JMenu fileMenu = new JMenu("파일");
        menuBar.add(fileMenu);

        JMenuItem loadItem = new JMenuItem("불러오기");
        fileMenu.add(loadItem);

        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    @Override
    public void addRow(String when, String id) {
        tableModel.addRow(new String[]{when, id});
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
