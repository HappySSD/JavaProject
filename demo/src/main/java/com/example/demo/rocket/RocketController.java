package com.example.demo.rocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.config.MqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ransahojie
 * @Description: TODO
 * @date 2018/5/28 11:04
 */
@Slf4j
@RestController
public class RocketController {

    @Autowired
    private MqConfig mqConfig;

    @Value("${spring.mq.topic}")
    private String topic;

    @Value("${spring.mq.tag}")
    private String tag;

    @RequestMapping("send")
    public void sendMq(){

        try {
            Message message=new Message();
            message.setTopic(topic);
            message.setTags(tag);
            JSONObject jsonObject = new JSONObject();
            String json = "{\"activeCode\":\"wm0003\",\"actionCode\":\"zhuce\",\"actionTime\":1527737792333,\"activeCode\":\"VM003\",\"beInviteMobile\":\"18601991115\",\"beUserId\":\"2222fbfe7d7c40d9a6eaf343711dbd1\",\"businessType\":\"6\",\"channelCode\":\"WM000001\",\"channelUrl\":\"http://t1.gomefinance.com.cn/src/html/login.html?refm=WM000001\",\"eventCode\":\"register\",\"inviteCode\":\"\",\"inviteMobile\":\"\",\"landingUrl\":\"https://m.gomemyc.com/static/invite/index.html?id=10590020&fxgl=fxgl&gatewayInviteCode=inJEgLFU&from=singlemessage\",\"orderAmount\":\"663.55\",\"plant\":\"h5\",\"serialNo\":\"d121daa1-56e6-442f-8ae6-be4603876a81\",\"userId\":\"\"}";
            jsonObject = JSONObject.parseObject(json);
            message.setBody(JSON.toJSONString(jsonObject).getBytes());
            mqConfig.sendMsg(message);
            log.info("发送rocketmq消息成功");
        }catch (Exception e){
            log.error("发送mq异常");
        }
    }

}
