package entrylogmanager.view;

import entrylogmanager.Constants;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainView {
    private final ArrayList<ViewListener> listeners;
    private final JTable table;

    public MainView() {
        final JFrame frame = new JFrame();
        frame.setTitle(Constants.TITLE);
        frame.setSize(Constants.WIDTH, Constants.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.listeners = new ArrayList<>();
        table = new JTable();
        final JDigitField digitField = new JDigitField();
        final JButton excelButton = new JButton(Constants.EXCEL_BUTTON_TEXT);

        frame.add(digitField, BorderLayout.NORTH);
        frame.add(table, BorderLayout.CENTER);
        frame.add(excelButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public void addListener(final ViewListener listener) {
        listeners.add(listener);
    }

    public void setTableModel(final TableModel tableModel) {
        table.setModel(tableModel);
    }
}
