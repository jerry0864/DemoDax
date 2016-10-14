package com.dax.demo.greedao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化greenDao 正式项目放application里
        DataHelper.getInstance().init(getApplicationContext());

        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
    }

    int id = 0;
    public void add(View view){
        User user = new User(++id,10+id,"zhangsan-"+id);
        DataHelper.getInstance().getUserDao().insert(user);
    }

    public void query(View view){
        List<User> list =  DataHelper.getInstance().getUserDao().queryBuilder().list();
        String info = (list == null||list.size()==0?"list=null":"size:"+list.size()+"-- "+list.get(0).toString());
        textView.setText(info);
    }

    public void modify(View view){
        List<User> list =  DataHelper.getInstance().getUserDao().queryBuilder().list();
        if(list ==null||list.size()==0){
            return;
        }
        User user = list.get(0);
        user.setAge(15);
        user.setName("wangwu");
        DataHelper.getInstance().getUserDao().update(user);
        query(null);
    }

    public void delete(View view){
        List<User> list =  DataHelper.getInstance().getUserDao().queryBuilder().list();
        if(list ==null||list.size()==0){
            return;
        }
        User user = list.get(0);
        long id = user.getId();
        DataHelper.getInstance().getUserDao().deleteByKey(id);
        query(null);
    }
}
