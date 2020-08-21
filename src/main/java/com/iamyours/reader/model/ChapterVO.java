package com.iamyours.reader.model;

import java.util.Objects;

public class ChapterVO {
    public int index;
    public String name;

    public ChapterVO(int index, String name) {
        this.index = index;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChapterVO chapterVO = (ChapterVO) o;
        return index == chapterVO.index &&
                Objects.equals(name, chapterVO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name);
    }

    @Override
    public String toString() {
        return name;
    }
}
