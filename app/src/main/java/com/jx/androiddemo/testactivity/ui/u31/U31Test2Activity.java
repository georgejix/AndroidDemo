package com.jx.androiddemo.testactivity.ui.u31;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jx.androiddemo.R;

public class U31Test2Activity extends AppCompatActivity {
    private U31Test2Fragment mFragment0;
    private U31Test2_2Fragment mFragment1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u31_test2);
        initView();
        initListener();
    }

    private void initView() {
        mFragment0 = new U31Test2Fragment(U31Test2Activity.this, () -> {
            showFragment1();
        });
        mFragment1 = new U31Test2_2Fragment(U31Test2Activity.this, () -> {
            showFragment0();
        });
        showFragment0();
    }

    private void initListener() {
    }

    private void showFragment0() {
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.layout_frame, mFragment0).commit();
    }

    private void showFragment1() {
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).addSharedElement(mFragment0.tv_hello1, "tv_hello").replace(R.id.layout_frame, mFragment1).commit();
    }
}
