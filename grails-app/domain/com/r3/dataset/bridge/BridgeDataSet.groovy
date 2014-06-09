package com.r3.dataset.bridge

import com.r3.Project
import com.r3.RailWay
import com.r3.dataset.BaseDataSet
import com.r3.dataset.DataSetMetaInfo
import com.r3.dataset.ProfileDrawingAware
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class BridgeDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    public static final String TABLE_TEMPLATE = "bridge_table"
    public static final String CHART_TEMPLATE = "bridge_chart"
    public static final String DEFAULT_TEMPLATE = "empty"
    private static final HashMap<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)
    private static final HashMap<RepresentationRole, String> CHART_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN, TABLE_TEMPLATE)
        CHART_TEMPLATES.put(RepresentationRole.PROFILE_CHART_SYMBOLS, CHART_TEMPLATE)
    }

    static hasMany = [values: BridgeDataEntry]
    EnterType enterType = EnterType.BEGIN_END

    SortedSet<BridgeDataEntry> values

    BridgeDataSet(@NotNull final Project project,
                  @NotNull final RailWay railWay,
                  @NotNull final String title) {
        super(project, railWay, title)
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        if (RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN == representationRole) {
            return TABLE_TEMPLATE
        } /*else if (RepresentationRole.PROFILE_CHART_SYMBOLS == representationRole) {
            return CHART_TEMPLATE
        }*/
        return DEFAULT_TEMPLATE
    }

    @Override
    Map<RepresentationRole, String> getTableTemplates() {
        return TABLE_TEMPLATES
    }

    @Override
    Map<RepresentationRole, String> getChartTemplates() {
        return Collections.emptyMap()//CHART_TEMPLATES
    }
}
