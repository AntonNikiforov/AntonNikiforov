package by.training.webxml.entity;

public class OldCard {

    public static void main(String[] args) {

        OldCard card = new OldCard();

        card.setTheme(Theme.ARCHITECTURE);
        card.setType(Type.GREETINGS);
        card.setCountry("Minsk");
        card.setYear(1999);
        card.setAuthor("unknown");
        card.setValuable(Valuable.THEMATIC);
        card.setSent(true);

        System.out.println(card);
    }

    private Theme theme;
    private Type type;
    private String country;
    private int year;
    private String author;
    private Valuable valuable;
    private boolean sent;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Valuable getValuable() {
        return valuable;
    }

    public void setValuable(Valuable valuable) {
        this.valuable = valuable;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != OldCard.class) {
            return false;
        }

        OldCard oldCard = (OldCard) o;

        if (sent != oldCard.sent) {
            return false;
        }
        if (year != oldCard.year) {
            return false;
        }
        if (!author.equals(oldCard.author)) {
            return false;
        }
        if (!country.equals(oldCard.country)) {
            return false;
        }
        if (theme != oldCard.theme) {
            return false;
        }
        if (type != oldCard.type) {
            return false;
        }
        if (valuable != oldCard.valuable) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = theme.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + year;
        result = 31 * result + author.hashCode();
        result = 31 * result + valuable.hashCode();
        result = 31 * result + (sent ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{theme=").append(theme);
        sb.append(", type=").append(type);
        sb.append(", country='").append(country).append('\'');
        sb.append(", year=").append(year);
        sb.append(", author='").append(author).append('\'');
        sb.append(", valuable=").append(valuable);
        sb.append(", sent=").append(sent);
        sb.append('}');
        return sb.toString();
    }
}
