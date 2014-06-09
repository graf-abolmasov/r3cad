package com.r3.dataset;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: graf
 * Date: 10/8/13
 * Time: 1:11 AM
 */

public enum CurveType implements MessageSourceResolvable {
    LEFT_FULL_CURVE,
    RIGHT_FULL_CURVE,
    STRAIGHT;

    @Override
    public String[] getCodes() {
        return new String[]{getClass().getSimpleName() + "." + name()};
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
