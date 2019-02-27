package io.seanforfun.seckill.rabbitmq;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

/**
 * @author: Seanforfun
 * @date: Created in 2019/2/22 11:10
 * @description: ${description}
 * @modified:
 * @version: 0.0.1
 */
@Configuration
public class MqConfigure {

    // Message Queue configure
//    public final static String SAVE_VEHICLE_QUEUE_NAME = "VEHICLE ADD QUEUE";
//
//    @Bean
//    public Queue getSaveVehicleQueue(){
//        return new Queue(SAVE_VEHICLE_QUEUE_NAME, true);
//    }

    public static final String DIRECT_QUEUE = "DIRECT QUEUE";

    public static final String FANOUT_QUEUE_1 = "FANOUT QUEUE 1";
    public static final String FANOUT_QUEUE_2 = "FANOUT QUEUE 2";
    public static final String FANOUT_EXCHANGE = "FANOUT EXCHANGE";

    public static final String TOPIC_QUEUE_1 = "TOPIC QUEUE 1";
    public static final String TOPIC_QUEUE_2 = "TOPIC QUEUE 2";
    public static final String TOPIC_EXCHANGE = "TOPIC EXCHANGE";

    /**
     * Direct Pattern
     * @return
     */
    @Bean
    public Queue directQueue(){
        return new Queue(DIRECT_QUEUE, true);
    }

    /**
     * Fanout pattern
     * @return
     */
    @Bean
    public Queue fanoutQueue1(){
        return new Queue(FANOUT_QUEUE_1, true);
    }
    @Bean
    public Queue fanoutQueue2(){
        return new Queue(FANOUT_QUEUE_2, true);
    }
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    @Bean
    public Binding fanoutQueue1Bind(){
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }
    @Bean
    public Binding fanoutQueue2Bind(){
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    /**
     * Topic pattern
     * @return
     */
    @Bean
    public Queue topicQueue1(){
        return new Queue(TOPIC_QUEUE_1, true);
    }
    @Bean
    public Queue topicQueue2(){
        return new Queue(TOPIC_QUEUE_2, true);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }
    @Bean
    public Binding topicQueue1Bind(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("test.*");
    }
    @Bean
    public Binding topicQueue2Bind(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("test.test1");
    }
}
