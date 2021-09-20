package com.jx.arch.util;

import android.content.Context;
import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <通用工具类> <功能详细描述>
 */
public final class GeneralUtils
{

    /**
     * 判断对象是否为null , 为null返回true,否则返回false
     *
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNull(Object obj)
    {
        return isEmpty(obj);
    }

    /**
     * 判断对象是否为null , 为null返回false,否则返回true
     *
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNotNull(Object obj)
    {
        return !isEmpty(obj);
    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNullOrZeroLength(String str)
    {
        return isNullString(str);
    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回false,否则返回true
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNotNullOrZeroLength(String str)
    {
        return !isNullString(str);
    }

    /**
     * 判断集合对象是否为null或者0大小 , 为空或0大小返回true ,否则返回false
     *
     * @param c collection 集合接口
     * @return boolean 布尔值
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNullOrZeroSize(Collection<? extends Object> c)
    {
        return isNull(c) || c.isEmpty();
    }

    /**
     * 判断集合对象是否为null或者0大小 , 为空或0大小返回false, 否则返回true
     *
     * @param c collection 集合接口
     * @return boolean 布尔值
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNotNullOrZeroSize(Collection<? extends Object> c)
    {
        return !isNullOrZeroSize(c);
    }

    /**
     * 判断数字类型是否为null或者0，如果是返回true，否则返回false
     *
     * @param number 被判断的数字
     * @return boolean
     */
    public static boolean isNullOrZero(Number number)
    {
        if (GeneralUtils.isNotNull(number))
        {
            return (number.intValue() != 0) ? false : true;
        }
        return true;
    }

    /**
     * 判断数字类型是否不为null或者0，如果是返回true，否则返回false
     *
     * @param number 被判断的数字
     * @return boolean
     */
    public static boolean isNotNullOrZero(Number number)
    {
        return !isNullOrZero(number);
    }

    /**
     * <保留x位有效数字> <功能详细描述>
     *
     * @param num String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String retained2SignificantFigures(String num, int x)
    {
        return new BigDecimal(num).setScale(x, RoundingMode.HALF_UP).toString();
    }

    /**
     * 格式化数据，保留两位小数
     *
     * @param num
     * @return
     */
    public static String retained2SignificantFigures(String num)
    {
        if (GeneralUtils.isNullOrZeroLength(num))
        {
            return "0.00";
        }
        return new BigDecimal(num).setScale(2, RoundingMode.DOWN).toString();
    }

    /**
     * 格式化数据，保留三位小数
     *
     * @param num
     * @return
     */
    public static String retainedNSignificantFigures(String num, int size)
    {
        if (GeneralUtils.isNullOrZeroLength(num))
        {
            return "0.00";
        }
        return new BigDecimal(num).setScale(size, RoundingMode.UP).toString();
    }

    /**
     * 格式化数据，为金额添加 千分位，保留size位小数
     *
     * @param price
     * @param halfUp
     * @param size
     * @return
     */
    public static String formatPriceGrouping(double price, boolean halfUp, int size)
    {
        DecimalFormat formater = new DecimalFormat();
        // keep 2 decimal places
        formater.setMaximumFractionDigits(3);
        formater.setMinimumFractionDigits(size);
        formater.setGroupingSize(3);
        formater.setRoundingMode(halfUp ? RoundingMode.HALF_UP : RoundingMode.FLOOR);
        return formater.format(price);
    }

    public static String integerReservation(String num)
    {
        if (GeneralUtils.isNullOrZeroLength(num) || Double.valueOf(num) == 0)
        {
            return "0";
        }
        return new BigDecimal(num).stripTrailingZeros().toPlainString();
    }

    /**
     * 格式化数据，判断是整数还是小数
     */
    public static String compareNumber(String num)
    {
        if (GeneralUtils.isNullOrZeroLength(num))
        {
            return "0";
        }
        BigDecimal number = new BigDecimal(num);
        if (new BigDecimal(number.intValue()).compareTo(number) == 0)
        {
            //整数
            return String.valueOf(number.intValue());
        }
        else
        {
            //小数
            return retained2SignificantFigures(num);
        }
    }


    /**
     * 格式化数据，判断是整数还是小数
     */
    public static String compareNumber(String num, int size)
    {
        if (GeneralUtils.isNullOrZeroLength(num))
        {
            return "0";
        }
        BigDecimal number = new BigDecimal(num);
        if (new BigDecimal(number.intValue()).compareTo(number) == 0)
        {
            //整数
            return String.valueOf(number.intValue());
        }
        else
        {
            //小数
            return retainedNSignificantFigures(num, size);
        }
    }

