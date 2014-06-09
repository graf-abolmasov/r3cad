package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class SleepersDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    private static final String DEFAULT_TEMPLATE = "sleepers"
    private static final Map<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(null, DEFAULT_TEMPLATE)
    }

    static constraints = {
        railWay(nullable: false)
    }

    static hasMany = [values: SleepersDataEntry]

    SortedSet<SleepersDataEntry> values

    SleepersDataSet(@NotNull final Project project,
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
