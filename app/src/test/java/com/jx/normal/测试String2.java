package com.jx.normal;

import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 测试String2 {
    @Test
    public void main() {
        测试String2 test = new 测试String2();
        test.t1();
        test.t2();
        System.out.println(filterUnUselessZero(test.t3("4", "7")));
    }

    private void t1() {
        Character c = 's';
        System.out.println(c);

        String s1 = "ab";
        String s2 = "ab";
        System.out.println(s1.hashCode() + "," + s2.hashCode());
        System.out.println(s1 == s2);
    }

    private void t2() {
        //修改String值
        String value = "Hello World!";
        System.out.println(value.hashCode());
        try {
            Field field = String.class.getDeclaredField("value");
            field.setAccessible(true);
            char[] c = (char[]) field.get(value);
            c[5] = '_';
            System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //hash值不变
        System.out.println(value.hashCode());
        value = "sss";
        System.out.println(value.hashCode());
    }

    //计算折数
    private String t3(String v1, String v2) {
        String percent = "";
        try {
            Double d1 = Double.parseDouble(v1);
            Double d2 = Double.parseDouble(v2);
            BigDecimal b = new BigDecimal(d1 * 10 / d2);
            percent = Double.toString(b.setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue());
        } catch (Exception e) {
        }
        return percent;
    }

    //去除数字结尾无用的0
    public static String filterUnUselessZero(String str) {
        String value = "";
        if (null != str) {
            String regEx = ".0+$";
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(str);
            boolean rs = matcher.find();
            if (rs && null != matcher.group(0)) {
                value = str.replace(matcher.group(0), "");
            } else {
                value = str;
            }
        }
        return value;
    }
}
