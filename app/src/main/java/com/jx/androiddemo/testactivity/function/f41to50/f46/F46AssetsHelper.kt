package com.jx.androiddemo.testactivity.function.f41to50.f46

import android.text.TextUtils
import android.util.Log
import com.jx.androiddemo.BaseApplication
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object F46AssetsHelper {
    private val TAG: String = "DlnaAssetsHelper"
    fun copyAssetsToDevice(dst: File) {
        try {
            val assetsDir = "dlnaConfig"
            if (!dst.exists()) {
                dst.mkdirs()
            }
            val context = BaseApplication.getInstance().baseContext ?: return
            val assetManager = context.assets
            val dlnaFiles = assetManager.list(assetsDir)
            dlnaFiles?.forEach { dlnaFile ->
                if (!TextUtils.isEmpty(dlnaFile)) {
                    val inputStream = assetManager.open(assetsDir + File.separator + dlnaFile)
                    val dstPath = dst.absolutePath + File.separator + dlnaFile
                    Log.i(TAG, "copy $dlnaFile,to $dstPath")
                    copy(dstPath, inputStream)
                }
            }
        } catch (e: Exception) {
            Log.i(TAG, "copyAssetsToDevice err")
        }
    }

    /**
     * 修改部分内容
     */
    private fun copy(storagePath: String?, inputStream: InputStream) {
        val file = File(storagePath)
        try {
            if(file.exists()){
                file.delete()
            }
            if (!file.exists()) {
                val outputStream = FileOutputStream(file)
                val writer = outputStream.bufferedWriter()
                val reader = inputStream.bufferedReader()
                var str:String? = null
                while ((reader.readLine()?.also { str = it }) != null) {
                    if(true == str?.contains("<friendlyName>CarTV</friendlyName>")){
                        str = str?.replace("CarTV", "CarTV1")
                    }else if(true == str?.contains("<UDN>uuid:time8888-p444-v444-r444-macaddress12</UDN>")){
                        str = str?.replace("time8888", getTimeHex())
                    }
                    writer.write(str)
                    writer.write("\n")
                    Log.i(TAG, "read $str")
                }
                writer.flush()
                outputStream.flush()
                writer.close()
                outputStream.close()
                reader.close()
                inputStream.close()
            }
        } catch (e: Exception) {
            Log.i(TAG, "copy err")
        }
    }

    private fun getTimeHex():String{
        val time = System.currentTimeMillis()
        val timeStr = java.lang.Long.toHexString(time)
        return timeStr.substring(timeStr.length - 8)
    }

    /**
     * 不修改直接拷贝
     */
    private fun copy2(storagePath: String?, inputStream: InputStream) {
        val file = File(storagePath)
        try {
            if (!file.exists()) {
                val fos = FileOutputStream(file)
                val buffer = ByteArray(inputStream.available())
                var lenght = 0
                while ((inputStream.read(buffer).also { lenght = it }) != -1) {
                    fos.write(buffer, 0, lenght)
                }
                fos.flush()
                fos.close()
                inputStream.close()
            }
        } catch (e: Exception) {
            Log.i(TAG, "copy err")
        }
    }
}