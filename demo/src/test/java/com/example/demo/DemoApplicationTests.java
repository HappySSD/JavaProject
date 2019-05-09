package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

  @Autowired
  private RedisTemplate redisTemplate;

  @Test
  public void testGetUser() {
    redisTemplate.opsForValue().set("test", "ok");
    String result = (String) redisTemplate.opsForValue().get("test");
    log.info(JSON.toJSONString(result));
  }

  @Test
  public void testRedis() {
    User result = (User) redisTemplate.opsForValue().get("user::tom");
    log.info(JSON.toJSONString(result));
  }
}
