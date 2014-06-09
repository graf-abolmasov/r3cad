package com.r3.drawing

import com.r3.dataset.ProfileDrawingAware
import com.r3.drawing.area.StackedDrawArea
import org.springframework.dao.DataIntegrityViolationException

class StackedDrawAreaController {

    def edit(Long id) {
        withPlainDrawArea(id) { stackedDrawAreaInstance ->
            return buildModel(stackedDrawAreaInstance)
        }
    }

    def update(Long id, Long version) {
        withPlainDrawArea(id) { StackedDrawArea instance ->
            if (version != null) {
                if (instance.version > version) {
                    instance.errors.rejectValue("version", "default.optimistic.locking.failure")
                    render(view: "edit", model: buildModel(instance))
                    return
                }
            }

            bindData(instance, params)

            if (instance.save()) {
                flash.message = message(code: 'default.update.success')
                redirect(action: "edit", id: instance.id, params: [ok: true])
            } else {
                render(view: "edit", model: buildModel(instance))
            }
        }
    }

    def delete(Long id) {
        withPlainDrawArea(id) { instance ->
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

    private def withPlainDrawArea(Long id, Closure c) {
        def stackedDrawAreaInstance = StackedDrawArea.get(id)
        if (stackedDrawAreaInstance) {
            c.call stackedDrawAreaInstance
        } else {
            response.sendError(404)
        }
    }

    private Map buildModel(Object instance) {
        def project = instance.drawing.project
        def dataSets = project.dataSets?.findAll {
            it instanceof ProfileDrawingAware
        }
        return [stackedDrawAreaInstance: instance, dataSets: dataSets]
    }
}
