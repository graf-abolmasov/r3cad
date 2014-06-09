package com.r3

import grails.converters.JSON

class UserController {

    static scaffold = true

    static class UserTypeAheadDatum {
        String value
        String[] tokens
        String fullName
    }

    def ajaxTypeAhead(Project project) {
        if (!project || !project.users) {
            render '[]'
            return
        }

        def datums = new ArrayList<UserTypeAheadDatum>(project.users.size())
        project.users.each { user ->
            datums.add(new UserTypeAheadDatum(value: user.lastName, fullName: user.toString(), tokens: [user.firstName, user.middleName, user.lastName, user.login]))
        }

        render datums as JSON
    }
}
