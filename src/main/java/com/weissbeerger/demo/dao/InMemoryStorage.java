package com.weissbeerger.demo.dao;

import com.weissbeerger.demo.model.taModel.Client;
import com.weissbeerger.demo.model.taModel.Status;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class InMemoryStorage {

    ConcurrentHashMap<String, Client> inMemoryStorage = new ConcurrentHashMap<>();
    BlockingQueue<String> queue =  new LinkedBlockingDeque<>(500);


    public List<Client> getClients() {
        return new ArrayList<Client>(inMemoryStorage.values());
    }

    public void setOfflineForExpired(long expTime) {
        inMemoryStorage.forEach((k,v)->inMemoryStorage.compute(k,(key,val)->{
            if(val.getStatus()== Status.ONLINE){
                if(val.getUpdateTime()<expTime){
                    val.setStatus(Status.OFFLINE);
                    add("[CLIENT OFFLINE]"+val);
                }
            }
        return val;}));
    }

    public void add(Client client) {
        inMemoryStorage.put(client.getClientId(),client);
    }

    public boolean setStatus(String clientId, Status s) {
        Client client = inMemoryStorage.computeIfPresent(clientId, (k, v) -> {
                    v.setStatus(s);
                    v.setUpdateTime(System.currentTimeMillis());
                    add("[CLIENT ALIVE]"+v);
                    return v;
                }
        );
        return client!=null;
    };

    public void add(String msg){
        if(queue.size()>499){
            queue.poll();
        }
        queue.add(msg);
    }
    public List<String> getAll(){
        return new ArrayList<>(queue);
    }
}
