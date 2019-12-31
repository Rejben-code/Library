package pl.library.oi.file;

import pl.library.Exception.DataExportException;
import pl.library.Exception.DataImportException;
import pl.library.model.Library;

import java.io.*;

public class SerializableFileManager implements FileManager {
    private static final String FILE_NAME = "Library.o";

    @Override
    public Library importData() {
        try(FileInputStream fIS = new FileInputStream(FILE_NAME);
            ObjectInputStream oIS = new ObjectInputStream(fIS);
            ) {
          return (Library) oIS.readObject();
        }catch (FileNotFoundException e){
            throw new DataImportException("Brak pliku "+ FILE_NAME);
        }catch (IOException e){
            throw new DataImportException("Bład odczytu pliku "+ FILE_NAME);
        }catch (ClassNotFoundException e){
            throw new DataImportException("Niezgodny typ danych w pliku "+ FILE_NAME);
        }
    }

    @Override
    public void exportData(Library library) {
        try(FileOutputStream fOS = new FileOutputStream(FILE_NAME);
            ObjectOutputStream oOS = new ObjectOutputStream(fOS);
        ){
            oOS.writeObject(library);
        }catch (FileNotFoundException e){
            throw new DataExportException("Brak pliku " + FILE_NAME);
        }catch (IOException e){
            throw new DataExportException("Błąd zapisu danych do pliku " + FILE_NAME);
        }

    }
}
