package com.jx.arch.util;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lylaut on 2020/06/15
 */
public class FileUtil
{

    //写数据 到 /data/data/xx.xx.xx/fileName 不需要权限
    public static void writeFile(Context context, String fileName, String writeStr) throws Exception
    {
        FileOutputStream fout = context.openFileOutput(fileName, MODE_PRIVATE);
        byte[] bytes = writeStr.getBytes();
        fout.write(bytes);
        fout.close();
    }

    //从 /data/data/xx.xx.xx/fileName 读数据
    public static String readFile(Context context, String fileName) throws Exception
    {
        String res = "";
        FileInputStream fin = context.openFileInput(fileName);
        int length = fin.available();
        byte[] buffer = new byte[length];
        fin.read(buffer);
        res = new String(buffer, StandardCharsets.UTF_8);
        fin.close();
        return res;
    }

    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException
    {
        // 下载位置
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs())
        {
            downloadFile.createNewFile();
        }
        return downloadFile.getAbsolutePath();
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    public static String getNameFromUrl(String url)
    {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 文件是否存在
     *
     * @param fileName
     * @return
     */
    public static boolean isVideoExist(String fileName)
    {
        File videoFile = new File(fileName);
        return videoFile.exists();
    }

    /**
     * 删除文件夹目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return
     */
    public static boolean deleteDirectory(String filePath)
    {
        boolean flag = false;
        // 如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
        {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory())
        {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        if (files != null && files.length > 0)
        {
            // 遍历删除文件夹下的所有文件(包括子目录)
            for (File file : files)
            {
                if (file.isFile())
                {
                    // 删除子文件
                    flag = deleteFile(file.getAbsolutePath());
                    if (!flag)
                    {
                        break;
                    }
                }
                else
                {
                    // 删除子目录
                    flag = deleteDirectory(file.getAbsolutePath());
                    if (!flag)
                    {
                        break;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 删除单个文件
     *
     * @param filePath 被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (file.isFile() && file.exists())
        {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除视频文件保留当前正在使用的
     *
     * @param filePath
     * @param fileList
     */
    public static void deleteFileByName(String filePath, List<String> fileList)
    {
        if (!filePath.endsWith(File.separator))
        {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory())
        {
            return;
        }
        if (null == fileList)
        {
            return;
        }
        File[] files = dirFile.listFiles();
        if (files.length > 0)
        {
            for (int i = 0; i < files.length; i++)
            {
                String fileName = "";
                if (files[i].isFile())
                {
                    fileName = files[i].getAbsolutePath();
                }
                if (GeneralUtils.isNullOrZeroLength(fileName))
                {
                    continue;
                }
                // 文件名不存在视频名称集合中
                if (!fileList.contains(fileName))
                {
                    deleteFile(files[i].getAbsolutePath());
                }
            }
        }
    }

    public static boolean isStorageSpaceEnough(float freeSize, float basis)
    {
        // 暂定满足300M即为储存空间足够
        float limitSpace = 300 * basis * basis;
        if (freeSize >= limitSpace)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
