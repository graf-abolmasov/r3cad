package com.r3.drawing.style

class LineStyle {

    static constraints = {
        strokeDashArray(nullable: true)
        strokeColor(min: 0, max: 16777215)
    }

    String name
    double strokeWidth = 0.25
    String fill = "none"
    int strokeColor
    String strokeDashArray

    public void SetStyle(LineStyle lineStyle) {
        this.name = lineStyle.name
        this.strokeWidth = lineStyle.strokeWidth
        this.strokeColor = lineStyle.strokeColor
        this.fill = lineStyle.fill
        this.strokeDashArray = lineStyle.strokeDashArray
    }

    public void setStyleColor(int color) {
        this.strokeColor = color
    }

    public void setStyleWidth(double width) {
        this.strokeWidth = width
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (!(o instanceof LineStyle)) return false

        LineStyle lineStyle = (LineStyle) o

        if (name != lineStyle.name)
            return false
        if (strokeColor != lineStyle.strokeColor)
            return false
        if (Double.compare(lineStyle.strokeWidth, strokeWidth) != 0)
            return false
        if (!(fill.equalsIgnoreCase(lineStyle.fill)))
            return false
        if (strokeDashArray != lineStyle.strokeDashArray)
            return false

        return true
    }

    int hashCode() {
        int result
        long temp
        result = name.hashCode()
        temp = strokeWidth != +0.0d ? Double.doubleToLongBits(strokeWidth) : 0L
        result = 31 * result + (int) (temp ^ (temp >>> 32))
        result = 31 * result + (fill != null ? fill.hashCode() : 0)
        result = 31 * result + strokeColor
        result = 31 * result + (strokeDashArray != null ? strokeDashArray.hashCode() : 0)
        return result
    }
}
