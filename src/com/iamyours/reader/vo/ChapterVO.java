package com.iamyours.reader.vo;

/**
 * 章节
 */
public class ChapterVO {
    public String name;
    public int index;

    public ChapterVO(String name, int seek) {
        this.name = name;
        this.index = seek;
    }
}
