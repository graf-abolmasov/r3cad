<%@ page import="com.r3.dataset.Location" %>
<script type="text/javascript">
    require([
        "r3/editor/TableEditor",
        "r3/editor/NumericEditableColumn",
        "r3/editor/SelectEditableColumn",
        'i18n',
        'dojo/dom-construct',
        "dojo/domReady!"
    ], function (TableEditor, NumericColumn, SelectColumn, i18n) {

        new TableEditor({
            columns: [
                numericColumn1('location_km',      {constraints: {min: 0, places: 0}}),
                numericColumn1('location_pk',      {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn1('location_plus',    {constraints: {min: 0}}),
                numericColumn2('endLocation_km',   {constraints: {min: 0, places: 0}}),
                numericColumn2('endLocation_pk',   {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn2('endLocation_plus', {constraints: {min: 0}}),
                selectColumn('sleepersType', i18n.enums.SleepersType)
            ]
        }, 'SleepersDataEntriesTableEditor').startup();

        function numericColumn1(field, editorArgs) {
            return NumericColumn('DataEntry.', field, '.label', editorArgs);
        }

        function numericColumn2(field, editorArgs, getFunction, setFunction) {
            return NumericColumn('SleepersDataEntry.', field, '.label', editorArgs, getFunction, setFunction);
        }

        function selectColumn(field, selectOptions) {
            return SelectColumn('SleepersDataEntry.', field, '.label', selectOptions);
        }

    });
</script>