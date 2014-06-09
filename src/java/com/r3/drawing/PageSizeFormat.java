package com.r3.drawing;

import grails.util.Pair;

/**
 * User: graf
 * Date: 9/20/13
 * Time: 12:03 AM
 */
public enum PageSizeFormat {
    A0(1189, 841),
    A1(841, 594),
    A2(594, 420),
    A3(420, 297),
    A4(297, 210);

    PageSizeFormat(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pair<Integer, Integer> multiply(int coefficient) {
        if (coefficient == 0) {
            throw new IllegalArgumentException("Coefficient must be greater that 0");
        } else if (coefficient == 1) {
            return new Pair<Integer, Integer>(width, height);
        }

        return new Pair<Integer, Integer>(height * coefficient, width);
    }

    private int width;
    private int height;
}
