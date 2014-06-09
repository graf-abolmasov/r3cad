package com.r3.drawing.area

import com.r3.drawing.layout.Drawing
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.TextStyle
import com.r3.tree.StackedDrawAreaTreeNode
import com.r3.tree.TreeNodeAware

import javax.validation.constraints.NotNull

class StackedDrawArea implements Comparable, TreeNodeAware {

    def grailsLinkGenerator

    static constraints = {
        parent(nullable: true)
        height(min: 0)
        label(nullable: false, blank: false, maxSize: 64)
        lineStyle(nullable: true)
        textStyle(nullable: true)
        representationRole(nullable: true)
        drawing(nullable: true)
    }

    static mapping = {
        cache(true)
        sort(orderNumber: 'asc')
        dataSets(batchSize: 10, cache: true)
        children(batchSize: 10, cache: true)
        parent(fetch: 'join')
    }

    static transients = ['fullHeight', 'fullPath']

    static belongsTo = [parent: StackedDrawArea]

    static hasMany = [dataSets: StackedArea2DataSet, children: StackedDrawArea]

    Drawing drawing

    SortedSet<StackedDrawArea> children

    Collection<StackedArea2DataSet> dataSets

    int height
    int orderNumber
    boolean visible = true

    String label
    RepresentationRole representationRole

    LineStyle lineStyle
    TextStyle textStyle

    @Override
    int compareTo(Object o) {
        if (!(o instanceof StackedDrawArea)) {
            throw new IllegalArgumentException("Compare only StackedDrawArea objects")
        }

        def other = (StackedDrawArea) o

        assert parent == other.parent

        def diff = orderNumber - other.orderNumber
        if (diff != 0) {
            return diff
        }

        return label.compareTo(other.label)
    }

    String getFullPath() {
        def rows = new LinkedList<StackedDrawArea>()
        def recursiveCollector
        recursiveCollector = { row ->
            if (row.parent) {
                recursiveCollector(row.parent)
                rows.add(row)
            }
        }
        recursiveCollector(this)
        if (rows.isEmpty()) {
            return label
        }
        def result = new StringBuilder()
        def it = rows.iterator()
        result.append(it.next().label)
        while (it.hasNext()) {
            result.append('->').append(it.next().label)
        }
        return result.toString()
    }

    int getFullHeight() {
        if (!visible) {
            return 0
        }
        if (!children) {
            return height
        }
        int totalHeight = 0
        for (StackedDrawArea childRow in children) {
            totalHeight += childRow.getFullHeight()
        }
        return Math.max(height, totalHeight)
    }

    @Override
    StackedDrawAreaTreeNode asTreeNode(@NotNull final String parentId) {
        return new StackedDrawAreaTreeNode(this, parentId, grailsLinkGenerator)
    }
}


