package com.r3.mapping.dataset

import com.r3.dataset.*
import com.r3.mapping.DataItem
import com.r3.mapping.DataMappingService
import com.r3.mapping.Pk2MmMap

import javax.validation.constraints.NotNull

/**
 * Created by graf on 25/06/14.
 */
class GradientDataSetMap extends DataSetMap {

    GradientDataSetMap(@NotNull final GradientDataSet dataSet,
                       @NotNull final DataMappingService mappingService) {
        super(dataSet, mappingService)
    }

    protected List<DataItem> map(GradientDataSet dataSet) {
        def railHeadDataSet = RailHeadDataSet.findByRailWay(dataSet.railWay, [cache: true])
        if (railHeadDataSet == null) {
            return Collections.emptyList()
        }
        subscribeTo(railHeadDataSet)

        def railHeads = railHeadDataSet.values
        if (railHeads == null || railHeads.size() < 2) {
            return Collections.emptyList()
        }

        final Map<Integer, Double> irregularPickets = [:]
        def irregularPicketDataEntries = IrregularPicketDataSet.findByRailWay(dataSet.railWay, [cache: true])?.values
        irregularPicketDataEntries?.each { ip ->
            irregularPickets[ip.toPk()] = ip.doubleValue
        }

        final List<DataItem> result = new ArrayList<DataItem>(railHeads.size())
        final Iterator<RailHeadDataEntry> it = railHeads.iterator()
        final Pk2MmMap pk2mm = mappingService.mapPk2mm(dataSet.project)

        RailHeadDataEntry prevPicket = it.next()
        while (it.hasNext()) {
            RailHeadDataEntry currPicket = it.next()

            if (currPicket.plus > 0.0) {
                continue
            }

            def length = irregularPickets[prevPicket.toPk()] ?: (Location.PK_IN_KM * 1.0)
            def gradient = -1.0 * Math.round((currPicket.doubleValue - prevPicket.doubleValue) / length * 1000.0d) / 10.0
            result.add(new DataItem(pk2mm[prevPicket], gradient)) //simple round to 0.0
            prevPicket = currPicket
        }
        result.add(new DataItem(pk2mm[prevPicket], 999.0d));

        return Collections.unmodifiableList(result)
    }
}
