package com.jx.normal;

import org.junit.Test;

/**
 * 1.通过new对象实现反射机制 2.通过路径实现反射机制 3.通过类名实现反射机制
 */
public class 测试反射
{
    @Test
    public void main()
    {
        AAA aaa1 = new AAA();
        Class clazz1 = aaa1.getClass();
        System.out.println(clazz1.getName());

        try
        {
            Class clazz2 = Class.forName("com.test.normal.测试反射$AAA");
            System.out.println(clazz2.getName());
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        Class clazz3 = AAA.class;
        System.out.println(clazz3.getName());

    }

    class AAA
    {

    }
}
