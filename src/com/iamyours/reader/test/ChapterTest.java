package com.iamyours.reader.test;

import java.util.regex.Pattern;

public class ChapterTest {
    private static Pattern chapterPattern = Pattern.compile("^第\\S+章\\s\\S+$");

    public static void main(String[] args) {
        String text = "第四百二十一1章 大蛇丸叛逃";
        boolean b = chapterPattern.matcher(text).matches();
        System.out.println(b);
    }
}
