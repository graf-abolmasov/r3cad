package com.r3.mapping;

import java.io.Serializable;
import java.util.Comparator;

public final class DataItem implements Serializable {

    public static class DataItemByValueComparator implements Comparator<DataItem> {

        @Override
        @SuppressWarnings("unchecked")
        public int compare(DataItem o1, DataItem o2) {
            return ((Comparable)o1.value).compareTo(o2.value);
        }
    }

    public static class DataItemByMmComparator implements Comparator<DataItem> {

        @Override
        public int compare(DataItem o1, DataItem o2) {
            long dif = o1.x - o2.x;

            if (dif > 0) {
                return 1;
            } else if (dif < 0) {
                return -1;
            }

            return 0;
        }
    }

    public DataItem(long x, final Serializable value) {
        this.x = x;
        this.value = value;
    }

    private long x;

    private Serializable value;

    public long getX() {
        return x;
    }

    public Serializable getValue() {
        return value;
    }
}
