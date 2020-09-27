package console.domain;

import static utils.CommonsConstant.ROOT_DIRECTORY;

public class ExcelVo {

    private String title;
    private String author;
    private String company;
    private String isbn;
    private String imageUrl;

    private ExcelVo(String title, String author, String company, String isbn, String imageUrl) {
        this.title = title;
        this.author = author;
        this.company = company;
        this.isbn = isbn;
        this.imageUrl = imageUrl;
    }

    private ExcelVo(String title, String author, String company) {
        this.title = title;
        this.author = author;
        this.company = company;
    }

    // 정적 팩토리 메소드
    public static ExcelVo search(String title, String author, String company) {
        return new ExcelVo(title, author, company);
    }

    public static ExcelVo print(String[] cellArr) {
        String title = cellArr[0];
        String author = cellArr[1];
        String company = cellArr[2];
        String isbn = cellArr[3];
        String imageUrl = cellArr[4];
        return new ExcelVo(title, author, company, isbn, imageUrl);
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
        return ROOT_DIRECTORY + imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "제목 : '" + title + '\'' +
                ", 저자 : '" + author + '\'' +
                ", 출판사 : '" + company + '\'' +
                ", isbn 번호 : '" + isbn + '\'' +
                ", 책 이미지 : '" + imageUrl + '\'';
    }
}