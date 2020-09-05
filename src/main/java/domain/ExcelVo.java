package domain;

public class ExcelVo {
    private String title;
    private String author;
    private String company;
    private String isbn;
    private String imageurl;

    public ExcelVo() {    }

    public ExcelVo(String title, String author, String company, String isbn, String imgurl) {
        super();
        this.title = title;
        this.author = author;
        this.company = company;
        this.isbn = isbn;
        this.imageurl = imgurl;
    }
    public ExcelVo(String title, String author, String company) {
        super();
        this.title = title;
        this.author = author;
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public String toString() {
        return "domain.ExcelVo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", company='" + company + '\'' +
                ", isbn='" + isbn + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }
}