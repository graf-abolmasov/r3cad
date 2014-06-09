<%@ page import="com.r3.RailWay" %>
<script type="text/javascript">
    require([
        "dgrid/editor",
        "r3/editor/MasterDetailsTableEditor",
        "r3/editor/NumericEditableColumn",
        "r3/editor/SelectEditableColumn",
        'i18n',
        'dojo/dom-construct',
        "dojo/domReady!"
    ], function (Editor, TableEditor, NumericColumn, SelectColumn, i18n) {
        var railWaySelectOptions = ${i18n.selectOptionsJsArray(collection: projectInstance.railWays)};

        new TableEditor({
            columns: [
                numericColumn1('location_km', {constraints: {min: 0, places: 0}}),
                numericColumn1('location_pk', {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn1('location_plus', {constraints: {min: 0}}),
                selectColumn('type', i18n.enums.PillarType),
                textColumn('number'),
                textColumn('alternativeName'),
                selectColumn('leftWireSuspensionType', i18n.enums.WireSuspensionType),
                selectColumn('rightWireSuspensionType', i18n.enums.WireSuspensionType),
                selectColumn('beforeIronCrampAnchorType', i18n.enums.IronCrampAnchorType),
                selectColumn('afterIronCrampAnchorType', i18n.enums.IronCrampAnchorType)
            ],
            detailsStyle: 'width: 15%',
            details: [
                {
                    columns: [
                        selectColumn('railWayId', railWaySelectOptions),
                        numericColumn3('distance')
                    ],
                    restUrlTemplate: "${createLink(resource: 'dataSet/dataEntry/distanceToWay', action: 'index',
                    dataSetId: dataSetInstance.id, dataEntryId: 'MASTER_ID',
                    params: [projectId: dataSetInstance.projectId])}/",
                    createUrlTemplate: "${createLink(resource: 'dataSet/dataEntry', action: 'create',
                    dataSetId: dataSetInstance.id, dataEntryId: 'MASTER_ID',
                    params: [projectId: dataSetInstance.projectId])}"
                }
            ]
        }, 'PillarDataEntriesTableEditor').startup();

        function numericColumn1(field, editorArgs) {
            return NumericColumn('DataEntry.', field, '.label', editorArgs);
        }

        function numericColumn3(field, editorArgs) {
            return NumericColumn('DistanceToWay.', field, '.label', editorArgs);
        }

        function selectColumn(field, selectOptions) {
            return SelectColumn('PillarDataEntry.', field, '.label', selectOptions);
        }

        function textColumn(field) {
            return Editor({
                label: i18n.t('PillarDataEntry.' +  field + '.label'),
                field: field,
                editOn:'click',
                sortable: false,
                autoSave: true
            })
        }
    })
    ;
</script>