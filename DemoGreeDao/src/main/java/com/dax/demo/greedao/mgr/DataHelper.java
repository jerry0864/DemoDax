package com.dax.demo.greedao.mgr;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dax.demo.greedao.dao.DaoMaster;
import com.dax.demo.greedao.dao.DaoSession;
import com.dax.demo.greedao.dao.OrderDao;
import com.dax.demo.greedao.dao.UserDao;

/**
 * Created by jerryliu on 2016/10/12.
 */

public class DataHelper {
    private DataHelper(){};
    public static DataHelper getInstance(){
        return Builder.INSTANCE;
    }
    UserDao userDao;
    OrderDao orderDao;
    public void init(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"mydao.db");
        SQLiteDatabase base = helper.getWritableDatabase();
        DaoMaster master = new DaoMaster(base);
        DaoSession session = master.newSession();
        userDao = session.getUserDao();
        orderDao = session.getOrderDao();
    }

    public UserDao getUserDao(){
        return userDao;
    }

    public OrderDao getOrderDao(){
        return orderDao;
    }

    static class Builder{
        static DataHelper INSTANCE;
        static{
            INSTANCE = new DataHelper();
        }
    }
}
