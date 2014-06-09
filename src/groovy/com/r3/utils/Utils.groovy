package com.r3.utils

import groovy.transform.CompileStatic

/**
 * User: graf
 * Date: 7/25/13
 * Time: 8:40 PM
 */
final class Utils {

    private static final double X_SCALE = 1.0 / 10000

    private static final double Y_SCALE = 1.0 / 100

    @CompileStatic
    static double scaleX(long mm) {
        return mm * X_SCALE
    }

    @CompileStatic
    static double scaleY(double m) {
        return m * 1000 * Y_SCALE
    }

    @CompileStatic
    static String toRomanNumber(int number) {
        switch (number) {
            case 1..3: return 'I' * number;
            case 4: return 'IV'
            case 5..8: return ('V' + 'I' * (number - 5))
            case 9: return 'IX'
            case 10: return 'X'
        }
        return number.toString()
    }

    @CompileStatic
    static int rgb2Color(int red, int green, int blue) {
        return red * 65536 + green * 256 + blue
    }
}
