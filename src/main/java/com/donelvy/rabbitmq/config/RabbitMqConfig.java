package com.donelvy.rabbitmq.config;

import com.donelvy.rabbitmq.enumeration.MQExchangeEnum;
import com.donelvy.rabbitmq.enumeration.MQRoutingKeyEnum;
import com.donelvy.rabbitmq.enumeration.MqQueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : mawei
 * @description : TODO
 * @since : 2020-03-13 16:20
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 基本概念
     * 1.exchange:消息交换机，决定消息按什么规则，路由到哪个队列
     * 2.queue：队列，消息的载体，每个消息都会被投递到一个或者多个队列
     * 3.binding：绑定，将交换机和队列按照路由规则进行绑定
     * 4.routingKey：路由关键字,exchange
     * 5.producer：生产者根据这个关键字进行消息投递
     * 6.consumer：消费者
     * 7.channel：消息通道,在客户端的链接中可以建立多个channel，每个channel代表一段会话
     */

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.port}")
    private int port;

    @Value("${rabbitmq.userName}")
    private String userName;

    @Value("${rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }


    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    @Bean
    public Queue queueA() {
        return new Queue(MqQueueEnum.QUEUE_A.name(), true);//开启持久化
    }

    @Bean
    public Queue queueB() {
        return new Queue(MqQueueEnum.QUEUE_B.name(), true);//开启持久化
    }

    //===========================direct-exchange==============================================

    /**
     * 设置交换机
     * routing-key
     *
     * @return
     */
    @Bean
    public DirectExchange directExchangeA() {
        return new DirectExchange(MQExchangeEnum.DIRECT_EXCHANGE_A.name(), true, false);//交换机消息持久化
    }


    /**
     * 交换机绑定队列
     * 一个交换机可以绑定多个队列（也就是消息可以通过一个交换机，发送到不同的队列去）
     *
     * @return
     */
    @Bean
    public Binding bindingA() {
        return BindingBuilder
                .bind(queueA())
                .to(directExchangeA())
                .with(MQRoutingKeyEnum.ROUTING_KEY_A.name());

    }


    @Bean
    public Binding bindingB() {
        return BindingBuilder
                .bind(queueB())
                .to(directExchangeA())
                .with(MQRoutingKeyEnum.ROUTING_KEY_B.name());

    }

    //===========================direct-exchange==============================================


    //===========================fanoutExchange==============================================
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(MQExchangeEnum.FANOUT_EXCHANGE.name(), true, false);
    }

    @Bean
    public Binding bindingFanoutB() {
        return BindingBuilder
                .bind(queueB())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindingFanoutA() {
        return BindingBuilder
                .bind(queueA())
                .to(fanoutExchange());
    }

//===========================fanoutExchange==============================================


//===========================topic-exchange==============================================

    @Bean
    public TopicExchange toPicExchange() {
        return new TopicExchange(MQExchangeEnum.TOPIC_EXCHANGE.name(), true, false);
    }

    @Bean
    public Binding bindingTopicA(){
        return BindingBuilder
                .bind(queueA())
                .to(toPicExchange())
                .with("item.*");
    }

    @Bean
    public Binding bindingTopicB(){
        return BindingBuilder
                .bind(queueB())
                .to(toPicExchange())
                .with("321.*");
    }


//===========================topic-exchange==============================================


}
