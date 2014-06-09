package com.r3.utils

class UtilsTest extends GroovyTestCase {

    void testToRoman() {
        assert Utils.toRomanNumber(0) == '0'
        assert Utils.toRomanNumber(1) == 'I'
        assert Utils.toRomanNumber(2) == 'II'
        assert Utils.toRomanNumber(3) == 'III'
        assert Utils.toRomanNumber(4) == 'IV'
        assert Utils.toRomanNumber(5) == 'V'
        assert Utils.toRomanNumber(6) == 'VI'
        assert Utils.toRomanNumber(7) == 'VII'
        assert Utils.toRomanNumber(8) == 'VIII'
        assert Utils.toRomanNumber(9) == 'IX'
        assert Utils.toRomanNumber(10) == 'X'
        assert Utils.toRomanNumber(11) == '11'
    }

    void testDefaultScales() {
        assert Utils.scaleX(10000) == 1
        assert Utils.scaleY(0.1) == 1
    }
}
