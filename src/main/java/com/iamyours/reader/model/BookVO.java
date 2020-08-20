package com.iamyours.reader.model;

public class BookVO {
    public int chapterIndex;
    public String bookPath;

    public BookVO(int chapterIndex, String bookPath) {
        this.chapterIndex = chapterIndex;
        this.bookPath = bookPath;
    }

    public BookVO() {
    }

    @Override
    public String toString() {
        return "BookVO{" +
                "chapterIndex=" + chapterIndex +
                ", bookPath='" + bookPath + '\'' +
                '}';
    }
}
