package com.r3.drawing

import com.r3.drawing.PageSizeFormat
import grails.util.Pair

class PageSizeFormatTest extends GroovyTestCase {

    /**
     *
     Размеры сторон формата, мм (Книжный формат)

     А0	841х1189
     А1	594х841
     А2	420х594
     А3	297х420
     А4	210х297

     */

    void testWidth() {
        assert PageSizeFormat.A0.getWidth() == 1189
        assert PageSizeFormat.A1.getWidth() == 841
        assert PageSizeFormat.A2.getWidth() == 594
        assert PageSizeFormat.A3.getWidth() == 420
        assert PageSizeFormat.A4.getWidth() == 297
    }

    void testHeight() {
        assert PageSizeFormat.A0.getHeight() == 841
        assert PageSizeFormat.A1.getHeight() == 594
        assert PageSizeFormat.A2.getHeight() == 420
        assert PageSizeFormat.A3.getHeight() == 297
        assert PageSizeFormat.A4.getHeight() == 210
    }

    void testMultiply() {
        assert shouldFail {
            PageSizeFormat.A0.multiply(0)
        }

        assert PageSizeFormat.A0.multiply(1) == new Pair<Integer, Integer>(1189, 841)
        assert PageSizeFormat.A0.multiply(2) == new Pair<Integer, Integer>(841 * 2, 1189)
        assert PageSizeFormat.A0.multiply(3) == new Pair<Integer, Integer>(841 * 3, 1189)
    }
}
