package com.dax.java.clone;

import java.util.ArrayList;

/**
 * Desc:
 * Created by liuxiong on 2017/4/21.
 * Email:liuxiong@corp.netease.com
 */

public class Man extends Person {
    public String job;
    public ArrayList al = new ArrayList();

    @Override
    public String toString() {
        return name+"-" +age+"-"+job +"-" +flag;
    }

}
