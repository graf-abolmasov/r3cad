package com.r3.dataset.bridge;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: Felix-13
 * Date: 26.10.13
 * Time: 19:11
 */
public enum EnterType implements MessageSourceResolvable {
    BEGIN_END,
    BEGIN_LENGTH,
    END_LENGTH,
    AXIS_LENGTH;

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
