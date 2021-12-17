package com.jx.androiddemo.testactivity.ui.u31;

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
import com.jx.androiddemo.testactivity.ui.u31.texttrans.ChangeTextTransition;
import com.jx.androiddemo.testactivity.ui.u31.texttrans.ShareElementInfo;

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
            public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
                if (sharedElements != null && sharedElementSnapshots != null) {
                    for (int i = 0; i < sharedElements.size(); i++) {
                        View snapshotView = sharedElementSnapshots.get(i);
                        View shareElementView = sharedElements.get(i);
                        ShareElementInfo shareElementInfo = null;
                        if (isEnter.get()) {
                            //进入时使用前一个Activity传过来的值
                            shareElementInfo = ShareElementInfo.getFromView(snapshotView);
                        } else {
                            //退出时使用当前Activity设置的值
                            shareElementInfo = ShareElementInfo.getFromView(shareElementView);
                        }
                        if (shareElementInfo != null) {
                            shareElementInfo.setEnter(isEnter.get());
                            ShareElementInfo.saveToView(shareElementView, shareElementInfo);
                        }
                    }
                }
            }

            @Override
            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
                for (int i = 0; sharedElements != null && i < sharedElements.size(); i++) {
                    View shareElementView = sharedElements.get(i);
                    ShareElementInfo shareElementInfo = ShareElementInfo.getFromView(shareElementView);
                    if (shareElementInfo != null) {
                        if (isEnter.get()) {
                            shareElementInfo.captureToViewInfo(shareElementView);
                        } else {
                            View snapshotView = sharedElementSnapshots == null ? null : sharedElementSnapshots.get(i);
                            ShareElementInfo infoFromSnapshot = ShareElementInfo.getFromView(snapshotView);
                            if (infoFromSnapshot != null) {
                                shareElementInfo.setFromViewBundle(infoFromSnapshot.getFromViewBundle());
                            }
                            shareElementInfo.captureToViewInfo(shareElementView);
                        }
                        shareElementInfo.setEnter(isEnter.get());
                    }
                }
                isEnter.set(false);
            }
        });
    }
}
