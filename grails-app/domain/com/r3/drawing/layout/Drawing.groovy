package com.r3.drawing.layout

import com.r3.Project
import com.r3.drawing.model.DrawingModel
import com.r3.drawing.PageSizeFormat
import com.r3.drawing.area.PlainDrawArea
import com.r3.tree.TreeNodeAware
import grails.util.Pair

/**
 * User: graf
 * Date: 9/26/13
 * Time: 9:22 PM
 */
abstract class Drawing implements TreeNodeAware {

    def grailsLinkGenerator

    static hasMany = [rows: PlainDrawArea]

    Collection<PlainDrawArea> rows

    static belongsTo = [project: Project]

    static constraints = {
        pageFormatCoefficient(min: 1)
        name(blank: false, unique: 'project')
    }

    static embedded = ['stampData', 'margin']

    static transients = ['template', 'model', 'pageSize', 'pageHeight', 'pageWidth']

    static mapping = {
        project(fetch: 'join')
    }

    int pageFormatCoefficient = 1

    String name

    int pageNumber

    Margin margin = new Margin()

    StampData stampData = new StampData()

    PageSizeFormat pageFormat

    abstract String getTemplate()

    abstract <T extends DrawingModel> T getModel()

    Pair<Integer, Integer> getPageSize() {
        return pageFormat.multiply(pageFormatCoefficient)
    }
}

class Margin {
    int left = 20
    int top = 10
    int bottom = 5
    int right = 20

    static constraints = {
        left(min: 0)
        right(min: 0)
        top(min: 0)
        bottom(min: 0)
    }
}

class StampData {
    String designer
    String supervisor
    String mainSpecialist
    String departmentHead
    String gostInspector
    String chief
    Date designerDate
    Date supervisorDate
    Date mainSpecialistDate
    Date departmentHeadDate
    Date gostInspectorDate
    Date chiefDate

    static constraints = {
        designer(nullable: true)
        supervisor(nullable: true)
        mainSpecialist(nullable: true)
        departmentHead(nullable: true)
        gostInspector(nullable: true)
        chief(nullable: true)
        designerDate(nullable: true)
        supervisorDate(nullable: true)
        mainSpecialistDate(nullable: true)
        departmentHeadDate(nullable: true)
        gostInspectorDate(nullable: true)
        chiefDate(nullable: true)
    }
}