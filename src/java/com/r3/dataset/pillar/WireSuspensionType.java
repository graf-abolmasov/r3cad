package com.r3.dataset.pillar;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: graf
 * Date: 11/6/13
 * Time: 12:55 AM
 */
public enum WireSuspensionType implements MessageSourceResolvable {
    ABSENT,
    USUAL,
    FLEXIBLE,
    PORTAL_CONSTRUCTION;

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
