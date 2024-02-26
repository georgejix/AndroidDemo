package com.jx.kt

import org.junit.Test
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class sp文件转换 {

    @Test
    fun main() {
        val infile = File("D:\\dev\\code\\dimens_sp.xml")
        val outfile = File("D:\\dev\\code\\dimens_sp2.xml")
        val inputStream = FileInputStream(infile)
        val reader = inputStream.bufferedReader()
        val outputStream = FileOutputStream(outfile)
        val writer = outputStream.bufferedWriter()
        var str: String? = null
        while (reader.readLine().also { str = it } != null) {
            str?.let {
                val p1 = it.indexOf(">") + 1
                val p2 = it.lastIndexOf("<") - 2
                if (p1 >= 0 && p2 >= 0 && p2 > p1) {
                    var content = it.substring(p1, p2)
                    var content2 = content.toDouble() * 2
                    val content3 = content2.toString().replace(".0", "")
                    val content4 = it.replace("${content}sp", "${content3}px")
                    writer.write("${content4}\n")
                } else {
                    writer.write("${it}\n")
                }
            }
        }
        reader.close()
        writer.close()
        inputStream.close()
        outputStream.close()
    }
}