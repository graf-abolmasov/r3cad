package com.r3.dataset

import com.r3.Project
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class GroundLevelDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    private static final String DEFAULT_TEMPLATE = "picket_double"
    private static final String CHART_TEMPLATE = "ground"
    private static final Map<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)
    private static final Map<RepresentationRole, String> CHART_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(RepresentationRole.PROFILE_TABLE_GROUND_LEVEL, DEFAULT_TEMPLATE)
        CHART_TEMPLATES.put(RepresentationRole.PROFILE_CHART_GROUND_LEVEL, CHART_TEMPLATE)
    }

    static hasMany = [values: GroundLevelDataEntry]

    SortedSet<GroundLevelDataEntry> values

    GroundLevelDataSet(@NotNull final Project project, @NotNull final String title) {
        super(project, title)
    }

    @Override
    Map<RepresentationRole, String> getChartTemplates() {
        return CHART_TEMPLATES
    }

    @Override
    Map<RepresentationRole, String> getTableTemplates() {
        return TABLE_TEMPLATES
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        if (RepresentationRole.PROFILE_CHART_GROUND_LEVEL == representationRole) {
            return CHART_TEMPLATE
        }
        return DEFAULT_TEMPLATE
    }
}

