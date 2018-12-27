package com.gaoyy.learningcustomview.view.magic;

/**
 * 描述了字体变化过程中的起始透明度值，结束透明度值
 */
public class AlphaRange {

    /**
     * 起始透明度值
     */
    private int startAlpha;
    /**
     * 结束透明度值
     */
    private int endAlpha;

    public AlphaRange(int startAlpha, int endAlpha) {
        this.startAlpha = startAlpha;
        this.endAlpha = endAlpha;
    }

    public int getStartAlpha() {
        return startAlpha;
    }

    public void setStartAlpha(int startAlpha) {
        this.startAlpha = startAlpha;
    }

    public int getEndAlpha() {
        return endAlpha;
    }

    public void setEndAlpha(int endAlpha) {
        this.endAlpha = endAlpha;
    }
}
