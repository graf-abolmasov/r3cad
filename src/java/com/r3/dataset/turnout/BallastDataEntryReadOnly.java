package com.r3.dataset.turnout;

import com.r3.dataset.Location;

import java.io.Serializable;

/**
 * User: Felix-13
 * Date: 23.10.13
 * Time: 20:01
 */
public class BallastDataEntryReadOnly extends Location implements Serializable {
    private int layer0;
    private int layer1;
    private int layer2;
    private int layer3;
    private int layer4;
    private int layer5;
    private int layer6;
    private int layer7;
    private int layer8;
    private int layer9;

    public BallastDataEntryReadOnly(int km, int pk, int plus, int layer0, int layer1, int layer2, int layer3,
                                    int layer4, int layer5, int layer6, int layer7, int layer8, int layer9) {
        super(km, pk, plus);
        this.layer0 = layer0;
        this.layer1 = layer1;
        this.layer2 = layer2;
        this.layer3 = layer3;
        this.layer4 = layer4;
        this.layer5 = layer5;
        this.layer6 = layer6;
        this.layer7 = layer7;
        this.layer8 = layer8;
        this.layer9 = layer9;
    }

    public int getLayer0() {
        return layer0;
    }

    public int getLayer1() {
        return layer1;
    }

    public int getLayer2() {
        return layer2;
    }

    public int getLayer3() {
        return layer3;
    }

    public int getLayer4() {
        return layer4;
    }

    public int getLayer5() {
        return layer5;
    }

    public int getLayer6() {
        return layer6;
    }

    public int getLayer7() {
        return layer7;
    }

    public int getLayer8() {
        return layer8;
    }

    public int getLayer9() {
        return layer9;
    }
}
