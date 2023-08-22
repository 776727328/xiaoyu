package com.example.chatgpt.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer02 {


    /**
     * 一个消费者
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("sp_test_02"))
    public void receive1(String message) {
        System.out.println("message1 = " + message);
    }


    /**
     * 一个消费者
     *
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue("sp_test_02"))
    public void receive2(String message) {
        System.out.println("message2 = " + message);
    }

}