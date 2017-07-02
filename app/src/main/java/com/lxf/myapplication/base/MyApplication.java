package com.lxf.myapplication.base;

import android.app.Application;

import com.lxf.myapplication.bean.DaoMaster;
import com.lxf.myapplication.bean.DaoSession;
import com.lxf.myapplication.canlendar.MYDPCNCalendar;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.greenrobot.greendao.database.Database;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.calendars.DPCNCalendar;
import cn.aigestudio.datepicker.bizs.themes.DPCNTheme;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;

/**
 * Created by lxf on 2017/7/1.
 * <p>
 * 自定义
 */

public class MyApplication extends Application {

    private static final boolean ENCRYPTED = false;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "main-db-encrypted" : "main-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();

        initLogger();

        DPTManager.getInstance().initCalendar(new DPCNTheme() {
            @Override
            public int colorTitleBG() {
                return 0xFF3F51B5;
            }
        });

        DPCManager.getInstance().initCalendar(new MYDPCNCalendar());
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
