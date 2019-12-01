package org.daoyang.example_2.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import static com.rabbitmq.client.BuiltinExchangeType.DIRECT;
import static com.rabbitmq.client.BuiltinExchangeType.TOPIC;

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

        String exchangeName = "test_consumer_direct";
        String bindingKey = "consumer2";
        String queueName = "test_consumer_queue2";

        //创建一个type=DIRECT、持久化的、非自动删除的直连交换器
        channel.exchangeDeclare(exchangeName, DIRECT, true, false, null);
        //创建一个持久化的、非排他的、非自动删除的队列
        channel.queueDeclare(queueName, true, false, false, null);
        //将交换器与队列通过绑定键绑定
        channel.queueBind(queueName, exchangeName, bindingKey);

        //启用自定义消费者MyConsumer
        channel.basicConsume(queueName, true, new MyConsumer(channel));
    }

}
