package com.dax.demo.greedao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 实体类
 *
 * Created by jerryliu on 2016/10/12.
 */
@Entity
public class User {
    @Id(autoincrement = true)
    long id;
    int age;
    @Property(nameInDb = "user_name")
    String name;
    @Generated(hash = 1271781227)
    public User(long id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id+"--"+name+" -- "+ age;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
