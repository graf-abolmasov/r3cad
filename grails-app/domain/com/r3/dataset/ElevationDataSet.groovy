package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class ElevationDataSet extends BaseDataSet {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    static constraints = {
        railWay(nullable: false)
    }

    static hasMany = [values: ElevationDataEntry]

    SortedSet<ElevationDataEntry> values

    ElevationDataSet(@NotNull final Project project,
                     @NotNull final RailWay railWay,
                     @NotNull final String title) {
        super(project, railWay, title)
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        return ''
    }
}
