package com.r3.mapping.dataset

import com.r3.dataset.RailHeadDataSet
import com.r3.dataset.TurnoutDataEntryReadOnly
import com.r3.dataset.TurnoutDataSet
import com.r3.dataset.WayProfileDataSet
import com.r3.dataset.bridge.BridgeDataEntryReadOnly
import com.r3.dataset.bridge.BridgeDataSet
import com.r3.mapping.DataItem
import com.r3.mapping.DataMappingService
import com.r3.mapping.Pk2MmMap

import javax.validation.constraints.NotNull

class WayProfileDataSetMap extends DataSetMap {

    WayProfileDataSetMap(@NotNull final WayProfileDataSet dataSet,
                         @NotNull final DataMappingService mappingService) {
        super(dataSet, mappingService)
    }

    protected List<DataItem> map(WayProfileDataSet dataSet) {
        final Pk2MmMap pk2mm = mappingService.mapPk2mm(dataSet.project)

        final List<DataItem> list = new ArrayList<>()
        if (dataSet.aggregateRailHeadDataSet) {
            def railHeadAltsDataSet = RailHeadDataSet.findByRailWay(dataSet.railWay, [cache: true])
            if (railHeadAltsDataSet) {
                list.addAll(mappingService.map(railHeadAltsDataSet).values)
                subscribeTo(railHeadAltsDataSet)
            }
        }

        if (dataSet.aggregateBridgeDataSet) {
            def bridgeDataSet = BridgeDataSet.findByRailWay(dataSet.railWay, [cache: true])
            if (bridgeDataSet) {
                def bridgeValues = mappingService.map(bridgeDataSet).values
                for (DataItem di in bridgeValues) {
                    final bridge = (BridgeDataEntryReadOnly) di.value
                    if (bridge.beginRailHeadValue > 0) {
                        list.add(new DataItem(di.x, bridge.beginRailHeadValue))
                    }
                    if (bridge.axisLocation && bridge.axisRailHeadValue > 0) {
                        list.add(new DataItem(pk2mm[bridge.axisLocation], bridge.axisRailHeadValue))
                    }
                    if (bridge.endLocation && bridge.endRailHeadValue > 0) {
                        list.add(new DataItem(pk2mm[bridge.endLocation], bridge.endRailHeadValue))
                    }
                }
                subscribeTo(bridgeDataSet)
            }
        }

        if (dataSet.aggregateTurnoutDataSet) {
            def turnoutDataSet = TurnoutDataSet.findByRailWay(dataSet.railWay, [cache: true])
            if (turnoutDataSet) {
                def turnoutValues = mappingService.map(turnoutDataSet).values
                for (DataItem di in turnoutValues) {
                    final turnout = (TurnoutDataEntryReadOnly) di.value
                    if (turnout.adjacentLinkLocation && turnout.adjacentLinkRailHeadValue > 0) {
                        list.add(new DataItem(pk2mm[turnout.adjacentLinkLocation], turnout.adjacentLinkRailHeadValue))
                    }
                    if (turnout.stockRailJointLocation && turnout.stockRailJointRailHeadValue > 0) {
                        list.add(new DataItem(pk2mm[turnout.stockRailJointLocation], turnout.stockRailJointRailHeadValue))
                    }
                    if (turnout.tongueLocation && turnout.tongueRailHeadValue > 0) {
                        list.add(new DataItem(pk2mm[turnout.tongueLocation], turnout.tongueRailHeadValue))
                    }
                    if (turnout.tailLocation && turnout.tailRailHeadValue > 0) {
                        list.add(new DataItem(pk2mm[turnout.tailLocation], turnout.tailRailHeadValue))
                    }
                    if (turnout.afterCrossLinkLocation && turnout.afterCrossLinkRailHeadValue > 0) {
                        list.add(new DataItem(pk2mm[turnout.afterCrossLinkLocation], turnout.afterCrossLinkRailHeadValue))
                    }
                    if (turnout.stockRailJointLocationSecond && turnout.stockRailJointRailHeadValueSecond > 0) {
                        list.add(new DataItem(pk2mm[turnout.stockRailJointLocationSecond], turnout.stockRailJointRailHeadValueSecond))
                    }
                    if (turnout.tongueLocationSecond && turnout.tongueRailHeadValueSecond > 0) {
                        list.add(new DataItem(pk2mm[turnout.tongueLocationSecond], turnout.tongueRailHeadValueSecond))
                    }
                    if (turnout.tailLocationSecond && turnout.tailRailHeadValueSecond > 0) {
                        list.add(new DataItem(pk2mm[turnout.tailLocationSecond], turnout.tailRailHeadValueSecond))
                    }
                    if (turnout.afterCrossLinkLocationSecond && turnout.afterCrossLinkRailHeadValueSecond > 0) {
                        list.add(new DataItem(pk2mm[turnout.afterCrossLinkLocationSecond], turnout.afterCrossLinkRailHeadValueSecond))
                    }
                }
                subscribeTo(turnoutDataSet)
            }
        }

        Collections.sort(list, new DataItem.DataItemByMmComparator())
        return list
    }
}
