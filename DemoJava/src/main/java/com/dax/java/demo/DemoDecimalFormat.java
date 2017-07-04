package com.dax.java.demo;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Desc:小数点格式化的几种方法
 * Created by liuxiong on 2017/6/26.
 * Email:liuxiong@corp.netease.com
 */

public class DemoDecimalFormat {
    public static void main(String[] args){
        //decimal();
        //strFormat();
        bigDecimal();
    }

    public static void strFormat(){
        double d = 0.6598;
        String s=String.format("%.2f",d);//表示小数点后任意两位小数，四舍五入
        System.out.println(s);
    }

    public static void decimal(){
        double pi = 31.645926d;
        //模式中的"#"表示如果该位存在字符，则显示字符，如果不存在，则不显示。
        //取整数，默认四舍五入
        System.out.println(new DecimalFormat("0").format(pi));//32
        System.out.println(new DecimalFormat("000").format(pi));//032
        System.out.println(new DecimalFormat("#").format(pi));//32
        System.out.println(new DecimalFormat("###").format(pi));//32
        System.out.println(new DecimalFormat("#.##").format(pi));//31.65

        long aa = 123456789;
        System.out.println(new DecimalFormat(",###").format(aa));//123,456,789
    }

    public static void bigDecimal(){
        double d = 99.1994;
        //BigDecimal bd=new BigDecimal(d);//不允许使用，不能精确得到值
        //BigDecimal bd=BigDecimal.valueOf(d);//推荐
        BigDecimal bd=new BigDecimal(String.valueOf(d));
        double d1=bd.setScale(2,BigDecimal.ROUND_HALF_EVEN).doubleValue();
        System.out.println(d1);
    }
}


