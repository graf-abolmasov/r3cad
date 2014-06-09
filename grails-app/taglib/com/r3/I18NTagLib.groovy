package com.r3

class I18NTagLib {
    static namespace = "i18n"

    static defaultEncodeAs = [taglib: 'none']

    def selectOptionsJsArray = { attrs ->
        final Class enumClazz = getAttr(attrs, 'enumClass')
        if (enumClazz){
            out << enumToJsArray(enumClazz)
            return
        }

        final Collection collection = getAttr(attrs, 'collection')
        final String idProperty = getAttr(attrs, 'id', 'id')
        final String labelProperty = getAttr(attrs, 'label', 'label')
        if (collection) {
            out << collectionToJsArray(collection, idProperty, labelProperty)
            return
        }

        throwTagError("enumClass or collection attribute must be provided")
    }

    private String enumToJsArray(Class enumClazz) {
        if (enumClazz == null || enumClazz.values().length == 0) {
            return '[ ]'
        }

        final String enumClassName = enumClazz.simpleName
        List<String> selectOptions = ["{id: '', label: '${message(code: "default.enumField.null")}'}"]
        selectOptions += enumClazz.values().collect {
            "{ id: '${it.name()}', label: '${message(code: "${enumClassName}.${it.name}", default: it.name())}'}"
        }

        return "[${selectOptions.join(', ')}]"
    }

    private static String collectionToJsArray(Collection collection, String idProperty, String labelProperty) {
        if (collection == null || collection.isEmpty()) {
            return '[ ] '
        }

        List<String> selectOptions = collection.collect {
            def id = it."$idProperty"
            def label = it."$labelProperty"
            id = id instanceof Number ? id : "'$id'"
            "{id: $id, label: '$label'}"
        }

        return "[${selectOptions.join(', ')}]"
    }

    private static <T> T getAttr(Map attrs, String attrName) {
        return attrs.remove(attrName)
    }

    private static <T> T getAttr(Map attrs, String attrName, T defValue) {
        return attrs.remove(attrName) ?: defValue
    }
}
