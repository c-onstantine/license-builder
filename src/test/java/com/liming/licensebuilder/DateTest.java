package com.liming.licensebuilder;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ltf
 * @date 2021-07-20 19:27
 */
@SpringBootTest
public class DateTest {
    @Test
    public void test1(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间：" + sdf.format(d));
    }
    @Test
    public void test2(){
        Date date = new Date();
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(Calendar.DAY_OF_YEAR, 380);
        Date time = cd.getTime();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sd.format(time);
        System.out.println(format);
    }
    @Test
    public void test3(){
        String str = "E:\\Project\\idea project\\SpringBootProject\\license-builder\\license\\license.lic";
        System.out.println(str.replaceAll("\\\\", "/"));
    }
}
