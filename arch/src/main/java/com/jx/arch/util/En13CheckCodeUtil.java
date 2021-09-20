package com.jx.arch.util;

public class En13CheckCodeUtil
{
    /**
     * European Article Number (欧洲物品编码的缩写)
     * EAN-13条码系由「国家代码」3位数，「厂商代码」4位数，「产品代码」5位数，以及「检查码」1位数组成；
     * 校验规则
     * 检查码之计算步骤如下：
     * C1 = N1+N3+N5+N7+N9+N11
     * C2 = (N2+N4+N6+N8+N10+N12)×3
     * CC = (C1+C2) 取个位数
     * C (检查码) = 10 – CC (若值为10，则取0)
     *
     * @param code en13
     * @return
     */
    public static boolean checkEn13Code(String code)
    {
        String regexStr = "^([023])\\d{12}$";
        if (GeneralUtils.isNullOrZeroLength(code) || !code.matches(regexStr))
        {
            return false;
        }

        // 检查校验码
        String mCode = code.substring(0, 12);
        int s1 = 0;
        int s2 = 0;
        for (int i = 1; i <= 12; i++)
        {
            if (i % 2 == 1)
            {
                s1 = s1 + Integer.parseInt(mCode.substring(i - 1, i));// 奇数
            }
            else
            {
                s2 = s2 + Integer.parseInt(mCode.substring(i - 1, i));// 偶数
            }
        }
        s2 = s2 * 3;
        int c = 10 - (s1 + s2) % 10;
        int cLast = c == 10 ? 0 : c;
        int mCodeLast = Integer.parseInt(code.substring(12));
        return mCodeLast == cLast;
    }
}
