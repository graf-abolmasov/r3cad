<%@ page import="com.r3.dataset.ElevationDataEntry; com.r3.dataset.CurveType; com.r3.dataset.Location" %>
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
                selectColumn('elementType', i18n.enums.CurveType, function (data) {
                    if (data.elementType == '${CurveType.STRAIGHT.name()}') {
                        data.angle = 0;
                        data.angleAsString = '0°0\'0\"';
                        data.radius = 0;
                        data.h = 0;
                        data.t1 = 0;
                        data.t2 = 0;
                        data.l1 = 0;
                        data.l2 = 0;
                    }
                    return data.elementType
                }),
                angleColumn('angleAsString'),
                numericColumn2('radius', {constraints: {places: 0}}),
                numericColumn2('h', {constraints: {places: 0}}),
                numericColumn2('t1', {smallDelta: 0.01}),
                numericColumn2('t2', {smallDelta: 0.01}),
                numericColumn2('length', {smallDelta: 0.001}),
                numericColumn2('endKm', {constraints: {min: 0, places: 0}}),
                numericColumn2('endPk', {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn2('endPlus', {constraints: {min: 0}}),
                numericColumn2('l1', {smallDelta: 10, constraints: {min: 0, places: 0}}),
                numericColumn2('l2', {smallDelta: 10, constraints: {min: 0, places: 0}}),
                textColumn('g'),
                textColumn('h_'),
                textColumn('i'),
                textColumn('j'),
                textColumn('k'),
                textColumn('l'),
                textColumn('m'),
                textColumn('n'),
                textColumn('p'),
                textColumn('s'),
                textColumn('t'),
                textColumn('u'),
                textColumn('v'),
                textColumn('w'),
                textColumn('x'),
                textColumn('y'),
                textColumn('z'),
                textColumn('aa'),
                textColumn('ab'),
                textColumn('ad'),
                textColumn('ae'),
                textColumn('af')
            ]
        }, 'ElevationDataEntriesTableEditor').startup();

        function numericColumn1(field, editorArgs) {
            return NumericColumn('DataEntry.', field, '.label', editorArgs);
        }

        function numericColumn2(field, editorArgs, setFunction) {
            return NumericColumn('ElevationDataEntry.', field, '.label', editorArgs, undefined, setFunction);
        }

        function selectColumn(field, selectOptions, setFunction) {
            return SelectColumn('ElevationDataEntry.', field, '.label', selectOptions, undefined, setFunction);
        }

        function angleColumn(field) {
            return Editor({
                label: i18n.t('ElevationDataEntry.' +  field + '.label'),
                field: field,
                editOn:'click',
                sortable: false,
                autoSave: true
            })
        }

        function textColumn(field) {
            return Editor({
                label: i18n.t('ElevationDataEntry.' +  field + '.label'),
                field:field,
                editOn:'click',
                sortable: false,
                autoSave: true
            })
        }
    });
</script>