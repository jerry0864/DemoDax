package com.dax.demo.greedao;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dax.demo.greedao.dao.UserDao;
import com.dax.demo.greedao.entity.Order;
import com.dax.demo.greedao.entity.User;
import com.dax.demo.greedao.mgr.DataHelper;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化greenDao 正式项目放application里
        DataHelper.getInstance().init(getApplicationContext());
        // 打开sql日志
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;

        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
        List<User> list = DataHelper.getInstance().getUserDao().queryBuilder().list();
        adapter.addDatas(list);
    }

    MyAdapter adapter;
    int i = 0;
    public void add(View view) {
        i++;
        User user = new User(null,10 * i, "zhangsan-" + i, System.currentTimeMillis() - i * 1000 * 60 * 60 * 24 * 12);
        DataHelper.getInstance().getUserDao().insert(user);
    }

    int x = 0;
    public void addorder(View view){
        Order order = new Order(null,3,"product-"+(x++),11.11+x,"2017-7-24");
        DataHelper.getInstance().getOrderDao().insert(order);
        //一对多关系第一次查找会缓存，如果发生变动，需要清除缓存。否则查询的一对多的数据不会更新。
        User user = DataHelper.getInstance().getUserDao().queryBuilder().where(UserDao.Properties.User_id.eq(3)).unique();
        user.resetOrders();
    }

    public void query(View view) {
        List<User> list = DataHelper.getInstance().getUserDao().queryBuilder().where(UserDao.Properties.User_id.eq(3), UserDao.Properties.Age.eq(166)).list();
        adapter.addDatas(list);
    }

    public void queryAll(View view){
        List<User> list = DataHelper.getInstance().getUserDao().queryBuilder().list();
        adapter.addDatas(list);
    }

    public void update(View view) {
        DataHelper.getInstance().getUserDao().queryBuilder().list();
        User user = DataHelper.getInstance().getUserDao().load(1L);
        user.setAge(166);
        DataHelper.getInstance().getUserDao().update(user);
    }

    public void delete(View view) {
        User user = new User();
        user.setUser_id(10l);
        DataHelper.getInstance().getUserDao().delete(user);
    }

    class MyAdapter extends BaseAdapter {
        Context context;
        List<User> users = new ArrayList<>();

        public MyAdapter(Context context) {
            this.context = context;
        }

        public void addDatas(List<User>data){
            if(data !=null&&data.size()>0){
                users.clear();
                users.addAll(data);
                notifyDataSetChanged();
            }
        }

        public void addData(User user){
            if(user !=null){
                users.add(user);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public User getItem(int position) {
            return users.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            User user = getItem(position);
            StringBuffer sb = new StringBuffer();
            sb.append(user.getUser_id()).append(" - ").append(user.getAge()).append(" - ").append(user.getName()).append(" - ").append(user.getTesttime()).append((" - orderinfo: ")).append(user.getOrders().size()).append(" - ").append(user.getOrders().toString());
            textView.setText(sb.toString());
            return convertView;
        }
    }

    public class BackupTask extends AsyncTask<String, Void, Integer> {
        private static final String COMMAND_BACKUP = "backupDatabase";
        public static final String COMMAND_RESTORE = "restroeDatabase";
        private Context mContext;

        public BackupTask(Context context) {
            this.mContext = context;
        }

        @Override
        protected Integer doInBackground(String... params) {
            // 获得正在使用的数据库路径，我的是 sdcard 目录下的 /dlion/db_dlion.db
             // 默认路径是 /data/data/(包名)/databases/*.db
            File dbFile = mContext.getDatabasePath(Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/dlion/db_dlion.db");
            File exportDir = new File(Environment.getExternalStorageDirectory(),
                    "dlionBackup");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            File backup = new File(exportDir, dbFile.getName());
            String command = params[0];
            if (command.equals(COMMAND_BACKUP)) {
                try {
                    backup.createNewFile();
                    fileCopy(dbFile, backup);
                    return Log.d("backup", "ok");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    return Log.d("backup", "fail");
                }
            } else if (command.equals(COMMAND_RESTORE)) {
                try {
                    fileCopy(backup, dbFile);
                    return Log.d("restore", "success");
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                    return Log.d("restore", "fail");
                }
            } else {
                return null;
            }
        }

        private void fileCopy(File dbFile, File backup) throws IOException {
            FileChannel inChannel = new FileInputStream(dbFile).getChannel();
            FileChannel outChannel = new FileOutputStream(backup).getChannel();
            try {
                inChannel.transferTo(0, inChannel.size(), outChannel);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inChannel != null) {
                    inChannel.close();
                }
                if (outChannel != null) {
                    outChannel.close();
                }
            }
        }
    }

    // 数据恢复
    private void dataRecover() {
        new BackupTask(this).execute("restroeDatabase");
    }

    // 数据备份
    private void dataBackup() {
        new BackupTask(this).execute("backupDatabase");
    }
}
