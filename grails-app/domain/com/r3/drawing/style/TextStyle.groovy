package com.r3.drawing.style

class TextStyle {

    static constraints = {
        color(min: 0, max: 16777215)
        size(min: 1.8d)
        sparsity(min: 0d)
    }

    String name
    String font
    int color
    double size
    double sparsity = 1d
    boolean oblique
    double strokeWidth = 0.25
}
