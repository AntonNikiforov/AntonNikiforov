package by.training.library.entity;

import java.sql.Date;

public class Comment {

    private int id;
    private String text;
    private Date date;
    private User user;
    private Book book;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != Comment.class) return false;

        Comment comment = (Comment) o;

        if (id != comment.id) return false;
        if (!book.equals(comment.book)) return false;
        if (!date.equals(comment.date)) return false;
        if (!text.equals(comment.text)) return false;
        if (!user.equals(comment.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + text.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + book.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{id=").append(id);
        sb.append(", text='").append(text).append('\'');
        sb.append(", date=").append(date);
        sb.append(", user=").append(user);
        sb.append(", book=").append(book);
        sb.append('}');
        return sb.toString();
    }
}
