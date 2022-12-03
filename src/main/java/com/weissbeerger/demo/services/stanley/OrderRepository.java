package com.weissbeerger.demo.services.stanley;

import com.weissbeerger.demo.model.stanley.OrderBook;
import com.weissbeerger.demo.model.stanley.Status;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OrderRepository {
    @PersistenceContext
    private EntityManager em;
    @Transactional
    public void createOrder(OrderBook o){
        OrderBook orderBook = findOrder(o.getId());
        if(orderBook !=null){
            throw new IllegalArgumentException("order already exist");
        }
        em.persist(o);
    }
    @Transactional
    public void updateStatus(int orderId, Status status, String message){
        OrderBook o = findOrder(orderId);
        o.setStatus(status);
        o.setMessage(message);
        em.persist(o);
    }
    @Transactional
    public OrderBook findOrder(int id){
        return em.find(OrderBook.class,id);
    }
}
