package com.dax.java.service_loader;


/**
 * Desc:
 * Created by liuxiong on 2017/5/9.
 * Email:liuxiong@corp.netease.com
 */

public class Pig implements IAnimal {
    @Override
    public String getName() {
        return "pig";
    }

    @Override
    public String getWeight() {
        return "100KG";
    }
}
