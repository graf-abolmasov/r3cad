package com.r3

class Organization {

    static hasMany = [users: User, projects: Project]

    static constraints = {
        name(nullable: false)
        address(nullable: false)
        phone(nullable: false)
        licenceAvailable(min: 0)
    }

    static mapping = {
        projects(sort: 'name')
    }

    Set<User> users
    Set<Project> projects

    int licenceAvailable

    String name
    String address
    String phone
}
