package com.r3.mapping

import com.r3.Project
import com.r3.dataset.*
import com.r3.mapping.dataset.DataSetMap
import grails.events.Listener
import grails.plugin.cache.Cacheable

import javax.validation.constraints.NotNull

class DataMappingService {

    static transactional = false

    def grailsApplication

    def grailsCacheManager

    @Listener(namespace = 'gorm', topic='after*')
    def onDataEntryChanged(@NotNull final DataEntry dataEntry) {
        def dataSet = dataEntry.dataSet
        invalidateMappingCacheRecursively(dataSet.id, dataSet.title)
        def project = dataSet.project
        def bounds = thisProxied().getBounds(project)
        def leftBound = bounds.leftBound
        def rightBound = bounds.rightBound
        def pk = dataEntry.toPk()

        if (pk < leftBound) {
            updateBoundsCache(project, new Bounds(pk, dataEntry.id, bounds))
        } else if (pk > rightBound) {
            updateBoundsCache(project, new Bounds(bounds, pk, dataEntry.id))
        } else if ((dataEntry.id == bounds.leftBoundId && pk != leftBound)
                || (dataEntry.id == bounds.rightBoundId && pk != rightBound)) {
            invalidateBoundsCache(project)
        }
    }

    @Listener(namespace = 'gorm', topic='after*')
    def onIrregularPicketDataEntryChanged(@NotNull final IrregularPicketDataEntry dataEntry) {
        def project = dataEntry.dataSet.project
        invalidatePk2mmCache(project)
        onDataEntryChanged(dataEntry)
    }

    @Cacheable(value = 'mapping', key = '#ds.id')
    DataSetMap map(final DataSet ds) {
        return ds.map()
    }

    @Cacheable(value = 'bounds', key = '#project.id')
    Bounds getBounds(@NotNull final Project project) {
        return new Bounds(project)
    }

    @Cacheable(value = 'pk2mm', key = '#project.id')
    Pk2MmMap mapPk2mm(final Project project) {
        return new Pk2MmMap(project, thisProxied().getBounds(project))
    }

    private void invalidatePk2mmCache(final Project project) {
        grailsCacheManager.getCache('pk2mm').evict(project.id)
        invalidateAllMappingCache(project)
        log.debug("Invalidate pk2mm cache for project [name='${project.name}'; id=${project.id}]")
    }

    private void invalidateBoundsCache(final Project project) {
        grailsCacheManager.getCache('bounds').evict(project.id)
        invalidateAllMappingCache(project)
        log.debug("Invalidate bounds cache for project [name='${project.name}'; id=${project.id}]")
    }

    private Bounds updateBoundsCache(final Project project, final Bounds newBounds) {
        grailsCacheManager.getCache('bounds').put(project.id, newBounds)
        log.debug("Update bounds cache for project [name='${project.name}'; id=${project.id}]")
        invalidateAllMappingCache(project)
        return newBounds
    }

    private DataMappingService thisProxied() {
        return grailsApplication.mainContext.dataMappingService
    }

    private void invalidateAllMappingCache(final Project project) {
        project.dataSets.each { dataSet -> invalidateMappingCacheRecursively(dataSet.id, dataSet.title) }
    }

    private void invalidateMappingCacheRecursively(final Long dataSetId, final String title) {
        def cache = grailsCacheManager.getCache('mapping')
        DataSetMap dataSetMap = cache.get(dataSetId, DataSetMap)
        if (dataSetMap == null) {
            return
        }
        dataSetMap.observers.each { id, t ->
            invalidateMappingCacheRecursively(id, t)
        }
        cache.evict(dataSetId)
        log.debug("Invalidate mapping cache for dataset [title='$title', id=$dataSetId]")
    }
}
