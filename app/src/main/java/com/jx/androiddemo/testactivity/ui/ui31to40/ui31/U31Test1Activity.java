package com.jx.androiddemo.testactivity.ui.ui31to40.ui31;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;

import com.jx.androiddemo.R;

public class U31Test1Activity extends Activity {
    private TextView tv_hello1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /**
         *1、打开FEATURE_CONTENT_TRANSITIONS开关(可选)，这个开关默认是打开的
         */
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u31_test1);
        tv_hello1 = findViewById(R.id.tv_hello1);
        initView();
        initListener();
    }

    private void initView() {

        /**
         *2、设置除ShareElement外其它View的退出方式(左边滑出)
         */
        getWindow().setExitTransition(new Slide(Gravity.LEFT));
        /**
         *3、设置两个Activity的共享元素的TransitionName，
         *两个Activity的共享元素必须设置同样的TransitionName
         */
        ViewCompat.setTransitionName(tv_hello1, "tv_hello");
    }

    private void initListener() {
        tv_hello1.setOnClickListener(v -> {
            Intent intent = new Intent(this, U31Test1_2Activity.class);
            Pair<View, String> pair1 = new Pair<>((View) tv_hello1, ViewCompat.getTransitionName(tv_hello1));
            /**
             *4、生成带有共享元素的Bundle，这样系统才会知道这几个元素需要做动画
             */
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1);
            ActivityCompat.startActivity(this, intent, activityOptionsCompat.toBundle());
        });
    }
}
