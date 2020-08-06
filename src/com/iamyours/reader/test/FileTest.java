package com.iamyours.reader.test;

import com.iamyours.reader.util.FileUtil;

import java.io.*;

public class FileTest {
    public static void main(String[] args) {
        String path = "/Users/yanxx/Downloads/test.txt";
        String charset = FileUtil.getFilecharset(new File(path));
        System.out.println(charset);
        randomAccessFile(path,charset);
//        try {
//            FileInputStream fis = new FileInputStream(new File(path));
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//            String str = null;
//            int count = 0;
//            while ((str = br.readLine()) != null) {
//                System.out.println(str);
//                if (count++ > 10) break;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    private static void randomAccessFile(String path,String charset) {
        try {
            RandomAccessFile rf = new RandomAccessFile(path, "r");
            String line;
            int count = 0;
            while ((line = rf.readLine()) != null) {
                System.out.println(new String(line.getBytes("ISO-8859-1"),charset));
                System.out.println(rf.getFilePointer());
                System.out.println("----------");
                if (count++ > 10) break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
