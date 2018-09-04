package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        val userId = "f494a1874380462a8d39947d0e3cad89";
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        JSONObject jsonObject = new JSONObject();
//        JSONObject dataJsonObject = new JSONObject();
//        dataJsonObject.put("userId", userId);
//        jsonObject.put("timestamp", String.valueOf(new Date().getTime()));
//        jsonObject.put("format", "JSON");
//        jsonObject.put("data", dataJsonObject);
//        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toJSONString(), headers);
//        ResponseEntity<String> apiReturn = restTemplate.exchange("https://m-uat.gomelife.com/insurance-web/insurance/account/insurance/status", HttpMethod.POST, entity, String.class);


        val url = "https://m-uat.gomelife.com/insurance-web/insurance/account/insurance/status";
        JSONObject jsonObject = new JSONObject();
        JSONObject dataJsonObject = new JSONObject();
        dataJsonObject.put("userId", userId);
        jsonObject.put("timestamp", String.valueOf(new Date().getTime()));
        jsonObject.put("format", "JSON");
        jsonObject.put("data", dataJsonObject);

        try {
            String result = HttpClientUtils.post(url, jsonObject.toJSONString(), "application/json", "utf-8", null, null);
            log.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testUtils() {
        List a = Lists.newArrayList(1, 2, 3, 4, 5).stream().filter(i -> i > 6).collect(Collectors.toList());
        log.info(String.valueOf(CollectionUtils.isEmpty(a)));
    }

}
