package com.yefl.demo.ui;

import android.content.Intent;

import com.yefl.baselibrary.ui.activity.BaseActivity;
import com.yefl.demo.R;

public class MainActivity extends BaseActivity{

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        startActivity(new Intent(this, TActivity.class));
    }
}
