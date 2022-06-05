package com.weissbeerger.demo.model.stanley;

import java.util.List;

public class StoredBookListDTO {
    List<StoredBook> bookList;

    public StoredBookListDTO() {
    }

    public List<StoredBook> getBookList() {
        return bookList;
    }

    public void setBookList(List<StoredBook> storedBookList) {
        this.bookList = storedBookList;
    }
}
