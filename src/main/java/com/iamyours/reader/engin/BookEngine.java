package com.iamyours.reader.engin;

import com.iamyours.reader.model.BookVO;
import com.iamyours.reader.model.ChapterVO;
import com.iamyours.reader.util.StringUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookEngine {
    private static Pattern chapterPattern = Pattern.compile("(第\\S+章\\s\\S+\\s)");
    private BookVO bookVO;
    private String content;
    private String currentContent;
    private long loadTime;
    private ArrayList<ChapterVO> chapterList = new ArrayList<>();

    public String getCurrentContent() {
        return currentContent;
    }

    public ArrayList<ChapterVO> getChapterList() {
        return chapterList;
    }

    public BookEngine(BookVO bookVO) {
        this.bookVO = bookVO;
        initContent();
    }

    private void initContent() {
        long t = System.currentTimeMillis();
        File file = new File(bookVO.bookPath);
        try {
            content = IOUtils.toString(new FileReader(file));
            Matcher match = chapterPattern.matcher(content);
            chapterList.clear();
            while (match.find()) {
                chapterList.add(new ChapterVO(match.start(), match.group(1)));
            }
            loadTime = System.currentTimeMillis() - t;
            System.out.println("load " + chapterList.size() + " chapters in " + loadTime + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int currentChapterIndex = -1;
    private int currentIndex = 0;
    private boolean wrapped = true;

    public void selectChapter(ChapterVO c) {
        currentChapterIndex = chapterList.indexOf(c);
        selectChapter();
    }

    private void selectChapter() {
        if (currentChapterIndex == -1) return;
        if (currentChapterIndex >= chapterList.size()) return;
        ChapterVO vo = chapterList.get(currentChapterIndex);
        currentIndex = vo.index;
        readNext();
    }


    public void readNext() {
        if (currentChapterIndex == -1) currentChapterIndex = 0;
        currentContent = StringUtil.wrapInCenter(readContent(), 35);
        checkCurrentChapter();
    }

    public void setChapterListener(ChapterListener chapterListener) {
        this.chapterListener = chapterListener;
    }

    private ChapterListener chapterListener;

    public interface ChapterListener {
        void chapterChanged(int index);
    }

    private void checkCurrentChapter() {
        if (currentChapterIndex < chapterList.size() - 2) {
            ChapterVO next = chapterList.get(currentChapterIndex + 1);
            if (currentIndex > next.index) {
                chapterListener.chapterChanged(currentChapterIndex + 1);
//                chapterJList.setSelectedIndex(currentChapterIndex + 1);
//                chapterJList.ensureIndexIsVisible(chapterJList.getSelectedIndex());
            }
        }
    }

    private int maxLineCount = 3;

    private String readContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < maxLineCount; ) {
            String line = readLine();
            if ("".equals(line.trim())) continue;
            sb.append(line);
            i++;
        }
        return sb.toString();
    }

    private String readLine() {
        if (currentIndex + 1 >= content.length()) return "";
        int index = content.indexOf("\n", currentIndex + 1);
        if (index == -1) return "";
        String result = content.substring(currentIndex, index);
        currentIndex = index;
        return result;
    }


}
