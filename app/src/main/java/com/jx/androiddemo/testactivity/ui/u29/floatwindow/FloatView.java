package com.jx.androiddemo.testactivity.ui.u29.floatwindow;

import android.view.View;
import android.view.WindowManager;

/**
 * Created by yhao on 17-11-14.
 * https://github.com/yhaolpz
 */

abstract class FloatView {

    abstract void setSize(int width, int height);

    abstract void setView(View view);

    abstract void setGravity(int gravity, int xOffset, int yOffset);

    abstract void init();

    abstract void dismiss();

    void updateXY(int x, int y) {
    }

    void updateSizeStart() {

    }

    void updateSizeMove(int widthChange, int heightChange) {
    }

    void updateSizeEnd() {

    }

    void clickShow() {

    }

    void updateX(int x) {
    }

    void updateY(int y) {
    }

    int getX() {
        return 0;
    }

    int getY() {
        return 0;
    }

    void setChangeSizeListener(ChangeSizeListener changeSizeListener) {
    }

    WindowManager getWindowManager() {
        return null;
    }
}
