package com.r3.drawing.layout

import com.r3.Project
import com.r3.RailWay
import com.r3.dataset.DataSet
import com.r3.dataset.InterWaySpaceDataSet
import com.r3.dataset.ProfileDrawingAware
import com.r3.drawing.PageSizeFormat
import com.r3.drawing.area.PlainDrawArea
import com.r3.drawing.area.RepresentationRole
import com.r3.drawing.area.StackedArea2DataSet
import com.r3.drawing.area.StackedDrawArea
import com.r3.drawing.layout.Margin
import com.r3.drawing.layout.ProfileDrawing
import com.r3.drawing.layout.StampData

import javax.validation.constraints.NotNull

@Deprecated
class DefaultProfileDrawing {
    @Deprecated
    static ProfileDrawing createDefault(@NotNull final Project project) {
        def rootTableRow = new StackedDrawArea(label: 'Корневая линейка таблицы чертежа продольного профиля')

        def resultDrawing = new ProfileDrawing(
                project: project,
                name: 'Продольный профиль пути',
                rootTableRow: rootTableRow,
                pageNumber: 1)
        rootTableRow.drawing = resultDrawing

        def defaultProjectsRowsMap = buildDefaultProjectRows(rootTableRow)
        processProjectDataSets(project.dataSets, rootTableRow, defaultProjectsRowsMap, resultDrawing)

        for (RailWay railWay : project.railWays) {
            def railWayTableRow = new StackedDrawArea(
                    orderNumber: railWay.number * 10,
                    label: railWay.label,
                    drawing: resultDrawing,
                    parent: rootTableRow,
                    height: 15)
            rootTableRow.addToChildren(railWayTableRow)
            def defaultRailWayRowsMap = buildDefaultRailWayRows(railWayTableRow)
            defaultRailWayRowsMap.putAll(defaultProjectsRowsMap)
            processRailWayDataSet(railWay.dataSets, railWayTableRow, defaultRailWayRowsMap, resultDrawing)
        }

        return resultDrawing
    }

    private static Map<RepresentationRole, List<StackedDrawArea>> buildDefaultProjectRows(StackedDrawArea rootTableRow) {
        HashMap<RepresentationRole, List<StackedDrawArea>> result = new HashMap()

        result[RepresentationRole.PROFILE_TABLE_CURVES_AND_LINES_IN_PLAN] = [new StackedDrawArea(
                parent: rootTableRow,
                drawing: rootTableRow.drawing,
                label: 'Прямые и кривые в плане пути',
                height: 30,
                orderNumber: Integer.MAX_VALUE - 1,
                representationRole: RepresentationRole.PROFILE_TABLE_CURVES_AND_LINES_IN_PLAN
        )]

        result[RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN] = [new StackedDrawArea(
                parent: rootTableRow,
                drawing: rootTableRow.drawing,
                label: 'Развернутый план пути',
                height: 25,
                orderNumber: -1,
                representationRole: RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN
        )]

        result[RepresentationRole.PROFILE_TABLE_GROUND_LEVEL] = [new StackedDrawArea(
                parent: rootTableRow,
                drawing: rootTableRow.drawing,
                label: 'Отметка земли',
                height: 10,
                orderNumber: Integer.MAX_VALUE - 3,
                representationRole: RepresentationRole.PROFILE_TABLE_GROUND_LEVEL
        )]

        result[RepresentationRole.PROFILE_TABLE_PICKETS] = [new StackedDrawArea(
                parent: rootTableRow,
                drawing: rootTableRow.drawing,
                label: 'Пикет',
                height: 5,
                orderNumber: -2,
                representationRole: RepresentationRole.PROFILE_TABLE_PICKETS
        ), new StackedDrawArea(
                parent: rootTableRow,
                drawing: rootTableRow.drawing,
                label: 'Пикет',
                height: 5,
                orderNumber: Integer.MAX_VALUE - 2,
                representationRole: RepresentationRole.PROFILE_TABLE_PICKETS
        )]

        result.each { list ->
            list.value.each { area ->
                rootTableRow.addToChildren(area)
            }
        }

        return result
    }

