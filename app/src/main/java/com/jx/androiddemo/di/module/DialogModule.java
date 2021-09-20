package com.jx.androiddemo.di.module;

import android.app.Dialog;

import com.jx.androiddemo.di.DialogScope;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogModule
{
    private Dialog mDialog;

    public DialogModule(Dialog dialog)
    {
        this.mDialog = dialog;
    }

    @Provides
    @DialogScope
    public Dialog provideDialog()
    {
        return mDialog;
    }
}
