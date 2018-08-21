package com.yefl.baselibrary.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.yefl.baselibrary.ui.App.BaseApp;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends RxAppCompatActivity {
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        initTopBar();
        initView();
        initVar();
        BaseApp.getInstance().AddActivity(this);

    }

    protected int getContentView() {
        return -1;
    }

    //标题栏
    protected void initTopBar() {

    }

    //布局
    protected void initView() {

    }

    //数据
    protected void initVar() {

    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        BaseApp.getInstance().RemoveActivity(this);
        super.onDestroy();
    }
}