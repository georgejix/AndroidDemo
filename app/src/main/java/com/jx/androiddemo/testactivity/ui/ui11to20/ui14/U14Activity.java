package com.jx.androiddemo.testactivity.ui.ui11to20.ui14;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;
import com.jx.androiddemo.testactivity.ui.ui1to10.ui4.Utils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U14Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "TestSomeViewActivity";

    @BindView(R.id.textview_one)
    TextView oneTextView;
    @BindView(R.id.textview_two)
    TextView twoTextView;
    @BindView(R.id.textview_three)
    MTextView threeTextView;
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u14;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        //设置不同颜色
        String textOne = oneTextView.getText().toString();
        SpannableStringBuilder sp = new SpannableStringBuilder(textOne);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)),
                0,
                textOne.length(),
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)),
                3,
                11,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        oneTextView.setText(sp);

        //设置不同字体
        String textTwo = twoTextView.getText().toString();
        SpannableStringBuilder spTwo = new SpannableStringBuilder(textTwo);
        spTwo.setSpan(new AbsoluteSizeSpan(Utils.sp2px(this, 19)),
                3,
                11,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        twoTextView.setText(spTwo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            addButton();
        }
        //threeTextView.setText(stringFilter(ToDBC(getResources().getString(R.string.alarm_service_contract))));
        //threeTextView.setText(ToSBC(getResources().getString(R.string.alarm_service_contract)));
        threeTextView.setMText(getResources().getString(R.string.alarm_service_contract));
    }

    @SuppressLint("CheckResult")
    private void initListener() {
        //延时方法
        Observable.timer(50, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {

                });

        //点击
        /*RxView.clicks(null)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                });*/
    }

    private WindowManager windowManager;
    private Button button;
    //通过windowmanager添加一个view到window，可以通过type设置z轴优先级（应用window级：1-99/子window级：1000-1999/系统window级：2000-2999）
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void addButton(){
        //判断是否有 允许显示在其他应用的上层 的权限
        if (! Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent,10);
        }else {
            windowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
            button = new Button(this);
            button.setText("12345");
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
            params.format = PixelFormat.TRANSLUCENT;// 支持透明
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x = 100;
            params.y = 300;
            params.width = 100;
            params.height = 100;
            windowManager.addView(button, params);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != button && null != windowManager){
            windowManager.removeView(button);
        }
    }
}