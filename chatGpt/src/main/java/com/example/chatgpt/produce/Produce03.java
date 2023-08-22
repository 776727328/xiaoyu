package com.example.chatgpt.produce;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Produce03 {


    /**
     * 注入rabbitTemplate
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Fanout 广播
     */
    public void testFanout() {
        /**
         *  参数1: 交换机名称
         *  参数2: routingKey
         *  参数3: message
         * */
        rabbitTemplate.convertAndSend("test_fanout_03", "", "Fanout的模型发送的消息");
    }

}
