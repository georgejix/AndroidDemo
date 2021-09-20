package com.jx.arch.config;

import com.jx.arch.util.SharePrefInit;

/**
 * <全局静态缓存数据>
 * <功能详细描述> 初始化sp
 *
 * @author wangtao
 */
public class GlobalInit
{
    /**
     * 清空 SharePrefInit的  SP
     */
    public static void clearAll()
    {
        SharePrefInit.clear();
    }
}
