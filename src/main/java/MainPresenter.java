public class MainPresenter implements Contract.Presenter, DBListener {
    private final Contract.View view;

    public MainPresenter(Contract.View view) {
        this.view = view;
        DAO.INSTANCE.addDBListener(this);
    }

    @Override
    public void addEntryLog(String date, String code) {
        DAO.INSTANCE.insert(date, code);
    }

    @Override
    public void loadDB(String fileName) {
        DAO.INSTANCE.setFileName(fileName);
        DAO.INSTANCE.createNewTable();
        DAO.INSTANCE.selectAll();
    }

    @Override
    public void onDataSelected(String date, String code) {
        view.addRow(date, code);
    }

    @Override
    public void onDataUpdated(String date, String code) {
        view.addRow(date, code);
    }
}
