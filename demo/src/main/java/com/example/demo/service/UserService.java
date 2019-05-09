package com.example.demo.service;

import com.example.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

  @Autowired
  private RedisTemplate redisTemplate;

  @Cacheable(value = "user", key = "#name")
  public User getUserByName(String name) {
    slowService();
    return User.builder().age(1).name("tom").build();
  }

  private void slowService() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
