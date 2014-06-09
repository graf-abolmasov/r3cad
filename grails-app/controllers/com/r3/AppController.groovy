package com.r3

class AppController {

    static defaultAction = "open"

    def open() {
        redirect(controller: 'project')
    }

    def run(Integer projectId) {
        def projectInstance = Project.get(projectId)
        if (projectInstance == null) {
            redirect(controller: 'project')
        }
        [projectInstance: projectInstance]
    }
}
