import com.r3.dataset.StructuredKmPkPlusEditor

// Place your Spring DSL code here
beans = {
    groovyPageResourceLoader(com.cadrlife.jhaml.grailsplugin.HamlGroovyPageResourceLoader) {
        baseResource = "file:."
        pluginSettings = new grails.util.PluginBuildSettings(grails.util.BuildSettingsHolder.settings)
    }

    kmPkPlusStructEditor com.r3.dataset.StructuredKmPkPlusEditor
}
