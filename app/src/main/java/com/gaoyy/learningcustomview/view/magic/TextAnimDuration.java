package com.gaoyy.learningcustomview.view.magic;

/**
 * 用于描述路径上的数字文本各种属性变化的持续时间
 */
public class TextAnimDuration {


    /**
     * 路径运动动画
     */
    private int pathDuration;

    /**
     * 透明度变化时间
     */
    private int alphaDuration;
    /**
     * 大小变化时间
     */
    private int sizeDuration;


    public TextAnimDuration(int pathDuration, int alphaDuration, int sizeDuration) {
        this.pathDuration = pathDuration;
        this.alphaDuration = alphaDuration;
        this.sizeDuration = sizeDuration;
    }

    public int getPathDuration() {
        return pathDuration;
    }

    public TextAnimDuration setPathDuration(int pathDuration) {
        this.pathDuration = pathDuration;
        return this;
    }

    public int getAlphaDuration() {
        return alphaDuration;
    }

    public TextAnimDuration setAlphaDuration(int alphaDuration) {
        this.alphaDuration = alphaDuration;
        return this;
    }

    public int getSizeDuration() {
        return sizeDuration;
    }

    public TextAnimDuration setSizeDuration(int sizeDuration) {
        this.sizeDuration = sizeDuration;
        return this;
    }
}
