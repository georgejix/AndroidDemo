package com.jx.arch.config;

import com.jx.arch.util.SharePrefSetting;

/**
 * <全局静态缓存数据>
 * <功能详细描述>
 *
 * @author wangtao
 */
public class GlobalSetting
{
    /**
     * 清空 SharePrefSetting 的  SP
     */
    public static void clearAll()
    {
        SharePrefSetting.clear();
    }
}
