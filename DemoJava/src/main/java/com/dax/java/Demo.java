package com.dax.java;

import com.dax.java.clone.Man;

import java.io.File;

public class Demo {
    public static void main(String[] args){
        //testClone();
        //testStringCompare();

        testFormatTime2Array();
        //renamePig();
    }

    private static void renamePig() {
        String path = "F:\\BaiduNetdiskDownload\\pig\\";
        File file = new File(path);
        File[] files = file.listFiles();
        for(File file1:files){
            String name = file1.getName();
            String prefix = "粉红猪小妹 第2季 第";
            if(name.startsWith(prefix)){
                int index = name.indexOf(prefix);
                String string = "2_"+name.substring(index);
                file1.renameTo(new File(path+string));
            }
        }
    }

    private static void testFormatTime2Array() {
        long time = 10*60*60*1000+12*60*1000+13*1000;
        int hour = (int)(time/1000/60/60);
        int hourfirst = hour/10;
        int hoursecond = hour%10;
        System.out.println("hour-> "+hour+" hourfirst-> "+hourfirst+" hoursecond-> "+hoursecond);
        int minute = (int)(time/1000/60)%60;
        int minfirst = minute/10;
        int minsecond = minute%10;
        System.out.println("minute-> "+minute+" minfirst-> "+minfirst+" minsecond-> "+minsecond);
        int second = (int)(time/1000)%60;
        int secfirst = second/10;
        int secsecond = second%10;
        System.out.println("second-> "+second+" secfirst-> "+secfirst+" secsecond-> "+secsecond);
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


