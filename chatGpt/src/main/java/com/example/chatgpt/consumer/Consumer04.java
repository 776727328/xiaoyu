package com.example.chatgpt.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.RabbitUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer04 {


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "test_routing", type = "direct"),//自定交换机名称和类型
                    key = {"info", "error", "warn"}
            )
    })
    public void receive1(String message, Channel channel) throws Exception {
        long msgTag = 0;
        System.out.println("message1 = " + message);
        if (msgTag <= 2) {

            throw new Exception("模拟异常");

        }

    }


//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value = @Queue,
//                    exchange = @Exchange(value = "test_routing", type = "direct"),
//                    key = {"error"}
//            )
//    })
//    public void receive2(String message) {
//        System.out.println("message2 = " + message);
//    }
}