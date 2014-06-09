package com.r3

class User {
    String login

    String firstName
    String middleName
    String lastName

    static hasMany = [projects: Project]

    static belongsTo = [organization: Organization]

    static transients = ['fullName']

    static mapping = {
        table 'users'
        login index: 'Login_Idx'
    }

    static constraints = {
        login   blank: false, unique: true
        firstName  blank: false
        middleName nullable: true, blank: false
        lastName   blank: false
    }

    public String getFullName() {
        return "${lastName} ${firstName} ${middleName}"
    }
}
