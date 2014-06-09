package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.RepresentationRole

import javax.validation.constraints.NotNull

class BallastDataSet extends BaseDataSet implements ProfileDrawingAware {

    final DataSetMetaInfo metaInfo = new DataSetMetaInfo(false, true)

    private static final String DEFAULT_TEMPLATE = "ballast"
    private static final Map<RepresentationRole, String> TABLE_TEMPLATES = new HashMap(1)

    int countLayers = 1
    String name0
    String name1
    String name2
    String name3
    String name4
    String name5
    String name6
    String name7
    String name8
    String name9

    static {
        TABLE_TEMPLATES.put(null, DEFAULT_TEMPLATE)
    }

    static constraints = {
        railWay(nullable: false)
        countLayers(min: 1, max: 10)
        name0(nullable: true)
        name1(nullable: true)
        name2(nullable: true)
        name3(nullable: true)
        name4(nullable: true)
        name5(nullable: true)
        name6(nullable: true)
        name7(nullable: true)
        name8(nullable: true)
        name9(nullable: true)
    }

    static hasMany = [values: BallastDataEntry]

    SortedSet<BallastDataEntry> values

    BallastDataSet(@NotNull final Project project,
                   @NotNull final RailWay railWay,
                   @NotNull final String title) {
        super(project, railWay, title)
        this.countLayers = 1
        this.name0 = 'Слой 1'
        this.name1 = 'Слой 2'
        this.name2 = 'Слой 3'
        this.name3 = 'Слой 4'
        this.name4 = 'Слой 5'
        this.name5 = 'Слой 6'
        this.name6 = 'Слой 7'
        this.name7 = 'Слой 8'
        this.name8 = 'Слой 9'
        this.name9 = 'Слой 10'
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
