package com.r3.dataset.turnout;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: Felix-13
 * Date: 15.10.13
 * Time: 20:02
 */
public enum Model implements MessageSourceResolvable {
    NONE,
    ONE_SIXTH,
    ONE_NINTH,
    ONE_ELEVENTH,
    ONE_EIGHTEENTH,
    ONE_TWENTY_SECOND,
    TWO_SIXTH,
    TWO_NINTH,
    TWO_ELEVENTH,
    TWO_EIGHTEENTH;

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
