package com.gaoyy.learningcustomview.view.magic;

/**
 * 路径上的点坐标
 */
public class PathPoint {
    /**
     * x坐标
     */
    private float x;
    /**
     * y坐标
     */
    private float y;

    public PathPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "PathPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
