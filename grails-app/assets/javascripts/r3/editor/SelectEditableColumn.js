define([
    "dgrid/editor",
    "dijit/form/Select",
    "dojo/data/ObjectStore",
    "dojo/store/Memory",
    'i18n'
], function (Editor, Select, ObjectStore, Memory, i18n) {
    return function(i18nPrefix, field, i18nSuffix, selectOptions, getFunction, setFunction) {
        var options = {
            label: i18n.t(i18nPrefix + field + i18nSuffix),
            field: field,
            editorArgs: {store: new ObjectStore({
                objectStore: new Memory({ data: selectOptions })
            }), sortByLabel: false},
            sortable: false,
            autoSave: true,
            get: getFunction,
            set: setFunction
        };
        return Editor(options, Select);
    }
});
