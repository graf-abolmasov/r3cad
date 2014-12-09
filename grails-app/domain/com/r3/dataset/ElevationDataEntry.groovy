package com.r3.dataset

class ElevationDataEntry extends DataEntry<PlanElementsDataEntryReadOnly> {

    static embedded = ['location']

    static transients = ['values', 'valuesMap', 'angleAsString']

    static belongsTo = [dataSet: ElevationDataSet]

    static final int MINUTES_IN_GRAD = 60
    static final int SECONDS_IN_MINUTE = 60
    static final int SECONDS_IN_GRAD = MINUTES_IN_GRAD * SECONDS_IN_MINUTE
    static final int ROUND_ANGLE = 360

    CurveType elementType = CurveType.STRAIGHT
    double angle //in degree
    int radius //in meter
    int h //in mm
    int t1 //in mm
    int t2 //in mm
    int length //in mm
    int endKm
    int endPk
    int endPlus //in mm
    int l1 //in meter

    int l2 //in meter
    String g
    String h_
    String i
    String j
    String k
    String l
    String m
    String n
    String p
    String s
    String t
    String u
    String v
    String w
    String x
    String y
    String z
    String aa
    String ab
    String ad
    String ae
    String af

    static final String ANGLE_AS_STRING_FORMAT = '%d°%d\'%d"'

    String getAngleAsString() {
        int deg = angle.toInteger()
        def minsec = angle - deg
        int min = (minsec * MINUTES_IN_GRAD).toInteger()
        int sec = (minsec * SECONDS_IN_GRAD - min * MINUTES_IN_GRAD).round(0).toInteger()
        if (sec == 60) {
            sec = 0
            min++
        }
        if (min == 60) {
            min = 0
            deg++
        }
        return String.format(ANGLE_AS_STRING_FORMAT, deg, min, sec)
    }

    static double toDegrees(int degree, int min, double sec) {
        return (degree + min.toDouble() / MINUTES_IN_GRAD + sec / SECONDS_IN_GRAD).round(6)
    }

    static double calculateLength(int radius, double angle, int l1, int l2) {
        return (Math.PI * 2 * radius * angle / ROUND_ANGLE + l1 / 2 + l2 / 2).round(3)
    }

    static double calculateTangens(int radius, double angle, int l) {
        return (radius * Math.tan(Math.toRadians(angle) / 2) + l.toDouble() / 2).round(3)
    }

    @Override
    PlanElementsDataEntryReadOnly getValues() {
        return new PlanElementsDataEntryReadOnly(km, pk, plus, elementType, angle, getAngleAsString(), radius, h,
                t1, t2, length, endKm, endPk, endPlus, l1, l2)
    }

    @Override
    Map<String, Object> getValuesMap() {
        def result = [:]
        result['radius'] = radius
        result['elementType'] = elementType.name()
        result['angle'] = angle
        result['angleAsString'] = getAngleAsString()
        result['h'] = h
        result['t1'] = t1
        result['t2'] = t2
        result['length'] = 1.0 * length / DataEntry.MM_IN_METER
        result['endKm'] = endKm
        result['endPk'] = endPk
        result['endPlus'] = endPlus / DataEntry.MM_IN_METER
        result['l1'] = 1.0 * l1 / DataEntry.MM_IN_METER
        result['l2'] = 1.0 * l2 / DataEntry.MM_IN_METER
        result['g'] = g
        result['h_'] = h_
        result['i'] = i
        result['j'] = j
        result['k'] = k
        result['l'] = l
        result['m'] = m
        result['n'] = n
        result['p'] = p
        result['s'] = s
        result['t'] = t
        result['u'] = u
        result['v'] = v
        result['w'] = w
        result['y'] = y
        result['x'] = x
        result['z'] = z
        result['aa'] = aa
        result['ab'] = ab
        result['ad'] = ad
        result['ae'] = ae
        result['af'] = af
        return result
    }
}
