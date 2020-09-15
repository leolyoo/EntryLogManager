public interface Contract {
    interface View {
        void addRow(String date, String code);

        String now();

        String getDigit();
    }

    interface Presenter {
        void addEntryLog(String date, String code);

        void loadDB(String fileName);
    }
}
