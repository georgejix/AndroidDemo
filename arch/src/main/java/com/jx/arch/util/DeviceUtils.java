package com.jx.arch.util;

import android.annotation.SuppressLint;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.jx.arch.bean.StorageSpaceBean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

public class DeviceUtils
{

    private static final String TAG = DeviceUtils.class.getName();

    @SuppressLint("HardwareIds")
    public static String getDeviceSN()
    {
        return android.os.Build.SERIAL;
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    public static boolean isThereInternetConnection()
    {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) ArchTool.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }

    public static int netConnectType()
    {
        return NetUtil.isNetworkAvailable();
    }

    public static String getVersionName(Context context)
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String versionName = "";
        try
        {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return versionName;
        }
        if (packInfo != null)
        {
            versionName = packInfo.versionName;
        }
        return versionName;
    }

    public static String getVersionCode(Context context)
    {
        long versionCode = 0;
        try
        {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P)
            {
                PackageManager packageManager = context.getPackageManager();

                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                versionCode = packageInfo.versionCode;
            }
            else
            {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                versionCode = packageInfo.getLongVersionCode();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return String.valueOf(versionCode);
    }

    public static String getAppPackageName(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo;
        String packageName = "";
        try
        {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
            return packageName;
        }
        if (packInfo != null)
        {
            packageName = packInfo.packageName;
        }
        return packageName;
    }

    /**
     * 获取手机序列号
     *
     * @return 手机序列号
     */
    @SuppressLint({"NewApi", "MissingPermission"})
    public static String getSerialNumber()
    {
        String serial = "";
        try
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            {//9.0+
                serial = Build.getSerial();
            }
            else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
            {//8.0+
                serial = Build.SERIAL;
            }
            else
            {//8.0-
                Class<?> c = Class.forName("android.os.SystemProperties");
                Method get = c.getMethod("get", String.class);
                serial = (String) get.invoke(c, "ro.serialno");
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            QMLog.e("e", "读取设备序列号异常：" + e.toString());
        }
        return serial;
    }

    /**
     * 获取登录设备号
     *
     * @return
     */
    public static String getLoginSerialNumber()
    {
        return "pad_" + getSerialNumber();
    }

    /**
     * 导航栏，状态栏隐藏
     *
     * @param mWindow
     */
    public static void NavigationBarStatusBar(Window mWindow)
    {
        View decorView = mWindow.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        mWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hideNavigationBar(mWindow);
    }

    /**
     * 隐藏虚拟栏 ，显示的时候再隐藏掉
     *
     * @param window
     */
    public static void hideNavigationBar(final Window window)
    {
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    //布局位于状态栏下方
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    //全屏
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    //隐藏导航栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            window.getDecorView().setSystemUiVisibility(uiOptions);
        });
    }

    /**
     * 清除显示状态改变监听
     *
     * @param window
     */
    public static void clearHideNavigationBar(final Window window)
    {
        if (null == window || null == window.getDecorView())
        {
            return;
        }
        window.getDecorView().setOnSystemUiVisibilityChangeListener(visibility -> {
        });
    }

    /**
     * dialog 需要全屏的时候用，和clearFocusNotAle() 成对出现
     * 在show 前调用  focusNotAle   show后调用clearFocusNotAle
     *
     * @param window
     */
    public static void focusNotAle(Window window)
    {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    /**
     * dialog 需要全屏的时候用，focusNotAle() 成对出现
     * 在show 前调用  focusNotAle   show后调用clearFocusNotAle
     *
     * @param window
     */
    public static void clearFocusNotAle(Window window)
    {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    /**
     * 显示导航栏
     */
    public static void showNavigationBar(Window window)
    {
        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * 显示导航栏(布局不变化)
     */
    public static void showNavigationBarBlack(Window window)
    {
        View decorView = window.getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    /**
     * DialogFragment隐藏导航栏
     */
    public static void doHiddenNavigationBar(Window window)
    {
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                //隐藏导航栏
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        window.getDecorView().setSystemUiVisibility(uiOptions);
    }

    /**
     * 获取本机 IP 地址
     *
     * @return
     */
    public static String getHostIP()
    {
        String hostIp = null;
        try
        {
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            InetAddress inetAddress = null;
            while (enumeration.hasMoreElements())
            {
                NetworkInterface ni = (NetworkInterface) enumeration.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements())
                {
                    inetAddress = ias.nextElement();
                    if (inetAddress instanceof Inet6Address)
                    {
                        continue;    // skip ipv6
                    }
                    String ip = inetAddress.getHostAddress();
                    if (!"127.0.0.1".equals(ip))
                    {
                        hostIp = inetAddress.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e)
        {
            QMLog.i(TAG, "SocketException");
            e.printStackTrace();
        }
        return hostIp;
    }

    /**
     * 获取 ip 地址前缀
     *
     * @param devAddress
     * @return
     */
    public static String getLocAddressIndex(String devAddress)
    {
        if (GeneralUtils.isNotNullOrZeroLength(devAddress))
        {
            return devAddress.substring(0, devAddress.lastIndexOf(".") + 1);
        }
        return null;
    }


    @SuppressLint({"PrivateApi", "UsableSpace"})
    public static StorageSpaceBean queryWithStorageManager(Context context)
    {
        StorageSpaceBean spaceBean = new StorageSpaceBean();
        // 5.0 查外置存储
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        float unit = 1024;
        int version = Build.VERSION.SDK_INT;
        if (version < Build.VERSION_CODES.M)    // 小于6.0
        {
            try
            {
                Method getVolumeList = StorageManager.class.getDeclaredMethod("getVolumeList");
                StorageVolume[] volumeList = (StorageVolume[]) getVolumeList.invoke(storageManager);
                long totalSize = 0, availableSize = 0;
                if (volumeList != null)
                {
                    Method getPathFile = null;
                    for (StorageVolume volume : volumeList)
                    {
                        if (getPathFile == null)
                        {
                            getPathFile = volume.getClass().getDeclaredMethod("getPathFile");
                        }
                        File file = (File) getPathFile.invoke(volume);
                        if (null != file)
                        {
                            totalSize += file.getTotalSpace();
                            availableSize += file.getUsableSpace();
                            spaceBean.blockCount = file.getTotalSpace();
                            spaceBean.availableCount = file.getUsableSpace();
                            spaceBean.freeBlocks = file.getUsableSpace();
                            spaceBean.totalSpace = spaceBean.blockCount;
                        }
                    }
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            try
            {
                @SuppressLint("PrivateApi") Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes");    // 6.0
                List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager);
                long total = 0L, used = 0L;
                if (GeneralUtils.isNotNullOrZeroSize(getVolumeInfo))
                {
                    for (Object obj : getVolumeInfo)
                    {
                        Field getType = obj.getClass().getField("type");
                        int type = getType.getInt(obj);
                        QMLog.d(TAG, "type: " + type);
                        if (type == 1)    // TYPE_PRIVATE
                        {
                            long totalSize = 0L;
                            // 获取内置内存总大小
                            if (version >= Build.VERSION_CODES.O)
                            {//8.0
                                unit = 1000;
                                Method getFsUuid = obj.getClass().getDeclaredMethod("getFsUuid");
                                String fsUuid = (String) getFsUuid.invoke(obj);
                                totalSize = getTotalSize(context, fsUuid);    // 8.0 以后使用
                            }
                            else if (version >= Build.VERSION_CODES.N_MR1)
                            {   // 7.1.1
                                Method getPrimaryStorageSize = StorageManager.class.getMethod("getPrimaryStorageSize");    // 5.0 6.0 7.0没有
                                totalSize = (long) getPrimaryStorageSize.invoke(storageManager);
                            }
                            long systemSize = 0L;
                            Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                            boolean readable = (boolean) isMountedReadable.invoke(obj);
                            if (readable)
                            {
                                Method file = obj.getClass().getDeclaredMethod("getPath");
                                File f = (File) file.invoke(obj);
                                if (totalSize == 0 && null != f)
                                {
                                    totalSize = f.getTotalSpace();
                                }
                                systemSize = totalSize - f.getTotalSpace();
                                used += totalSize - f.getFreeSpace();
                                total += totalSize;
                            }
                        }
                        else if (type == 0)
                        {   // TYPE_PUBLIC
                            // 外置存储
                            Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                            boolean readable = (boolean) isMountedReadable.invoke(obj);
                            if (readable)
                            {
                                Method file = obj.getClass().getDeclaredMethod("getPath");
                                File f = (File) file.invoke(obj);
                                used += f.getTotalSpace() - f.getFreeSpace();
                                total += f.getTotalSpace();
                            }
                        }
                        else if (type == 2)
                        {   // TYPE_EMULATED

                        }
                    }
                }
                spaceBean.blockCount = total;
                spaceBean.availableCount = total - used;
                spaceBean.freeBlocks = total - used;
                spaceBean.totalSpace = spaceBean.blockCount;
            } catch (SecurityException e)
            {
                QMLog.e(TAG, "缺少权限：permission.PACKAGE_USAGE_STATS");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return spaceBean;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static long getTotalSize(Context context, String fsUuid)
    {
        try
        {
            UUID id;
            if (fsUuid == null)
            {
                id = StorageManager.UUID_DEFAULT;
            }
            else
            {
                id = UUID.fromString(fsUuid);
            }
            StorageStatsManager stats = context.getSystemService(StorageStatsManager.class);
            return stats.getTotalBytes(id);
        } catch (NoSuchFieldError | NoClassDefFoundError | NullPointerException | IOException e)
        {
            e.printStackTrace();
            return -1;
        }
    }

}
