package console.domain;

public class ExcelVo {

    private String title;
    private String author;
    private String company;
    private String isbn;
    private String imageUrl;

    public ExcelVo(String title, String author, String company, String isbn, String imageUrl) {
        super();
        this.title = title;
        this.author = author;
        this.company = company;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
    }

    public ExcelVo(String title, String author, String company) {
        this.title = title;
        this.author = author;
        this.company = company;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImageUrl() {
        return "results/" + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Override
    public String toString() {
        return "console.domain.ExcelVo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", company='" + company + '\'' +
                ", isbn='" + isbn + '\'' +
                ", imageurl='" + imageUrl + '\'' +
                '}';
    }
}