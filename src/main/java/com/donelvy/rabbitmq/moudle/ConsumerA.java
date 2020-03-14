package com.donelvy.rabbitmq.moudle;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author : mawei
 * @description : TODO
 * @since : 2020-03-13 18:20
 */
@Slf4j
@Component
public class ConsumerA {
    /**
     * 消费队列A的消息
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = "QUEUE_A")//监听队列A的消息
    public void consume(Message message, Channel channel) throws IOException {
        try {
            log.info("【consume】ConsumerA,content:{}", message.getBody());
            //手动ack
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        } catch (Exception e) {
            log.info("【consume】 exception:{}",e.getCause());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }

    }
}
