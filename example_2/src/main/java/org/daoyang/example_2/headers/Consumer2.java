package org.daoyang.example_2.headers;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

import static com.rabbitmq.client.BuiltinExchangeType.HEADERS;

/**
 * @author daoyang
 */
public class Consumer2 {

    public static void main(String[] args) throws Exception {

        //RabbitMQ基本配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //获取连接
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_consumer_headers";
        String queueName = "test_consumer_queue2";

        Map<String, Object> headers = new HashMap<>(16);
        headers.put("test", "daoyang.#");

        //创建一个type=DIRECT、持久化的、非自动删除的直连交换器
        channel.exchangeDeclare(exchangeName, HEADERS, true, false, null);
        //创建一个持久化的、非排他的、非自动删除的队列
        channel.queueDeclare(queueName, true, false, false, null);
        //将交换器与队列通过headers绑定
        channel.queueBind(queueName, exchangeName, "", headers);

        //启用自定义消费者MyConsumer
        channel.basicConsume(queueName, true, new MyConsumer(channel));
    }

}
