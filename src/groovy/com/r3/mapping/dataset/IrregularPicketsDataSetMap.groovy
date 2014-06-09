package com.r3.mapping.dataset

import com.r3.dataset.IrregularPicketDataSet
import com.r3.mapping.DataMappingService

import javax.validation.constraints.NotNull

class IrregularPicketsDataSetMap extends DataSetMap {

    IrregularPicketsDataSetMap(@NotNull IrregularPicketDataSet dataSet,
                               @NotNull DataMappingService mappingService) {
        super(dataSet, mappingService)
        dataSet.project.dataSets.each { addObserver(it.id, it.title) }
    }
}
