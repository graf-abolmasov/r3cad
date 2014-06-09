package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.drawing.area.StackedArea2DataSet
import com.r3.mapping.dataset.DataSetMap
import com.r3.tree.DataSetTreeNode

import javax.validation.constraints.NotNull

abstract class BaseDataSet implements DataSet {

    def grailsLinkGenerator

    def dataMappingService

    static belongsTo = [project: Project, railWay: RailWay]

    static constraints = {
        title(blank: false, maxSize: 128)
        railWay(nullable: true)
    }

    static hasMany = [tableRows: StackedArea2DataSet]

    static mapping = {
        sort("title")
        tableRows(batchSize: 10)
    }

    BaseDataSet(@NotNull final Project project,
                @NotNull final String title) {
        this.title = title
        this.project = project
        project.addToDataSets(this)
    }

    BaseDataSet(@NotNull final Project project,
                @NotNull final RailWay railWay,
                @NotNull final String title) {
        this(project, title)
        this.railWay = railWay
        railWay.addToDataSets(this)
    }

    Project project
    RailWay railWay
    Collection<StackedArea2DataSet> tableRows

    String title

    DataSetTreeNode asTreeNode(@NotNull final String parentId, boolean visible, boolean clarifyRailsWay) {
        return new DataSetTreeNode(this, parentId, grailsLinkGenerator, visible, clarifyRailsWay)
    }

    DataSetTreeNode asTreeNode(@NotNull final String parentId) {
        return new DataSetTreeNode(this, parentId, grailsLinkGenerator)
    }

    @Override
    DataSetMap map() {
        return new DataSetMap(this, dataMappingService)
    }
}

class KmPkPlus extends Location {

    static constraints = {
        km(min: 0)
        pk(min: 0, max: 9)
        plus(min: 0)
    }

    KmPkPlus() {
        super()
    }

    KmPkPlus(int km, int pk, int plus) {
        super(km, pk, plus)
    }

    void setKm(int km) {
        super.km = km
    }

    void setPk(int pk) {
        super.pk = pk
    }

    void setPlus(int plus) {
        super.plus = plus
    }

    Location toLocation() {
        return new Location(km, pk, plus)
    }
}