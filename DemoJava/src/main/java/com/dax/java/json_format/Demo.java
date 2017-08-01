package com.dax.java.json_format;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Desc:
 * Created by liuxiong on 2017/8/1.
 * Email:liuxiong@corp.netease.com
 */
public class Demo {
    private static final String SHUXING_FILE_PATH_INPUT = "D:\\test\\json\\lianhuashuxing.txt";//jibenshuxing,dianhuashuxing,lianhuashuxing
    private static final String SHUXING_FILE_PATH_OUTPUT = "D:\\test\\json\\lianhuashuxing_output.txt";

    private static final String KEY = "moreAtt";//基本属性：baseAtt 炼化属性：moreAtt 点化属性：lessAtt
    public static void main(String[] args) throws Exception{
        //formatJSON();

        //汉语转拼音
//        String str = "你好世界";
//        System.out.println(PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE));
//        System.out.println(PinyinHelper.getShortPinyin(str));

    }

    private static void formatJSON() throws Exception{
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        StringBuffer sb = new StringBuffer();


        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(SHUXING_FILE_PATH_INPUT))));
        BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(new File(SHUXING_FILE_PATH_OUTPUT)));
        String line;
        int linecount = 0;
        JSONArray temparray = null;
        while((line = reader.readLine())!=null){
            if(linecount%2==0){
                temparray = new JSONArray();
                temparray.put(line);
            }else{
                temparray.put(line);
                array.put(temparray);
            }
            linecount++;
        }
        obj.put(KEY,array);
        byte[] data = sb.append(obj.toString()).toString().getBytes();
        bo.write(data);
        bo.flush();
        reader.close();
        bo.close();
    }
}
