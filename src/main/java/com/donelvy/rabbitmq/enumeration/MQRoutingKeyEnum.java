package com.donelvy.rabbitmq.enumeration;

/**
 * @author : mawei
 * @description : TODO
 * @since : 2020-03-13 17:37
 */
public enum MQRoutingKeyEnum {
    ROUTING_KEY_A("ROUTING_KEY_A"),
    ROUTING_KEY_B("ROUTING_KEY_B");

    private String id;

    MQRoutingKeyEnum(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
