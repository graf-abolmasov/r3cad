package com.r3.dataset

import com.r3.Project
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class PicketsDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(true, true)

    private static final String DEFAULT_TEMPLATE = 'empty'
    private static final String PICKETS_TEMPLATE = 'pickets'
    private static final HashMap<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)

    static {
        TABLE_TEMPLATES.put(RepresentationRole.PROFILE_TABLE_PICKETS, PICKETS_TEMPLATE)
    }

    PicketsDataSet(@NotNull Project project, @NotNull String title) {
        super(project, title)
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
        if (RepresentationRole.PROFILE_TABLE_PICKETS == representationRole) {
            return PICKETS_TEMPLATE
        }
        return DEFAULT_TEMPLATE
    }
}
