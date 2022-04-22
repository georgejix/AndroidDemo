package com.jx.androiddemo.testactivity.ui.ui31to40.ui31;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.jx.androiddemo.R;

import org.jetbrains.annotations.NotNull;

public class U31Test3Activity extends FragmentActivity {
    private ViewPager2 view_pager;
    private TextView tv_pre, tv_next;
    private int mPosition;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u31_test3);
        initView();
    }

    private void initView() {
        view_pager = findViewById(R.id.view_pager);
        tv_pre = findViewById(R.id.tv_pre);
        tv_next = findViewById(R.id.tv_next);
        tv_pre.setOnClickListener(this::onClick);
        tv_next.setOnClickListener(this::onClick);

        view_pager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @NotNull
            @Override
            public Fragment createFragment(int i) {
                return new U31Test3Fragment(U31Test3Activity.this, i);
            }

            @Override
            public int getItemCount() {
                return 5;
            }
        });
        view_pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mPosition = position;
            }
        });
        view_pager.setPageTransformer(new DepthPageTransformer());
    }

    private void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tv_pre:
                view_pager.setCurrentItem(Math.max(0, mPosition - 1));
                break;
            case R.id.tv_next:
                view_pager.setCurrentItem(Math.min(4, mPosition + 1));
                break;
        }
    }
}
