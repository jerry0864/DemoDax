package com.dax.demo.nestedscroll;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String>data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        //show listview
        data = new ArrayList();
        for(int i = 0;i<60;i++){
            data.add("test----> "+i);
        }
        RecyclerView rv;
        ListView list = (ListView) findViewById(R.id.list_view);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data.toArray());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
            }
        });
        //show SwipeRefreshLayout
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });

        //show RecylerView
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        RecyclerView.Adapter recyclerAdapter = new RecyclerView.Adapter(){
//
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
//                parent.addView(linearLayout);
//                return new MyHolder(linearLayout);
//            }
//
//            @Override
//            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//                MyHolder holder1 = (MyHolder) holder;
//                holder1.text.setText(data.get(position));
//            }
//
//            @Override
//            public int getItemCount() {
//                return data.size();
//            }
//        };
//        recyclerView.setAdapter(recyclerAdapter);


    }

    class MyHolder extends RecyclerView.ViewHolder{
        private TextView text;
        public MyHolder(View itemView) {
            super(itemView);
            LinearLayout linearLayout = (LinearLayout) itemView;
            text = new TextView(MainActivity.this);
            linearLayout.addView(text);
        }
    }
}
//一种解决listview内嵌在scrollview中不能滑动的解决方案；
//    ListView lv = (ListView)findViewById(R.id.myListView);  // your listview inside scrollview
//lv.setOnTouchListener(new ListView.OnTouchListener() {
//@Override
//public boolean onTouch(View v, MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//        case MotionEvent.ACTION_DOWN:
//        // Disallow ScrollView to intercept touch events.
//        v.getParent().requestDisallowInterceptTouchEvent(true);
//        break;
//
//        case MotionEvent.ACTION_UP:
//        // Allow ScrollView to intercept touch events.
//        v.getParent().requestDisallowInterceptTouchEvent(false);
//        break;
//        }
//
//        // Handle ListView touch events.
//        v.onTouchEvent(event);
//        return true;
//        }
//        });
