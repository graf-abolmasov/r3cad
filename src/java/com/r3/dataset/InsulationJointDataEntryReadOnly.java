package com.r3.dataset;

import java.io.Serializable;

/**
 * User: Felix-13
 * Date: 19.10.13
 * Time: 12:13
 */
public class InsulationJointDataEntryReadOnly extends Location implements Serializable {
    private String name;
    private InsulationJointUsingApATek useApATek;

    public InsulationJointDataEntryReadOnly(int km, int pk, int plus, String name, InsulationJointUsingApATek useApATek) {
        super(km, pk, plus);
        this.name = name;
        this.useApATek = useApATek;
    }

    public String getName() {
        return name;
    }

    public InsulationJointUsingApATek getUseApATek() {
        return useApATek;
    }
}
