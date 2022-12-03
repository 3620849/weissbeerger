package com.weissbeerger.demo.services.stanley;

import com.weissbeerger.demo.model.stanley.Author;
import com.weissbeerger.demo.model.stanley.Book;
import jakarta.persistence.*;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BookRepository {
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public void createNewBook(Book book){
        Book bookDb = findBookByName(book.getBookName());
        if(bookDb!=null){
            throw new IllegalArgumentException("book with such name already exist");
        }
        List<Author> authors = em.createQuery("SELECT a FROM Author a where a.authorName=:name", Author.class)
                .setParameter("name", book.getAuthor().getAuthorName()).getResultList();
        Author author = book.getAuthor();
        if(authors==null || authors.size()==0){
            em.persist(author);
        }else {
            author=authors.get(0);
        }
        if(author==null){
            throw new IllegalArgumentException("no such author");
        }
        book.setAuthor(author);
        em.persist(book);
        em.flush();
    }
    @Transactional
    public Book findBookByName(String name){
        List<Book> books = em.createQuery("SELECT b FROM Book b where b.bookName=:name", Book.class)
                .setParameter("name", name).getResultList();
        if(books==null || books.size()==0){
            return null;
        }
        return books.get(0);
    };

}
