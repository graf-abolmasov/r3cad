package com.r3.dataset.turnout;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: Felix-13
 * Date: 15.10.13
 * Time: 20:14
 */
public enum RailsType implements MessageSourceResolvable {
    NONE,
    P43,
    P50,
    P65,
    P75;

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
        return null;
    }

    public String getName() {
        return name();
    }
}
