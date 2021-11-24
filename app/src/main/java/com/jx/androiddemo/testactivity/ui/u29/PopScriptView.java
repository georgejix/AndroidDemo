package com.jx.androiddemo.testactivity.ui.u29;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.ui.u29.floatwindow.ChangeSizeListener;
import com.jx.androiddemo.testactivity.ui.u29.floatwindow.FloatWindow;
import com.jx.androiddemo.tool.ClickListenerUtil;

public class PopScriptView extends LinearLayout {
    private final static String TAG = "PopScriptView";
    private Context mContext;
    private View mView;
    private TextView tv_content;
    private View mDragView;
    private View mScaleView;
    private View layout_root;
    private View layout_root_out;
    private View layout_show;
    private View layout_count_down;
    private TextView tv_count_down;
    private ImageView img_start;
    private ScrollView sl_content;

    private ChangeSizeListener mChangeSizeListener;
    private int mMinWidth, mMinHeight;
    private boolean mIsPortrait = true;
    private Animation mCountDownAnim;
    private CountDownTimer mCountDownTimer;
    private final Object mLock = new Object();
    private final int COUNTDOWN_TIME = 3 * 1000;
    private State mState = State.FINISH;
    private int cursor = 0;
    private final long WORDS_TIME = 300;
    private final ForegroundColorSpan blueSpan = new ForegroundColorSpan(0xFF00EDFF);
    private final ForegroundColorSpan whiteSpan = new ForegroundColorSpan(0xFFFFFFFF);
    private Handler mMainHandler;
    private final static int MSG_REFRESH = 1001;
    private String mContent;

    enum State {
        FINISH, STARTED, PAUSE,
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //响应横竖屏重置ui
        if (null != FloatWindow.get() && mIsPortrait != FloatWindow.get().isPortrait()) {
            mIsPortrait = FloatWindow.get().isPortrait();
            int margin = getResources().getDimensionPixelSize(R.dimen.pxtodp20);
            boolean isLand = layout_root.getRotation() % 180 != 0;
            int w = getResources().getDimensionPixelSize(mIsPortrait ? R.dimen.pxtodp700 : R.dimen.pxtodp600);
            int h = getResources().getDimensionPixelSize(mIsPortrait ? R.dimen.pxtodp700 : R.dimen.pxtodp600);

            LayoutParams params1 = (LayoutParams) layout_root.getLayoutParams();
            params1.width = isLand ? h : w;
            params1.height = isLand ? w : h;
            layout_root.setLayoutParams(params1);

            FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) layout_root_out.getLayoutParams();
            params2.width = w;
            params2.height = h;
            layout_root_out.setLayoutParams(params2);

