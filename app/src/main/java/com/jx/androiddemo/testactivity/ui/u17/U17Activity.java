package com.jx.androiddemo.testactivity.ui.u17;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jx.androiddemo.BaseMvpActivity;
import com.jx.androiddemo.R;
import com.jx.androiddemo.constant.Constants;
import com.jx.androiddemo.testactivity.function.empty.EmptyContract;
import com.jx.androiddemo.testactivity.function.empty.EmptyPresenter;
import com.jx.androiddemo.tool.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class U17Activity extends BaseMvpActivity<EmptyPresenter> implements EmptyContract.View {
    private final String TAG = "ChooseDateActivity";

    @BindView(R.id.textview_starttime)
    TextView starttimeText;
    @BindView(R.id.textview_endtime)
    TextView endtimeText;
    @BindView(R.id.textview_current_month)
    TextView currentMonthText;
    @BindView(R.id.textview_week_first)
    TextView firstInWeekText;
    @BindView(R.id.textview_week_second)
    TextView secondInWeekText;
    @BindView(R.id.textview_week_third)
    TextView thirdInWeekText;
    @BindView(R.id.textview_week_fourth)
    TextView fourthInWeekText;
    @BindView(R.id.textview_week_fifth)
    TextView fifthInWeekText;
    @BindView(R.id.textview_week_sixth)
    TextView sixthInWeekText;
    @BindView(R.id.textview_week_seventh)
    TextView seventhInWeekText;
    @BindView(R.id.chooseDateView)
    ChooseDateView chooseDateView;
    @BindView(R.id.view_starttime)
    View startTimeLine;
    @BindView(R.id.view_endtime)
    View endTimeLine;

    @BindView(R.id.layout_starttime)
    View layout_starttime;
    @BindView(R.id.layout_endtime)
    View layout_endtime;
    @BindView(R.id.textview_close)
    View textview_close;
    @BindView(R.id.textview_pre_month)
    View textview_pre_month;
    @BindView(R.id.textview_next_month)
    View textview_next_month;

    private Calendar calendar;
    private SimpleDateFormat sdf;
    private int firstDayOfWeek = -1;
    private String dayOfWeek[] = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private boolean startTimeSelected;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_u17;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initEventAndData() {
        initView();
        initListener();
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        calendar = Calendar.getInstance(Locale.CHINA);
        //calendar = Calendar.getInstance(Locale.FRENCH);
        sdf = new SimpleDateFormat("yyyy年MM月");
        if (null != sdf && null != calendar) {
            currentMonthText.setText(sdf.format(calendar.getTime()));
        }
        if (null != calendar) {
            firstDayOfWeek = calendar.getFirstDayOfWeek();
        }
        if (-1 != firstDayOfWeek) {
            firstInWeekText.setText(dayOfWeek[(firstDayOfWeek - 1) % 7]);
            secondInWeekText.setText(dayOfWeek[(firstDayOfWeek + 1 - 1) % 7]);
            thirdInWeekText.setText(dayOfWeek[(firstDayOfWeek + 2 - 1) % 7]);
            fourthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 3 - 1) % 7]);
            fifthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 4 - 1) % 7]);
            sixthInWeekText.setText(dayOfWeek[(firstDayOfWeek + 5 - 1) % 7]);
            seventhInWeekText.setText(dayOfWeek[(firstDayOfWeek + 6 - 1) % 7]);
        }
        if (null != chooseDateView && null != calendar) {
            chooseDateView.setCalendar((Calendar) calendar.clone());
            chooseDateView.setOnDaySelectedListener(index -> {
                if (-1 != index && null != chooseDateView && null != chooseDateView.getDayList() && chooseDateView.getDayList().size() > 0 &&
                        chooseDateView.getDayList().size() > index) {
                    if (startTimeSelected) {
                        chooseDateView.setStartTime(index);
                        if (null != chooseDateView.getStartTime()) {
                            starttimeText.setText((chooseDateView.getStartTime().getMonth() + 1) + "月" +
                                    chooseDateView.getStartTime().getDay() + "日");
                        }
                    } else {
                        chooseDateView.setEndTime(index);
                        if (null != chooseDateView.getEndTime()) {
                            endtimeText.setText((chooseDateView.getEndTime().getMonth() + 1) + "月" +
                                    chooseDateView.getEndTime().getDay() + "日");
                        }
                    }
                    chooseDateView.invalidate();
                }
            });
            chooseDateView.setErrorListener(s -> {
                if (null != s) {
                    ToastUtil.showTextToast(s);
                }
            });
            chooseDateView.setMaxRange(31);
        }
        setSelected(true);
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
        RxView.clicks(layout_starttime)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    setSelected(true);
                });
        RxView.clicks(layout_endtime)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    setSelected(false);
                });
        RxView.clicks(textview_close)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    ToastUtil.showTextToast("close");
                });
        RxView.clicks(textview_pre_month)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    changeMonth(-1);
                });
        RxView.clicks(textview_next_month)
                .throttleFirst(Constants.CLICK_TIME, TimeUnit.MILLISECONDS)
                .compose(this.bindToLifecycle())
                .subscribe(o ->
                {
                    changeMonth(1);
                });
    }

    private void setSelected(boolean startTimeSelected) {
        synchronized (this) {
            this.startTimeSelected = startTimeSelected;
            if (startTimeSelected) {
                startTimeLine.setBackgroundColor(getResources().getColor(R.color.red));
                endTimeLine.setBackgroundColor(getResources().getColor(R.color.white));
            } else {
                startTimeLine.setBackgroundColor(getResources().getColor(R.color.white));
                endTimeLine.setBackgroundColor(getResources().getColor(R.color.red));
            }
        }
    }

    private void changeMonth(int i) {
        if (null != calendar) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + i);
        }
        if (null != sdf && null != calendar) {
            currentMonthText.setText(sdf.format(calendar.getTime()));
        }
        if (null != chooseDateView && null != calendar) {
            chooseDateView.setCalendar((Calendar) calendar.clone());
            chooseDateView.invalidate();
        }
    }
}