package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class TopViewPlanDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(true, true)

    private static final String DEFAULT_TEMPLATE = 'empty'
    private static final String TOP_VIEW_PLAN_TEMPLATE = 'top_view_plan'
    private static final HashMap<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN, TOP_VIEW_PLAN_TEMPLATE)
    }

    static constraints = {
        railWay(nullable: false)
    }

    TopViewPlanDataSet(@NotNull final Project project,
                       @NotNull final RailWay railWay,
                       @NotNull final String title) {
        super(project, railWay, title)
    }

    @Override
    SortedSet<DataEntry> getValues() {
        return Collections.emptySortedSet()
    }

    @Override
    Map<RepresentationRole, String> getChartTemplates() {
        return Collections.emptyMap()
    }

    @Override
    Map<RepresentationRole, String> getTableTemplates() {
        return TABLE_TEMPLATES
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        if (RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN == representationRole) {
            return TOP_VIEW_PLAN_TEMPLATE
        }
        return DEFAULT_TEMPLATE
    }
}
