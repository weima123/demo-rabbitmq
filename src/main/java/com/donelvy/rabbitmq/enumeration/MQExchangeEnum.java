package com.donelvy.rabbitmq.enumeration;

/**
 * @author : mawei
 * @description : TODO
 * @since : 2020-03-13 17:58
 */
public enum MQExchangeEnum {
    DIRECT_EXCHANGE_A("DIRECT_EXCHANGE_A"),
    FANOUT_EXCHANGE("FANOUT_EXCHANGE"),
    TOPIC_EXCHANGE("TOPIC_EXCHANGE")
    ;

    private String id;

    MQExchangeEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
