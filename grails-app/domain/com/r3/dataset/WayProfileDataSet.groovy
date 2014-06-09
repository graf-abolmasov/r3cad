package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole
import com.r3.mapping.dataset.WayProfileDataSetMap

import javax.validation.constraints.NotNull

class WayProfileDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(true, true)

    private static final String DEFAULT_TEMPLATE = "way_profile"
    private static final HashMap<RepresentationRole, String> CHART_TEMPLATES = new HashMap(1)

    static {
        CHART_TEMPLATES.put(null, DEFAULT_TEMPLATE)
    }

    boolean aggregateRailHeadDataSet
    boolean aggregateTurnoutDataSet
    boolean aggregateBridgeDataSet

    WayProfileDataSet(@NotNull final Project project,
                      @NotNull final RailWay railWay,
                      @NotNull final String title,
                      boolean aggregateRailHeadDataSet,
                      boolean aggregateBridgeDataSet,
                      boolean aggregateTurnoutDataSet) {
        super(project, railWay, title)
        this.aggregateRailHeadDataSet = aggregateRailHeadDataSet
        this.aggregateBridgeDataSet = aggregateBridgeDataSet
        this.aggregateTurnoutDataSet = aggregateTurnoutDataSet
    }

    @Override
    SortedSet<DataEntry> getValues() {
        return Collections.emptySortedSet()
    }

    @Override
    Map<RepresentationRole, String> getChartTemplates() {
        return CHART_TEMPLATES
    }

    @Override
    Map<RepresentationRole, String> getTableTemplates() {
        return Collections.emptyMap()
    }

    @Override
    WayProfileDataSetMap map() {
        return new WayProfileDataSetMap(this, dataMappingService)
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        return DEFAULT_TEMPLATE
    }
}
