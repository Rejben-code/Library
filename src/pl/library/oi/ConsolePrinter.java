package pl.library.oi;

import pl.library.model.Books;
import pl.library.model.Magazine;
import pl.library.model.Publication;
import pl.library.model.LibraryUser;
import pl.library.model.User;

import java.util.Collection;

public class ConsolePrinter {

    public void printBooks(Collection<Publication> publications){
        long counter = publications.stream()
                .filter(p->p instanceof Books)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (counter==0){
            printLine("Brak książek w bibliotece");
        }
    }
    public void printMagazines(Collection<Publication> publications){
        int counter = 0;
        for(Publication publication : publications){
            if (publication instanceof Magazine){
                printLine(publication.toString());
                counter++;
            }
        }
        if (counter==0){
            printLine("Brak książek w magazynów");
        }
    }
    public void printUsers(Collection<LibraryUser> users) {
        users.stream()
                .map(User::toString)
                .forEach(this::printLine);
    }

    public void printLine(String text){
        System.out.println(text);
    }

}
