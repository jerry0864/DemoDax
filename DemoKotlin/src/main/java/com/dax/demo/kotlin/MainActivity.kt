package com.dax.demo.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var result = listOf<Int>(1)
        for (a in result){
            println(a)
        }
        val a = 0;
        if(a !in result){}
    }
}
