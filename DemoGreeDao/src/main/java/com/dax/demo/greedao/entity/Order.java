package com.dax.demo.greedao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Desc:
 * Created by liuxiong on 2017/7/24.
 */
@Entity
public class Order {
    @Id(autoincrement = true)
    Long order_id;
    long user_id;
    String product_name;
    double money;
    String order_time;
    @Generated(hash = 510363154)
    public Order(Long order_id, long user_id, String product_name, double money,
            String order_time) {
        this.order_id = order_id;
        this.user_id = user_id;
        this.product_name = product_name;
        this.money = money;
        this.order_time = order_time;
    }
    @Generated(hash = 1105174599)
    public Order() {
    }
    public Long getOrder_id() {
        return this.order_id;
    }
    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }
    public long getUser_id() {
        return this.user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public String getProduct_name() {
        return this.product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public double getMoney() {
        return this.money;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public String getOrder_time() {
        return this.order_time;
    }
    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    @Override
    public String toString() {
        return product_name+":"+money;
    }
}
