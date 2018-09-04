package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ransahojie
 * @Description: TODO
 * @date 2018/6/19 10:34
 */
@Slf4j
@RestController
public class RequestController {

    @RequestMapping("test")
    public Object test(@RequestParam(required = false) int a) {

        log.info("ok");

        if (a < 0) {
            log.info("error");
        }

        return a;
    }
}
