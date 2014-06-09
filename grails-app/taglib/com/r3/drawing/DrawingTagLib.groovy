package com.r3.drawing

import com.r3.drawing.renderer.Renderer
import com.r3.drawing.style.DominantBaseline
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.TextAnchor
import com.r3.drawing.style.TextStyle

class DrawingTagLib {

    static namespace = "drw"

    private static <T> T getAttr(Map attrs, String attrName) {
        return attrs.remove(attrName)
    }

    private static <T> T getAttr(Map attrs, String attrName, T defValue) {
        return attrs.remove(attrName) ?: defValue
    }

    /**
     *
     * @param attrs
     * @return
     */
    private Renderer getRenderer(Map attrs) {
        def renderer = getAttr(attrs, "renderer")

        if (!renderer) {
            renderer = pageScope.renderer
        }

        if (!renderer) {
            throwTagError("Renderer is not specified")
        }

        return renderer
    }

    /**
     * В данной библиотеке пока не поддерживаются:
     * Стили линий для Автокад. (Есть набросок)
     * А так же возможность заливки замкнутых фигур.
     *
     */

    /**
     * @attr renderer
     * @attr x1
     * @attr y1
     * @attr x2
     * @attr y2
     * @attr dx
     * @attr dy
     * @attr style
     */
    def line = { attrs ->
        def renderer = getRenderer(attrs)

        def dx = getAttr(attrs, "dx", 0d).toDouble()
        def dy = getAttr(attrs, "dy", 0d).toDouble()

        def x1 = getAttr(attrs, "x1", 0d).toDouble() + dx
        def y1 = getAttr(attrs, "y1", 0d).toDouble() + dy
        def x2 = getAttr(attrs, "x2", 0d).toDouble() + dx
        def y2 = getAttr(attrs, "y2", 0d).toDouble() + dy

        def LineStyle style = getAttr(attrs, "style")
        out << (((x1 != x2) || (y1 != y2)) ? renderer.line(x1, y1, x2, y2, style, attrs) : '')
    }

    /**
     * @attr renderer
     * @attr x
     * @attr y
     * @attr dx
     * @attr dy
     * @attr text-anchor Horizontal align. Default: start. Enum: middle, end, start
     * @attr dominant-baseline Vertical align Default: "". Enum: middle, text-after-edge, text-before-edge
     * @attr rotate Angle of rotation counter-clockwise
     * @attr style
     */
    def text = { attrs, body ->
        def renderer = getRenderer(attrs)

        def dx = getAttr(attrs, "dx", 0d).toDouble()
        def dy = getAttr(attrs, "dy", 0d).toDouble()

        def x = getAttr(attrs, "x", 0d).toDouble() + dx
        def y = getAttr(attrs, "y", 0d).toDouble() + dy

        def rotate = getAttr(attrs, "rotate", 0d).toDouble()

        String text = body()

        TextAnchor textAnchor = getAttr(attrs, "text-anchor", TextAnchor.START)

        DominantBaseline dominantBaseline = getAttr(attrs, "dominant-baseline", null)
        TextStyle style = getAttr(attrs, "style", null)

        out << renderer.text(x, y, rotate, textAnchor, dominantBaseline, text, style, attrs)
    }

    /**
     * @attr renderer
     * @attr x
     * @attr y
     * @attr width
     * @attr height
     * @attr dx
     * @attr dy
     * @attr style
     */
    def rect = { attrs ->
        def renderer = getRenderer(attrs)

        def dx = getAttr(attrs, "dx", 0d).toDouble()
        def dy = getAttr(attrs, "dy", 0d).toDouble()

        def x = getAttr(attrs, "x", 0d).toDouble() + dx
        def y = getAttr(attrs, "y", 0d).toDouble() + dy

        def width = getAttr(attrs, "width", 0d).toDouble()
        def height = getAttr(attrs, "height", 0d).toDouble()

        def rotate = getAttr(attrs, "rotate", 0d).toDouble()

        LineStyle style = getAttr(attrs, "style")
        out << ((width != 0 && height != 0) ? renderer.rect(x, y, width, height, style, rotate, attrs): '')
    }

    /**
     * Указывается центр круга (x,y) и радиус radius
     * @attr renderer
     * @attr x
     * @attr y
     * @attr radius
     * @attr dx
     * @attr dy
     */
    def circle = { attrs, body ->
        def renderer = getRenderer(attrs)

        def dx = getAttr(attrs, "dx", 0d).toDouble()
        def dy = getAttr(attrs, "dy", 0d).toDouble()

        def x = getAttr(attrs, "x", 0d).toDouble() + dx
        def y = getAttr(attrs, "y", 0d).toDouble() + dy

        def radius = getAttr(attrs, "radius", 0d).toDouble()

        LineStyle style = getAttr(attrs, "style")

        out << ((radius != 0) ? renderer.circle(x, y, Math.abs(radius), style, attrs) : '')
    }

