package com.r3.dataset

import com.r3.Project
import com.r3.RailWay
import com.r3.dataset.pillar.PillarDataEntry
import com.r3.dataset.pillar.WireHeight
import com.r3.utils.JsonUtils
import grails.converters.JSON
import grails.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException

import javax.servlet.http.HttpServletResponse

/**
 * Чисто REST контроллер, не поддерживает ни одной страницы HTML
 */
@Transactional(readOnly = true)
class WireHeightController {

    static defaultAction = "list"

    public static final LinkedHashMap<String, ArrayList<String>> EXCLUDE_FIELDS = [exclude: ['id', 'pillar', 'version', 'errors', 'class']]

    static allowedMethods = [
            list: 'GET',
            show: 'GET',
            save: 'POST',
            update: 'PUT',
            delete: 'DELETE'
    ]

    def list(Long projectId) {
        def dataEntry = PillarDataEntry.get(projectId)
        if (!dataEntry) {
            response.sendError(404)
            return
        }

        def list = dataEntry.getWireHeights()
        if (!list) {
            response.status = 204
        }
        response.addHeader('Content-Range:', "items 0-${list.size() - 1}/${list.size()}")
        render list as JSON
    }

    @Transactional
    def save(Long projectId) {
        def dataEntry = PillarDataEntry.get(projectId)
        if (!dataEntry) {
            response.sendError(404)
            return
        }

        Project project = dataEntry.dataSet.project
        def railWay = dataEntry.distancesToWays ? RailWay.findByIdNotInListAndProject(dataEntry.distancesToWays.railWay.id, project) : project.mappingWay()
        if (!railWay) {
            response.status = HttpServletResponse.SC_NOT_ACCEPTABLE
            render 'Cannot add one more distance'
            return
        }

        def wireHeight = new WireHeight(railWay: railWay)
        dataEntry.addToWireHeights(wireHeight)
        dataEntry.save(flush: true)

        response.status = 201
        render wireHeight as JSON
    }

    def show(Long id) {
        withWireHeight(id) { distanceToWay ->
            render distanceToWay as JSON
        }
    }

    @Transactional
    def update(Long id) {
        withWireHeight(id) { wireHeight ->
            def version = populate(wireHeight)

            if (version != null) {
                if (wireHeight.version > version) {
                    wireHeight.errors.rejectValue("version", "default.optimistic.locking.failure")
                    response.status = 409
                    render wireHeight as JSON
                    wireHeight.discard()
                    return
                }
            }

            if (!wireHeight.save()) {
                response.status = 400
                render wireHeight as JSON
                return
            }

            render wireHeight as JSON
        }
    }

    @Transactional
    def delete(Long id) {
        withWireHeight(id) { wireHeight ->
            try {
                wireHeight.delete()
                response.status = 204
                render 'ok'
            } catch (DataIntegrityViolationException ignored) {
                response.sendError(500)
            }
        }
    }

    private def withWireHeight(Long id, Closure c) {
        def wireHeightInstance = WireHeight.get(id)
        if (wireHeightInstance) {
            c.call wireHeightInstance
        } else {
            response.sendError(404)
        }
    }

    private Long populate(WireHeight wireHeight) {
        def object = JsonUtils.unwrapNulls(request.JSON)
        if (!object) {
            throw new IllegalArgumentException('There is nothing to bind to the instance')
        }
        bindData(wireHeight, object, EXCLUDE_FIELDS)
        return object['version']?.toLong()
    }
}
