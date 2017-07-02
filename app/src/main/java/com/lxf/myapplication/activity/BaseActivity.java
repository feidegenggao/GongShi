package com.lxf.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lxf.myapplication.base.MyApplication;
import com.lxf.myapplication.bean.DaoSession;

/**
 * Created by lxf on 2017/7/1.
 */

public class BaseActivity extends Activity {
    private DaoSession daoSession;

    protected DaoSession getDaoSession() {
        if (daoSession != null) return daoSession;
        daoSession = ((MyApplication) getApplication()).getDaoSession();
        return daoSession;
    }
}
