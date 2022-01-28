package com.jx.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class 比对两句话
{
    @Test
    public void main()
    {
        String str1 = "晚饭后出去散步，走着走着又到了这里。";
        String str2 = "晚饭前,不去散步，走着走着又到了这里。";
        List<Integer> mark = compareTwoStr(str1, str2);
        for (int i = 0; i < mark.size(); i = i + 2)
        {
            System.out.println(mark.get(i) + "-" + mark.get(i + 1));
        }
    }

    /**
     * 比较两个字符串不相等处，两个字符串去除标点之后，长度必须一致才会比对
     *
     * @param str1
     * @param str2
     * @return
     */
    public static List<Integer> compareTwoStr(String str1, String str2)
    {
        List<Integer> mark = new ArrayList<>();
        String str1WithoutSymbol = removeSymbol(str1);
        String str2WithoutSymbol = removeSymbol(str2);
        int symbolCount = 0;
        char[] str1Char = str1.toCharArray();
        char[] str2Char = str2WithoutSymbol.toCharArray();
        for (int i = 0; i < str1Char.length; i++)
        {
            if ("".equals(removeSymbol(String.valueOf(str1Char[i]))))
            {
                symbolCount++;
                continue;
            }
            if (str1Char[i] != str2Char[i - symbolCount])
            {
                if (0 == mark.size() % 2)
                {
                    mark.add(i);
                }
            }
            else
            {
                if (0 != mark.size() % 2)
                {
                    mark.add(i);
                }
            }
            if (i - symbolCount == str2Char.length - 1)
            {
                if (0 == mark.size() % 2 && i + 1 < str1Char.length)
                {
                    //存在非符号，才把后面标出来
                    if (!"".equals(removeSymbol(str1.substring(i + 1))))
                    {
                        mark.add(i + 1);
                    }
                }
                break;
            }
        }
        if (0 != mark.size() % 2)
        {
            mark.add(str1Char.length);
        }
        return mark;
    }

    /**
     * 去除字符串中的符号标点
     *
     * @param input
     * @return
     */
    public static String removeSymbol(String input)
    {
        return input.replaceAll("\\p{P}|\\p{S}", "");
    }
}
