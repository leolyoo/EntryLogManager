public interface Contract {
    interface View {
        void addRow(String date, String id);

        String now();

        String getDigit();
    }

    interface Presenter {
        void addEntryLog(String date, String id);
        
        void loadFile(String fileName);
    }
}
