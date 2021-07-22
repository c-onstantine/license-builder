package com.liming.licensebuilder.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ltf
 * @date 2021-07-20 16:14
 */
@Data
@ToString
@ApiModel
public class LicenseCreatorParam implements Serializable {

    private static final long serialVersionUID = -2967437406781440635L;

    /**
     * 证书subject
     */
    @ApiModelProperty(value = "证书subject")
    private String subject = "buptnsrc_license";

    /**
     * 密钥别称
     */
    @ApiModelProperty(value = "密钥别称")
    private String privateAlias = "privateKey";

    /**
     * 密钥密码（需要妥善保管，不能让使用者知道）
     */
    @ApiModelProperty(value = "密钥密码（需要妥善保管，不能让使用者知道）",required = true)
    private String keyPass;

    /**
     * 访问秘钥库的密码
     */
    @ApiModelProperty(value = "公钥密码",required = true)
    private String storePass;

    /**
     * 证书生成路径
     */
    @ApiModelProperty(value = "证书生成路径,默认在当前路径的license文件夹中")
    private String licensePath;

    /**
     * 密钥库存储路径
     */
    @ApiModelProperty(value = "密钥库存储路径",required = true)
    private String privateKeysStorePath;

    /**
     * 证书生效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "证书生效时间(默认当前时间)")
    private Date issuedTime = new Date();

    /**
     * 证书失效时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "证书失效时间",required = true)
    private Date expiryTime;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private String consumerType = "user";

    /**
     * 用户数量
     */
    @ApiModelProperty(value = "用户数量")
    private Integer consumerAmount = 1;

    /**
     * 描述信息
     */
    @ApiModelProperty(value = "证书描述信息")
    private String description = "";

    /**
     * 额外的服务器硬件校验信息
     */
    @ApiModelProperty(value = "额外的服务器硬件校验信息(如ip,mac地址等）")
    private LicenseCheckModel licenseCheckModel;
}
