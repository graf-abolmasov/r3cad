package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole
import com.r3.mapping.dataset.GradientDataSetMap

import javax.validation.constraints.NotNull

class GradientDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(true, true)

    private static final String DEFAULT_TEMPLATE = 'gradient'
    private static final HashMap<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(RepresentationRole.PROFILE_TABLE_GRADIENT, DEFAULT_TEMPLATE)
    }

    static constraints = {
        railWay(nullable: false)
    }

    GradientDataSet(@NotNull Project project, @NotNull RailWay railWay, @NotNull String title) {
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
    GradientDataSetMap map() {
        return new GradientDataSetMap(this, dataMappingService)
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        return DEFAULT_TEMPLATE
    }
}
