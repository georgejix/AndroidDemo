package com.jx.androiddemo.testactivity.ui.ui31to40.ui31;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.jx.androiddemo.R;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui31.texttrans.ChangeTextTransition;
import com.jx.androiddemo.testactivity.ui.ui31to40.ui31.texttrans.ShareElementInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class U31Test7_2Activity extends Activity {
    private TextView tv_hello1;
    final AtomicBoolean isEnter = new AtomicBoolean(true);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u31_test7_2);
        initView();
    }

    private void initView() {
        tv_hello1 = findViewById(R.id.tv_hello1);

        /**
         * 1、设置相同的TransitionName
         */
        ViewCompat.setTransitionName(tv_hello1, "tv_hello");

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(new ChangeBounds());
        transitionSet.addTransition(new ChangeTransform());
        transitionSet.addTransition(new ChangeTextTransition());
        getWindow().setSharedElementEnterTransition(transitionSet);
        getWindow().setSharedElementReturnTransition(transitionSet);
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public View onCreateSnapshotView(Context context, Parcelable snapshot) {
                if (snapshot instanceof ShareElementInfo) {
                    View view = super.onCreateSnapshotView(context, ((ShareElementInfo) snapshot).getSnapshot());
                    ShareElementInfo.saveToView(view, (ShareElementInfo) snapshot);
                    return view;
                } else {
                    return super.onCreateSnapshotView(context, snapshot);
                }
            }

            @Override
            public void onSharedElementStart(List<String> sharedElementNames, List<View> currentPageViewList, List<View> prePageViewList) {
                super.onSharedElementStart(sharedElementNames, currentPageViewList, prePageViewList);
                if (currentPageViewList != null && prePageViewList != null) {
                    for (int i = 0; i < currentPageViewList.size(); i++) {
                        View prePageView = prePageViewList.get(i);
                        View currentPageView = currentPageViewList.get(i);
                        ShareElementInfo shareElementInfo = null;
                        if (isEnter.get()) {
                            //进入时使用前一个Activity传过来的值
                            shareElementInfo = ShareElementInfo.getFromView(prePageView);
                        } else {
                            //退出时使用当前Activity设置的值
                            shareElementInfo = ShareElementInfo.getFromView(currentPageView);
                        }
                        if (shareElementInfo != null) {
                            shareElementInfo.setEnter(isEnter.get());
                            ShareElementInfo.saveToView(currentPageView, shareElementInfo);
                        }
                    }
                }
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> currentPageViewList, List<View> prePageViewList) {
                super.onSharedElementEnd(sharedElementNames, currentPageViewList, prePageViewList);
                for (int i = 0; currentPageViewList != null && i < currentPageViewList.size(); i++) {
                    View currentPageView = currentPageViewList.get(i);
                    ShareElementInfo shareElementInfo = ShareElementInfo.getFromView(currentPageView);
                    if (shareElementInfo != null) {
                        if (isEnter.get()) {
                            shareElementInfo.captureToViewInfo(currentPageView);
                        } else {
                            View prePageView = prePageViewList == null ? null : prePageViewList.get(i);
                            ShareElementInfo infoFromSnapshot = ShareElementInfo.getFromView(prePageView);
                            if (infoFromSnapshot != null) {
                                shareElementInfo.setFromViewBundle(infoFromSnapshot.getFromViewBundle());
                            }
                            shareElementInfo.captureToViewInfo(currentPageView);
                        }
                        shareElementInfo.setEnter(isEnter.get());
                    }
                }
                isEnter.set(false);
            }
        });
    }
}