    /**
     * Указывается центр элипса (x,y) и радиусы (rx,ry).
     * Атрибут rotate должен повернуть элипс на определенный градус.
     * @attr renderer
     * @attr x
     * @attr y
     * @attr rx Horizontal radius
     * @attr ry Vertical radius
     * @attr dx
     * @attr dy
     * @attr rotate Angle of rotation counter-clockwise
     * @attr style
     */
    def ellipse = { attrs, body ->
        def renderer = getRenderer(attrs)

        def dx = getAttr(attrs, "dx", 0d).toDouble()
        def dy = getAttr(attrs, "dy", 0d).toDouble()

        def x = getAttr(attrs, "x", 0d).toDouble() + dx
        def y = getAttr(attrs, "y", 0d).toDouble() + dy

        def rx = getAttr(attrs, "rx", 0d).toDouble()
        def ry = getAttr(attrs, "ry", 0d).toDouble()

        def rotate = getAttr(attrs, "rotate", 0d).toDouble()

        LineStyle style = getAttr(attrs, "style")

        out << ((rx != 0 && ry != 0) ? renderer.ellipse(x, y, rx, ry, rotate, style, attrs) : '')
    }

    /**
     * Круговая дуга строится по следующему правилу.
     * От начальной точки (x1,y1) до конечной точки (x2,y2) радиусом - radius, по часовой стрелке.
     * Ограничения  (связанные с автокадом): угол дуги не более 180 градусов, т.е. 2*radius>= расстояние между точками,
     * в противном устанавливается минимально возможный радиус.
     * Если не указан радуис, по умолчанию подбирается радиус такой, что строится дуга по часовой стрелке и угол дуги 90 градусов.
     * @attr renderer
     * @attr x1
     * @attr y1
     * @attr x2
     * @attr y2
     * @attr dx
     * @attr dy
     * @attr radius Radius or circle arc. Default=Math.sqrt( Math.pow(x2-x1,2) + Math.pow(y2-y1,2))/Math.sqrt(2)
     * @attr class
     */
    def circleArc = { attrs ->
        def renderer = getRenderer(attrs)

        def dx = getAttr(attrs, "dx", 0d).toDouble()
        def dy = getAttr(attrs, "dy", 0d).toDouble()

        def x1 = getAttr(attrs, "x1", 0d).toDouble() + dx
        def y1 = getAttr(attrs, "y1", 0d).toDouble() + dy
        def x2 = getAttr(attrs, "x2", 0d).toDouble() + dx
        def y2 = getAttr(attrs, "y2", 0d).toDouble() + dy
        def chord = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
        def radius = getAttr(attrs, "radius", chord / 1.4142).toDouble()
        if (2 * radius < chord) {
            radius = chord
        }

        LineStyle style = getAttr(attrs, "style")

        out << (((x1 != x2) || (y1 != y2)) ? renderer.circleArc(x1, y1, x2, y2, radius, style, attrs) : '')
    }

    /**
     * @attr renderer
     * @attr x
     * @attr y
     * @attr dx
     * @attr dy
     * @attr textstyle
     * @attr linestyle
     */
    def group = { attrs, body ->
        def renderer = getRenderer(attrs)

        def id = getAttr(attrs, "id", '').toString()

        def dx = getAttr(attrs, "dx", 0d).toDouble()
        def dy = getAttr(attrs, "dy", 0d).toDouble()

        def x = getAttr(attrs, "x", 0d).toDouble() + dx
        def y = getAttr(attrs, "y", 0d).toDouble() + dy

        TextStyle textStyle = getAttr(attrs, "textstyle")
        LineStyle lineStyle = getAttr(attrs, "linestyle")

        out << renderer.group(x, y, body, attrs, id, lineStyle, textStyle)
    }

    /**
     * @attr renderer
     * @attr forRenderer
     */
    def renderFor = { attrs, body ->
        def renderer = getRenderer(attrs)
        String forRender = getAttr(attrs, "forRenderer")
        out << renderer.renderFor(body, forRender)
    }

    /**
     * @attr renderer
     * @attr style
     */
    def lineStyle = { attrs ->
        def renderer = getRenderer(attrs)
        LineStyle style = getAttr(attrs, "style")
        out << renderer.lineStyle(style)
    }

    def textStyle = { attrs ->
        def renderer = getRenderer(attrs)
        TextStyle style = getAttr(attrs, "style")
        out << renderer.textStyle(style)
    }
}