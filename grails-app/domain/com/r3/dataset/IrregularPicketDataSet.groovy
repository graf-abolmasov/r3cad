package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole
import com.r3.mapping.dataset.IrregularPicketsDataSetMap

import javax.validation.constraints.NotNull

class IrregularPicketDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    private static final HashMap<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)
    private static final HashMap<RepresentationRole, String> CHART_TEMPLATES = new HashMap(1)

    private static final String DEFAULT_TEMPLATE = "picket_double"
    private static final String CHART_TEMPLATE = "irr_pickets"

    static {
        TABLE_TEMPLATES.put(null, DEFAULT_TEMPLATE)
        CHART_TEMPLATES.put(RepresentationRole.PROFILE_CHART_IRR_PICKETS, CHART_TEMPLATE)
    }

    static constraints = {
        railWay(nullable: false)
    }

    static hasMany = [values: IrregularPicketDataEntry]

    SortedSet<IrregularPicketDataEntry> values

    IrregularPicketDataSet(@NotNull Project project, @NotNull RailWay railWay, @NotNull String title) {
        super(project, railWay, title)
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
    IrregularPicketsDataSetMap map() {
        return new IrregularPicketsDataSetMap(this, dataMappingService)
    }

    @Override
    String getTemplate(RepresentationRole representationRole) {
        if (RepresentationRole.PROFILE_CHART_IRR_PICKETS == representationRole) {
            return CHART_TEMPLATE
        }
        return DEFAULT_TEMPLATE
    }
}

