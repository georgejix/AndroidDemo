package com.jx.androiddemo.testactivity.function.f11to20.f12;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.empty.EmptyContract;
import com.jx.androiddemo.testactivity.empty.EmptyPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class F12Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "HttpActivity";

    @BindView(R.id.img_1)
    ImageView img1;

    private ViewHandler viewHandler;
    private Bitmap bitmap;

    private final int REFRESH_IMG = 1001;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_f12;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    private void initView() {
        if (null == viewHandler) {
            viewHandler = new ViewHandler();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                //String s = HttpUtils.getInstance().sendGetMessage("http://pic1.cxtuku.com/00/06/78/b9903ad9ea2b.jpg", "");
                byte b[] = HttpURLConnectionUtil.getInstance().sendGet("http://pic1.cxtuku.com/00/06/78/b9903ad9ea2b.jpg");
                if (null != b) {
                    bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                }
                if (null != viewHandler) {
                    viewHandler.sendEmptyMessage(REFRESH_IMG);
                }

                /*Map<String, String> params = new HashMap<String, String>();
                        params.put("username", "getOrderRecord");
                        params.put("password", "122223");
                String s = HttpURLConnectionUtil.getInstance().sendPostMessage("https://passport.baidu.com/v2/api/?login", params, "utf-8");
                if(null != s){
                    Log.d(TAG, s);
                }*/
            }
        }).start();
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

    class ViewHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_IMG:
                    if (null != img1 && null != bitmap) {
                        img1.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }
}