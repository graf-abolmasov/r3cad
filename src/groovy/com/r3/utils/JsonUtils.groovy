package com.r3.utils

import com.r3.Project
import com.r3.RailWay
import com.r3.dataset.DataEntry
import com.r3.dataset.DataSet
import com.r3.dataset.DistanceToWay
import com.r3.dataset.Location
import com.r3.dataset.bridge.BridgePier
import com.r3.dataset.bridge.BridgeSpan
import com.r3.dataset.pillar.WireHeight
import com.r3.drawing.area.StackedDrawArea
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.TextStyle
import com.r3.tree.TreeNode
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * User: graf
 * Date: 9/1/13
 * Time: 11:20 PM
 */
final class JsonUtils {

    private static void registerCustomMarshaller(Class clazz) {
        JSON.registerObjectMarshaller(clazz) { o ->
            return simpleMarshaller(o)
        }
    }

    static void registerCustomMarshallers() {
        registerCustomMarshaller(DataSet)
        registerCustomMarshaller(StackedDrawArea)
        registerCustomMarshaller(RailWay)
        registerCustomMarshaller(DataEntry)
        registerCustomMarshaller(Project)
        registerCustomMarshaller(BridgeSpan)
        registerCustomMarshaller(BridgePier)
        registerCustomMarshaller(DistanceToWay)
        registerCustomMarshaller(WireHeight)
        registerCustomMarshaller(TreeNode)
    }

    static Map unwrapNulls(Map jsonObject) {
        def result = jsonObject?.collectEntries { k, v ->
            JSONObject.NULL.equals(v) ? [k, null] : [k, v]
        }
        return result ?: Collections.emptyMap()
    }

    static LinkedHashMap simpleMarshaller(final TreeNode treeNode) {
        def result = [:]
        result['identity'] = treeNode.identity
        result['parentId'] = treeNode.parentId
        result['iconClass'] = treeNode.iconClass
        result['label'] = treeNode.label
        result['editorUrl'] = treeNode.editorUrl
        result['leaf'] = treeNode.leaf
        result['visible'] = treeNode.visible
        return result
    }

    static LinkedHashMap simpleMarshaller(final StackedDrawArea tr) {
        def result = [:]
        result['class'] = StackedDrawArea.name
        if (tr.id) {
            result['id'] = tr.id
        }
        result['height'] = tr.height
        result['orderNumber'] = tr.orderNumber
        result['visible'] = tr.visible
        result['label'] = tr.label
        result['version'] = tr.version
        if (tr.lineStyleId) {
            result['lineStyle'] = [:]
            result['lineStyle']['class'] = LineStyle.name
            result['lineStyle']['id'] = tr.lineStyleId
        }

        if (tr.textStyleId) {
            result['textStyle'] = [:]
            result['textStyle']['class'] = TextStyle.name
            result['textStyle']['id'] = tr.textStyleId
        }
        if (tr.hasErrors()) {
            result['errors'] = tr.errors
        }
        return result
    }

    static LinkedHashMap simpleMarshaller(final RailWay rw) {
        def result = [:]
        result['class'] = RailWay.name
        if (rw.id) {
            result['id'] = rw.id
            result['version'] = rw.version
        }
        result['number'] = rw.number
        result['underRepair'] = rw.underRepair
        result['label'] = rw.label
        if (rw.hasErrors()) {
            result['errors'] = rw.errors
        }
        return result
    }

    static LinkedHashMap simpleMarshaller(final Project p) {
        def result = [:]
        result['class'] = Project.name
        result['name'] = p.name
        if (p.id) {
            result['id'] = p.id
        }
        if (p.mapByWayId) {
            result['mapByWay'] = [:]
            result['mapByWay']['class'] = RailWay.name
            result['mapByWay']['id'] = p.mapByWayId
        }
        result['version'] = p.version
        if (p.hasErrors()) {
            result['errors'] = p.errors
        }
        return result
    }

    static LinkedHashMap simpleMarshaller(final DataEntry de) {
        def result = [:]
        result['class'] = de.class.name
        if (de.id) {
            result['id'] = de.id
        }
        result['location_as_is'] = [km: de.km, pk: de.pk, plus: de.plus]
        result['location'] = 'struct'
        result['location_km'] = de.km
        result['location_pk'] = de.pk
        result['location_plus'] = 1.0 * de.plus / Location.MM_IN_METER
        result['version'] = de.version
        if (de.hasErrors()) {
            result['errors'] = de.errors
        }
        result << de.getValuesMap()
        return result
    }

    static LinkedHashMap simpleMarshaller(final DataSet ds) {
        def result = [:]
        result['class'] = ds.class.name
        if (ds.id) {
            result['id'] = ds.id
        }
        result['title'] = ds.title
        result['version'] = ds.version
        result['isBelongToForever'] = ds.metaInfo.belongsToForever

        if (ds.metaInfo.belongsToForever) {
            result['belongsTo'] = ds.railWay?.label ?: 'Проекту'
        }

        if (ds.railWayId) {
            result['railWay'] = ds.railWayId
        }

        if (ds.hasErrors()) {
            result['errors'] = ds.errors
        }
        return result
    }

    static HashMap simpleMarshaller(final DistanceToWay d2w) {
        def result = new HashMap()
        result['class'] = DistanceToWay.name
        if (d2w.id) {
            result['id'] = d2w.id
        }
        result['railWayId'] = d2w.railWayId
        result['distance'] = d2w.distance
        return result
    }

    static HashMap simpleMarshaller(final WireHeight wh) {
        def result = new HashMap()
        result['class'] = WireHeight.name
        if (wh.id) {
            result['id'] = wh.id
        }
        if (wh.hasErrors()) {
            result['errors'] = wh.errors
        }
        result['railWay'] = wh.railWayId
        result['height'] = wh.height
        return result
    }

    static HashMap simpleMarshaller(final BridgeSpan bs) {
        def result = new HashMap()
        result['class'] = BridgeSpan.name
        if (bs.id) {
            result['id'] = bs.id
        }
        if (bs.hasErrors()) {
            result['errors'] = bs.errors
        }
        result['spansType'] = bs.spansType.name()
        result['length'] = bs.length ? 1.0 * bs.length / Location.MM_IN_METER : 0
        result['movementType'] = bs.movementType.name()
        result['lumenLength'] = bs.lumenLength ? 1.0 * bs.lumenLength / Location.MM_IN_METER : 0
        result['ballast'] = bs.ballast ? 1.0 * bs.ballast / Location.MM_IN_CM : 0
        result['measurementBeginLeft'] = bs.measurementBeginLeft
        result['measurementBeginRight'] = bs.measurementBeginRight
        result['measurementAxisLeft'] = bs.measurementAxisLeft
        result['measurementAxisRight'] = bs.measurementAxisRight
        result['measurementEndLeft'] = bs.measurementEndLeft
        result['measurementEndRight'] = bs.measurementEndRight
        return result;
    }

    static HashMap simpleMarshaller(final BridgePier bp) {
        def result = new HashMap()
        result['class'] = BridgeSpan.name
        if (bp.id) {
            result['id'] = bp.id
        }
        if (bp.hasErrors()) {
            result['errors'] = bp.errors
        }
        result['km'] = bp.km
        result['pk'] = bp.pk
        result['plus'] = (1.0 * bp.plus / Location.MM_IN_METER)
        return result;
    }
}
