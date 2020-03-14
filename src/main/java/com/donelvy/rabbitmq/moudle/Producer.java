package com.donelvy.rabbitmq.moudle;

import com.donelvy.rabbitmq.enumeration.MQExchangeEnum;
import com.donelvy.rabbitmq.enumeration.MQRoutingKeyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author : mawei
 * @description :
 * @since : 2020-03-13 17:28
 */
@Slf4j
@Component
public class Producer implements RabbitTemplate.ConfirmCallback ,RabbitTemplate.ReturnCallback{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setConfirmCallback(this);
    }


    /**
     * 消息发送到队列中，进行消息的确认，返回监控
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("【produce】 confirm ,回调id:" + correlationData);
        if (ack) {
            log.info("【produce】 confirm success ");
        } else {
            log.info("【produce】 confirm failed ,reason:{}" + cause);
        }
    }

    /**
     * 消息从交换机发送到队列失败，返回监控
     * @param message
     * @param i
     * @param s
     * @param s1
     * @param s2
     */
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        log.info("【produce】returnedMessage [消息从交换机到队列失败]  message：{}",message);

    }
    //direct-exchange
    public void produceDIRECT(String content) {
        log.info("【produce】content:{}",content);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(MQExchangeEnum.DIRECT_EXCHANGE_A.name(), MQRoutingKeyEnum.ROUTING_KEY_B.name(), content, correlationData);
    }
    //fanout-exchange
    public void produceFanout(String content) {
        log.info("【produce】content:{}",content);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(MQExchangeEnum.FANOUT_EXCHANGE.name(), null, content, correlationData);
    }

    //topic-exchange
    public void produceTopic(String content) {
        log.info("【produce】content:{}",content);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(MQExchangeEnum.TOPIC_EXCHANGE.name(), content, content, correlationData);
    }
}
