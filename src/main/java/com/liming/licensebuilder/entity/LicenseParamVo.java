package com.liming.licensebuilder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author ltf
 * @date 2021-07-21 15:18
 */
@Data
@ToString
@ApiModel
public class LicenseParamVo implements Serializable {

    private static final long serialVersionUID = -2000096890766986997L;
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


    @ApiModelProperty(value = "证书有效时间/天")
    private Integer expireDate;


    @ApiModelProperty(value = "额外的服务器硬件校验信息(如ip,mac地址等）")
    private LicenseCheckModel licenseCheckModel;
}
