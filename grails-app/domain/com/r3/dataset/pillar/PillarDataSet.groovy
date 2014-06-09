package com.r3.dataset.pillar

import com.r3.Project
import com.r3.dataset.BaseDataSet
import com.r3.dataset.DataSetMetaInfo
import com.r3.dataset.ProfileDrawingAware
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class PillarDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    private static final String DEFAULT_TEMPLATE = "pillar_number"

    static hasMany = [values: PillarDataEntry]

    SortedSet<PillarDataEntry> values

    PillarDataSet(@NotNull Project project, @NotNull String title) {
        super(project, title)
    }

    @Override
    Map<RepresentationRole, String> getChartTemplates() {
        return Collections.emptyMap()
    }

    @Override
    Map<RepresentationRole, String> getTableTemplates() {
        return Collections.emptyMap()
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        return DEFAULT_TEMPLATE
    }
}
