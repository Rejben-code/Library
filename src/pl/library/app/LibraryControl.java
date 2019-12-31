package pl.library.app;

import pl.library.Exception.*;
import pl.library.model.Books;
import pl.library.model.Library;
import pl.library.model.Magazine;
import pl.library.model.Publication;
import pl.library.oi.ConsolePrinter;
import pl.library.oi.DataReader;
import pl.library.oi.file.FileManager;
import pl.library.oi.file.FileManagerBulider;
import pl.library.model.LibraryUser;
import pl.library.model.User;

import java.nio.file.NoSuchFileException;
import java.util.Comparator;
import java.util.InputMismatchException;

class LibraryControl {
    private ConsolePrinter printer = new ConsolePrinter();
    private DataReader dataReader = new DataReader(printer);
    private FileManager fileManager;

    private Library library;


    LibraryControl() {
        try{
            fileManager = new FileManagerBulider(printer, dataReader).bulid();
            library = fileManager.importData();
            printer.printLine("Zaimportowane dane z pliku");
        } catch (DataImportException | InvalidDataException | NoSuchFileException e) {
            printer.printLine(e.getMessage());
            printer.printLine("Zaincjowano nową bazę");
            library = new Library();
        }

    }

    private void printOptions() {
        printer.printLine("Wybierz opcję: ");
        for (Options options: Options.values()) {
            printer.printLine(options.toString());
        }
    }

    private void addBook() {
        try {
            Books books = dataReader.readAndCreateBook();
            library.addPublitation(books);
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnej książki");
        }
    }

     private void addMAgazine(){
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            library.addPublitation(magazine);
        }catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        } catch (ArrayIndexOutOfBoundsException e) {
            printer.printLine("Osiągnięto limit pojemności, nie można dodać kolejnego magazynu");
        }

    }
    private void addUser() {
        LibraryUser libraryUser = dataReader.createLibraryUser();
        try {
            library.addUser(libraryUser);
        } catch (UserAlreadyExistsException e) {
            printer.printLine(e.getMessage());
        }
    }

    private void printMagazine(){
        printer.printBooks(library.getSortedPublications(
          Comparator.comparing(Publication::getTitle, String.CASE_INSENSITIVE_ORDER))
        );
    }


    private void printBooks() {
        printer.printMagazines(library.getSortedPublications(
                Comparator.comparing(Publication::getTitle,String.CASE_INSENSITIVE_ORDER))
        );
    }
    private void findBook() {
        printer.printLine("Podaj tytuł publikacji:");
        String title = dataReader.getString();
        String notFoundMessage = "Brak publikacji o takim tytule";
        library.findPublicationByTitle(title)
                .map(Publication::toString)
                .ifPresentOrElse(System.out::println, () -> System.out.println(notFoundMessage));
    }




    private void printUsers(){
        printer.printUsers(library.getSortedUsers(
                Comparator.comparing(User::getLastName, String.CASE_INSENSITIVE_ORDER))
        );

    }
    private void exit(){
        try{
            fileManager.exportData(library);
            printer.printLine("Export danych do pliku zakończony powodzeniem");
        }catch (DataExportException e) {
            printer.printLine(e.getMessage());
        }
        System.out.println("koniec programu, papa");
        dataReader.close();
    }

   void controlLoop() {
        Options option;

        do {
            printOptions();
            option = getOption();
            switch (option) {
                case ADD_BOOK:
                    addBook();
                    break;
                case ADD_MAGAZINE:
                    addMAgazine();
                    break;
                case PRINT_BOOKS:
                    printBooks();
                    break;
                case PRINT_MAGAZINES:
                    printMagazine();
                    break;
                case DELETE_BOOKS:
                    deleteBook();
                    break;
                case DELETE_MAGAZINES:
                    deleteMagazine();
                    break;
                case ADD_USER:
                    addUser();
                    break;
                case PRINT_USERS:
                    printUsers();
                    break;
                case FIND_BOOK:
                    findBook();
                    break;
                case EXIT:
                    exit();
                    break;

                default:
                    printer.printLine("Nie ma takiej opcji, wprowadź ponownie: ");
            }
        }while (option!=Options.EXIT);


        }

        private Options getOption(){
        boolean optionOK = false;
        Options options = null;
        while (!optionOK){
            try {
                options = Options.createFromint(dataReader.getInit());
                optionOK=true;
            }catch (NoSuchOptionException e){
                printer.printLine(e.getMessage()+", podaj ponownie:");
            }catch (InputMismatchException ignored){
                printer.printLine("Wprowadzono wartość, która nie jest liczbą, podaj ponownie: ");
            }
        }
        return options;
        }
    private enum Options {
        EXIT (0, "Wyjście z programu"),
        ADD_BOOK(1, "Dodanie książki"),
        ADD_MAGAZINE(2, "Dodanie magazynu/gazety"),
        PRINT_MAGAZINES(3, "Wyświetlenie dostępnych magazynów/gazet"),
        PRINT_BOOKS(4, "Wyświetlenie dostępnych książek"),
        DELETE_BOOKS(5,"Usuń magazyn"),
        DELETE_MAGAZINES(6,"Usuń książkę"),
        ADD_USER(7,"Dodaj czytelnika"),
        PRINT_USERS(8,"Wyświetl czytelników"),
        FIND_BOOK(9,"Wyszukaj książkę");

        private int value;
        private String description;

        @Override
        public String toString() {
            return value + " - " + description;
        }

        Options(int value, String description) {
            this.value = value;
            this.description = description;

        }
        static Options createFromint(int option) throws NoSuchOptionException {
            try {
                return Options.values()[option];
            }catch (ArrayIndexOutOfBoundsException e){
                throw new NoSuchOptionException("Brak opcji o id" + option);
            }
        }


    }
    private void deleteMagazine() {
        try {
            Magazine magazine = dataReader.readAndCreateMagazine();
            if (library.removePublication(magazine))
                printer.printLine("Usunięto magazyn.");
            else
                printer.printLine("Brak wskazanego magazynu.");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć magazynu, niepoprawne dane");
        }
    }

    private void deleteBook() {
        try {
            Books book = dataReader.readAndCreateBook();
            if (library.removePublication(book))
                printer.printLine("Usunięto książkę.");
            else
                printer.printLine("Brak wskazanej książki.");
        } catch (InputMismatchException e) {
            printer.printLine("Nie udało się utworzyć książki, niepoprawne dane");
        }
    }


    }

