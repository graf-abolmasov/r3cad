package com.r3.dataset.bridge;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: Felix-13
 * Date: 26.10.13
 * Time: 21:29
 */
public enum MovementType implements MessageSourceResolvable {
    UP,
    DOWN;

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
