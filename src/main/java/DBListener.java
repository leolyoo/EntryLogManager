public interface DBListener {
    void onDataSelected(String date, String code);

    void onDataUpdated(String date, String code);
}
