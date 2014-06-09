package com.r3

class ProjectController {

    def demoService

    static allowedMethods = [
            index : 'GET',
            edit  : 'GET',
            update: 'POST'
    ]

    def index() {
        def userProjects = Project.findAll()
        def count = userProjects.size()
        [projectInstanceList: userProjects, projectInstanceTotal: count]
    }

    def createDemo(Integer count) {
        demoService.createDemoData(count)
        redirect(action: 'index')
    }

    def edit(Long id) {
        withProject(id) { projectInstance ->
            [projectInstance: projectInstance]
        }
    }

    def update(Long id, Long version) {
        withProject(id) { projectInstance ->
            if (version != null) {
                if (projectInstance.version > version) {
                    projectInstance.errors.rejectValue("version", "default.optimistic.locking.failure")
                    render(view: "edit", model: [projectInstance: projectInstance])
                    return
                }
            }
            bindData(projectInstance, params)
            if (!projectInstance.save()) {
                render(view: "edit", model: [projectInstance: projectInstance])
                return
            }
            flash.message = message(code: 'default.update.success')
            redirect(action: "edit", id: id)
        }
    }

    private def withProject(Long id, Closure c) {
        def projectInstance = Project.get(id)
        if (projectInstance) {
            c.call projectInstance
        } else {
            response.sendError(404)
        }
    }
}