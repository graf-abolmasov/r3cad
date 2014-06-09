package com.r3.dataset;

import com.r3.dataset.turnout.*;

import java.io.Serializable;

/**
 * User: Felix-13
 * Date: 15.10.13
 * Time: 19:39
 */
public class TurnoutDataEntryReadOnly extends Location implements Serializable {
    public TurnoutDataEntryReadOnly(int km, int pk, int plus, int number, int numberSecond, TurnoutType turnoutType, Direct direct,
                                    Orientation orientation, Model model, RailsType railsType, SleepersType sleepersType, ControlType controlType,
                                    KmPkPlus adjacentLinkLocation, double adjacentLinkRailHeadValue,
                                    KmPkPlus stockRailJointLocation, double stockRailJointRailHeadValue,
                                    KmPkPlus tongueLocation, double tongueRailHeadValue,
                                    KmPkPlus tailLocation, double tailRailHeadValue,
                                    KmPkPlus afterCrossLinkLocation, double afterCrossLinkRailHeadValue,
                                    KmPkPlus stockRailJointLocationSecond, double stockRailJointRailHeadValueSecond,
                                    KmPkPlus tongueLocationSecond, double tongueRailHeadValueSecond,
                                    KmPkPlus tailLocationSecond, double tailRailHeadValueSecond,
                                    KmPkPlus afterCrossLinkLocationSecond, double afterCrossLinkRailHeadValueSecond) {
        super(km, pk, plus);
        this.number = number;
        this.numberSecond = numberSecond;
        this.turnoutType = turnoutType;
        this.direct = direct;
        this.orientation = orientation;
        this.model = model;
        this.railsType = railsType;
        this.sleepersType = sleepersType;
        this.controlType = controlType;

        if (adjacentLinkLocation != null) {
            this.adjacentLinkLocation = adjacentLinkLocation.toLocation();
        }
        this.adjacentLinkRailHeadValue = adjacentLinkRailHeadValue;

        if (stockRailJointLocation != null) {
            this.stockRailJointLocation = stockRailJointLocation.toLocation();
        }
        this.stockRailJointRailHeadValue = stockRailJointRailHeadValue;

        if (tongueLocation != null) {
            this.tongueLocation = tongueLocation.toLocation();
        }
        this.tongueRailHeadValue = tongueRailHeadValue;

        if (tailLocation != null) {
            this.tailLocation = tailLocation.toLocation();
        }
        this.tailRailHeadValue = tailRailHeadValue;

        if (afterCrossLinkLocation != null) {
            this.afterCrossLinkLocation = afterCrossLinkLocation.toLocation();
        }
        this.afterCrossLinkRailHeadValue = afterCrossLinkRailHeadValue;

        if (stockRailJointLocationSecond != null) {
            this.stockRailJointLocationSecond = stockRailJointLocationSecond.toLocation();
        }
        this.stockRailJointRailHeadValueSecond = stockRailJointRailHeadValueSecond;

        if (tongueLocationSecond != null) {
            this.tongueLocationSecond = tongueLocationSecond.toLocation();
        }
        this.tongueRailHeadValueSecond = tongueRailHeadValueSecond;

        if (tailLocationSecond != null) {
            this.tailLocationSecond = tailLocationSecond.toLocation();
        }
        this.tailRailHeadValueSecond = tailRailHeadValueSecond;

        if (afterCrossLinkLocationSecond != null) {
            this.afterCrossLinkLocationSecond = afterCrossLinkLocationSecond.toLocation();
        }
        this.afterCrossLinkRailHeadValueSecond = afterCrossLinkRailHeadValueSecond;
    }

    private int number;
    private int numberSecond;
    private TurnoutType turnoutType = TurnoutType.NORMAL;
    private Direct direct = Direct.DIRECT;
    private Orientation orientation = Orientation.RIGHT;
    private Model model = Model.NONE;
    private RailsType railsType = RailsType.NONE;
    private SleepersType sleepersType = SleepersType.COMBO;
    private ControlType controlType = ControlType.NONE;

    private Location adjacentLinkLocation; //Примыкающее звено
    private double adjacentLinkRailHeadValue;

    private Location stockRailJointLocation; //Стык рамного рельса
    private double stockRailJointRailHeadValue;

    private Location tongueLocation; // Остряк
    private double tongueRailHeadValue;

    private Location tailLocation; // Хвост
    private double tailRailHeadValue;

    private Location afterCrossLinkLocation; // Закрестовинное звено
    private double afterCrossLinkRailHeadValue;

    private Location stockRailJointLocationSecond; //Стык рамного рельса
    private double stockRailJointRailHeadValueSecond;

    private Location tongueLocationSecond; // Остряк
    private double tongueRailHeadValueSecond;

    private Location tailLocationSecond; // Хвост
    private double tailRailHeadValueSecond;

    private Location afterCrossLinkLocationSecond; // Закрестовинное звено
    private double afterCrossLinkRailHeadValueSecond;

    public int getNumber() {
        return number;
    }

    public int getNumberSecond() {
        return numberSecond;
    }

    public TurnoutType getTurnoutType() {
        return turnoutType;
    }

    public Direct getDirect() {
        return direct;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public Model getModel() {
        return model;
    }

    public RailsType getRailsType() {
        return railsType;
    }

    public ControlType getControlType() {
        return controlType;
    }

    public SleepersType getSleepersType() {
        return sleepersType;
    }

    public Location getAdjacentLinkLocation() {
        return adjacentLinkLocation;
    }

    public double getAdjacentLinkRailHeadValue() {
        return adjacentLinkRailHeadValue;
    }

    public Location getStockRailJointLocation() {
        return stockRailJointLocation;
    }

    public double getStockRailJointRailHeadValue() {
        return stockRailJointRailHeadValue;
    }

    public Location getTongueLocation() {
        return tongueLocation;
    }

    public double getTongueRailHeadValue() {
        return tongueRailHeadValue;
    }

    public Location getTailLocation() {
        return tailLocation;
    }

    public double getTailRailHeadValue() {
        return tailRailHeadValue;
    }

    public Location getAfterCrossLinkLocation() {
        return afterCrossLinkLocation;
    }

    public double getAfterCrossLinkRailHeadValue() {
        return afterCrossLinkRailHeadValue;
    }

    public Location getStockRailJointLocationSecond() {
        return stockRailJointLocationSecond;
    }

    public double getStockRailJointRailHeadValueSecond() {
        return stockRailJointRailHeadValueSecond;
    }

    public Location getTongueLocationSecond() {
        return tongueLocationSecond;
    }

    public double getTongueRailHeadValueSecond() {
        return tongueRailHeadValueSecond;
    }

    public Location getTailLocationSecond() {
        return tailLocationSecond;
    }

    public double getTailRailHeadValueSecond() {
        return tailRailHeadValueSecond;
    }

    public Location getAfterCrossLinkLocationSecond() {
        return afterCrossLinkLocationSecond;
    }

    public double getAfterCrossLinkRailHeadValueSecond() {
        return afterCrossLinkRailHeadValueSecond;
    }
}
