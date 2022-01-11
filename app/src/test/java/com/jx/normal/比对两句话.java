package com.jx.normal;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class 比对两句话 {
    @Test
    public void main() {
        String str1 = "晚饭后出去散步，走着走着又到了这里。";
        String str2 = "晚饭前,不去散步，走着走着又到了那里。";

        String str1WithoutSymbol = removeSymbol(str1);
        String str2WithoutSymbol = removeSymbol(str2);
        if (str1WithoutSymbol.length() != str2WithoutSymbol.length()) {
            System.out.println("长度不一致");
        } else {
            int symbolCount = 0;
            char[] str1Char = str1.toCharArray();
            char[] str2Char = str2WithoutSymbol.toCharArray();
            List<Integer> mark = new ArrayList<>();
            for (int i = 0; i < str1Char.length; i++) {
                if ("".equals(removeSymbol(String.valueOf(str1Char[i])))) {
                    symbolCount++;
                    continue;
                }
                if (str1Char[i] != str2Char[i - symbolCount]) {
                    if (0 == mark.size() % 2) {
                        mark.add(i);
                    }
                } else {
                    if (0 != mark.size() % 2) {
                        mark.add(i);
                    }
                }
            }
            for (int i = 0; i < mark.size(); i++) {
                System.out.println(mark.get(i) + "");
            }
        }
    }

    private String removeSymbol(String input) {
        /*String string = "测试<>《》！*(^)$%~!@#$…&%￥―+=、。，；‘’“”：・`文本";
        System.out.println(string.replaceAll("\\pP|\\pS", ""));*/
        return input.replaceAll("\\pP|\\pS", "");
    }
}
