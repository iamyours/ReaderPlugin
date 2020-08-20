package com.iamyours.reader.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {

    public static String wrapInCenter(String content, int maxSize) {
        String[] arr = content.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String str : arr) {
            if (str.length() < maxSize) {
                wrapInCenter(sb, str, maxSize);
            } else {
                for (int i = 0; i < str.length(); i += maxSize) {
                    int end = i + maxSize;
                    if (end > str.length()) end = str.length();
                    wrapInCenter(sb, str.substring(i, end), maxSize);
                }
            }
        }
        return sb.toString();
    }

    private static void wrapInCenter(StringBuilder sb, String str, int maxSize) {
        sb.append(getLeftString())
                .append(str)
                .append(getRightString(maxSize - str.length()));
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static String getRightString(int offset) {
        StringBuilder sb = new StringBuilder(" ");
        for (int i = 0; i < offset; i++) {
            sb.append("**");
        }
        sb.append("*********************\n");
        return sb.toString();
    }

    private static String getLeftString() {
        return sdf.format(new Date()) + " ";
    }
}
