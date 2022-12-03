package com.weissbeerger.demo.model.stanley;

public class BookDTO {
    private String bookName;
    private String authorName;
    private int quantity;

    public BookDTO() {
    }

    public BookDTO(String bookName, String authorName, int quantity) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.quantity = quantity;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
