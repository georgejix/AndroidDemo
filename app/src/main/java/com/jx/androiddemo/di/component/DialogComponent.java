package com.jx.androiddemo.di.component;

import android.app.Dialog;

import com.jx.androiddemo.di.DialogScope;
import com.jx.androiddemo.di.module.DialogModule;

import dagger.Component;

@DialogScope
@Component(dependencies = AppComponent.class, modules = DialogModule.class)
public interface DialogComponent
{
    Dialog getDialog();
}
