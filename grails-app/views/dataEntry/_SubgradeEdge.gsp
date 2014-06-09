<%@ page import="com.r3.dataset.Location" %>
<script type="text/javascript">
    require([
        "r3/editor/TableEditor",
        "r3/editor/NumericEditableColumn",
        'dojo/dom-construct',
        "dojo/domReady!"
    ], function (TableEditor, NumericColumn) {

        new TableEditor({
            columns: [
                numericColumn1('location_km',         {constraints: {min: 0, places: 0}}),
                numericColumn1('location_pk',         {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn1('location_plus',       {constraints: {min: 0}}),
                numericColumn2('leftValue',  {constraints: {min: 0, places: 2}}),
                numericColumn2('leftLeft',   {constraints: {min: 0, places: 2}}),
                numericColumn2('rightValue', {constraints: {min: 0, places: 2}}),
                numericColumn2('rightRight', {constraints: {min: 0, places: 2}})
            ]
        }, 'SubgradeEdgeDataEntriesTableEditor').startup();

        function numericColumn1(field, editorArgs) {
            return NumericColumn('DataEntry.', field, '.label', editorArgs);
        }

        function numericColumn2(field, editorArgs) {
            return NumericColumn('SubgradeEdgeDataEntry.', field, '.label', editorArgs);
        }
    });
</script>