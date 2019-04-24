package com.dax.java.demo;

/**
 * Desc: precision demo
 * Created by liuxiong on 2019/3/19.
 * Email:liuxiong@corp.netease.com
 */
public class DemoPrecision {
    public static void main(String[] args){
        System.out.println(0.1 + 0.2);  //输出：0.30000000000000004
        System.out.println(1.1 + 1.2);  //输出：2.3
        System.out.println(0.1f + 0.2f);//输出：0.3
    }
}
