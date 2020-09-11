public class MainPresenter implements Contract.Presenter {
    private final Contract.View view;

    public MainPresenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void addEntryLog(String when, String id) {
        view.addRow(when, id);
    }
}
