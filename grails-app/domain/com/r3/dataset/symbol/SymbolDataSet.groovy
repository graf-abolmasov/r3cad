package com.r3.dataset.symbol

import static com.r3.drawing.area.RepresentationRole.PROFILE_CHART_SYMBOLS
import static com.r3.drawing.area.RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN

import com.r3.Project
import com.r3.dataset.BaseDataSet
import com.r3.dataset.DataSetMetaInfo
import com.r3.dataset.ProfileDrawingAware
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class SymbolDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    public static final String TABLE_TEMPLATE = "picket_symbol_table"
    public static final String CHART_TEMPLATE = "picket_symbol_chart"
    public static final String DEFAULT_TEMPLATE = "empty"
    private static final HashMap<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)
    private static final HashMap<RepresentationRole, String> CHART_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(PROFILE_TABLE_TOP_VIEW_PLAN, TABLE_TEMPLATE)
        CHART_TEMPLATES.put(PROFILE_CHART_SYMBOLS, CHART_TEMPLATE)
    }

    static hasMany = [values: SymbolDataEntry]

    SortedSet<SymbolDataEntry> values

    SymbolDataSet(@NotNull Project project, @NotNull String title) {
        super(project, title)
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        if (PROFILE_TABLE_TOP_VIEW_PLAN == representationRole) {
            return TABLE_TEMPLATE
        } else if (PROFILE_CHART_SYMBOLS == representationRole) {
            return CHART_TEMPLATE
        }
        return DEFAULT_TEMPLATE
    }

    @Override
    Map<RepresentationRole, String> getTableTemplates() {
        return TABLE_TEMPLATES
    }

    @Override
    Map<RepresentationRole, String> getChartTemplates() {
        return CHART_TEMPLATES
    }
}
