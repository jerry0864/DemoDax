package com.dax.java;

import com.dax.java.clone.Man;

public class Demo {
    public static void main(String[] args){
        testClone();
        //testStringCompare();

    }

    private static void testStringCompare() {
        //java首先会在缓冲区查找是否有"aaa"这个常量对象，有就直接将其地址赋给a，没有就创建一个"aaa"，然后将其赋给b
        String a = "aaa";
        String b = "aaa";
        System.out.println(a==b);
        System.out.println(a.equals(b));

        System.out.println("-------");

        String c = new String("aaa");
        String d = new String("aaa");
        System.out.println(c==d);
        System.out.println(c.equals(d));
    }

    private static void testClone() {
        //对引用类型和基本数据类型的拷贝
//        Person person = new Person();
//        person.name = "zhangsan";
//        person.age = 18;
//        Person person1 = person.clone();
        //person1.name = "zhangsan";
       // person.age = 18;
//        System.out.println(person == person1);
//        System.out.println(person.name == person1.name);
//        System.out.println(person.info == person1.info);

        //子类如果增加的是基本数据类型的字段，不需要复写clone方法；但如果有增加引用数据类型字段，则需要复写clone方法，添加引用数据的拷贝
        Man m = new Man();
        m.age = 18;
        m.name = "zhaowu";
        m.job = "coder";
        m.al.put("key1","value1");
        Man m1 = (Man) m.clone();
        System.out.println(m.toString());
        System.out.println(m1.toString());
        System.out.println(m.al.toString());
        System.out.println(m1.al.toString());
    }
}


