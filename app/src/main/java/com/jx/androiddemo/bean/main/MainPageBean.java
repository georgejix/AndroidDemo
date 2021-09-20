package com.jx.androiddemo.bean.main;

//主页配置菜单对象
public class MainPageBean {
    public MainPageBean(String name, Class clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    //主页显示名称
    public String name;

    //activity对象
    public Class clazz;
}
