package com.example.demo.entity;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author zhaoqingjuan
 * @ClassName OperationDTO
 * @Description TODO
 * @date 2017年5月12日 下午17:30:05
 */
@Data
@Builder
public class OperationDTO implements Serializable {

	private static final long serialVersionUID = -8723390964664391368L;

	/**
	 * 邀请人手机号
	 */
    private String inviteMobile;
    /**
     * 被邀请人手机号
     */
    private String beInviteMobile;
    /**
     * 邀请人passportUserId
     */
    private String passportUserId;
    /**
     * 邀请人用户中心Id
     */
    private String passportUserCenterId;
    /**
     * 邀请人渠道
     */
    private String passportChannel;
    /**
     * 被邀请人passportUserId
     */
    private String bePassportUserId;
    /**
     * 被邀请人用户中心Id
     */
    private String bePassportUserCenterId;
    /**
     * 被邀请人渠道
     */
    private String bePassportChannel;


    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 渠道码
     */
    private String channelCode;
    /**
     * 着陆url
     */
    private String landingUrl;
    /**
	 * 业务线:1,理财2,基金3,保险 4,国美金融 6,网贷
	 */
    private int businessType;
    /**
	 * 对应pt字段 来源理财、基金、保险的填写财富端，来自统一入口的填写财富端，来源消费金融的，写xiaojin，来源网贷的，写wangdai
	 */
	private int businessDetail;

	/**
	 * 订单号
	 */
    private String orderNo;
	/** ------------------------>日志新增字段 --->begin --- */
	private String firstLoginType;// 首登标识
	private String actionCode;// 行为类型 注册 登陆？

	/** ------------------------>日志新增字段 --->end --- */
	/** ------------------------>日志新增设备信息字段 --->begin --- */
	private String mobileModel;
	private String osVersion;
	private String networkType;
	private String romInfo;
	private String imei;
	private String macaddress;
	private String idfa;
	/** ------------------------>日志新增设备信息字段 --->end --- */
	/** ------------------------>日志新增两个字段20160629 --->begin --- */
	private String version;// 系统版本号
	private String plant;// 运行平台的类型
	private String orderAmount;// 订单金额
	/** ------------------------>日志新增两个字段20160629 --->end --- */
	/** ------------------------>日志新增两个字段20160629 --->begin --- */
	private String uuid;// 安卓特有设备信息，用于标识唯一用户。

	/** ------------------------>日志新增两个字段20160629 --->end --- */
	private String flog;

	/**-----渠道新增字段--------*/

	/**
	 * 渠道URL
	 */
	private String ChannelUrl;

	/**
	 * 行为时间
	 */
	private Long actionTime;

	/**
	 * 事件code
	 */
	private String eventCode;

	/**
	 * 业务端唯一交易流水号
	 */
	private String serialNo;

	/**
	 * 子事件码:1一级注册，2二级注册
	 */
	private int subEventCode;

	/**-----渠道新增字段结束--------*/
}
