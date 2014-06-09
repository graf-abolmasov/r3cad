package com.r3.dataset.pillar;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: graf
 * Date: 10/24/13
 * Time: 1:19 AM
 */
public enum PillarType implements MessageSourceResolvable {
    STEEL,
    REINFORCED_CONCRETE;

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
