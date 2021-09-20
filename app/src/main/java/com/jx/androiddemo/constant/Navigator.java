package com.jx.androiddemo.constant;

import android.content.Context;
import android.content.Intent;

import com.jx.androiddemo.activity.main.MainActivity;

/**
 * Class used to navigate through the application.
 */
public class Navigator {
    /**
     * Goes to MainActivity.
     */
    public static void navigateToMainActivity(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
}
