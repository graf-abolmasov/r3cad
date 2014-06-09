package com.r3.dataset

import org.grails.databinding.converters.AbstractStructuredBindingEditor

class StructuredKmPkPlusEditor extends AbstractStructuredBindingEditor<KmPkPlus> {

    public KmPkPlus getPropertyValue(Map values) {
        int km = (values['km']?:0) as int
        int pk = (values['pk']?:0) as int
        int plus = Math.round(((values['plus']?:0.0) as double) * Location.MM_IN_METER)
        new KmPkPlus(km, pk, plus)
    }
}
