package com.windaka.suizhi.upcapture.util;

import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtil {
    public static void method(String fileName, String content) {
        try {
// 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
// 文件长度，字节数
            long fileLength = randomFile.length();
// 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content+"\r\n");
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
