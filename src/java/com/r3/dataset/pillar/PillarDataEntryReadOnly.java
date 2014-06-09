package com.r3.dataset.pillar;

import java.io.Serializable;

/**
 * User: graf
 * Date: 11/6/13
 * Time: 1:34 AM
 */
public class PillarDataEntryReadOnly implements Serializable {

    public PillarDataEntryReadOnly(int km, int pk, int plus, String number, String alternativeName, PillarType type,
                                   WireSuspensionType leftWireSuspensionType, WireSuspensionType rightWireSuspensionType,
                                   IronCrampAnchorType beforeIronCrampAnchorType, IronCrampAnchorType afterIronCrampAnchorType) {
        this.km = km;
        this.pk = pk;
        this.plus = plus;
        this.number = number;
        this.alternativeName = alternativeName;
        this.type = type;
        this.leftWireSuspensionType = leftWireSuspensionType;
        this.rightWireSuspensionType = rightWireSuspensionType;
        this.beforeIronCrampAnchorType = beforeIronCrampAnchorType;
        this.afterIronCrampAnchorType = afterIronCrampAnchorType;
    }

    private int km;
    private int pk;
    private int plus;

    private String number;
    private String alternativeName;
    private PillarType type;

    private WireSuspensionType leftWireSuspensionType;
    private WireSuspensionType rightWireSuspensionType;

    private IronCrampAnchorType beforeIronCrampAnchorType;
    private IronCrampAnchorType afterIronCrampAnchorType;

    public int getKm() {
        return km;
    }

    public int getPk() {
        return pk;
    }

    public int getPlus() {
        return plus;
    }

    public String getNumber() {
        return number;
    }

    public String getAlternativeName() {
        return alternativeName;
    }

    public PillarType getType() {
        return type;
    }

    public WireSuspensionType getLeftWireSuspensionType() {
        return leftWireSuspensionType;
    }

    public WireSuspensionType getRightWireSuspensionType() {
        return rightWireSuspensionType;
    }

    public IronCrampAnchorType getBeforeIronCrampAnchorType() {
        return beforeIronCrampAnchorType;
    }

    public IronCrampAnchorType getAfterIronCrampAnchorType() {
        return afterIronCrampAnchorType;
    }
}
