package com.r3.dataset;

/**
 * User: graf
 * Date: 11/5/13
 * Time: 3:36 PM
 */
public class Location implements Comparable<Location> {

    public static final int MM_IN_CM = 10;

    public static final int METER_IN_KM = 1000;
    public static final int METER_IN_PK = 100;

    public static final int MM_IN_METER = 1000;
    public static final int MM_IN_KM = MM_IN_METER * METER_IN_KM;
    public static final int MM_IN_PK = MM_IN_METER * METER_IN_PK;

    public static final int PK_IN_KM = 10;

    protected int km;
    protected int pk;
    protected int plus;

    public Location(){
        this(0);
    }

    public Location(Location location){
        this(location.getKm(), location.getPk(), location.getPlus());
    }

    public Location(long mm) {
        final long kms = mm / Location.MM_IN_KM;
        final long pks = (mm / Location.MM_IN_PK) % Location.PK_IN_KM;
        final long mms = mm % Location.MM_IN_PK;
        this.km = (int) kms;
        this.pk = (int) pks;
        this.plus = (int) mms;
    }

    public Location(int km, int pk, int plus) {
        this(km * Location.MM_IN_KM + pk * Location.MM_IN_PK + plus);
    }

    public int getKm() {
        return km;
    }

    public int getPk() {
        return pk;
    }

    public int getPlus() {
        return plus;
    }

    public Location plus(final Location otherLocation) {
        return plus(otherLocation.toLong());
    }

    public Location plus(long mm) {
        return new Location(mm + this.toLong());
    }

    public Location plus(int mm) {
        return plus((long) mm);
    }

    @Override
    public String toString() {
        return "Location(" +
                "km: " + getKm() +
                ", pk: " + getPk() +
                ", plus: " + getPlus() +
                ')';
    }

    public int compareTo(final Location otherLocation) {
        if (this == otherLocation) return 0;
        if (otherLocation == null)
            throw new NullPointerException("Can't compare location with 'null'");
        final long otherTotal = otherLocation.toLong();
        final long total = this.toLong();
        final long diff = total - otherTotal;
        return (int)diff;
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;

        Location location = (Location) o;

        if (getKm() != location.getKm()) return false;
        if (getPk() != location.getPk()) return false;
        if (getPlus() != location.getPlus()) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = getKm();
        result = 31 * result + getPk();
        result = 31 * result + getPlus();
        return result;
    }

    public long toLong() {
        return getKm() * Location.MM_IN_KM + getPk() * Location.MM_IN_PK + getPlus();
    }

    public int toPk() {
        return getKm() * Location.PK_IN_KM + getPk();
    }
}
