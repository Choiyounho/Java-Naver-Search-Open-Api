package console.domain;

public class ExcelVo {
    private String title;
    private String author;
    private String company;
    private String isbn;
    private String imageurl;


    public ExcelVo(String title, String author, String company, String isbn, String imgurl) {
        super();
        this.title = title;
        this.author = author;
        this.company = company;
        this.isbn = isbn;
        this.imageurl = imgurl;
    }

    @Override
    public String toString() {
        return "console.domain.ExcelVo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", company='" + company + '\'' +
                ", isbn='" + isbn + '\'' +
                ", imageurl='" + imageurl + '\'' +
                '}';
    }
}