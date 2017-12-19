package com.dax.java.service_loader;


import java.util.Iterator;
import java.util.ServiceLoader;

/**
 *
 * Desc: test main class
 * Created by liuxiong on 2017/5/9.
 */

public class Main {

    public static void main(String[] args){
        ServiceLoader<IAnimal> set = ServiceLoader.load(IAnimal.class);
        Iterator<IAnimal> iter = set.iterator();
        System.out.println("--> "+iter.hasNext());
        
        for(IAnimal animal:set){
            System.out.println(animal.getName()+":"+animal.getWeight());
        }
    }
}
