package geb.pages.com.r3

import geb.Page

/**
 * Created by graf on 14/06/14.
 */

class ProjectViews {
    static Class<ProjectIndexPage> index = ProjectIndexPage
}

class ProjectIndexPage extends Page {
    static url = "project/index"

    static at = {
        title == 'Open Project - R3'
    }

    static content = {
        newProjectBtn(to: ProjectIndexPage, wait: true) {
            $('#newProject')
        }
    }

    def clickNewProjectBtn() {
        newProjectBtn.click()
    }
}
