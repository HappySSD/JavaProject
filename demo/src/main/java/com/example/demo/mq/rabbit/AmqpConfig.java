package com.example.demo.mq.rabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.IOException;

/**
 * Created by Administrator on 2018/10/11.
 */
@Configuration
public class AmqpConfig {

    @Value("${rabmq.addresses}")
    String addresses;
    @Value("${rabmq.username}")
    String userName;
    @Value("${rabmq.password}")
    String password;
    @Value("${rabmq.port}")
    int port;
    @Value("${rabmq.virtualhost}")
    String virtualHost;
    @Value("${rabitmq.queue.name}")
    String queueName;

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        //必须要设置
        connectionFactory.setPublisherConfirms(true);


        return connectionFactory;
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory());
        return template;

    }

    @Bean
    public Channel rabbitChannel() throws IOException {
        Connection connection = rabbitConnectionFactory().createConnection();
        Channel channel = connection.createChannel(false);
        channel.queueDeclare(queueName, false, false, false, null);
        return channel;
    }
}
