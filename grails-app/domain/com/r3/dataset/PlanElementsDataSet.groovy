package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class PlanElementsDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    private static final String DEFAULT_TEMPLATE = "curves_and_lines_in_plan"
    private static final Map<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)

    boolean isCalculateField = false

    static {
        TABLE_TEMPLATES.put(RepresentationRole.PROFILE_TABLE_CURVES_AND_LINES_IN_PLAN, DEFAULT_TEMPLATE)
    }

    static constraints = {
        railWay(nullable: false)
    }

    static hasMany = [values: PlanElementsDataEntry]

    SortedSet<PlanElementsDataEntry> values

    PlanElementsDataSet(@NotNull final Project project,
                        @NotNull final RailWay railWay,
                        @NotNull final String title) {
        super(project, railWay, title)
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        return DEFAULT_TEMPLATE
    }

    @Override
    Map<RepresentationRole, String> getTableTemplates() {
        return TABLE_TEMPLATES
    }

    @Override
    Map<RepresentationRole, String> getChartTemplates() {
        return Collections.emptyMap()
    }
}
