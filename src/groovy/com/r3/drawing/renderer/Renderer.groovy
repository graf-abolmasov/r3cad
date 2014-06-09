package com.r3.drawing.renderer

import com.r3.drawing.style.DominantBaseline
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.TextAnchor
import com.r3.drawing.style.TextStyle

/**
 * User: graf
 * Date: 8/11/13
 * Time: 5:07 PM
 */
interface Renderer {
    public static final Locale LOCALE_US = Locale.US

    String line(double x1, double y1, double x2, double y2, LineStyle style, Map restAttributes)

    String text(double x, double y, double rotate, TextAnchor textAnchor, DominantBaseline dominantBaseline, String text, TextStyle style, Map restAttributes)

    String rect(double x, double y, double width, double height, LineStyle style, double rotate, Map restAttributes)

    String circle(double x, double y, double radius, LineStyle style, Map restAttributes)

    String ellipse(double x, double y, double rx, double ry, double rotate, LineStyle style, Map restAttributes)

    String circleArc(double x1, double y1, double x2, double y2, double radius, LineStyle style, Map restAttributes)

    String group(double x, double y, Closure body, Map restAttributes, String id, LineStyle lineStyle, TextStyle textStyle)

    String renderFor(Closure body, String forRender)

    String textStyle(TextStyle textStyle)

    String lineStyle(LineStyle lineStyle)
}