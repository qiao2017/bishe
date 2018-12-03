package com.bishe.qiao.bishe.model;

public class ListBook {
    private int bookId;
    private String bookHeadUrl;
    private String bookName;
    private String bookScore;
    private String author;
    private String publish;
    private String content;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookHeadUrl() {
        return bookHeadUrl;
    }

    public void setBookHeadUrl(String bookHeadUrl) {
        this.bookHeadUrl = bookHeadUrl;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookScore() {
        return bookScore;
    }

    public void setBookScore(String bookScore) {
        this.bookScore = bookScore;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
