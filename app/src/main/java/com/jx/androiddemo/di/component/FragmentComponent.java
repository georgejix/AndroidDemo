package com.jx.androiddemo.di.component;

import android.app.Activity;

import com.jx.androiddemo.di.FragmentScope;
import com.jx.androiddemo.di.module.FragmentModule;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent
{
    Activity getActivity();
}

