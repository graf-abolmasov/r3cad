package com.r3.drawing.symbol

class SymbolType {

    static constraints = {
        name(blank: false)
    }

    static hasMany = [symbols: ConventionalSymbol, attributes: SymbolAttribute]

    static mapping = {
        symbols batchSize: 10
    }

    Collection<ConventionalSymbol> symbols

    String name
}
