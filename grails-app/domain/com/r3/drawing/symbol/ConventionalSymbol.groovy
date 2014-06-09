package com.r3.drawing.symbol

import com.r3.drawing.area.RepresentationRole

class ConventionalSymbol {

    static constraints = {
        key(unique: true)
        name(unique: true)
        type(nullable: true)
    }

    static hasMany = [icons: SymbolIcon]

    static belongsTo = [type: SymbolType]

    static embedded = ['defaultIcon']

    Map<String, SymbolIcon> icons

    String key
    String name
    SymbolIcon defaultIcon

    SymbolIcon getIcon(RepresentationRole representationRole) {
        if (!icons) {
            return defaultIcon
        }

        return icons[representationRole.name()] ?: defaultIcon
    }

    int getHeight(RepresentationRole representationRole) {
        getIcon(representationRole).height
    }

    int getWidth(RepresentationRole representationRole) {
        getIcon(representationRole).width
    }

    String getTemplate(RepresentationRole representationRole) {
        getIcon(representationRole).template
    }
}

