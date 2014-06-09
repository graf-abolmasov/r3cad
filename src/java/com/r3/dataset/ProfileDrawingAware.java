package com.r3.dataset;

import com.r3.drawing.area.RepresentationRole;

import java.util.Map;

/**
 * User: graf
 * Date: 9/18/13
 * Time: 12:37 AM
 */
@Deprecated
public interface ProfileDrawingAware {
    @Deprecated
    public Map<RepresentationRole, String> getTableTemplates();

    @Deprecated
    public Map<RepresentationRole, String> getChartTemplates();
}
