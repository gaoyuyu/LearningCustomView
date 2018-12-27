package com.gaoyy.learningcustomview.view.magic;

/**
 * 描述了字体变化过程中的起始大小，结束大小值
 */
public class TextSizeRange {

    private float startTextSize;
    private float endTextSize;

    public TextSizeRange(float startTextSize, float endTextSize) {
        this.startTextSize = startTextSize;
        this.endTextSize = endTextSize;
    }

    public float getStartTextSize() {
        return startTextSize;
    }

    public void setStartTextSize(float startTextSize) {
        this.startTextSize = startTextSize;
    }

    public float getEndTextSize() {
        return endTextSize;
    }

    public void setEndTextSize(float endTextSize) {
        this.endTextSize = endTextSize;
    }
}
