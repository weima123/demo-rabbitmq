package com.donelvy.rabbitmq.enumeration;

/**
 * @author : mawei
 * @description : TODO
 * @since : 2020-03-13 17:36
 */
public enum  MqQueueEnum {

    QUEUE_A("QUEUE_A"),
    QUEUE_B("QUEUE_B");

    private String id;

    MqQueueEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
