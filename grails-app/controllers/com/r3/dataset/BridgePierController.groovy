package com.r3.dataset

import com.r3.dataset.bridge.BridgeDataEntry
import com.r3.dataset.bridge.BridgePier
import com.r3.utils.JsonUtils
import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException

/**
 * Чисто REST контроллер, не поддерживает ни одной страницы HTML
 */
@Transactional(readOnly = true)
class BridgePierController {

    static defaultAction = "list"

    public static final LinkedHashMap<String, ArrayList<String>> EXCLUDE_FIELDS = [exclude: ['id', 'pillar', 'version', 'errors', 'class']]

    static allowedMethods = [
            list: 'GET',
            show: 'GET',
            save: 'POST',
            update: 'PUT',
            delete: 'DELETE'
    ]

    def list(Long bid) {
        def dataEntry = BridgeDataEntry.get(bid)
        if (!dataEntry) {
            response.sendError(404)
            return
        }

        def list = dataEntry.getPiersValues()
        if (!list) {
            response.status = 204
        }
        response.addHeader('Content-Range:', "items 0-${list.size() - 1}/${list.size()}")
        render list as JSON
    }

    @Transactional
    def save(Long bid) {
        def dataEntry = BridgeDataEntry.get(bid)
        if (!dataEntry) {
            response.sendError(404)
            return
        }

        def piersValues = new BridgePier()
        dataEntry.addToPiersValues(piersValues)
        dataEntry.save(flush: true)

        response.status = 201
        render piersValues as JSON
    }

    def show(Long id) {
        withPiersValues(id) { piersValues ->
            render piersValues as JSON
        }
    }

    @Transactional
    def update(Long id) {
        withPiersValues(id) { piersValues ->
            def version = populate(piersValues)

            if (version != null) {
                if (piersValues.version > version) {
                    piersValues.errors.rejectValue("version", "default.optimistic.locking.failure")
                    response.status = 409
                    render piersValues as JSON
                    piersValues.discard()
                    return
                }
            }

            if (!piersValues.save()) {
                response.status = 400
                render piersValues as JSON
                return
            }

            render piersValues as JSON
        }
    }

    @Transactional
    def delete(Long id) {
        withPiersValues(id) { piersValues ->
            try {
                piersValues.delete()
                response.status = 204
                render 'ok'
            } catch (DataIntegrityViolationException ignored) {
                response.sendError(500)
            }
        }
    }

    private def withPiersValues(Long id, Closure c) {
        def piersValuesInstance = BridgePier.get(id)
        if (piersValuesInstance) {
            c.call piersValuesInstance
        } else {
            response.sendError(404)
        }
    }

    private Long populate(BridgePier piersValues) {
        def object = JsonUtils.unwrapNulls(request.JSON)
        if (!object) {
            throw new IllegalArgumentException('There is nothing to bind to the instance')
        }
        bindData(piersValues, object, EXCLUDE_FIELDS)
        return object['version']?.toLong()
    }
}
