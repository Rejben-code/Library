package pl.library.model;

import java.util.Objects;


public class Books extends Publication {
    public static final String TYPE = "Książka";
    public Books(String author, String title, int releaseDate, int pages, String isbn, int years, String publisher ){
        super(title,publisher,years);
        this.author = author;
        this.releaseDate = releaseDate;
        this.pages = pages;
        this.isbn = isbn;
    }
    public Books(String author,String title, int releaseDate, int pages, String isbn, String publisher ){
        super(title,publisher);
        this.author = author;
        this.releaseDate = releaseDate;
        this.pages = pages;
        this.isbn = isbn;
    }

    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(int releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    private int releaseDate;
    private int pages;

    private String isbn;

    @Override
    public String toString() {
        return super.toString() + ", " + author + ", " + pages + ", " + isbn;
    }

    @Override
    public String toCsv() {
        return (TYPE+ ";") +
                getTitle() + ";" +
                getPublisher() + ";" +
                getYear() + ";" +
                author + ";" +
                pages + ";" +
                isbn + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Books books = (Books) o;
        return releaseDate == books.releaseDate &&
                pages == books.pages &&
                Objects.equals(author, books.author) &&
                Objects.equals(isbn, books.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, releaseDate, pages, isbn);
    }
}
