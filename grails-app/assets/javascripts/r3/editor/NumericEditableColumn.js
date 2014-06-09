define([
    "dgrid/editor",
    "dijit/form/NumberTextBox",
    'i18n'
], function (Editor, NumberTextBox, i18n) {
    return function(i18nPrefix, field, i18nSuffix, editorArgs, getFunction, setFunction) {
        var options = {
            editOn: 'click',
            sortable: false,
            autoSave: true,
            field: field,
            label: i18n.t(i18nPrefix + field + i18nSuffix),
            editorArgs: editorArgs || {},
            set: setFunction,
            get: getFunction
        };
        return Editor(options, NumberTextBox);
    }
});
