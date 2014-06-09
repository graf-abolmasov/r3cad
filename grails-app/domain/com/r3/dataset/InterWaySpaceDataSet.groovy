package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class InterWaySpaceDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    private static final String DEFAULT_TEMPLATE = "picket_double"
    private static final Map<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(null, DEFAULT_TEMPLATE)
    }

    static constraints = {
        title(unique: ['project', 'railWay'], blank: false, maxSize: 128)
        railWay(nullable: true)
    }

    static hasMany = [values: InterWaySpaceDataEntry]

    RailWay firstRailWay
    RailWay secondRailWay

    SortedSet<InterWaySpaceDataEntry> values

    InterWaySpaceDataSet(@NotNull final Project project,
                         @NotNull final String title,
                         @NotNull final RailWay firstRailWay,
                         @NotNull final RailWay secondRailWay) {
        super(project, title)
        this.firstRailWay = firstRailWay
        this.secondRailWay = secondRailWay
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

