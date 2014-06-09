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
                numericColumn1('location_km',     {constraints: {min: 0, places: 0}}),
                numericColumn1('location_pk',     {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn1('location_plus',   {constraints: {min: 0}}),
                numericColumn2('number', {smallDelta: 1}),
                selectColumn('turnoutType',  i18n.enums.TurnoutType),
                selectColumn('direct',       i18n.enums.Direct),
                selectColumn('orientation',  i18n.enums.Orientation),
                selectColumn('model',        i18n.enums.Model),
                selectColumn('railsType',    i18n.enums.RailsType),
                selectColumn('sleepersType', i18n.enums.SleepersType),
                selectColumn('controlType',  i18n.enums.ControlType),
                numericColumn2('numberSecond', {smallDelta: 1})

%{--,Editor({--}%
%{--label:'${message(code: 'plansElementsDataEntry.angle.label')}',--}%
%{--field:'angleAsString',--}%
%{--editOn:'click',--}%
%{--editorArgs: { style: { 'width': '99%' } },--}%
%{--sortable: false,--}%
%{--autoSave: false,--}%
%{--set: function(data) {--}%
%{--var deg=data.angleAsString.split('°')[0];--}%
%{--var minsec=data.angleAsString.split('°')[1];--}%
%{--var min=minsec.split('\'')[0];--}%
%{--var sec=minsec.split('\'')[1].split('\"')[0];--}%
%{--data.angle=parseFloat(deg) + parseFloat(min / ${PlansElementsDataEntry.MINUTES_IN_GRAD}) + parseFloat(sec / ${PlansElementsDataEntry.SECONDS_IN_GRAD});--}%
%{--return data.angleAsString;--}%
%{--}--}%
%{--})--}%
            ]
        }, 'TurnoutDataEntriesTableEditor').startup();

        function numericColumn1(field, editorArgs) {
            return NumericColumn('DataEntry.', field, '.label', editorArgs);
        }

        function numericColumn2(field, editorArgs) {
            return NumericColumn('TurnoutDataEntry.', field, '.label', editorArgs);
        }

        function selectColumn(field, selectOptions) {
            return SelectColumn('TurnoutDataEntry.', field, '.label', selectOptions);
        }
    });
</script>