package com.bishe.qiao.bishe.model;

public class Book {
    private String bookName;
    private String bookUrl;
    private int bookId;

    public Book(String bookName, String bookUrl, int bookId) {
        this.bookName = bookName;
        this.bookUrl = bookUrl;
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public void setBookUrl(String bookUrl) {
        this.bookUrl = bookUrl;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
