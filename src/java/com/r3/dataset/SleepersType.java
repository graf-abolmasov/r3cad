package com.r3.dataset;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: Felix-13
 * Date: 19.10.13
 * Time: 21:38
 */
public enum SleepersType implements MessageSourceResolvable {
    NONE,
    WOODEN,
    CONCRETE,
    COMBO;

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
