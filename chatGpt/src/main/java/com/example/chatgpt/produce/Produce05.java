package com.example.chatgpt.produce;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Produce05 {


    /**
     * 注入rabbitTemplate
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Topic 动态路由  订阅模式
     */
    public void testTopic() {
        /**
         *  参数1: 交换机名称
         *  参数2: 路由key
         *  参数3: 发送的消息
         * */
        rabbitTemplate.convertAndSend("test_topic", "ymx.name.Mr_YanMingXin", "ymx路由消息");
    }
}
