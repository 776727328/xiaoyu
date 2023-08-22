package com.example.chatgpt.consumer;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer05 {


    /**
     * "*"代表一个单词，"#"代表0到多个单词
     *
     * @param message
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = "topic", name = "test_topic"),
                    key = {"ymx.name", "ymx.name.*"}
            )
    })
    public void receive1(String message) {
        System.out.println("message1 = " + message);
    }


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = "topic", name = "test_topic"),
                    key = {"ymx.#", "ymx.name.#"}
            )
    })
    public void receive2(String message) {
        System.out.println("message2 = " + message);
    }
}