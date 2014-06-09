package com.r3.dataset

import com.r3.dataset.bridge.BridgeDataEntry
import com.r3.dataset.bridge.BridgeSpan
import com.r3.utils.JsonUtils
import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException

/**
 * Чисто REST контроллер, не поддерживает ни одной страницы HTML
 */
@Transactional(readOnly = true)
class BridgeSpanController {

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

        def list = dataEntry.getSpansValues()
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

        def spansValues = new BridgeSpan()
        dataEntry.addToSpansValues(spansValues)
        dataEntry.save(flush: true)

        response.status = 201
        render spansValues as JSON
    }

    def show(Long id) {
        withSpansValues(id) { spansValues ->
            render spansValues as JSON
        }
    }

    @Transactional
    def update(Long id) {
        withSpansValues(id) { spansValues ->
            def version = populate(spansValues)

            if (version != null) {
                if (spansValues.version > version) {
                    spansValues.errors.rejectValue("version", "default.optimistic.locking.failure")
                    response.status = 409
                    render spansValues as JSON
                    spansValues.discard()
                    return
                }
            }

            if (!spansValues.save()) {
                response.status = 400
                render spansValues as JSON
                return
            }

            render spansValues as JSON
        }
    }

    @Transactional
    def delete(Long id) {
        withSpansValues(id) { spansValues ->
            try {
                spansValues.delete()
                response.status = 204
                render 'ok'
            } catch (DataIntegrityViolationException ignored) {
                response.sendError(500)
            }
        }
    }

    private def withSpansValues(Long id, Closure c) {
        def spansValuesInstance = BridgeSpan.get(id)
        if (spansValuesInstance) {
            c.call spansValuesInstance
        } else {
            response.sendError(404)
        }
    }

    private Long populate(BridgeSpan spansValues) {
        def object = JsonUtils.unwrapNulls(request.JSON)
        if (!object) {
            throw new IllegalArgumentException('There is nothing to bind to the instance')
        }
        bindData(spansValues, object, EXCLUDE_FIELDS)
        return object['version']?.toLong()
    }
}
