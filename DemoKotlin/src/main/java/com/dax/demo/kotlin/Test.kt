package com.dax.demo.kotlin

/**
 * Desc:
 * Created by liuxiong on 2018/10/1.
 * Email:liuxiong@corp.netease.com
 */
fun main(args: Array<String>) {
    var a = 1
    // simple name in template:
    val s1 = "a is $a"

    a = 2
    // arbitrary expression in template:
//    val s2 = "${s1.replace("is", "was")}, but now is $a"
    val s2 = "${s1.substring(2)}, but now is $a"
    println(s2)

//    list()
    //funWhen(2)

    //map
    val maps = mapOf<String,String>(Pair("a","b"),Pair("c","d"));
    val maps1 = mapOf("a" to 1,"c" to true)
    for((k,v) in maps1){
        println(k+"-"+v)
    }

    //类型判断 is ,!is
}

//当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空。
fun sum(a: Int, b: Int): Int? {
    a.inv()
    return 0;
}

//kotlin没有三元运算符
fun maxOf(a: Int, b: Int)=if (a > b) a else b

///关键字：Any,Unit，is,!is,->

//list
val items = listOf<String>("a","c","e","f")
fun list(){
    for (item in items) println(item)
    items.filter {
        it.equals("a")
    }.sorted().map {  }.forEach{

    }
}


//when
fun funWhen(obj:Any){
    val ranges = listOf<Int>(1,2,3,4)
    when(obj){
        in ranges -> println("in range")
        2 -> if(obj is Int) println("int") else println("not int")
        3,4 -> {
            print("")
        }
        else->{}
    }
}