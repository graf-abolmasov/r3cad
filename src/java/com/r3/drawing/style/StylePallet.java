package com.r3.drawing.style;

import java.util.HashMap;

/**
 * User: Felix-13
 * Date: 11.09.13
 * Time: 19:53
 */
public class StylePallet {

    public StylePallet(HashMap lineStyles, HashMap textStyles) {
        this.lineStyles = lineStyles;
        this.textStyles = textStyles;
    }

    public HashMap getLineStyles() {
        return lineStyles;
    }

    public HashMap getTextStyles() {
        return textStyles;
    }

    private HashMap lineStyles;
    private HashMap textStyles;
}
