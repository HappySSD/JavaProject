package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * rocketmq 配置
 * @author ranshaojie
 * @date 2017年5月14日 下午1:44:57
 */
@Slf4j
@Configuration
public class MqConfig {

    //mq 地址
    @Value("${spring.mq.namesrvAddr}")
    private String nameservAdd;
    //mq 消费者组
    @Value("${spring.mq.producerGroup}")
    private String producerGroup;
    //mq 消费者初始化名称
    @Value("${spring.mq.producerInstanceName}")
    private String producerInstanceName;

    private static DefaultMQProducer producer;

    @PostConstruct
    public void init() throws MQClientException {
        try {
            // 参数信息
            log.info("======================DefaultMQProducer initialize!=====================");
            log.info("initialize param:producerName={}", producerGroup);
            log.info("initialize param:namesrvAddr={}", nameservAdd);

            // 初始化
            producer = new DefaultMQProducer(producerGroup);

            producer.setNamesrvAddr(nameservAdd);

            producer.setInstanceName(producerInstanceName);

            producer.start();

            log.info("DefaultMQProudcer start success! producer:{}",producer);

        } catch (MQClientException e) {
            log.error("MQProducer start MQClientException producer:{},e:{}", producer, e);
        }

    }

    @PreDestroy
    public void destroy() {

        producer.shutdown();    //清理资源，关闭网络连接，注销producer

        log.info("DefaultMQProudcer stop success!");

    }

    /**
     * 发送消息队列
     *
     * @param message 要发送的消息
     * @return 发送状态
     */
    public boolean sendMsg(Message message) {
        try {
            SendResult sendResult = producer.send(message);
            log.info("MQProducer sendMsg sendResult:{}", sendResult);
            return sendResult != null && sendResult.getSendStatus() == SendStatus.SEND_OK;
        } catch (Exception e) {
            log.error("MQProducer sendMsg MQClientException producer:{},e:{}", producer, e);
        }
        return false;
    }

    public SendResult send(Message message) {

        SendResult sendResult = null;
        try {
            sendResult = producer.send(message);
            log.info("MQProducer sendMsg sendResult:{}", sendResult);
        } catch (Exception e) {
            log.error("MQProducer sendMsg MQClientException producer:{},e:{}", producer, e);
        }
        return sendResult;
    }

}
