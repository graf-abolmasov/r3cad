package com.r3

import com.r3.dataset.BaseDataSet
import com.r3.drawing.layout.Drawing
import com.r3.tree.ProjectTreeNode
import com.r3.tree.TreeNodeAware

import javax.validation.constraints.NotNull

class Project implements TreeNodeAware {

    def grailsLinkGenerator

    static hasMany = [
            users: User,
            railWays: RailWay,
            dataSets: BaseDataSet,
            drawings: Drawing
    ]

    static constraints = {
        name(nullable: false, blank: false)
        mapByWay(nullable: true)
    }

    static belongsTo = User

    static hasOne = [organization: Organization]

    static mapping = {
        sort name: 'asc'
        dataSets sort: 'title', batchSize: 10
        drawings sort: 'pageNumber', batchSize: 3
    }


    String name

    RailWay mapByWay;

    Collection<User> users

    Collection<BaseDataSet> dataSets

    Collection<Drawing> drawings

    SortedSet<RailWay> railWays

    RailWay mappingWay() {
        mapByWay ?: railWays?.find { it.underRepair } ?: railWays ? railWays.first() : null
    }

    @Override
    ProjectTreeNode asTreeNode(@NotNull final String parentId) {
        return new ProjectTreeNode(this, grailsLinkGenerator)
    }
}
