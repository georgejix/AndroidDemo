package com.jx.normal;

import org.junit.Test;

import java.lang.reflect.Field;

public class 测试String2
{
    @Test
    public void main()
    {
        测试String2 test = new 测试String2();
        test.t1();
        test.t2();
    }

    private void t1()
    {
        Character c = 's';
        System.out.println(c);

        String s1 = "ab";
        String s2 = "ab";
        System.out.println(s1.hashCode() + "," + s2.hashCode());
        System.out.println(s1 == s2);
    }

    private void t2()
    {
        //修改String值
        String value = "Hello World!";
        System.out.println(value.hashCode());
        try
        {
            Field field = String.class.getDeclaredField("value");
            field.setAccessible(true);
            char[] c = (char[]) field.get(value);
            c[5] = '_';
            System.out.println(value);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        //hash值不变
        System.out.println(value.hashCode());
        value = "sss";
        System.out.println(value.hashCode());
    }
}
