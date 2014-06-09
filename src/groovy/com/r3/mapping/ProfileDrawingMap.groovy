package com.r3.mapping

import com.r3.dataset.*
import com.r3.drawing.layout.ProfileDrawing
import com.r3.mapping.dataset.DataSetMap
import grails.util.Pair
import org.apache.commons.logging.LogFactory

import javax.validation.constraints.NotNull

/**
 * User: graf
 * Date: 7/26/13
 * Time: 2:42 AM
 */
class ProfileDrawingMap implements DrawingMap {

    private static final LOGGER = LogFactory.getLog(this)

    private long yZeroPoint = Long.MAX_VALUE
    private long xZeroPoint
    private Pair<Integer, Integer> boundPickets

    private Pk2MmMap pk2mm
    private Bounds bounds
    private Map<Long, DataSetMap> dataSetMappings
    private long length

    public ProfileDrawingMap(@NotNull final ProfileDrawing drawing,
                             @NotNull final DataMappingService mappingService) {
        LOGGER.debug("Start drawing's model initialization ['$drawing.name, id=$drawing.id]")
        def totalProcessingTime = -System.currentTimeMillis()
        def processingTime = -System.currentTimeMillis()

        bounds = mappingService.getBounds(drawing.project)

        processingTime += System.currentTimeMillis()
        LOGGER.debug("Bounds initialization done in $processingTime msec")

        processingTime = -System.currentTimeMillis()
        pk2mm = mappingService.mapPk2mm(drawing.project)
        xZeroPoint = pk2mm[bounds.leftBound]
        length = pk2mm[bounds.rightBound] - xZeroPoint
        processingTime += System.currentTimeMillis()
        LOGGER.debug("Pk2mm initialization done in $processingTime msec")

        processingTime = -System.currentTimeMillis()
        HashSet<DataSet> allDataSets = collectDataSets(drawing)
        processingTime += System.currentTimeMillis()
        LOGGER.debug("Data sets collection done in $processingTime msec: [total=${allDataSets.size()}]")

        processingTime = -System.currentTimeMillis()
        dataSetMappings = map(allDataSets, mappingService)
        processingTime += System.currentTimeMillis()
        LOGGER.debug("Mapping initialization done in $processingTime msec")

        boundPickets = new Pair<Integer, Integer>(bounds.leftBound, bounds.rightBound)
        totalProcessingTime += System.currentTimeMillis()
        LOGGER.debug("Model initialization done in $totalProcessingTime msec ['$drawing.name', id=$drawing.id]")
    }

    private Map<Long, DataSetMap> map(final Set<DataSet> allDataSets, final DataMappingService mappingService) {
        Map<Long, DataSetMap> result = new HashMap(allDataSets.size())
        for (DataSet ds : allDataSets) {
            if (ds instanceof ProfileDrawingAware) {
                def processingTime = -System.currentTimeMillis()
                result[ds.id] = mapDataSet(mappingService, ds)
                processingTime += System.currentTimeMillis()
                LOGGER.debug("Data set mapping initialization done in $processingTime msec: [${ds.title}; id=${ds.id}]")
            }
        }
        return result
    }

    private HashSet<DataSet> collectDataSets(ProfileDrawing drawing) {
        def allDataSets = new HashSet<DataSet>()
        def recursiveCollector
        recursiveCollector = { row ->
            if (row.children) {
                row.children.each {
                    recursiveCollector(it)
                }
            } else if (row.visible && row.dataSets) {
                allDataSets.addAll(row.dataSets.dataSet)
            }
        }
        recursiveCollector(drawing.rootTableRow)
        allDataSets.addAll(drawing.rows.dataSet)

        def project = drawing.project
        allDataSets.addAll(IrregularPicketDataSet.findAllByProjectAndRailWay(project, project.mappingWay()))
        return allDataSets
    }

    private DataSetMap mapDataSet(DataMappingService mappingService, DataSet ds, boolean updateMinAlt = false) {
        def result = mappingService.map(ds)

        if (updateMinAlt && !result.isEmpty()) {
            def min = Collections.min(result.values, new DataItem.DataItemByValueComparator())?.value
            yZeroPoint = (min < yZeroPoint ? min : yZeroPoint) as Long
        }

        return result
    }

    private DataSetMap mapDataSet(DataMappingService mappingService, GroundLevelDataSet ds) {
        return mapDataSet(mappingService, ds, true)
    }

    private DataSetMap mapDataSet(DataMappingService mappingService, WayProfileDataSet ds) {
        return mapDataSet(mappingService, ds, true)
    }

    @Override
    Pk2MmMap getPk2mm() {
        return pk2mm
    }

    @Override
    DataSetMap dataSet(final Long id) {
        return dataSetMappings[id]
    }

    @Override
    long getLength() {
        return length
    }

    @Override
    Pair<Integer, Integer> getBoundPickets() {
        return boundPickets
    }

    @Override
    long getZeroY() {
        return yZeroPoint
    }

    @Override
    long getZeroX() {
        return xZeroPoint
    }
}
