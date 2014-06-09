package com.r3.drawing.renderer

import com.r3.drawing.style.DominantBaseline
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.TextAnchor
import com.r3.drawing.style.TextStyle

/**
 * User: graf
 * Date: 8/11/13
 * Time: 5:11 PM
 */
class CadRenderer implements Renderer {

    public static final String ALIAS = "cad"

    private static final String CAD_COLOR = '%d,%d,%d'

    static String color2CADString(int red, int green, int blue) {
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
        return String.format(LOCALE_US, CAD_COLOR, red, green, blue)
    }

    static String color2CADString(int color) {
        if (color > 16777215)
            return '255,255,255'
        else if (color < 0)
            return '0,0,0'
        else {
            int red, green, blue
            red = color / (65536)
            green = (color - red * 65536) / 256
            blue = (color - red * 65536) % 256
            return String.format(LOCALE_US, CAD_COLOR, red, green, blue)
        }
    }

    private TextStyle beginTextStyle = new TextStyle(name: "text_normal", font: "eskd_kr.shx", size: 2.5, sparsity: 1.3, oblique: true)
    private LineStyle currentLineStyle = new LineStyle(name: "currentLineStyle", strokeWidth: 0.25, fill: "none", strokeColor: 0)

    private final Deque<TextStyle> textStyles = new LinkedList<TextStyle>()
    private final Deque<LineStyle> lineStyles = new LinkedList<LineStyle>()

    private static final LINE_TYPE_CAD = '( COMMAND "ТИПЛИН" "УСТАНОВИТЬ" "%S" "" )'
    private static final LINE_WEIGHT_CAD = '( COMMAND "ВЕСЛИН" "%.2f" ) '
    private static final LINE_COLOR_CAD = '( COMMAND "ЦВЕТ" "П" "%s") '

    private static final LINE_CAD = '( COMMAND "ОТРЕЗОК" "%.3f,%.3f" "%.3f,%.3f" "") '

    private String checkAndSetCurrentLineStyle(LineStyle lineStyle) {
        if (lineStyle == null) {
            if (lineStyles.size() != 0)
                lineStyle = lineStyles.last()
            else
                lineStyle = currentLineStyle
        }
        if (!lineStyle.equals(currentLineStyle)) {
            StringBuilder out = new StringBuilder(String.format(LOCALE_US, LINE_WEIGHT_CAD, lineStyle.strokeWidth))
            out.append(String.format(LOCALE_US, LINE_COLOR_CAD, color2CADString(lineStyle.strokeColor)))
            if (lineStyle.strokeDashArray)
                out.append(String.format(LOCALE_US, LINE_TYPE_CAD, lineStyle.name))
            else
                out.append(String.format(LOCALE_US, LINE_TYPE_CAD, 'continuous'))
            currentLineStyle.SetStyle(lineStyle)
            return out.toString()
        }
        return ""
    }

    @Override
    public String line(double x1, double y1, double x2, double y2, LineStyle style, Map restAttributes) {
        return new StringBuilder()
                .append(checkAndSetCurrentLineStyle(style))
                .append(String.format(LOCALE_US, LINE_CAD, x1, -y1, x2, -y2))
                .toString()
    }

    private static final TEXT_CAD = '( COMMAND "ТЕКСТ" "СТИЛЬ" "%s" %s "%.3f,%.3f"  "%.3f" "%s" ) '

    private static String align2string(TextAnchor anchor, DominantBaseline baseline) {
        StringBuilder wholetextanchor = new StringBuilder()

        switch (baseline) {
            case DominantBaseline.MIDDLE:
                wholetextanchor.append('"выравнивание" "С')
                break
            case DominantBaseline.TEXT_AFTER_EDGE:
                wholetextanchor.append('"выравнивание" "Н')
                break
            case DominantBaseline.TEXT_BEFORE_EDGE:
                wholetextanchor.append('"выравнивание" "В')
                break
            default:
                switch (anchor) {
                    case TextAnchor.END:
                        return '"выравнивание" "впРаво"'
                    case TextAnchor.MIDDLE:
                        return '"выравнивание" "центр"'
                    default:
                        return ''
                }
        }

        switch (anchor) {
            case TextAnchor.END:
                return wholetextanchor.append('П"').toString()
            case TextAnchor.MIDDLE:
                return wholetextanchor.append('Ц"').toString()
            default:
                return wholetextanchor.append('Л"').toString()
        }
    }

    private String checkAndSetCurrentTextStyle(TextStyle textStyle) {
        if (!textStyle) {
            textStyle = textStyles.last()
        }

        StringBuilder out = new StringBuilder()
        if (textStyle.color != currentLineStyle.strokeColor) {
            currentLineStyle.setStyleColor(textStyle.color)
            out.append(String.format(LOCALE_US, LINE_COLOR_CAD, color2CADString(currentLineStyle.strokeColor)))
        }

        if (textStyle.font.contains(".shx")) {
            if (textStyle.strokeWidth != currentLineStyle.strokeWidth) {
                currentLineStyle.setStyleWidth(textStyle.strokeWidth)
                out.append(String.format(LOCALE_US, LINE_WEIGHT_CAD, currentLineStyle.strokeWidth))
            }
        }

        return out.toString()
    }

