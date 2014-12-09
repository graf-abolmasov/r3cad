package com.r3

import grails.converters.JSON

class UserController {

    static scaffold = true

    def index(Long projectId) {
        def project = Project.get(projectId)
        def users = project?.organization?.users ?: []

        def datums = users.collect { user ->
            [value: user.lastName, fullName: user.fullName, tokens: [user.firstName, user.middleName, user.lastName, user.login]]
        }

        render datums as JSON
    }
}
