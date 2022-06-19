package com.example.springdemo.service.impl;

import com.example.springdemo.dto.Message;
import com.example.springdemo.req.MessageReq;
import com.example.springdemo.service.MessageService;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Thread.sleep;

@Slf4j
public class MessageServiceImpl implements MessageService {
    PriorityQueue<Message> priorityQueue = new PriorityQueue<>((m1, m2) -> {
        long result = m2.getTimeStamp() - m1.getTimeStamp();
        if(result > 0) return 1;
        if (result < 0) return -1;
        return 0;
    });

    AtomicReference<PriorityQueue<Message>> queue = new AtomicReference<>();

    ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 50, 60,TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());

    Random random = new Random();

    @Override
    public void run() {
        log.info("message service is running...");
        queue.set(priorityQueue);
        while (true) {
            Message message = queue.get().peek();
            if(message != null) {
                if(message.getTimeStamp() <= System.currentTimeMillis()) {
                    queue.get().poll();
                    executor.execute(() -> {
                        try {
                            sendMsg(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            log.error(e.getMessage());
                        }
                    });
                }
            }
//            try {
//                sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                log.error(e.getMessage());
//            }
        }
    }

    private void sendMsg(Message message) throws InterruptedException {
        log.info("thread: {}, send message... id: {}, content: {}",
                Thread.currentThread().getName(), message.getId(), message.getContent());
        sleep(random.nextInt(10000));
    }

    @Override
    public void addMessage(MessageReq req) {
        log.info("add msg: {}", req);
        queue.get().offer(new Message(req.getId(), req.getDateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli(), req.getContent()));
    }
}
