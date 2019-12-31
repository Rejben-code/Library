package pl.library.oi.file;

import pl.library.model.Library;

public interface FileManager {
    Library importData();
    void exportData(Library library);
}
