package io.seanforfun.seckill.rabbitmq;

import io.seanforfun.seckill.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/22 11:21
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Service
@Slf4j
public class MqSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(Object message){
        String msgString = RedisService.beanToString(message);
        log.info("[Sender]: send message", msgString);
        amqpTemplate.convertAndSend(MqConfigure.DIRECT_QUEUE, msgString);
    }

    public void fanoutSender(Object message){
        String msgString = RedisService.beanToString(message);
        log.info("[Sender]: send message", msgString);
        amqpTemplate.convertAndSend(MqConfigure.FANOUT_EXCHANGE, "", msgString);
    }

    public void topicSender(Object message){
        String msgString = RedisService.beanToString(message);
        log.info("[Sender]: send message", msgString);
        amqpTemplate.convertAndSend(MqConfigure.TOPIC_EXCHANGE, "test.test1", msgString);
    }
}
