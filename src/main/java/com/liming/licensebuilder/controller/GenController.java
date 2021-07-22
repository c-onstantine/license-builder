package com.liming.licensebuilder.controller;

import com.liming.licensebuilder.entity.LicenseCheckModel;
import com.liming.licensebuilder.entity.LicenseCreatorParam;
import com.liming.licensebuilder.entity.LicenseParamVo;
import com.liming.licensebuilder.license.AbstractServerInfos;
import com.liming.licensebuilder.license.LicenseCreator;
import com.liming.licensebuilder.license.ServerInfos;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ltf
 * @date 2021-07-20 18:40
 * 生成证书接口
 */
@Slf4j
@RestController
@RequestMapping("/license")
@Api(tags = "证书生成接口")
public class GenController {
    @Value("${license.licensePath}")
    private String licensePath;
    /**
     * 获取服务器硬件信息
     */
    @RequestMapping(value = "/getServerInfos",method = RequestMethod.GET)
    @ApiOperation("获取服务器硬件信息接口，可以得到ip和mac，可以在获取结果中手动添加新的硬件信息。")
    public LicenseCheckModel getServerInfos(){
        AbstractServerInfos abstractServerInfos = new ServerInfos();
        return abstractServerInfos.getServerInfos();
    }
    ///**
    // * 生成证书
    // * @param param 生成证书需要的参数，如：{"subject":"ccx-models","privateAlias":"privateKey","keyPass":"5T7Zz5Y0dJFcqTxvzkH5LDGJJSGMzQ","storePass":"3538cef8e7","licensePath":"C:/Users/zifangsky/Desktop/license.lic","privateKeysStorePath":"C:/Users/zifangsky/Desktop/privateKeys.keystore","issuedTime":"2018-04-26 14:48:12","expiryTime":"2018-12-31 00:00:00","consumerType":"User","consumerAmount":1,"description":"这是证书描述信息","licenseCheckModel":{"ipAddress":["192.168.245.1","10.0.5.22"],"macAddress":["00-50-56-C0-00-01","50-7B-9D-F9-18-41"],"cpuSerial":"BFEBFBFF000406E3","mainBoardSerial":"L1HF65E00X9"}}
    // * @return java.util.Map<java.lang.String,java.lang.Object>
    // */
    //@RequestMapping(value = "/generateLicense1",method = RequestMethod.GET)
    //@ApiOperation("生成证书接口")
    //public Map<String,Object> generateLicense1(@RequestBody LicenseCreatorParam param) {
    //    Map<String,Object> resultMap = new HashMap<>(2);
    //
    //    if(StringUtils.isEmpty(param.getLicensePath())){
    //        param.setLicensePath(licensePath);
    //    }
    //
    //    LicenseCreator licenseCreator = new LicenseCreator(param);
    //    boolean result = licenseCreator.generateLicense();
    //
    //    if(result){
    //        resultMap.put("result","ok");
    //        resultMap.put("msg",param);
    //    }else{
    //        resultMap.put("result","error");
    //        resultMap.put("msg","证书文件生成失败！");
    //    }
    //    return resultMap;
    //}


    /**
     * 生成证书
     */
    @RequestMapping(value = "/generateLicense",method = RequestMethod.POST)
    @ApiOperation("生成证书接口")
    public Map<String,Object> generateLicense(@RequestBody LicenseParamVo paramVo) {
        Map<String,Object> resultMap = new HashMap<>(2);
        LicenseCreatorParam param = new LicenseCreatorParam();
        param.setKeyPass(paramVo.getKeyPass());
        param.setStorePass(paramVo.getStorePass());
        Integer expireDate = paramVo.getExpireDate();
        Calendar cd = Calendar.getInstance();
        cd.setTime(param.getIssuedTime());
        cd.add(Calendar.DAY_OF_YEAR, expireDate);
        Date time = cd.getTime();
        param.setExpiryTime(time);
        param.setLicenseCheckModel(paramVo.getLicenseCheckModel());
        System.out.println(licensePath);
        param.setLicensePath(licensePath+"/license.lic");
        param.setPrivateKeysStorePath(licensePath+"/privateKeys.keystore");
        LicenseCreator licenseCreator = new LicenseCreator(param);
        boolean result = licenseCreator.generateLicense();

        if(result){
            resultMap.put("result","ok");
            resultMap.put("msg",param);
        }else{
            resultMap.put("result","error");
            resultMap.put("msg","证书文件生成失败！");
        }
        return resultMap;
    }
    /**
     * 生成 公钥私钥库
     * @param pukey
     * @param prkey
     * @return
     */
    @RequestMapping(value = "/getKeyStore/{pukey}/{prkey}",method = RequestMethod.GET)
    @ApiOperation("生成公私钥库接口,秘钥口令必须大于6个字符")
    public Map<String,Object> getKeyStore(@PathVariable("pukey") String pukey, @PathVariable("prkey") String prkey){
        Map<String,Object> resultMap = new HashMap<>(2);
        try {
            Process cmd = Runtime.getRuntime().exec("cmd");
            BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(cmd.getOutputStream()));
            //bw.write("e:");
            //bw.newLine();
            //
            bw.write("md license");
            bw.newLine();
            bw.write("cd license");
            bw.newLine();
            bw.flush();
            bw.write("keytool -genkeypair -keysize 1024 -validity 3650 -alias \"privateKey\" -keystore \"privateKeys.keystore\" -storepass "+pukey+" -keypass "+prkey+" -dname \"CN=bupt, OU=bupt, O=nsrc, L=BJ, ST=BJ, C=CN\"");
            bw.newLine();
            bw.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bw.write("keytool -exportcert -alias \"privateKey\" -keystore \"privateKeys.keystore\" -storepass "+pukey+" -file \"certfile.cer\"");
            bw.newLine();
            bw.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bw.write("echo y | keytool -import -alias \"publicCert\" -file \"certfile.cer\" -keystore \"publicCerts.keystore\" -storepass "+pukey);
            bw.newLine();
            bw.flush();
        } catch (Exception e) {
            resultMap.put("result","error");
            resultMap.put("msg",e.getMessage());
        }
        resultMap.put("result","ok");
        resultMap.put("msg","200");
        return resultMap;
    }

}
