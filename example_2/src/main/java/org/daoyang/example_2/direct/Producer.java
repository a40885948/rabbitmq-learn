package org.daoyang.example_2.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author daoyang
 */
public class Producer {


    public static void main(String[] args) throws Exception {

        ////RabbitMQ基本配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //获取连接
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchange = "test_consumer_fanout";
        String routingKey = "consumer1";
        String msg = "hello world";
        channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());

    }
}
