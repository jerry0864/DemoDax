package com.dax.demo.kotlin

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvMainText = findViewById<TextView>(R.id.tv_main_content)
        val tvMainText1 = findViewById(R.id.tv_main_content) as TextView
        tv_main_content.setText("adafd")

        val array:IntArray
        showToast(this,"haha")

    }

    @Test fun test(){}
}

@Target(AnnotationTarget.FUNCTION)
annotation class Test

fun Activity.showToast(context: Context,msg:String,duration:Int = Toast.LENGTH_SHORT ){
    Toast.makeText(context,msg,duration).show()
}
