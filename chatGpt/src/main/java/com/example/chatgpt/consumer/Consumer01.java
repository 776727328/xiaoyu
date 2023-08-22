package com.example.chatgpt.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer01 {


    /**
     * 一个消费者
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("sp_test_01"))
    public void receive1(String message) {
        System.out.println("message1 = " + message);
    }
}