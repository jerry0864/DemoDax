package com.dax.java.clone;

import java.util.HashMap;

/**
 * Desc:
 * Created by liuxiong on 2017/4/20.
 * Email:liuxiong@corp.netease.com
 */

public class Person implements Cloneable {
    public String name;
    public int age;
    boolean flag = false;
    public HashMap info = new HashMap();

    @Override
    public Person clone()  {
        Person p = null;
        try {
            p = (Person) super.clone();
            p.flag = true;
            p.info = (HashMap) info.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return p;
    }
}
