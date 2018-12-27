package com.gaoyy.learningcustomview.view.magic;

/**
 * 用于描述文字图片的缩放的起始大小，结束大小
 * 最大值为1
 */
public class TextImgScaleRange {

    private float startImgScale;
    private float endImgScale;

    public TextImgScaleRange(float startImgScale, float endImgScale) {
        this.startImgScale = startImgScale;
        this.endImgScale = endImgScale;
    }

    public float getStartImgScale() {
        return startImgScale;
    }

    public void setStartImgScale(float startImgScale) {
        this.startImgScale = startImgScale;
    }

    public float getEndImgScale() {
        return endImgScale;
    }

    public void setEndImgScale(float endImgScale) {
        this.endImgScale = endImgScale;
    }
}
