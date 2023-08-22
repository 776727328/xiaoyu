package com.example.chatgpt.produce;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Produce04 {


    /**
     * 注入rabbitTemplate
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Route 路由模式
     */
    public void testRoute() {
        /**
         *  参数1: 交换机名称
         *  参数2: 路由key
         *  参数3: 发送的信息
         * */
        rabbitTemplate.convertAndSend("test_routing", "error", "发送info的key的路由信息");
    }
}
