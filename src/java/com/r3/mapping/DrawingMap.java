package com.r3.mapping;

import com.r3.mapping.dataset.DataSetMap;
import grails.util.Pair;

/**
 * Это чертеж в натуральную величину
 */
public interface DrawingMap {

    public Pk2MmMap getPk2mm();

    public DataSetMap dataSet(final Long id);

    public long getZeroX();

    public long getZeroY();

    public Pair<Integer, Integer> getBoundPickets();

    public long getLength();
}