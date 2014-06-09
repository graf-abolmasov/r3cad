package com.r3.drawing.area;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: graf
 * Date: 10/14/13
 * Time: 12:25 PM
 */
public enum RepresentationRole implements MessageSourceResolvable {
    PROFILE_CHART_SYMBOLS,
    PROFILE_CHART_GROUND_LEVEL,
    PROFILE_CHART_IRR_PICKETS,
    PROFILE_TABLE_GRADIENT,
    PROFILE_TABLE_GROUND_LEVEL,
    PROFILE_TABLE_CURVES_AND_LINES_IN_PLAN,
    PROFILE_TABLE_PICKETS,
    PROFILE_TABLE_TOP_VIEW_PLAN;

    @Override
    public String[] getCodes() {
        return new String[]{getClass().getName() + "." + name()};
    }

    @Override
    public Object[] getArguments() {
        return new Object[0];
    }

    @Override
    public String getDefaultMessage() {
        return name();
    }

    public String getName() {
        return name();
    }
}
