package com.yefl.baselibrary.app;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;

import com.github.lazylibrary.util.PreferencesUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yefl.baselibrary.manager.ThreadPoolManager;
import com.yefl.baselibrary.model.UserInfo;
import com.zhouyou.http.EasyHttp;

import org.litepal.LitePal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

public class BaseApp extends MultiDexApplication {
    public static Context context;
    public static boolean isDebug = true;
    private static UserInfo userinfo = null;
    protected static BaseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        instance = this;
//        Bugly.init(getApplicationContext(), "dab1abd07e", false);
        activitys = new LinkedList<Activity>();
//        LitePal.initialize(this);//数据库
//        EasyHttp.init(this);
//        EasyHttp.getInstance()
//                .setReadTimeOut(30 * 1000)
//                .setWriteTimeOut(30 * 1000)
//                .setConnectTimeout(30 * 1000);
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .tag("PRT")
//                .build();
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }


    private List<Activity> activitys;

    public static void setUser(UserInfo user) {
        userinfo = user;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(user);
            String personBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            PreferencesUtils.putString(BaseApp.getInstance(), "logon_user", personBase64);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserInfo getUser() {
        if (userinfo == null) {
            try {
                String personBase64 = PreferencesUtils.getString(BaseApp.getInstance(), "logon_user", "");
                byte[] base64Bytes = Base64.decode(personBase64.getBytes(), Base64.DEFAULT);
                ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                userinfo = (UserInfo) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userinfo;
    }

    public static BaseApp getInstance() {
        return instance;
    }


    public void AddActivity(Activity activity) {
        activitys.add(activity);
    }

    public void RemoveActivity(Activity activity) {
        if (activitys.contains(activity))
            activitys.remove(activity);
    }

    public void CloseAllActivity() {
        for (int i = 0; i < activitys.size(); i++) {
            if (activitys.get(i) != null
                    && !activitys.get(i).isFinishing()) {
                activitys.get(i).finish();
            }
        }
        activitys.clear();

        ThreadPoolManager.getInstance().shutdown();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}