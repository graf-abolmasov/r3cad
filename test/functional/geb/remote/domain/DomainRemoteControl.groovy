package geb.remote.domain

import grails.plugin.remotecontrol.RemoteControl

class DomainRemoteControl {

    protected RemoteControl remote = new RemoteControl()

    protected Class domainClazz

    DomainRemoteControl(Class domainClass) {
        this.domainClazz = domainClass
    }

    long first() {
        withDomainClass { Class domainClass ->
            remote.exec { domainClass.first().id }
        }
    }

    int count() {
        withDomainClass { Class domainClass ->
            remote.exec { domainClass.count() }
        }
    }

    private withDomainClass(Closure c) {
        c.call(domainClazz)
    }
}
