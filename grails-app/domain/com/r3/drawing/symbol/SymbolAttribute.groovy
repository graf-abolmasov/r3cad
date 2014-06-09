package com.r3.drawing.symbol

class SymbolAttribute {

    static constraints = {
        key(blank: false)
        name(blank: false)
        value(blank: false)
    }

    String key
    String name
    String value
}
