package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class DoubleDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, false)

    private static final String DEFAULT_TEMPLATE = "picket_double"
    private static final Map<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(null, DEFAULT_TEMPLATE)
    }

    static hasMany = [values: DoubleDataEntry]

    static constraints = {
        units(nullable: true)
    }

    SortedSet<DoubleDataEntry> values

    String units

    DoubleDataSet(@NotNull final Project project,
                  @NotNull final String title) {
        super(project, title)
    }

    DoubleDataSet(@NotNull final Project project,
                  @NotNull RailWay railWay,
                  @NotNull final String title) {
        super(project, railWay, title)
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
        return DEFAULT_TEMPLATE
    }
}

