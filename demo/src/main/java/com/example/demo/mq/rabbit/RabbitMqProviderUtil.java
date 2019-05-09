package com.example.demo.mq.rabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author ransahojie
 * @Description: rabbitmq发送消息
 * @date 2018/9/28 17:49
 */
@Slf4j
public class RabbitMqProviderUtil {

  @Autowired
  private Channel rabbitChannel;

  @Value("${rabitmq.queue.name}")
  private String queueName;

  /**
   * 发送消息
   *
   * @param routeKey 路由键名称
   * @param msgJson 消息json串
   */
  public boolean send(String routeKey, String msgJson) {
    boolean result = true;
    log.info("rabbitmq 消息发送开始 exchange=[{}],routeKey=[{}],msg=[{}]", routeKey, msgJson);
    try {

      rabbitChannel.basicPublish("", queueName, null, msgJson.getBytes());

    } catch (Exception e) {
      log.error("rabbitmq 消息发送失败 exchange=[{}],routeKey=[{}],msg=[{}]", routeKey, msgJson);
      result = false;
    }

    log.info("rabbitmq 消息发送成功 exchange=[{}],routeKey=[{}],msg=[{}]", routeKey, msgJson);

    return result;
  }
}
