package com.weissbeerger.demo.model.stanley;

import jakarta.persistence.*;

@Entity
public class StoredBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne
    private Book book;
    private int quantity;
    @Version
    private int version;

    public StoredBook() {
    }

    public StoredBook(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
