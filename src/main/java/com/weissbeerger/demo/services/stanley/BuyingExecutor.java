package com.weissbeerger.demo.services.stanley;

import com.weissbeerger.demo.model.stanley.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class BuyingExecutor {
    @Autowired
    StorageRepository storageRepository;
    @Autowired
    OrderRepository orderRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Async
    public void starBuyingBook(int orderId, int storedBookId, int qty) {
        Status status = Status.NEW;
        int attempt = 0;
        String message = "In process...";
        while (status==Status.NEW){
            try {
                if(attempt>0){
                    Thread.sleep(1000);
                }
                storageRepository.incrementDecrementStoredBook(storedBookId,qty);
                message="order executed";
                status=Status.COMPLETED;
            }catch (InvalidDataAccessApiUsageException ilEx){
                status=Status.REJECTED;
                message=ilEx.getMessage();
            }
            catch (ObjectOptimisticLockingFailureException ex){
                LOGGER.info("optimistic locking fail");
                ++attempt;
                if(attempt>=5){
                    status=Status.REJECTED;
                    message="server busy try again later";
                };
            }catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
                status=Status.REJECTED;
                message="server error";
            }

        }
        orderRepository.updateStatus(orderId, status,message);

    }
}
