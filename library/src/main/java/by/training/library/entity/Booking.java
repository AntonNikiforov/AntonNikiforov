package by.training.library.entity;

import java.sql.Date;

public class Booking {

    private int id;
    private User user;
    private Book book;
    private Date dateOfIssue;
    private Date dateOfReturn;
    private BookingType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public BookingType getType() {
        return type;
    }

    public void setType(BookingType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != Booking.class) return false;

        Booking booking = (Booking) o;

        if (id != booking.id) return false;
        if (!book.equals(booking.book)) return false;
        if (!dateOfIssue.equals(booking.dateOfIssue)) return false;
        if (!dateOfReturn.equals(booking.dateOfReturn)) return false;
        if (!user.equals(booking.user)) return false;
        if (!type.equals(booking.getType())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user.hashCode();
        result = 31 * result + book.hashCode();
        result = 31 * result + dateOfIssue.hashCode();
        result = 31 * result + dateOfReturn.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", book=").append(book);
        sb.append(", dateOfIssue=").append(dateOfIssue);
        sb.append(", dateOfReturn=").append(dateOfReturn);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
