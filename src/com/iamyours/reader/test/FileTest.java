package com.iamyours.reader.test;

import java.io.*;

public class FileTest {
    public static void main(String[] args) {
        String path = "/Users/yanxx/Downloads/27694.txt";
        randomAccessFile(path);
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

    private static void randomAccessFile(String path) {
        try {
            RandomAccessFile rf = new RandomAccessFile(path, "r");
            String line;
            int count = 0;
            while ((line = rf.readLine()) != null) {
                System.out.println(new String(line.getBytes("ISO-8859-1")));
                System.out.println(rf.getFilePointer());
                System.out.println("----------");
                if (count++ > 10) break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
