package pl.library.model;

import java.time.MonthDay;
import java.util.Objects;

public class Magazine extends Publication{

    private MonthDay month;

    public Magazine(String title, String publisher,String language, int yars, int month, int day) {
        super(title,publisher,yars);
        this.month = MonthDay.of(month,day);
        this.day = day;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return day == magazine.day &&
                Objects.equals(month, magazine.month) &&
                Objects.equals(language, magazine.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), month, day, language);
    }

    public MonthDay getMonth() {
        return month;
    }

    public void setMonth(MonthDay month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    private int day;
    private String language;

    @Override
    public String toString() {
        return super.toString() + ", " + month.getMonthValue() + ", " + day + ", " + language;
    }
    public static final String TYPE = "Magazyn";

    @Override
    public String toCsv() {
        return (TYPE + ";") +
                getTitle() + ";" +
                getPublisher() + ";" +
                getYear() + ";" +
                month.getMonthValue() + ";" +
                day + ";" +
                language + "";
    }
}
