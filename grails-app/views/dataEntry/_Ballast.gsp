<script type="text/javascript">
    require([
        "r3/editor/TableEditor",
        "dgrid/editor",
        "dijit/form/NumberSpinner",
        "r3/editor/NumericEditableColumn",
        'i18n',
        'dojo/dom-construct',
        "dojo/domReady!"
    ], function (TableEditor, Editor, NumberSpinner, NumericColumn, i18n) {

        new TableEditor({
            columns: [
                numericColumn('location_km',    {constraints: {min: 0, places: 0}}),
                numericColumn('location_pk',   {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn('location_plus', {constraints: {min: 0}})
                <g:each in="${0..dataSetInstance.countLayers-1}" var="i">
                ,layerColumn('layer${i}', '${dataSetInstance."name$i"}')
                </g:each>
            ]
        }, 'BallastDataEntriesTableEditor').startup();

        function numericColumn(field, editorArgs) {
            return NumericColumn('DataEntry.', field, '.label', editorArgs);
        }

        function layerColumn(field, layerName) {
            return Editor({
                editOn: 'click',
                sortable: false,
                autoSave: true,
                label: i18n.t('BallastDataSet.layer.label', layerName),
                field: field,
                editorArgs: {constraints: {min: 0, places: 0}}
            }, NumberSpinner)
        }
    });
</script>
