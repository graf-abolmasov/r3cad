package com.r3.drawing

import com.r3.dataset.ProfileDrawingAware
import com.r3.drawing.area.PlainDrawArea
import com.r3.drawing.area.StackedDrawArea
import org.codehaus.groovy.grails.web.binding.DataBindingUtils
import org.springframework.dao.DataIntegrityViolationException

class PlainDrawAreaController {

    def edit(Long id) {
        withPlainDrawArea(id) { PlainDrawArea plainDrawAreaInstance ->
            return buildModel(plainDrawAreaInstance)
        }
    }

    def update(Long id, Long version) {
        withPlainDrawArea(id) { PlainDrawArea instance ->
            if (version != null) {
                if (instance.version > version) {
                    instance.errors.rejectValue("version", "default.optimistic.locking.failure")
                    render(view: "edit", model: buildModel(instance))
                    return
                }
            }

            bindData(instance, request)

            if (instance.save()) {
                flash.message = message(code: 'default.update.success')
                redirect(action: "edit", id: instance.id, params: [ok: true])
            } else {
                render(view: "edit", model: buildModel(instance))
            }
        }
    }

    def delete(Long id) {
        withPlainDrawArea(id) { PlainDrawArea instance ->
            try {
                instance.delete()
                flash.message = message(code: 'default.delete.success')
                render 'ok'
            } catch (DataIntegrityViolationException ignored) {
                flash.message = message(code: 'default.delete.failure')
                redirect(action: "edit", id: id)
            }
        }
    }

    private static Map buildModel(PlainDrawArea instance) {
        def project = instance.drawing.project
        def dataSets = project.dataSets?.findAll {
            it instanceof ProfileDrawingAware
        }
        return [plainDrawAreaInstance: instance, dataSets: dataSets]
    }

    private def withPlainDrawArea(Long id, Closure c) {
        def plainDrawAreaInstance = PlainDrawArea.get(id)
        if (plainDrawAreaInstance) {
            c.call plainDrawAreaInstance
        } else {
            response.sendError(404)
        }
    }
}
