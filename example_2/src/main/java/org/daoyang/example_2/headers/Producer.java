package org.daoyang.example_2.headers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daoyang
 */
public class Producer {


    public static void main(String[] args) throws Exception {

        //RabbitMQ基本配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //获取连接
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchange = "test_consumer_headers";
        String msg = "hello world";

        //经测试不支持模糊匹配
        Map<String, Object> headers = new HashMap<>(16);
        headers.put("test", "daoyang.*");

        // 生成发送消息的属性
        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .headers(headers)
                .build();
        channel.basicPublish(exchange, "", true, props, msg.getBytes());
    }
}