    private static Map<RepresentationRole, List<StackedDrawArea>> buildDefaultRailWayRows(StackedDrawArea railWayTableRow) {
        Map<RepresentationRole, List<StackedDrawArea>> result = new HashMap()
        result[RepresentationRole.PROFILE_TABLE_GRADIENT] = [new StackedDrawArea(
                height: 5,
                label: 'Уклон',
                parent: railWayTableRow,
                drawing: railWayTableRow.drawing,
                representationRole: RepresentationRole.PROFILE_TABLE_GRADIENT,
                visible: true,
                orderNumber: 1
        )]
        result.each { list ->
            list.value.each { area ->
                railWayTableRow.addToChildren(area)
            }
        }

        return result
    }

    private static void processRailWayDataSet(final Collection<DataSet> dataSets,
                                              final StackedDrawArea rootTableRow,
                                              final Map<RepresentationRole, List<StackedDrawArea>> defaultTableRowMap,
                                              final ProfileDrawing resultDrawing) {
        for (DataSet dataSet in dataSets) {
            if (dataSet instanceof ProfileDrawingAware) {
                processDataSet(dataSet, rootTableRow, defaultTableRowMap, resultDrawing)
            }
        }
    }

    private static void processProjectDataSets(Collection<DataSet> dataSets, StackedDrawArea rootTableRow, Map<RepresentationRole, List<StackedDrawArea>> defaultTableRowMap, ProfileDrawing resultDrawing) {
        for (DataSet dataSet in dataSets) {
            if (dataSet.railWay) {
                //SKIP THIS SHIT
            } else if (dataSet instanceof InterWaySpaceDataSet) {
                def tableRow = new StackedDrawArea(
                        height: 10,
                        label: dataSet.title,
                        parent: rootTableRow,
                        drawing: rootTableRow.drawing,
                        orderNumber: dataSet.firstRailWay.number * 10 + 1,
                        visible: true)
                rootTableRow.addToChildren(tableRow)
                def stackedArea2DataSet = new StackedArea2DataSet(
                        dataSet: dataSet,
                        tableRow: tableRow,
                        visible: true
                )
                dataSet.addToTableRows(stackedArea2DataSet)
                tableRow.addToDataSets(stackedArea2DataSet)
            } else if (dataSet instanceof ProfileDrawingAware) {
                processDataSet(dataSet, rootTableRow, defaultTableRowMap, resultDrawing)
            }
        }
    }

    private static void processDataSet(final DataSet dataSet,
                                       final StackedDrawArea rootTableRow,
                                       final Map<RepresentationRole, List<StackedDrawArea>> defaultTableRowMap,
                                       final ProfileDrawing resultDrawing) {
        def tableTemplatesMap = dataSet.getTableTemplates()
        tableTemplatesMap.each { template ->
            def dataSetTableRowList
            if (defaultTableRowMap.containsKey(template.key)) {
                dataSetTableRowList = defaultTableRowMap[template.key]
            } else {
                dataSetTableRowList = [new StackedDrawArea(
                        height: 10,
                        label: dataSet.title,
                        parent: rootTableRow,
                        drawing: rootTableRow.drawing,
                        representationRole: template.key,
                        visible: true)]
                dataSetTableRowList.each {
                    rootTableRow.addToChildren(it)
                }
                if (template.key) {
                    defaultTableRowMap.put(template.key, dataSetTableRowList)
                }
            }

            dataSetTableRowList.each { tableRow ->
                def dataSet2Area = new StackedArea2DataSet(
                        dataSet: dataSet,
                        tableRow: tableRow)
                dataSet.addToTableRows(dataSet2Area)
                tableRow.addToDataSets(dataSet2Area)
            }
        }

        def chartTemplatesMap = dataSet.getChartTemplates()
        chartTemplatesMap.each {
            def chart = new PlainDrawArea(
                    drawing: resultDrawing,
                    dataSet: dataSet,
                    label: dataSet.title,
                    representationRole: it.key
            )
            resultDrawing.addToRows(chart)
        }
    }
}
