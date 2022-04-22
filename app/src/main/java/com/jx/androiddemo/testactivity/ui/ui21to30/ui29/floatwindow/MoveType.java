package com.jx.androiddemo.testactivity.ui.ui21to30.ui29.floatwindow;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by yhao on 2017/12/22.
 * https://github.com/yhaolpz
 */

public class MoveType {
    public static final int script = 1;

    @IntDef({script})
    @Retention(RetentionPolicy.SOURCE)
    @interface MOVE_TYPE {
    }
}
