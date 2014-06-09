package com.r3.drawing.renderer

import com.r3.drawing.style.DominantBaseline
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.TextAnchor
import com.r3.drawing.style.TextStyle
import com.r3.utils.Utils

import static org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib.attrsToString

/**
 * User: graf
 * Date: 8/11/13
 * Time: 5:11 PM
 */


class SvgRenderer implements Renderer {

    public static final String ALIAS = "svg"

    static final String SVG_COLOR = '#%06X'

    private static String color2SVGString(int red, int green, int blue) {
        if (red > 255)
            red = 255
        else if (red < 0)
            red = 0
        if (green > 255)
            green = 255
        else if (green < 0)
            green = 0
        if (blue > 255)
            blue = 255
        else if (blue < 0)
            blue = 0
        return String.format(LOCALE_US, SVG_COLOR, Utils.rgb2Color(red, green, blue))
    }

    private static String color2SVGString(int color) {
        if (color > 16777215)
            return '#FFFFFF'
        else if (color < 0)
            return '#000000'
        else {
            return String.format(LOCALE_US, SVG_COLOR, color)
        }
    }

    private static String textAnchorToString(TextAnchor anchor) {
        switch (anchor) {
            case TextAnchor.END:
                return "end"
            case TextAnchor.MIDDLE:
                return "middle"
            default:
                return "start"
        }
    }

    private static String dominantBaselineBuilder(DominantBaseline baseline) {
        switch (baseline) {
            case DominantBaseline.MIDDLE:
                return ' dominant-baseline="middle"'
            case DominantBaseline.TEXT_AFTER_EDGE:
                return ' dominant-baseline="text-after-edge"'
            case DominantBaseline.TEXT_BEFORE_EDGE:
                return ' dominant-baseline="text-before-edge"'
            default:
                return ''
        }
    }

    private static String mixinStyleClasses(LineStyle lineStyle, TextStyle textStyle, Map restAttributes) {
        if (!lineStyle && !textStyle) {
            return ''
        }

        StringBuffer classBuilder = new StringBuffer()
        classBuilder.append(' class="')

        if (lineStyle) {
            classBuilder.append(lineStyle.name).append(' ')
        }

        if (textStyle) {
            classBuilder.append(textStyle.name).append(' ')
        }

        final Object existedClasses = restAttributes.remove('class')
        if (existedClasses) {
            classBuilder.append(existedClasses).append(' ')
        }

        classBuilder.setLength(classBuilder.length() - 1)
        classBuilder.append('"')
        return classBuilder.toString()
    }

    private static String transformRotateBuilder(double rotate, double x, double y) {
        if (rotate == 0) {
            return ''
        }

        return String.format(LOCALE_US, ' transform="rotate(%.2f,%.2f,%.2f)"', rotate, x, y)
    }

    private static final LINE_SVG = '<line x1="%.2f" y1="%.2f" x2="%.2f" y2="%.2f"%s%s/>'

    @Override
    public String line(double x1, double y1, double x2, double y2, LineStyle style, Map restAttributes) {
        return String.format(LOCALE_US, LINE_SVG, x1, y1, x2, y2,
                mixinStyleClasses(style, null, restAttributes),
                attrsToString(restAttributes))
    }

    private static final TEXT_SVG = '<text x="%.2f" y="%.2f" text-anchor="%s"%s%s%s%s>%s</text>'

    @Override
    String text(double x, double y, double rotate, TextAnchor textAnchor, DominantBaseline dominantBaseline, String text, TextStyle style, Map restAttributes) {
        text = text?.trim()
        if (!text) {
            return ''
        }

        return String.format(LOCALE_US, TEXT_SVG, x, y, textAnchorToString(textAnchor),
                dominantBaselineBuilder(dominantBaseline),
                transformRotateBuilder(rotate, x, y),
                mixinStyleClasses(null, style, restAttributes),
                attrsToString(restAttributes),
                text.encodeAsHTML().replaceAll('&deg;', '&#176;'))
    }

    private static final RECT_SVG = '<rect x="%.2f" y="%.2f" width="%.2f" height="%.2f"%s%s%s/>'

    @Override
    String rect(double x, double y, double width, double height, LineStyle style, double rotate, Map restAttributes) {
        return String.format(LOCALE_US, RECT_SVG, x, y, width, height,
                transformRotateBuilder(rotate, x, y),
                mixinStyleClasses(style, null, restAttributes),
                attrsToString(restAttributes))
    }

    private static final CIRCLE_SVG = '<circle cx="%.2f" cy="%.2f" r="%.2f"%s%s/>'

    @Override
    String circle(double x, double y, double radius, LineStyle style, Map restAttributes) {
        return String.format(LOCALE_US, CIRCLE_SVG, x, y, radius,
                mixinStyleClasses(style, null, restAttributes),
                attrsToString(restAttributes))
    }

    private static final ELLIPSE_SVG = '<ellipse cx="%.2f" cy="%.2f" rx="%.2f" ry="%.2f"%s%s%s/>'

    @Override
    String ellipse(double x, double y, double rx, double ry, double rotate, LineStyle style, Map restAttributes) {
        return String.format(LOCALE_US, ELLIPSE_SVG, x, y, rx, ry,
                transformRotateBuilder(rotate, x, y),
                mixinStyleClasses(style, null, restAttributes),
                attrsToString(restAttributes))
    }

    private static final CIRCLEARC_SVG = '<path d="m%.2f,%.2f a %.2f,%.2f 0 0 0 %.2f,%.2f"%s%s/>'

    @Override
    String circleArc(double x1, double y1, double x2, double y2, double radius, LineStyle style, Map restAttributes) {
        return String.format(LOCALE_US, CIRCLEARC_SVG, x1, y1, radius, radius, x2 - x1, y2 - y1,
                mixinStyleClasses(style, null, restAttributes),
                attrsToString(restAttributes))
    }

    private static final G_SVG = '<g %s transform="translate(%.2f,%.2f)"%s%s>%s</g>'

    @Override
    String group(double x, double y, Closure body, Map restAttributes, String id, LineStyle lineStyle, TextStyle textStyle) {
        return String.format(LOCALE_US, G_SVG, id ? "id=\"$id\"" : '', x, y,
                mixinStyleClasses(lineStyle, textStyle, restAttributes),
                attrsToString(restAttributes), body())
    }

    @Override
    String renderFor(Closure body, String forRender) {
        return (ALIAS.equalsIgnoreCase(forRender)) ? body?.call()?.toString() ?: '' : ''
    }

    private static final TEXTSTYLE_SVG = '.%s {font-family: %s; font-style: %s; font-size: %.2fpt; fill: %s}'

    @Override
    String textStyle(TextStyle textStyle) {
        if (textStyle) {
            return String.format(LOCALE_US, TEXTSTYLE_SVG, textStyle.name, textStyle.font,
                    textStyle.oblique ? 'oblique' : 'normal', textStyle.size, color2SVGString(textStyle.color))
        }
        return ''
    }

    private static final LINESTYLE_SVG = '.%s { fill: %s; stroke: %s; stroke-width: %.2f;%s}'

    @Override
    String lineStyle(LineStyle lineStyle) {
        if (lineStyle) {
            return String.format(LOCALE_US, LINESTYLE_SVG, lineStyle.name, lineStyle.fill, color2SVGString(lineStyle.strokeColor),
                    lineStyle.strokeWidth, lineStyle.strokeDashArray ? " stroke-dasharray:${lineStyle.strokeDashArray};" : '')
        }
        return ''
    }
}
