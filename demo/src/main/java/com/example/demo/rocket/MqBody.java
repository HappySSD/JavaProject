package com.example.demo.rocket;

import lombok.Builder;
import lombok.Data;

/**
 * @author ransahojie
 * @Description: TODO
 * @date 2018/5/28 11:11
 */
@Data
@Builder
public class MqBody {
    private String passportUserId;
    private String serviceCode;
}
