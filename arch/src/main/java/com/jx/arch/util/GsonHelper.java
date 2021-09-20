package com.jx.arch.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GsonHelper
{
    private final static String TAG = "GsonHelper";

    //    private static Gson gson = new Gson();
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new NullStringEmptyTypeAdapterFactory())
            .create();

    private static Gson gsonNull = new GsonBuilder()
            .serializeNulls()
            .create();

    /**
     * 把json string 转化成类对象
     *
     * @param str
     * @param t
     * @return
     */
    public static <T> T toType(String str, Class<T> t)
    {
        try
        {
            if (str != null && !"".equals(str.trim()))
            {
                T res = gson.fromJson(str.trim(), t);
                return res;
            }
        } catch (Exception e)
        {
            QMLog.e(TAG, "toType() 数据转换出错:" + e.getMessage() + "; json : " + str);
        }
        return null;
    }

    /**
     * 把类对象转化成json string
     *
     * @param t
     * @return
     */
    public static <T> String toJson(T t)
    {
        return gson.toJson(t);
    }

    public static <T> String toJsonNull(T t)
    {
        return gsonNull.toJson(t);
    }

    /**
     * 把json转化为map
     *
     * @return
     */
    public static Map<String, String> JsonToMap(String json)
    {
        try
        {
            return gson.fromJson(json, HashMap.class);
        } catch (Exception e)
        {
            QMLog.e(TAG, "JsonToMap() 数据转换出错:" + e.getMessage() + "; json : " + json);
        }
        return null;
    }

    /**
     * 把json string 转化成List
     */
    public static <T> List<T> toList(String str, TypeToken typeToken)
    {
        Type listType = typeToken.getType();
        try
        {
            if (str != null && !"".equals(str.trim()))
            {
                List<T> res = gson.fromJson(str.trim(), listType);
                return res;
            }
        } catch (Exception e)
        {
            QMLog.e(TAG, "toList() 数据转换出错:" + e.getMessage() + "; json : " + str);
        }
        return null;
    }

    /**
     * 把json string 转化成LinkedBlockingQueue
     */
    public static <T> T toQueueList(String str, TypeToken<T> typeToken)
    {
        Type listType = typeToken.getType();
        try
        {
            if (GeneralUtils.isNotNullOrZeroLength(str))
            {
                return gson.fromJson(str.trim(), listType);
            }
        } catch (Exception e)
        {
            QMLog.e(TAG, "toList() 数据转换出错:" + e.getMessage() + "; json : " + str);
        }
        return null;
    }

}
