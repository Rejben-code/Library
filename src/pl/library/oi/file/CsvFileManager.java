package pl.library.oi.file;
import pl.library.Exception.DataExportException;
import pl.library.Exception.DataImportException;
import pl.library.Exception.InvalidDataException;
import pl.library.model.*;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;

public class CsvFileManager implements FileManager {
    private static final String PUBLICATIONS_FILE_NAME = "Library.csv";
    private static final String USERS_FILE_NAME = "Library_users.csv";


    @Override
    public Library importData() {
        Library library = new Library();
        importPublications(library);
        importUsers(library);
        return library;
    }
    private void importPublications(Library library) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(PUBLICATIONS_FILE_NAME))) {
           bufferedReader.lines()
                   .map(this::createObjectFromString)
                   .forEach(library::addPublitation);
    } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + PUBLICATIONS_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Publication createObjectFromString(String csvText) {
        String[] split = csvText.split(";");
        String type = split[0];
        if(Books.TYPE.equals(type)) {
            return createBook(split);
        } else if(Magazine.TYPE.equals(type)) {
            return createMagazine(split);
        }
        throw new InvalidDataException("Nieznany typ publikacji: " + type);
    }
    private void importUsers(Library library) {
        try (Scanner fileReader = new Scanner(new File(USERS_FILE_NAME))) {
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                LibraryUser libUser = createUserFromString(line);
                library.addUser(libUser);
            }
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku " + USERS_FILE_NAME);
        }
    }
    private LibraryUser createUserFromString(String csvText) {
        String[] split = csvText.split(";");
        String firstName = split[0];
        String lastName = split[1];
        String pesel = split[2];
        return new LibraryUser(firstName, lastName, pesel);
    }


    @Override
    public void exportData(Library library) {
        exportPublications(library);
        exportUsers(library);
    }

    private void exportPublications(Library library) {
        Collection<Publication> publications = library.getPublications().values();
        exportToCsv(publications, PUBLICATIONS_FILE_NAME);
    }

    private void exportUsers(Library library) {
        Collection<LibraryUser> users = library.getUsers().values();
        exportToCsv(users, USERS_FILE_NAME);
    }


    private Books createBook(String[] data) {
        String title = data[0];
        String publisher = data[1];
        int year = Integer.valueOf(data[2]);
        String author = data[3];
        int pages = Integer.valueOf(data[4]);
        String isbn = data[5];
        return new Books(title, author, year, pages, publisher, isbn);
    }

    private Magazine createMagazine(String[] data) {
        String title = data[1];
        String publisher = data[2];
        int year = Integer.valueOf(data[3]);
        int month = Integer.valueOf(data[4]);
        int day = Integer.valueOf(data[5]);
        String language = data[6];
        return new Magazine(title, publisher, language, year, month, day);
    }
    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku " + fileName);
        }
    }
}
