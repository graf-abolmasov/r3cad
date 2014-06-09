package com.r3

import com.r3.dataset.*
import com.r3.tree.RailWayTreeNode
import com.r3.tree.TreeNodeAware

import javax.validation.constraints.NotNull

class RailWay implements Comparable, TreeNodeAware {

    def grailsLinkGenerator

    static hasMany = [dataSets: BaseDataSet]

    static transients = ['rightRailWay', 'leftRailWay', 'leftInterWaySpace', 'rightInterWaySpace']

    static belongsTo = [project: Project]

    static constraints = {
        number(unique: 'project', min: 1)
        label(unique: 'project', blank: false)
    }

    static mapping = {
        sort number: 'asc'
        dataSets sort: 'title', batchSize: 10
    }

    static mappedBy = {
        dataSets: 'railWay'
    }

    RailWay(@NotNull final Project project, @NotNull final String label, int number, boolean underRepair = false) {
        this.number = number
        this.label = label
        this.underRepair = underRepair
        this.project = project
        project.addToRailWays(this)
    }

    int number
    boolean underRepair
    String label

    Collection<BaseDataSet> dataSets

    InterWaySpaceDataSet getLeftInterWaySpace() {
        return InterWaySpaceDataSet.findBySecondRailWay(this, [cache: true])
    }

    InterWaySpaceDataSet getRightInterWaySpace() {
        return InterWaySpaceDataSet.findByFirstRailWay(this, [cache: true])
    }

    RailWay getLeftRailWay() {
        leftInterWaySpace?.firstRailWay
    }

    RailWay getRightRailWay() {
        rightInterWaySpace?.secondRailWay
    }

    @Override
    int compareTo(Object o) {
        if (!(o instanceof RailWay)) {
            throw new IllegalArgumentException("Compare only RailWay objects")
        }

        final RailWay otherLine = o as RailWay
        return number - otherLine.number
    }

    @Override
    RailWayTreeNode asTreeNode(@NotNull final String parentId) {
        return new RailWayTreeNode(this, grailsLinkGenerator)
    }
}