    /**
     * 判断是否是小数
     *
     * @param num
     * @return
     */
    public static boolean compareNumberIsFloat(String num)
    {
        if (GeneralUtils.isNullOrZeroLength(num))
        {
            return false;
        }
        BigDecimal number = new BigDecimal(num);
        return !(new BigDecimal(number.intValue()).compareTo(number) == 0);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 取整数
     */
    public static String textInt(String money)
    {
        if (isNotNullOrZeroLength(money))
        {
            if (money.indexOf(".") > 0)
            {
                money = money.replaceAll("0+?$", "");//去掉后面无用的零
                money = money.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
            return money;
        }
        else
        {
            return "0";
        }
    }

    /**
     * 取整数
     */
    public static String textInt(double moneyDouble)
    {
        String money = String.valueOf(moneyDouble);
        if (money.indexOf(".") > 0)
        {
            money = money.replaceAll("0+?$", "");//去掉后面无用的零
            money = money.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        return money;
    }

    /**
     * 去除小数点及后面的数字，保留整数
     *
     * @param num
     * @return
     */
    public static int formatToInteger(String num)
    {
        if (isNotNullOrZeroLength(num))
        {
            if (num.indexOf(".") > 0)
            {
                num = num.substring(0, num.indexOf("."));
            }
            return Integer.parseInt(num);
        }
        else
        {
            return 0;
        }
    }

    /**
     * <减法运算并保留两位有效数字> <功能详细描述>
     *
     * @param subt1 String
     * @param subt2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String subtract(String subt1, String subt2, int size)
    {
        if (GeneralUtils.isNullOrZeroLength(subt1))
        {
            subt1 = "0";
        }

        if (GeneralUtils.isNullOrZeroLength(subt2))
        {
            subt2 = "0";
        }

        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        result = result.setScale(size, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <减法运算并保留两位有效数字> <功能详细描述>
     *
     * @param subt1 double
     * @param subt2 double
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static Double subtract(double subt1, double subt2, int size)
    {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        return result.setScale(size, RoundingMode.HALF_DOWN).doubleValue();
    }

    public static double subtract(String subt1, String subt2)
    {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        return result.setScale(0, RoundingMode.HALF_DOWN).doubleValue();
    }


    public static String subtract(String subt1, String subt2, String subt3, int size)
    {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal sub3 = new BigDecimal(subt3);
        BigDecimal result = sub1.subtract(sub2).subtract(sub3);
        result = result.setScale(size, RoundingMode.HALF_UP);
        return result.toString();
    }


    /**
     * 比较两个string值，返回相减后的值，保留两位
     */
    public static double stringCompare(String subt1, String subt2)
    {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    /**
     * 比较三个string值，返回相减后的值，保留两位
     */
    public static double stringCompareThree(String subt1, String subt2, String subt3)
    {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal sub3 = new BigDecimal(subt3);
        BigDecimal result = sub1.subtract(sub2).subtract(sub3);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    /**
     * <加法运算并保留两位有效数字> <功能详细描述>
     *
     * @param addend1
     * @param addend2
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String add(String addend1, String addend2, int size)
    {
        if (GeneralUtils.isNullOrZeroLength(addend1))
        {
            addend1 = "0";
        }

        if (GeneralUtils.isNullOrZeroLength(addend2))
        {
            addend2 = "0";
        }
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal result = add1.add(add2);
        result = result.setScale(size, RoundingMode.HALF_UP);
        return result.toString();
    }

    public static double add(double addend1, double addend2, double addend3)
    {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal add3 = new BigDecimal(addend3);
        BigDecimal result = add1.add(add2).add(add3);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static double add(double addend1, double addend2, int size)
    {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal result = add1.add(add2);
        result = result.setScale(size, RoundingMode.HALF_UP);
        return result.doubleValue();
    }

    public static String addThree(double addend1, double addend2, double addend3)
    {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal add3 = new BigDecimal(addend3);
        BigDecimal result = add1.add(add2).add(add3);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <乘法运算并保留两位有效数字> <功能详细描述>
     *
     * @param factor1 String
     * @param factor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String multiply(String factor1, String factor2)
    {
        if (GeneralUtils.isNullOrZeroLength(factor1))
        {
            factor1 = "0";
        }

        if (GeneralUtils.isNullOrZeroLength(factor2))
        {
            factor2 = "0";
        }
        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    public static String multiplyHalfUp(String factor1, String factor2, int size)
    {
        if (GeneralUtils.isNullOrZeroLength(factor1))
        {
            factor1 = "0";
        }

        if (GeneralUtils.isNullOrZeroLength(factor2))
        {
            factor2 = "0";
        }

        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(size, RoundingMode.DOWN);
        return result.toString();
    }

    /**
     * 乘法运算 进一位
     *
     * @param factor1 String
     * @param factor2 String
     * @param size    长度
     * @return
     */
    public static String multiply(String factor1, String factor2, int size)
    {
        if (GeneralUtils.isNullOrZeroLength(factor1))
        {
            factor1 = "0";
        }

        if (GeneralUtils.isNullOrZeroLength(factor2))
        {
            factor2 = "0";
        }
        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(size, RoundingMode.UP);
        return result.toString();
    }

    /**
     * <除法运算并保留两位有效数字> <功能详细描述>
     *
     * @param divisor1 String
     * @param divisor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String divide(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <除法运算并保留N位有效数字> <功能详细描述>
     *
     * @param divisor1 String
     * @param divisor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String divide(String divisor1, String divisor2, int size)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, size, RoundingMode.UP);
        return result.toString();
    }

    /**
     * <除法运算并保留N位有效数字> <功能详细描述>
     *
     * @param divisor1 String
     * @param divisor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static double divide(double divisor1, double divisor2, int size)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, size, RoundingMode.HALF_DOWN);
        return result.doubleValue();
    }

    /**
     * <除法运算并保留一位有效数字> <功能详细描述>
     *
     * @param divisor1 String
     * @param divisor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String dividePoint1(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 1, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * 下载进度保留两位小数后成100得到百分比
     *
     * @param divisor1
     * @param divisor2
     * @return
     */
    public static int divideToGetPercent(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 2, RoundingMode.HALF_UP);
        return result.multiply(new BigDecimal(100)).intValue();
    }

    /**
     * <参数排序>
     * <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static ArrayList<String> doSort(Map<String, String> param)
    {
        Set<String> keySet = param.keySet();
        Iterator<String> iterator = keySet.iterator();
        ArrayList<String> keys = new ArrayList<>();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            keys.add(key);
        }
        Collections.sort(keys);
        return keys;
    }

    /**
     * 将 String 转换为 Double 并保留小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static double formatStringToDouble(String number, int scale)
    {
        if (GeneralUtils.isNullOrZeroLength(number))
        {
            number = "0";
        }
        BigDecimal formatBd = new BigDecimal(number);
        return formatBd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 将 String 转换为 Double 不做任何四舍五入操作
     *
     * @param number
     * @return
     */
    public static double formatStringToDouble(String number)
    {
        if (GeneralUtils.isNullOrZeroLength(number))
        {
            number = "0";
        }
        BigDecimal formatBd = new BigDecimal(number);
        return formatBd.doubleValue();
    }

    /**
     * 将 String 转换为 Integer 不做任何四舍五入操作
     *
     * @param number
     * @return
     */
    public static int formatStringToInteger(String number)
    {
        BigDecimal formatBd = new BigDecimal(number);
        return formatBd.intValue();
    }

    /**
     * 将 Double 转换为 String 并保留小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static String formatDoubleToString(double number, int scale)
    {
        BigDecimal formatBd = new BigDecimal(number);
        return formatBd.setScale(scale, RoundingMode.HALF_UP).toString();
    }

    /**
     * 将 Double 转换为 String 并保留小数
     *
     * @param number
     * @param scale
     * @return
     */
    public static String formatDoubleToStringHalfDown(double number, int scale)
    {
        BigDecimal formatBd = new BigDecimal(number);
        return formatBd.setScale(scale, RoundingMode.HALF_DOWN).toString();
    }


    /**
     * 判断对象是否为空
     *
     * @param obj 对象
     * @return {@code true}: 为空<br>{@code false}: 不为空
     */
    public static boolean isEmpty(Object obj)
    {
        if (obj == null)
        {
            return true;
        }
        if (obj instanceof String && obj.toString().length() == 0)
        {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0)
        {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty())
        {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty())
        {
            return true;
        }
        if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0)
        {
            return true;
        }
        if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0)
        {
            return true;
        }
        if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0)
        {
            return true;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
        {
            return obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0;
        }
        return false;
    }

    /**
     * 判断字符串是否为空 为空即true
     *
     * @param str 字符串
     * @return
     */
    public static boolean isNullString(@Nullable String str)
    {
        return str == null || str.length() == 0 || "null".equals(str);
    }

    /**
     * 隐藏手机号中间四位
     *
     * @param phone
     * @return
     */
    public static String hideMidFourNumber(String phone)
    {
        if (isNullString(phone))
        {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(phone.substring(0, 3)).append("****").append(phone.substring(phone.length() - 4));
        return sb.toString();
    }

    /**
     * 获取字符串
     *
     * @param str
     * @return
     */
    public static String getFilterText(String str)
    {
        return GeneralUtils.isNotNullOrZeroLength(str) ? str : "";
    }

    public static String getFilterTextNull(String str)
    {
        return GeneralUtils.isNotNullOrZeroLength(str) && !str.equals("null") ? str : "";
    }

    public static String getFilterText(String str, String defaultStr)
    {
        return isNotNullOrZeroLength(str) ? str : isNotNullOrZeroLength(defaultStr) ? defaultStr : "";
    }

    public static String getFilterTextZero(String str)
    {
        return isNotNullOrZeroLength(str) ? str : "0";
    }

    public static int getFilterTextInt(String str, int defaultNumber)
    {
        if (isNotNullOrZeroLength(str))
        {
            try
            {
                defaultNumber = Integer.parseInt(str);
            } catch (Exception e)
            {
                return defaultNumber;
            }
        }
        return defaultNumber;
    }

    public static String scientificToString(String str)
    {
        BigDecimal bd = new BigDecimal(str);
        return bd.toPlainString();
    }

    /**
     * 根据字符串得到数字
     *
     * @param str
     * @return
     */
    public static String getNumberToString(String str)
    {
        if (GeneralUtils.isNullOrZeroLength(str))
        {
            return "";
        }
        String regexWX = "([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])";
        Pattern pattern = Pattern.compile(regexWX);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find())
        {
            return matcher.group(0);
        }
        return "";
    }

    /**
     * 获取两个List<String> 不同的元素
     *
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> getListDiff(List<String> list1, List<String> list2)
    {
        if (list1 == null || list2 == null)
        {
            return new ArrayList<>();
        }


        List<String> diff = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>(list1.size() + list2.size());
        List<String> maxList = list1;
        List<String> minList = list2;
        if (list2.size() > list1.size())
        {
            maxList = list2;
            minList = list1;
        }
        for (String string : maxList)
        {
            map.put(string, 1);
        }
        for (String string : minList)
        {
            Integer count = map.get(string);
            if (count != null)
            {
                map.put(string, ++count);
                continue;
            }
            map.put(string, 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if (entry.getValue() == 1)
            {
                diff.add(entry.getKey());
            }
        }
        return diff;
    }

    /**
     * 判断数字是否位于某区间
     */
    public static boolean isInterval(double num, String upPrice, String lowPrice)
    {
        if (GeneralUtils.isNullOrZeroLength(upPrice))
        {
            if (GeneralUtils.isNullOrZeroLength(lowPrice))
            {
                return true;
            }
            else
            {
                if (num >= Double.parseDouble(lowPrice))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            if (GeneralUtils.isNullOrZeroLength(lowPrice))
            {
                if (num <= Double.parseDouble(upPrice))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                if (num >= Double.parseDouble(lowPrice) && num <= Double.parseDouble(upPrice))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }

    }

    /**
     * 判断数字是否位于某区间
     */
    public static String isIntervalString(Context context, double num, String upPrice, String lowPrice)
    {
        if (GeneralUtils.isNullOrZeroLength(upPrice))
        {
            if (GeneralUtils.isNullOrZeroLength(lowPrice))
            {
                return "";
            }
            else
            {
                if (num >= Double.parseDouble(lowPrice))
                {
                    return "";
                }
                else
                {
                    return String.format("零售价不得低于%s", retained2SignificantFigures(lowPrice));
                }
            }
        }
        else
        {
            if (GeneralUtils.isNullOrZeroLength(lowPrice))
            {
                if (num <= Double.parseDouble(upPrice))
                {
                    return "";
                }
                else
                {
                    return String.format("零售价不得高于%s", retained2SignificantFigures(upPrice));
                }
            }
            else
            {
                if (num >= Double.parseDouble(lowPrice) && num <= Double.parseDouble(upPrice))
                {
                    return "";
                }
                else
                {
                    return String.format("请在%s～%s区间内设价", retained2SignificantFigures(lowPrice),
                            retained2SignificantFigures(upPrice));
                }
            }
        }

    }

    /**
     * 判断数字是否位于某区间
     */
    public static String isIntervalWarning(Context context, String upPrice, String lowPrice)
    {
        if (GeneralUtils.isNullOrZeroLength(upPrice))
        {
            if (GeneralUtils.isNullOrZeroLength(lowPrice))
            {
                return "";
            }
            else
            {
                lowPrice = retained2SignificantFigures(lowPrice);
                return String.format("零售价不得低于%s", lowPrice);
            }
        }
        else
        {
            if (GeneralUtils.isNullOrZeroLength(lowPrice))
            {
                upPrice = retained2SignificantFigures(upPrice);
                return String.format("零售价不得高于%s", upPrice);
            }
            else
            {
                lowPrice = retained2SignificantFigures(lowPrice);
                upPrice = retained2SignificantFigures(upPrice);
                return String.format("请在%s～%s区间内设价", lowPrice, upPrice);
            }
        }

    }

}
