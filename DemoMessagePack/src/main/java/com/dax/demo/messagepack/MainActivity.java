
package com.dax.demo.messagepack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.msgpack.MessagePack;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.text);
        Person p = new Person();
        p.setName("zhangsan");
        p.setAge(108);

        try {
            MessagePack pack = new MessagePack();
            byte[] b = pack.write(p);

            Person pp = pack.read(b, Person.class);
            tv.setText(pp.getName() + "---" + pp.getAge());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
