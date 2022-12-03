package com.weissbeerger.demo.controllers;

import com.weissbeerger.demo.model.stanley.*;
import com.weissbeerger.demo.services.stanley.BookService;
import com.weissbeerger.demo.services.stanley.OrderRepository;
import com.weissbeerger.demo.services.stanley.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publicApi")
public class StanleyBookController {
    @Autowired
    BookService bookService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    StorageRepository storageRepository;
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/addBook", method = RequestMethod.PUT)
    public ResponseEntity addBook(@RequestBody BookDTO bookDTO){
        try{
            bookService.addNewBook(bookDTO);}catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    //buy book
    @RequestMapping(value = "/buyBook/{id}", method = RequestMethod.POST)
    public ResponseEntity<OrderBook> buyBook(@PathVariable("id")int bookId, @RequestParam("qty")int qty){
        OrderBook orderBook = bookService.buyBook(bookId, qty);
        return new ResponseEntity(orderBook,HttpStatus.OK);
    }
    //get order status
    @RequestMapping(value = "/order/{orderId}",method = RequestMethod.GET)
    public ResponseEntity<OrderBook> getOrder(@PathVariable("orderId")int orderId){
        OrderBook orderBook = orderRepository.findOrder(orderId);
        return new ResponseEntity(orderBook,HttpStatus.OK);
    }
    //search book
    @RequestMapping(value = "/books/{keyword}",method = RequestMethod.GET)
    public ResponseEntity<StoredBookListDTO>searchBook(@PathVariable("keyword") String keyword){
        List<StoredBook> storedBooks = storageRepository.search(keyword);
        StoredBookListDTO sl = new StoredBookListDTO();
        sl.setBookList(storedBooks);
        return  new ResponseEntity(sl,HttpStatus.OK);
    }

    //list of books
    @RequestMapping(value = "/books",method = RequestMethod.GET)
    public ResponseEntity<StoredBookListDTO>getListOfBooks(){
        List<StoredBook> storedBooks = storageRepository.allStoredBooks();
        StoredBookListDTO sl = new StoredBookListDTO();
        sl.setBookList(storedBooks);
        return  new ResponseEntity(sl,HttpStatus.OK);
    }

}