    @Override
    String text(double x, double y, double rotate, TextAnchor textAnchor, DominantBaseline dominantBaseline, String text, TextStyle style, Map restAttributes) {
        text = text?.trim()
        if (!text) {
            return ''
        }

        return new StringBuilder()
                .append(checkAndSetCurrentTextStyle(style))
                .append(String.format(LOCALE_US, TEXT_CAD, style?.name ?: textStyles.last().name, align2string(textAnchor, dominantBaseline), x, -y, -rotate, text.replaceAll('"', '\\"').replaceAll('°', '@')))
                .toString()
    }

    private static final RECT_CAD = ' (COMMAND "ПРЯМОУГ" "%.3f,%.3f" "РАЗМЕРЫ" "%.3f" "%.3f" "ПОВОРОТ" "%.3f" "%.3f,%.3f") '

    @Override
    String rect(double x, double y, double width, double height, LineStyle style, double rotate, Map restAttributes) {
        double rotateRad = rotate / 180 * Math.PI
        double x2 = x + width * Math.cos(rotateRad) - height * Math.sin(rotateRad)
        double y2 = -y - width * Math.sin(rotateRad) - height * Math.cos(rotateRad)
        return new StringBuilder()
                .append(checkAndSetCurrentLineStyle(style))
                .append(String.format(LOCALE_US, RECT_CAD, x, -y, width, height, -rotate, x2, y2))
                .toString()

    }

    private final static CIRCLE_CAD = '( COMMAND "КРУГ" "%.3f,%.3f" "%.3f") '

    @Override
    String circle(double x, double y, double radius, LineStyle style, Map restAttributes) {
        return new StringBuilder()
                .append(checkAndSetCurrentLineStyle(style))
                .append(String.format(LOCALE_US, CIRCLE_CAD, x, -y, radius))
                .toString()
    }

    private static final ELLIPSE_CAD = '(COMMAND "ЭЛЛИПС" "Ц" "%.3f,%.3f" "%.3f,%.3f" "%.3f") '

    @Override
    String ellipse(double x, double y, double rx, double ry, double rotate, LineStyle style, Map restAttributes) {
        double rotateRad = rotate / 180 * Math.PI
        return new StringBuilder()
                .append(checkAndSetCurrentLineStyle(style))
                .append(String.format(LOCALE_US, ELLIPSE_CAD, x, -y, (rx * Math.cos(-rotateRad)), (rx * Math.sin(-rotateRad)), ry))
                .toString()
    }

    private static final CIRCLEARC_CAD = '( COMMAND "ДУГА" "%.3f,%.3f" "КОНЕЦ" "%.3f,%.3f" "РАДИУС" "%.3f") '

    @Override
    String circleArc(double x1, double y1, double x2, double y2, double radius, LineStyle style, Map restAttributes) {
        return new StringBuilder()
                .append(checkAndSetCurrentLineStyle(style))
                .append(String.format(LOCALE_US, CIRCLEARC_CAD, x1, -y1, x2, -y2, radius))
                .toString()
    }

    private final static G_CAD =
        ' (COMMAND "ПСК" "%.3f,%.3f" "") (COMMAND "СЛОЙ" "СОЗДАТЬ" "%s" "") %s (COMMAND "СЛОЙП" ) (COMMAND "ПСК" "Д") '

    @Override
    String group(double x, double y, Closure body, Map restAttributes, String id, LineStyle lineStyle, TextStyle textStyle) {
        if (lineStyle) {
            lineStyles.add(lineStyle)
        }
        if (textStyle) {
            textStyles.add(textStyle)
        }

        String out = String.format(LOCALE_US, G_CAD, x, -y, id ?: "0", body())

        if (lineStyle) {
            lineStyles.pollLast()
        }
        if (textStyle) {
            textStyles.pollLast()
        }
        return out
    }

    @Override
    String renderFor(Closure body, String forRender) {
        return (ALIAS.equalsIgnoreCase(forRender)) ? body?.call()?.toString() ?: ' ' : ' '
    }

    private static final TEXT_STYLE_CAD = '(COMMAND "СТИЛЬ" "%s" "%s" "%.2f" "%.2f" "%d" "нет" "нет")'

    @Override
    String textStyle(TextStyle textStyle) {
        if (textStyle) {
            return String.format(LOCALE_US, TEXT_STYLE_CAD, textStyle.name, textStyle.font, textStyle.size, textStyle.sparsity ?: 1d, textStyle.oblique ? 15 : 0)
        }
        return ''
    }

    private static String formatDashArrayToString(String stringDashArray) {
        StringBuilder out = new StringBuilder()
        int sign = 1
        stringDashArray?.eachMatch("\\d+\\.?\\d*", { it ->
            out.append(sign * it.toDouble())
            out.append(",")
            sign *= -1
        })
        if (out.length() > 0) {
            out.setLength(out.length() - 1)
        } else {
            out.append('1.0')
        }
        return out.toString()
    }

    private static final LINE_STYLE_CAD = '(COMMAND "типлин" "создать" "%s" "temp.lin" "%s" "%s" "Загрузить" "%s" "temp.lin" "")'

    @Override
    String lineStyle(LineStyle lineStyle) {
        if (lineStyle) {
            if (lineStyle.strokeDashArray)
                return String.format(LOCALE_US, LINE_STYLE_CAD, lineStyle.name, lineStyle.strokeDashArray, formatDashArrayToString(lineStyle.strokeDashArray), lineStyle.name)
            else
                return ''
        } else
            return ''
    }
}
