package com.r3.drawing.symbol;

import java.io.Serializable;

/**
 * User: graf
 * Date: 11/8/13
 * Time: 12:16 AM
 */
public class SymbolIconReadOnly implements Serializable {

    public SymbolIconReadOnly(SymbolIcon symbolIcon) {
        this(symbolIcon.getWidth(), symbolIcon.getHeight(), symbolIcon.getTemplate());
    }

    public SymbolIconReadOnly(int width, int height, String template) {
        this.width = width;
        this.height = height;
        this.template = template;
    }

    int width;
    int height;
    String template;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTemplate() {
        return template;
    }
}
