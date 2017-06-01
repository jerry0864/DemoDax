package com.dax.java.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 * Created by liuxiong on 2017/5/27.
 * Email:liuxiong@corp.netease.com
 */

public class Demo {
    public static void main(String[] args){
        //subListClear();
        subListOperation();
    }

    private static void subListOperation() {
        //sublist生成list后，不要再对原集合进行增删操作，否则后续再操作sublist会出现ConcurrentModificationException
        List<String> list = new ArrayList<>();
        list.add("string-1");
        list.add("string-2");
        list.add("string-3");
        list.add("string-4");
        list.add("string-5");
        printList(list);
        List<String> listSub = list.subList(3,list.size());
        list.add("String-6");
        System.out.println("======");
        printList(list);
        //printList(listSub);
    }

    private static void subListClear(){
        //sublist的用法 sublist操作的是原list，并没有生成新的list
        List<String> list = new ArrayList<>();
        list.add("string-1");
        list.add("string-2");
        list.add("string-3");
        list.add("string-4");
        list.add("string-5");
        printList(list);
        list.subList(3,list.size()).clear();
        System.out.println("======");
        printList(list);
    }

    private static void printList(List<String> list) {
        for(String s:list){
            System.out.println(s);
        }
    }


}
