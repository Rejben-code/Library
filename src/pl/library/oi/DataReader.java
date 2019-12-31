package pl.library.oi;

import pl.library.model.Books;
import pl.library.model.Magazine;
import pl.library.model.LibraryUser;

import java.util.Scanner;

public class DataReader {
    private Scanner sc = new Scanner(System.in);
    private ConsolePrinter printer;

    public DataReader(ConsolePrinter printer){
        this.printer=printer;
    }

    public String getString(){
        return sc.nextLine();
    }

    public void close(){
        sc.close();
    }
    public int getInit(){
        try {
            return sc.nextInt();
        }finally {
            sc.nextLine();
        }
    }
    public LibraryUser createLibraryUser(){
        printer.printLine("Imię");
        String firstName = sc.nextLine();
        printer.printLine("Nazwisko");
        String lastName = sc.nextLine();
        printer.printLine("Pesel");
        String pesel = sc.nextLine();
        return new LibraryUser(firstName, lastName, pesel);
    }

    public Books readAndCreateBook (){
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Autor: ");
        String author = sc.nextLine();
        printer.printLine("Wydawnictwo: ");
        String publisher = sc.nextLine();
        printer.printLine("ISBN: ");
        String isbn = sc.nextLine();
        printer.printLine("Rok wydania: ");
        int releaseDate =getInit();
        printer.printLine("Ilość stron: ");
        int pages = getInit();

        return new Books(author,title,releaseDate,pages,isbn,publisher);
    }
    public Magazine readAndCreateMagazine() {
        printer.printLine("Tytuł: ");
        String title = sc.nextLine();
        printer.printLine("Wydawnictwo: ");
        String publisher = sc.nextLine();
        printer.printLine("Język: ");
        String language = sc.nextLine();
        printer.printLine("Rok wydania: ");
        int year = getInit();
        printer.printLine("Miesiąc: ");
        int month = getInit();
        printer.printLine("Dzień: ");
        int day = getInit();

        return new Magazine(title,publisher,language,year,month,day);
    }
}
