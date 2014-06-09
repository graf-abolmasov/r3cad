package com.r3.dataset.turnout;

import org.springframework.context.MessageSourceResolvable;

/**
 * User: Felix-13
 * Date: 15.10.13
 * Time: 23:53
 */
public enum Orientation implements MessageSourceResolvable {
    RIGHT,
    LEFT;

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
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName() {
        return name();
    }
}
