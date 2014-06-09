package com.r3

import com.r3.drawing.area.RepresentationRole
import com.r3.drawing.symbol.ConventionalSymbol
import com.r3.drawing.symbol.SymbolIcon
import grails.transaction.Transactional

@Transactional
class SymbolLibraryService {

    def createDefaultSymbols() {
        def map = [:]
        map[RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN.name()] = new SymbolIcon(template: 'star/top_view_plan')
        new ConventionalSymbol(key: 'star', name: 'Звезда', defaultIcon: new SymbolIcon(template: 'star/default'), icons: map).save()

        map = [:]
        map[RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN.name()] = new SymbolIcon(template: 'java/duke_forward')
        new ConventionalSymbol(key: 'java_forward', name: 'Java (Forward)', defaultIcon: new SymbolIcon(template: 'java/logo'), icons: map).save()

        map = [:]
        map[RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN.name()] = new SymbolIcon(template: 'java/duke_reward')
        new ConventionalSymbol(key: 'java_reward', name: 'Java (Reward)', defaultIcon: new SymbolIcon(template: 'java/logo'), icons: map).save()

        map = [:]
        map[RepresentationRole.PROFILE_TABLE_TOP_VIEW_PLAN.name()] = new SymbolIcon(template: 'blank')
        new ConventionalSymbol(key: 'tomcat', name: 'Apache Tomcat', defaultIcon: new SymbolIcon(template: 'tomcat_logo'), icons: map).save()

        new ConventionalSymbol(key: 'super_star', name: 'Суперзвезда', defaultIcon: new SymbolIcon(template: 'super_star')).save()
        new ConventionalSymbol(key: 'dojo', name: 'Dojo Toolkit', defaultIcon: new SymbolIcon(template: 'dojo_logo')).save()
    }
}
