package com.jx.androiddemo.testactivity.function.f31to40.f37;

import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
annotation class LocalAnnotation(val name: String = "", val desc: String = "")