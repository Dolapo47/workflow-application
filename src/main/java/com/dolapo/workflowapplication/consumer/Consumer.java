package com.dolapo.workflowapplication.consumer;

import com.dolapo.workflowapplication.collection.WorkItem;
import com.dolapo.workflowapplication.repository.WorkItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private final WorkItemRepository workItemRepository;
    private final ExecutorService executorService;

    public Consumer(WorkItemRepository workItemRepository) {
        this.workItemRepository = workItemRepository;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumeJsonMessage(WorkItem workItem){
        executorService.execute(() -> {
            try{
                LOGGER.info(String.format("Received message -> %s", workItem.toString()));
                int result = workItem.getValue() * workItem.getValue();
                workItem.setResult(result);
                workItem.setStatus("Processed");
                workItem.setUpdatedAt(LocalDateTime.now().toString());
                workItemRepository.save(workItem);
            }catch(Exception e){
                e.printStackTrace();
            }
        });

    }
}
