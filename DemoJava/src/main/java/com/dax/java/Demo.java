package com.dax.java;

import com.dax.java.clone.Man;
import com.dax.java.util.FileUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Demo {
    public static void main(String[] args) throws Exception {
        //testClone();
        //testStringCompare();

        //testFormatTime2Array();
        //renamePig();
        //addPinyin();
        String aa = "aa";
        String bb = "bb";
        List<String> a =new ArrayList<>();
        a.add(aa);
        a.add(bb);

        List<String> b =new ArrayList<>();
        b.add(aa);
        b.add(bb);
        System.out.println(b.size());
        a.clear();
        System.out.println(b.size());
    }

    private static void doubleInt(){
        Double a= 3.14d;
        Double b= 3.0d;
        System.out.println(a == a.intValue());
        System.out.println(b == b.intValue());
        System.out.println(a.intValue());
        System.out.println(b.intValue());
    }

    private static void addPinyin() throws Exception {
        String targetpath = "D:\\test\\shuxing.json";
        String resultpath = "D:\\test\\shuxing_pinyin.json";
        File targetFile = new File(targetpath);
        File resultFile = new File(resultpath);
        String string = FileUtil.readFileString(targetFile);
        JSONObject targetObj = new JSONObject(string);
        JSONObject resultObj = new JSONObject();
        Iterator<String> keys = targetObj.keys();
        while(keys.hasNext()){
            String key = keys.next();
            JSONArray targetArray = targetObj.getJSONArray(key);
            JSONArray resultArray = new JSONArray();
            int len = targetArray.length();
            for(int i=0;i<len;i++){
                JSONObject obj1 = targetArray.getJSONObject(i);
                JSONObject obj2 = new JSONObject();
                Iterator<String> keys1 = obj1.keys();
                while(keys1.hasNext()){
                    String key1 = keys1.next();
                    obj2.put(key1,obj1.getString(key1));
                }
                obj2.put("pinyin",obj1.getString("value"));
                resultArray.put(i,obj2);
            }
            resultObj.put(key,resultArray);
        }
        saveFile(resultFile,resultObj.toString());
    }

    private static void saveFile(File resultFile, String s) throws IOException {
        if(!resultFile.exists()){
            resultFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(resultFile);
        byte[] bytes = s.getBytes();
        fos.write(bytes);
        fos.close();
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


