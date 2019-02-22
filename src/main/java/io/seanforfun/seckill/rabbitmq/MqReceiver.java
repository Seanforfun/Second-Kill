package io.seanforfun.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/22 11:21
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
@Slf4j
public class MqReceiver {
    public AtomicInteger count = new AtomicInteger(0);
    public int i = 0;

    @RabbitListener(queues = {MqConfigure.FANOUT_QUEUE_1})
    @RabbitHandler
    public  void receive(String receive, Channel channel, Message message) throws IOException {
        log.info("[Receiver0]: receive {}", receive);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println(count.getAndIncrement());
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
    @RabbitListener(queues = {MqConfigure.FANOUT_QUEUE_2})
    @RabbitHandler
    public void receive1(String receive, Channel channel, Message message) throws IOException {
        log.info("[Receiver1]: receive {}", receive);
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            System.out.println(count.getAndIncrement());
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }
    }
    @RabbitListener(queues = {MqConfigure.TOPIC_QUEUE_1})
    @RabbitHandler
    public void receive2(String receive, Channel channel, Message message) throws IOException {
        log.info("[Receiver0]: receive {}", receive);
    }
    @RabbitListener(queues = {MqConfigure.TOPIC_QUEUE_2})
    @RabbitHandler
    public void receive3(String receive, Channel channel, Message message) throws IOException {
        log.info("[Receiver1]: receive {}", receive);
    }
}
