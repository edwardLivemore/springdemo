package com.example.springdemo.service.impl;

import com.example.springdemo.dto.Message;
import com.example.springdemo.req.MessageReq;
import com.example.springdemo.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private ThreadPoolExecutor taskPool;

    @Autowired
    private ThreadPoolExecutor sendPool;

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 20, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200), new ThreadPoolExecutor.CallerRunsPolicy());

    PriorityBlockingQueue<Message> queue = new PriorityBlockingQueue<>(100, (m1, m2) -> {
        long result = m2.getTimeStamp() - m1.getTimeStamp();
        if (result > 0) return -1;
        if (result < 0) return 1;
        return 0;
    });

    Random random = new Random();

    @Override
    public void run() {
        log.info("message service is running...");
        while (true) {
            Message message = queue.peek();
            if (message != null) {
                if (message.getTimeStamp() <= System.currentTimeMillis()) {
                    queue.poll();
                    taskPool.execute(() -> {
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
        // 模拟同时发送20个用户
        for(int i = 0; i < 20; i++) {
            sendPool.execute(() -> log.info("thread: {}, send message... id: {}, content: {}",
                    Thread.currentThread().getName(), message.getId(), message.getContent()));
        }
        sleep(random.nextInt(10000));
    }

    @Override
    public void addMessage(MessageReq req) {
        log.info("add msg: {}", req);
        queue.offer(new Message(req.getId(), req.getDateTime().toInstant(ZoneOffset.of("+8")).toEpochMilli(), req.getContent()));
    }

    @Override
    public void showMessage() {
        log.info("show message start...");
        int count = 100;
        for (int i = 0; i < count; i++) {
            int finalI = i;
            executor.execute(() -> log.info(String.valueOf(finalI)));
        }
        log.info("show message end...");
    }
}
