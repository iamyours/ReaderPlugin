package com.iamyours.reader.vo;

/**
 * 章节
 */
public class ChapterVO {
    public String name;
    public long seek;

    public ChapterVO(String name, long seek) {
        this.name = name;
        this.seek = seek;
    }
}
