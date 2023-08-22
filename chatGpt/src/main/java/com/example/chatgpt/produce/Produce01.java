package com.example.chatgpt.produce;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Produce01 {


    /**
     * 注入rabbitTemplate
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Hello world
     */
    public void testHello() {
        /**
         * 参数1: 消息队列的名字
         * 参数2: 发送的消息
         */
        rabbitTemplate.convertAndSend("sp_test_01", "hello world");
    }

}
