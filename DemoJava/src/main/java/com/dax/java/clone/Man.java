package com.dax.java.clone;

import java.util.HashMap;

/**
 * Desc:
 * Created by liuxiong on 2017/4/21.
 */

public class Man extends Person implements Cloneable{
    public String job;
    public HashMap al = new HashMap();

    @Override
    public String toString() {
        return name+"-" +age+"-"+job +"-" +flag;
    }

    @Override
    public Man clone() {
        Man m = null;
        try {
            m = (Man) super.clone();
            m.al = (HashMap) al.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }
}