            FloatWindow.get().updateXY(margin, margin);
        }
    }

    public PopScriptView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PopScriptView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PopScriptView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.layout_pop_script, null);
        layout_root = mView.findViewById(R.id.layout_root);
        layout_root_out = mView.findViewById(R.id.layout_root_out);
        tv_content = mView.findViewById(R.id.tv_content);
        mDragView = mView.findViewById(R.id.img_move);
        mScaleView = mView.findViewById(R.id.img_scale);
        layout_show = mView.findViewById(R.id.layout_show);
        layout_count_down = mView.findViewById(R.id.layout_count_down);
        tv_count_down = mView.findViewById(R.id.tv_count_down);
        img_start = mView.findViewById(R.id.img_start);
        sl_content = mView.findViewById(R.id.sl_content);

        mView.findViewById(R.id.img_close).setOnClickListener(this::onClick);
        mView.findViewById(R.id.img_hide).setOnClickListener(this::onClick);
        mView.findViewById(R.id.img_rotate).setOnClickListener(this::onClick);
        mView.findViewById(R.id.img_start).setOnClickListener(this::onClick);
        mView.findViewById(R.id.img_restart).setOnClickListener(this::onClick);
        mView.findViewById(R.id.layout_count_down).setOnClickListener(this::onClick);

        mMinWidth = getResources().getDimensionPixelOffset(R.dimen.pxtodp400);
        mMinHeight = getResources().getDimensionPixelOffset(R.dimen.pxtodp300);
        mChangeSizeListener = new ChangeSizeListener() {
            @Override
            public void changeSize(int widthChange, int heightChange) {
                boolean isLand = layout_root.getRotation() % 180 != 0;
                int w = Math.max(widthChange, mMinWidth);
                int h = Math.max(heightChange, mMinHeight);
                LayoutParams params1 = (LayoutParams) layout_root.getLayoutParams();
                params1.width = isLand ? h : w;
                params1.height = isLand ? w : h;
                layout_root.setLayoutParams(params1);

                FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) layout_root_out.getLayoutParams();
                params2.width = w;
                params2.height = h;
                layout_root_out.setLayoutParams(params2);
            }

            @Override
            public void clickShow() {
                layout_root_out.setVisibility(VISIBLE);
                layout_show.setVisibility(GONE);
            }
        };
        addView(mView);
        mMainHandler = new Handler(Looper.getMainLooper(), msg -> {
            switch (msg.what) {
                case MSG_REFRESH:
                    if (State.STARTED == mState) {
                        setTextProcess(++cursor);
                        mMainHandler.sendEmptyMessageDelayed(MSG_REFRESH, WORDS_TIME);
                    }
                    break;
            }
            return true;
        });
    }

    private void onClick(View view) {
        if (!ClickListenerUtil.canClick()) {
            return;
        }
        int id = view.getId();
        if (R.id.img_close == id) {
            if (null != FloatWindow.get()) {
                FloatWindow.get().hide();
            }
        } else if (R.id.img_hide == id) {
            layout_root_out.setVisibility(GONE);
            layout_show.setVisibility(VISIBLE);
            if (State.STARTED == mState) {
                mState = State.PAUSE;
                refreshStartImg();
            }
        } else if (R.id.img_rotate == id) {
            rotate();
        } else if (R.id.img_start == id) {
            if (State.PAUSE == mState || mState == State.STARTED) {
                pauseOrContinue();
            } else if (State.FINISH == mState) {
                start();
            }
        } else if (R.id.img_restart == id) {
            restart();
        }
    }

    //旋转
    private void rotate() {
        float ro = layout_root.getRotation() + 90;
        if (ro % 360 == 180) {
            ro += 90;
        } else {
            LayoutParams params = (LayoutParams) layout_root.getLayoutParams();
            int temp = params.width;
            params.width = params.height;
            params.height = temp;
            layout_root.setLayoutParams(params);
        }
        layout_root.setRotation(ro);
    }

    //开始
    private void start() {
        if (State.FINISH != mState) {
            return;
        }
        if (null == mCountDownAnim) {
            mCountDownAnim = AnimationUtils.loadAnimation(mContext, R.anim.record_count_down);
        }
        synchronized (mLock) {
            if (null != mCountDownTimer) {
                mCountDownTimer.cancel();
                mCountDownTimer = null;
            }
        }
        mCountDownTimer = new CountDownTimer(COUNTDOWN_TIME, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                if (null == mView || mView.getVisibility() != VISIBLE) {
                    return;
                }
                tv_count_down.setText(Integer.toString((int) Math.ceil(millisUntilFinished / 1000.0)));
                tv_count_down.startAnimation(mCountDownAnim);
            }

            @Override
            public void onFinish() {
                synchronized (mLock) {
                    mCountDownTimer = null;
                }
                if (null == mView || mView.getVisibility() != VISIBLE) {
                    return;
                }
                layout_count_down.setVisibility(GONE);
                realStart();
            }
        };
        layout_count_down.setVisibility(VISIBLE);
        mCountDownTimer.start();
    }

    //重新开始
    private void restart() {
        mState = State.FINISH;
        Log.d(TAG, "finish");
        refreshStartImg();
        start();
    }

    //真开始
    private void realStart() {
        Log.d(TAG, "start");
        mState = State.STARTED;
        refreshStartImg();
        cursor = 0;
        mMainHandler.removeMessages(MSG_REFRESH);
        mMainHandler.sendEmptyMessageDelayed(MSG_REFRESH, WORDS_TIME);
    }

    //暂停,开始
    private void pauseOrContinue() {
        mMainHandler.removeMessages(MSG_REFRESH);
        if (State.STARTED == mState) {
            mState = State.PAUSE;
            Log.d(TAG, "pause");
        } else if (State.PAUSE == mState) {
            mState = State.STARTED;
            mMainHandler.sendEmptyMessageDelayed(MSG_REFRESH, WORDS_TIME);
            Log.d(TAG, "continue");
        }
        refreshStartImg();
    }

    private void refreshStartImg() {
        img_start.setImageResource(State.STARTED == mState ? R.mipmap.icon_pop_pause : R.mipmap.icon_pop_start);
        if (State.FINISH == mState) {
            String content = "213123123213132131312";
            SpannableStringBuilder builder = new SpannableStringBuilder(content);
            builder.setSpan(whiteSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_content.setText(builder);
            sl_content.smoothScrollTo(0, 0);
        }
    }

    private void setTextProcess(int c) {
        if (null == mContent) {
            mState = State.FINISH;
            return;
        }
        int totalLength = mContent.length();
        String content = mContent;
        if (totalLength < c) {
            mState = State.FINISH;
            refreshStartImg();
            return;
        }
        cursor = Math.min(c, totalLength);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(blueSpan, 0, cursor, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(whiteSpan, cursor, totalLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_content.setText(builder);
        Layout layout = tv_content.getLayout();
        if (layout != null) {
            Rect bound = new Rect();
            int line = layout.getLineForOffset(cursor);
            layout.getLineBounds(line, bound);
            int y = Math.max(bound.top - (bound.bottom - bound.top), 0);
            sl_content.smoothScrollTo(0, y);
        }
    }

    public float getRotation() {
        return null == layout_root ? 0 : layout_root.getRotation();
    }

    public View getDragView() {
        return mDragView;
    }

    public View getScaleView() {
        return mScaleView;
    }

    public View getShowView() {
        return layout_show;
    }

    public void setScript(String content) {
        if (null == content || !content.equals(mContent)) {
            finish();
        }
        mContent = content;
        refreshStartImg();
    }

    public void finish() {
        //关闭，置为结束
        mState = State.FINISH;
        synchronized (mLock) {
            if (null != mCountDownTimer) {
                mCountDownTimer.cancel();
                mCountDownTimer = null;
            }
        }
    }

    public ChangeSizeListener getChangeSizeListener() {
        return mChangeSizeListener;
    }
}
