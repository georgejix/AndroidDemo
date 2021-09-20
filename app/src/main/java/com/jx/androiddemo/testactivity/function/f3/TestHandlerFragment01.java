package com.jx.androiddemo.testactivity.function.f3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jx.androiddemo.R;


/**
 * Created by Administrator on 2018/7/11.
 */

public class TestHandlerFragment01 extends Fragment {
    private TextView contentText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_f3_handler, null);
        contentText = view.findViewById(R.id.tv_content);
        contentText.setText("before refreshView1");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
