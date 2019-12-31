package pl.library.oi.file;

import pl.library.oi.ConsolePrinter;
import pl.library.oi.DataReader;

import java.nio.file.NoSuchFileException;

public class FileManagerBulider {
    private ConsolePrinter printer;
    private DataReader reader;

    public FileManagerBulider(ConsolePrinter printer, DataReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public FileManager bulid() throws NoSuchFileException {
        printer.printLine("Wybierz format danych");
        FileType fileType = getFileType();
        switch (fileType) {
            case SERIAL:
                return new SerializableFileManager();
            case CSV:
                return new CsvFileManager();
            default:
             throw new NoSuchFileException("Nieobsługiwany typ danych");
        }
    }


    private FileType getFileType(){
        boolean typeOK = false;
        FileType result = null;
        do {
            printTypes();
            String type = reader.getString().toUpperCase();
            try {
                result = FileType.valueOf(type);
                typeOK = true;
            } catch (IllegalArgumentException e) {
                printer.printLine("Nieobsługiwany typ danych, wybierz ponownie");
            }
        } while (!typeOK);

        return result;

    }

    private void printTypes(){
        for (FileType value : FileType.values()){
            printer.printLine(value.name());
        }
    }
}
