package com.dax.java;

import com.dax.java.clone.Person;

public class Demo {
    public static void main(String[] args){
        //testClone();
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
        Person person = new Person();
        person.name = "zhangsan";
        person.age = 18;

        Person person1 = person.clone();
        person1.name = "zhangsan";
        person.age = 18;
        System.out.println(person == person1);
        System.out.println(person.name == person1.name);
        System.out.println(person.info == person1.info);
    }
}


