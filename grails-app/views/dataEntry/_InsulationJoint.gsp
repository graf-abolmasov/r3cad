<script type="text/javascript">
    require([
        "dgrid/editor",
        "r3/editor/TableEditor",
        "r3/editor/NumericEditableColumn",
        "r3/editor/SelectEditableColumn",
        'i18n',
        'dojo/dom-construct',
        "dojo/domReady!"
    ], function (Editor, TableEditor, NumericColumn, SelectColumn, i18n) {

        new TableEditor({
            columns: [
                numericColumn1('location_km',     {constraints: {min: 0, places: 0}}),
                numericColumn1('location_pk',     {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn1('location_plus',   {constraints: {min: 0}}),
                selectColumn('useApATek', i18n.enums.InsulationJointUsingApATek),
                textColumn('name')
            ]
        }, 'InsulationJointDataEntriesTableEditor').startup();

        function numericColumn1(field, editorArgs) {
            return NumericColumn('DataEntry.', field, '.label', editorArgs);
        }

        function selectColumn(field, selectOptions) {
            return SelectColumn('InsulationJointDataEntry.', field, '.label', selectOptions);
        }

        function textColumn(field) {
            return Editor({
                label: i18n.t('InsulationJointDataEntry.' +  field + '.label'),
                field:field,
                editOn:'click',
                sortable: false,
                autoSave: true
            })
        }
    });
</script>