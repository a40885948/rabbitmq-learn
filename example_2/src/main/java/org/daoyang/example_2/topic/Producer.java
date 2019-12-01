package org.daoyang.example_2.topic;

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

        String exchange = "test_consumer_exchange";
        String routingKey = "consumer.save.update";
        String msg = "#号匹配";
        channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());

    }
}
