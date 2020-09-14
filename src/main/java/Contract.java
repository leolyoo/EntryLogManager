import java.io.File;

public interface Contract {
    interface View {
        void addRow(String when, String id);

        String now();

        String getDigit();
    }

    interface Presenter {
        void addEntryLog(String when, String id);
        
        void loadFile(File selectedFile);
    }
}
