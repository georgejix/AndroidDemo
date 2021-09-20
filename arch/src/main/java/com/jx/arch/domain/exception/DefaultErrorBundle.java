package com.jx.arch.domain.exception;

import android.text.TextUtils;

import com.jx.arch.util.ErrorCodeManager;
import com.jx.arch.util.GeneralUtils;

/**
 * Wrapper around Exceptions used to manage default errors.
 */
public class DefaultErrorBundle extends Exception implements ErrorBundle
{

    private static final String DEFAULT_ERROR_MSG = "未知错误，请稍后再试";

    private static final String NETWORK_ERROR_MSG = "网络请求失败，请检查网络";

    public static final String STATUS_LOGIN_AGAIN = "-9";

    public static final String STATUS_STORE_LOCK = "-7";

    public static final String USE_PLATFORM_COUPON_ERROR = "-11";

    public static final String USE_COUPON_ERROR = "-12";

    public static final String FAST_SCAN_CODE_PAY_ERROR = "-13";

    public static final String INTEGRAL_NOT_ENOUGH_ERROR = "-14"; // 积分不足

    public static final String COUPON_HAS_BEAN_USED_ERROR = "-15"; // 优惠券已被使用

    public static final String TID_NOT_EXIST_ERROR = "-16"; // 订单不存在

    private final String status;

    private final String message;

    private final Object objectBean;

    public boolean isNetworkError()
    {
        return !TextUtils.isEmpty(message) && message.equals(NETWORK_ERROR_MSG);
    }

    public DefaultErrorBundle()
    {
        super();
        this.status = "";
        this.message = NETWORK_ERROR_MSG;
        this.objectBean = null;
    }

    public DefaultErrorBundle(String message)
    {
        super();
        this.status = "";
        this.message = message;
        this.objectBean = null;
    }

    /**
     * 带对象的失败提示
     *
     * @param message
     * @param objectBean
     */
    public DefaultErrorBundle(String message, Object objectBean)
    {
        super();
        this.message = message;
        this.status = "";
        this.objectBean = objectBean;
    }

    public DefaultErrorBundle(String status, String message)
    {
        super();
        this.status = status;
        this.message = message;
        this.objectBean = null;
        if (GeneralUtils.isNotNull(ErrorCodeManager.getInstance().onErrorCodeListener))
        {
            ErrorCodeManager.getInstance().onErrorCodeListener.onErrorCode(status, message);
        }
    }

    public DefaultErrorBundle(String status, String message, Object objectBean)
    {
        super();
        this.status = status;
        this.message = message;
        this.objectBean = objectBean;
    }

    public DefaultErrorBundle(final Throwable cause)
    {
        super(cause);
        this.status = "";
        this.message = DEFAULT_ERROR_MSG;
        this.objectBean = null;
    }

    @Override
    public String getErrorStatus()
    {
        return status;
    }

    @Override
    public String getErrorMessage()
    {
        return (message != null) ? message : DEFAULT_ERROR_MSG;
    }

    @Override
    public Object getObjectBean()
    {
        return objectBean;
    }
}
