package com.weissbeerger.demo.services.stanley;

import com.weissbeerger.demo.model.stanley.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    StorageRepository storageRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    BuyingExecutor buyingExecutor;
    public void addNewBook(BookDTO bookDTO){
        Author author = new Author(bookDTO.getAuthorName());
        bookRepository.createNewBook(new Book(author,bookDTO.getBookName()));
        storageRepository.createStoredBook(bookDTO.getBookName(),bookDTO.getQuantity());
    }
    public OrderBook buyBook(int storedBookId, int qty){
        OrderBook o = new OrderBook(Status.NEW);
        orderRepository.createOrder(o);
        buyingExecutor.starBuyingBook(o.getId(),storedBookId,qty);
        return o;
    }

}
