package com.jx.arch.config;

import com.jx.arch.config.host.BaseHostType;
import com.jx.arch.config.host.HostsProduct;
import com.jx.arch.config.host.HostsTest0;

public class Hosts
{
    /**
     * 修改环境变量
     */
    public static HostType hostType = HostType.PRODUCT;

    public enum HostType implements BaseHostType
    {
        //生产环境
        PRODUCT
                {
                    @Override
                    public String getCookieDomain()
                    {
                        return HostsProduct.COOKIE_DOMAIN;
                    }

                    @Override
                    public String getBaseHost()
                    {
                        return HostsProduct.BASE_HOST;
                    }
                },
        //Test0
        TEST0
                {
                    @Override
                    public String getCookieDomain()
                    {
                        return HostsTest0.COOKIE_DOMAIN;
                    }

                    @Override
                    public String getBaseHost()
                    {
                        return HostsTest0.BASE_HOST;
                    }
                }
    }

    public static String COOKIE_DOMAIN = hostType.getCookieDomain();

    public static String BASE_HOST = hostType.getBaseHost();

    public static void updateUrls(Hosts.HostType type)
    {
        hostType = type;
        COOKIE_DOMAIN = hostType.getCookieDomain();
        BASE_HOST = hostType.getBaseHost();
    }
}
