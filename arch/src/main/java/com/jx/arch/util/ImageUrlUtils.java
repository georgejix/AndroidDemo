package com.jx.arch.util;

import android.text.TextUtils;
import android.util.Base64;

public class ImageUrlUtils
{

    public static final String QR_CODE_PRE_FIX = "data:image/png;base64,";

    /**
     * 获取真实URL
     *
     * @param orgUrl     原始URL
     * @param prefixHost URL前缀，如 Hosts.IMG_HOST
     * @return
     */
    public static String getUrl(String orgUrl, String prefixHost)
    {
        if (GeneralUtils.isNullOrZeroLength(orgUrl))
        {
            return "";
        }
        if (!orgUrl.startsWith("http"))
        {
            if (orgUrl.startsWith("//"))
            {
                orgUrl = "https:" + orgUrl;
            }
            else
            {
                orgUrl = prefixHost + orgUrl;
            }
        }
        return orgUrl;
    }

    public static boolean isBase64Img(String imgurl)
    {
        if (!TextUtils.isEmpty(imgurl) && (imgurl.startsWith("data:image/png;base64,")
                || imgurl.startsWith("data:image/*;base64,") || imgurl.startsWith("data:image/jpg;base64,")
        ))
        {
            return true;
        }
        return false;
    }

    public static byte[] getUrlFromCode(String code)
    {
        code = ImageUrlUtils.getUrl(code, ImageUrlUtils.QR_CODE_PRE_FIX);
        byte[] decode = null;
        if (ImageUrlUtils.isBase64Img(code))
        {
            code = code.split(",")[1];
            decode = Base64.decode(code, Base64.DEFAULT);
        }
        return decode;
    }
}