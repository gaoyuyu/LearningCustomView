package com.gaoyy.learningcustomview.view.magic;

/**
 * 用于描述文字图片的旋转角度的起始角度，结束角度
 */
public class TextImgDegreeRange {

    private int startDegree;
    private int endDegree;

    public TextImgDegreeRange(int startDegree, int endDegree) {
        this.startDegree = startDegree;
        this.endDegree = endDegree;
    }

    public int getStartDegree() {
        return startDegree;
    }

    public void setStartDegree(int startDegree) {
        this.startDegree = startDegree;
    }

    public int getEndDegree() {
        return endDegree;
    }

    public void setEndDegree(int endDegree) {
        this.endDegree = endDegree;
    }
}
