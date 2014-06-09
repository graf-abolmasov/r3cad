<script type="text/javascript">
    require([
        "r3/editor/TableEditor",
        "r3/editor/NumericEditableColumn",
        'dojo/dom-construct',
        "dojo/domReady!"
    ], function (TableEditor, NumericColumn) {

        new TableEditor({
            columns: [
                numericColumn('location_km', {constraints: {min: 0, places: 0}}),
                numericColumn('location_pk', {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn('location_plus', {constraints: {min: 0}}),
                numericColumn('doubleValue', {constraints: {min: 0, places: 2}})
            ]
        }, 'InterWaySpaceDataEntriesTableEditor').startup();

        function numericColumn(field, editorArgs) {
            return NumericColumn('InterWaySpaceDataEntry.', field, '.label', editorArgs);
        }
    });
</script>