
package com.dax.demo.messagepack;

import org.msgpack.annotation.Message;

/**
 * Created by jerryliu on 2016/10/11.
 */
@Message
public class Person {

    private int age;

    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
