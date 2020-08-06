package com.iamyours.reader.test;

import com.iamyours.reader.util.FileUtil;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileTest {
    public static void main(String[] args) {
        String path = "/Users/yanxx/Downloads/凡人修仙传.txt";
        String charset = FileUtil.getFileCharset(new File(path));
        System.out.println(charset);
//        randomAccessFile(path, charset);
        loadChapters(path);
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

    private static Pattern chapterPattern = Pattern.compile("(第\\S+章\\s\\S+\\s)");

    private static void loadChapters(String path) {
        try {
            String str = IOUtils.toString(new FileReader(new File(path)));
            Matcher match = chapterPattern.matcher(str);
            System.out.println(match.matches());
            int fromIndex = 7656403;
            int index = str.indexOf("\n", fromIndex+10);
            System.out.println(index);
            System.out.println(str.substring(fromIndex, index));
            while (match.find()) {
                System.out.println(match.start());
                System.out.println(str.substring(match.start(), match.end()));
                System.out.println(match.end());
                System.out.println(match.group(1));
            }
//            System.out.println(str.substring(0, 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void randomAccessFile(String path, String charset) {
        try {
            RandomAccessFile rf = new RandomAccessFile(path, "r");
            String line;
            int count = 0;
            while ((line = rf.readLine()) != null) {
                String str = new String(line.getBytes("ISO-8859-1"), charset);
                System.out.println(str);
                System.out.println(rf.getFilePointer());
                System.out.println("----------" + line.length() + "," + str.length());
                if (count++ > 10) break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
