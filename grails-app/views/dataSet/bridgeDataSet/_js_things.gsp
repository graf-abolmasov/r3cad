<%@ page import="com.r3.dataset.Location; com.r3.dataset.bridge.EnterType; com.r3.dataset.bridge.SpansType; com.r3.dataset.bridge.MovementType; com.r3.dataset.bridge.WaterFlowDirection; com.r3.dataset.bridge.FlooringType; com.r3.dataset.bridge.BridgeSpan; com.r3.dataset.bridge.BridgeDataEntry; com.r3.dataset.bridge.BridgePier" %>
<script>

    function handleStoreError(err, json, Tooltip, put) {
        try {
            var obj = json.parse(err.error.responseText);
            var row = err.grid.row(obj.id);

            //Add Tooltip of record
            var errors = '<ul>';
            if (obj && obj.errors) {
                obj.errors.errors.forEach(function (item) {
                    errors += '<li>' + item.message + '</li>';
                });
            }
            errors += '</ul>';

            new Tooltip({
                connectId: [row.element.id],
                label: errors,
                position: ['below', 'above']
            });

            put(row.element, '.has-errors');
        } catch (e) {
            if (err.error.status != 404 && err.error.status != 500) {
                alert(err.error.responseText);
            }
            console.log(err);
        }
    }

    require([
        "dojo/_base/declare",
        "dgrid/OnDemandGrid",
        "dgrid/editor",
        "dgrid/Keyboard",
        "dgrid/Selection",
        'dojo/store/JsonRest',
        'dojo/store/Observable',
        "dijit/form/NumberSpinner",
        "dijit/form/TextBox",
        "dijit/form/Select",
        "dojo/data/ObjectStore",
        "dojo/store/Memory",
        "dojo/request/xhr",
        "dijit/registry",
        "dojo/dom",
        "dojo/ready",
        "r3/Utils",
        "put-selector/put",
        'dojo/json',
        'dijit/Tooltip',
        "dojo/domReady!"
    ], function(declare, OnDemandGrid, Editor, Keyboard, Selection, JsonRest, Observable, NumberSpinner, TextBox, Select, ObjectStore, Memory, req, registry, dom, ready, Utils, put, json, Tooltip) {

        var bridgeSpanUrl = '${createLink(mapping: 'restBridgeSpan', params: [bid:666])}/';

        var spansTypeOptionsData = [
            { id: '${SpansType.METALLIC.name()}', label: '${message(code:"${SpansType.name}.METALLIC")}'}
            ,{ id: '${SpansType.REINFORCED_CONCRETE.name()}', label: '${message(code:"${SpansType.name}.REINFORCED_CONCRETE")}'}
            ,{ id: '${SpansType.STEEL_REINFORCED_CONCRETE.name()}', label: '${message(code:"${SpansType.name}.STEEL_REINFORCED_CONCRETE")}'}
        ];

        var movementTypeOptionsData = [
            { id: '${MovementType.UP.name()}', label: '${message(code:"${MovementType.name}.UP")}'}
            ,{ id: '${MovementType.DOWN.name()}', label: '${message(code:"${MovementType.name}.DOWN")}'}
        ];

        var bridgeSpansGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                if (!(this.store)) {
                    return;
                }
                this.store.add({});
            }}))({
            columns: [
                { label: '', field: 'errors', sortable: false, showHeader:false}

                ,Editor({
                    label: '${message(code: 'bridgeSpan.spansType.label')}',
                    field: 'spansType',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: spansTypeOptionsData }),
                        labelProperty: "label"
                    }),
                        style: { width: "120px" }, sortByLabel: false},
                    sortable: false,
                    autoSave: true,
                    set: function (data) {
                        if (data.spansType!='${SpansType.METALLIC.name()}') {
                            data.movementType='${MovementType.UP.name()}'
                        }
                        return data.spansType
                    }
                }, Select)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.length.label')}',
                    field: 'length',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
                    autoSave: true,
                    set: function (data) {
                        return data.length ? Math.round(data.length * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.movementType.label')}',
                    field: 'movementType',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: movementTypeOptionsData }),
                        labelProperty: "label"
                    }),
                        style: { width: "120px" }, sortByLabel: false},
                    sortable: false,
                    autoSave: true
                }, Select)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.lumenLength.label')}',
                    field: 'lumenLength',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
                    autoSave: true,
                    set: function (data) {
                        return data.lumenLength ? Math.round(data.lumenLength * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.ballast.label')}',
                    field: 'ballast',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.1, constraints: {min:0, places:1} },
                    autoSave: true,
                    set: function (data) {
                        return data.ballast ? Math.round(data.ballast * ${Location.MM_IN_CM}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.measurementBeginLeft.label')}',
                    field: 'measurementBeginLeft',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
                    autoSave: true,
                    set: function (data) {
                        return data.measurementBeginLeft ? Math.round(data.measurementBeginLeft * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.measurementBeginRight.label')}',
                    field: 'measurementBeginRight',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
                    autoSave: true,
                    set: function (data) {
                        return data.measurementBeginRight ? Math.round(data.measurementBeginRight * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.measurementAxisLeft.label')}',
                    field: 'measurementAxisLeft',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
                    autoSave: true,
                    set: function (data) {
                        return data.measurementAxisLeft ? Math.round(data.measurementAxisLeft * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.measurementAxisRight.label')}',
                    field: 'measurementAxisRight',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
                    autoSave: true,
                    set: function (data) {
                        return data.measurementAxisRight ? Math.round(data.measurementAxisRight * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.measurementEndLeft.label')}',
                    field: 'measurementEndLeft',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
                    autoSave: true,
                    set: function (data) {
                        return data.measurementEndLeft ? Math.round(data.measurementEndLeft * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'bridgeSpan.measurementEndRight.label')}',
                    field: 'measurementEndRight',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
                    autoSave: true,
                    set: function (data) {
                        return data.measurementEndRight ? Math.round(data.measurementEndRight * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

            ]

        }, "bridge_spans_grid");

        bridgeSpansGrid.on("dgrid-error", function(err) {
            handleStoreError(err, json, Tooltip, put);
        });

        var bridgePierUrl = '${createLink(mapping: 'restBridgePier', params: [bid:666])}/';

        var bridgePiersGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                if (!(this.store)) {
                    return;
                }
                this.store.add({});
            }}))({
            columns: [
                { label: '', field: 'errors', sortable: false, showHeader:false}

                ,Editor({
                    label: '${message(code: 'dataEntry.km.label')}',
                    field: 'km',
                    editOn: 'click',
                    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, places: 0}},
                    sortable: false,
                    autoSave: true
                }, NumberSpinner)
                ,Editor({
                    label: '${message(code: 'dataEntry.pk.label')}',
                    field: 'pk',
                    editOn: 'click',
                    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, max: 9, places: 0}},
                    sortable: false,
                    autoSave: true
                }, NumberSpinner)
                ,Editor({
                    label: '${message(code: 'dataEntry.plus.label')}',
                    field: 'plus',
                    editOn: 'click',
                    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0}},
                    sortable: false,
                    autoSave: true,
                    set: function (data) {
                        return data.plus ? Math.round(data.plus * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)
            ]

        }, "bridge_piers_grid");

        bridgePiersGrid.on("dgrid-error", function(err) {
            handleStoreError(err, json, Tooltip, put);
        });

        var flooringTypeOptionsData = [
            { id: '${FlooringType.ON_BALLAST.name()}', label: '${message(code:"${FlooringType.name}.ON_BALLAST")}'}
            ,{ id: '${FlooringType.ON_CONCRETE_SLABS.name()}', label: '${message(code:"${FlooringType.name}.ON_CONCRETE_SLABS")}'}
            ,{ id: '${FlooringType.OM_WOODEN_SKIDS.name()}', label: '${message(code:"${FlooringType.name}.OM_WOODEN_SKIDS")}'}
        ];

        var waterFlowDirectionOptionsData = [
            { id: '${WaterFlowDirection.NONE.name()}', label: '${message(code:"${WaterFlowDirection.name}.NONE")}'}
            ,{ id: '${WaterFlowDirection.FROM_LEFT_TO_RIGHT.name()}', label: '${message(code:"${WaterFlowDirection.name}.FROM_LEFT_TO_RIGHT")}'}
            ,{ id: '${WaterFlowDirection.FROM_RIGHT_TO_LEFT.name()}', label: '${message(code:"${WaterFlowDirection.name}.FROM_RIGHT_TO_LEFT")}'}
            ,{ id: '${WaterFlowDirection.DRY.name()}', label: '${message(code:"${WaterFlowDirection.name}.DRY")}'}
            ,{ id: '${WaterFlowDirection.UNKNOWN.name()}', label: '${message(code:"${WaterFlowDirection.name}.UNKNOWN")}'}
        ];

        var bridgesGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                var grid = this;
                req('${createLink(controller: 'dataEntry', action: 'create', params: [dataEntryClass:BridgeDataEntry.name, dsid: dataSetInstance.id])}', {
                    handleAs: 'json'
                }).then(function (data) {
                            grid.store.add(data);
                        });
            }}))({
            store: new Observable(new JsonRest({target: '${createLink(mapping: 'restDataEntry', params: [dsid:dataSetInstance.id, dataEntryClass:BridgeDataEntry.name])}/'})),
            columns: [
                { label: '', field: 'errors', sortable: false, showHeader:false}
                <g:if test="${dataSetInstance.enterType==EnterType.BEGIN_END}">
                    <g:render template="bridgeDataSet/begin_end"/>
                </g:if>
                <g:elseif test="${dataSetInstance.enterType==EnterType.BEGIN_LENGTH}">
                    <g:render template="bridgeDataSet/begin_length"/>
                </g:elseif>
                <g:elseif test="${dataSetInstance.enterType==EnterType.AXIS_LENGTH}">
                    <g:render template="bridgeDataSet/axis_length"/>
                </g:elseif>
                <g:else>
                    <g:render template="bridgeDataSet/end_length"/>
                </g:else>
                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.km.label')}',--}%
                    %{--field: 'km',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, places: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.pk.label')}',--}%
                    %{--field: 'pk',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, max: 9, places: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.plus.label')}',--}%
                    %{--field: 'plus',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false,--}%
                    %{--set: function (data) {--}%
                        %{--return data.plus ? Math.round(data.plus * ${Location.MM_IN_METER}) : 0;--}%
                    %{--}--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.axisLocation.km.label')}',--}%
                    %{--field: 'axisLocation_km',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, places: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false,--}%
                    %{--get: function(data) {--}%
                        %{--return data.axisLocation.km--}%
                    %{--},--}%
                    %{--set: function(data) {--}%
                        %{--data.axisLocation.km=data.axisLocation_km;--}%
                        %{--return data.axisLocation_km;--}%
                    %{--}--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.axisLocation.pk.label')}',--}%
                    %{--field: 'axisLocation_pk',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, max: 9, places: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false,--}%
                    %{--get: function(data) {--}%
                        %{--return data.axisLocation.pk;--}%
                    %{--},--}%
                    %{--set: function(data) {--}%
                        %{--data.axisLocation.pk=data.axisLocation_pk;--}%
                        %{--return data.axisLocation_pk;--}%
                    %{--}--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.axisLocation.plus.label')}',--}%
                    %{--field: 'axisLocation_plus',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false,--}%
                    %{--get: function(data) {--}%
                        %{--return data.axisLocation_plus;--}%
                    %{--},--}%
                    %{--set: function (data) {--}%
                        %{--data.axisLocation.plus = data.axisLocation_plus ? Math.round(data.axisLocation_plus * ${Location.MM_IN_METER}):0;--}%
                        %{--return data.axisLocation_plus ? Math.round(data.axisLocation_plus * ${Location.MM_IN_METER}):0--}%
                    %{--}--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.endLocation.km.label')}',--}%
                    %{--field: 'endLocation_km',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, places: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false,--}%
                    %{--get: function(data) {--}%
                        %{--return data.endLocation.km--}%
                    %{--},--}%
                    %{--set: function(data) {--}%
                        %{--data.endLocation.km=data.endLocation_km;--}%
                        %{--return data.endLocation_km;--}%
                    %{--}--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.endLocation.pk.label')}',--}%
                    %{--field: 'endLocation_pk',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, max: 9, places: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false,--}%
                    %{--get: function(data) {--}%
                        %{--return data.endLocation.pk;--}%
                    %{--},--}%
                    %{--set: function(data) {--}%
                        %{--data.endLocation.pk=data.endLocation_pk;--}%
                        %{--return data.endLocation_pk;--}%
                    %{--}--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.endLocation.plus.label')}',--}%
                    %{--field: 'endLocation_plus',--}%
                    %{--editOn: 'click',--}%
                    %{--editorArgs: {style: { 'width': "99%" }, constraints: {min: 0}},--}%
                    %{--sortable: false,--}%
                    %{--autoSave: false,--}%
                    %{--get: function(data) {--}%
                        %{--return data.endLocation_plus;--}%
                    %{--},--}%
                    %{--set: function (data) {--}%
                        %{--data.endLocation.plus = data.endLocation_plus ? Math.round(data.endLocation_plus * ${Location.MM_IN_METER}):0;--}%
                        %{--return data.endLocation_plus ? Math.round(data.endLocation_plus * ${Location.MM_IN_METER}):0--}%
                    %{--}--}%
                %{--}, NumberSpinner)--}%

                %{--,Editor({--}%
                    %{--label: '${message(code: 'BridgeDataEntry.length.label')}',--}%
                    %{--field: 'length',--}%
                    %{--editOn: 'click',--}%
                    %{--sortable: false,--}%
                    %{--editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },--}%
                    %{--autoSave: false,--}%
                    %{--set: function (data) {--}%
                        %{--return data.length ? Math.round(data.length * ${Location.MM_IN_METER}) : 0;--}%
                    %{--}--}%
                %{--}, NumberSpinner)--}%

                ,Editor({
                    label: '${message(code: 'BridgeDataEntry.flooringType.label')}',
                    field: 'flooringType',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: flooringTypeOptionsData }),
                        labelProperty: "label"
                    }),
                        style: { width: "120px" }, sortByLabel: false},
                    sortable: false,
                    autoSave: false
                }, Select)

                ,Editor({
                    label: '${message(code: 'BridgeDataEntry.beginRailHeadValue.label')}',
                    field: 'beginRailHeadValue',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: { places:2} },
                    autoSave: false
                }, NumberSpinner)
                ,Editor({
                    label: '${message(code: 'BridgeDataEntry.axisRailHeadValue.label')}',
                    field: 'axisRailHeadValue',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: { places:2} },
                    autoSave: false
                }, NumberSpinner)
                ,Editor({
                    label: '${message(code: 'BridgeDataEntry.endRailHeadValue.label')}',
                    field: 'endRailHeadValue',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: { places:2} },
                    autoSave: false
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'BridgeDataEntry.lengthAbutmentLeft.label')}',
                    field: 'lengthAbutmentLeft',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, constraints: {min:0, places:3} },
                    autoSave: false,
                    set: function (data) {
                        return data.lengthAbutmentLeft ? Math.round(data.lengthAbutmentLeft * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)
                ,Editor({
                    label: '${message(code: 'BridgeDataEntry.lengthAbutmentRight.label')}',
                    field: 'lengthAbutmentRight',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, constraints: {min:0, places:3} },
                    autoSave: false,
                    set: function (data) {
                        return data.lengthAbutmentRight ? Math.round(data.lengthAbutmentRight * ${Location.MM_IN_METER}) : 0;
                    }
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'BridgeDataEntry.waterbodyName.label')}',
                    field: 'waterbodyName',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' } },
                    autoSave: false
                }, TextBox)

                ,Editor({
                    label: '${message(code: 'BridgeDataEntry.waterFlowDirection.label')}',
                    field: 'waterFlowDirection',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: waterFlowDirectionOptionsData }),
                        labelProperty: "label"
                    }),
                        style: { width: "120px" }, sortByLabel: false},
                    sortable: false,
                    autoSave: false
                }, Select)
            ]
        }, "bridges_grid");

        bridgesGrid.on("dgrid-select", function(event){
            if (!event.rows || event.rows.length > 1) {
                bridgeSpansGrid.set('store', undefined);
                bridgePiersGrid.set('store', undefined);
                return;
            }

            var bridgeId = event.rows[0].data.id;
            bridgeSpansGrid.set('store', new Observable(new JsonRest({target: bridgeSpanUrl.replace('666', bridgeId)})));
            bridgePiersGrid.set('store', new Observable(new JsonRest({target: bridgePierUrl.replace('666', bridgeId)})));
        });

        bridgesGrid.on("dgrid-deselect", function(event){
            bridgeSpansGrid.set('store', undefined);
            bridgePiersGrid.set('store', undefined);
        });

        bridgesGrid.on("dgrid-error", function(err) {
            handleStoreError(err, json, Tooltip, put);
        });

        ready(function() {
            var bridgesGridSave = registry.byId('toolbar1.save');
            var bridgesGridRefresh = registry.byId('toolbar1.refresh');
            var bridgesGridRemove = registry.byId('toolbar1.remove');
            var bridgesGridAdd = registry.byId('toolbar1.add');

            var bridgeSpansGridRemove = registry.byId('toolbar2.remove');
            var bridgeSpansGridAdd = registry.byId('toolbar2.add');

            var bridgePiersGridRemove = registry.byId('toolbar3.remove');
            var bridgePiersGridAdd = registry.byId('toolbar3.add');

            bridgesGridSave.on('click', function() {
                bridgesGrid.save();
            });

            bridgesGridRefresh.on('click', function() {
                bridgesGrid.revert();
            });

            bridgesGridAdd.on('click', function() {
                bridgesGrid.addNewRow();
            });

            bridgesGridRemove.on('click', function() {
                Utils.confirmDialog().then(
                        function(result) {
                            for (var id in bridgesGrid.selection) {
                                bridgesGrid.store.remove(id);
                            }
                        },
                        function(result) {/*NOP*/}
                )
            });

            bridgeSpansGridAdd.on('click', function() {
                bridgeSpansGrid.addNewRow();
            });

            bridgeSpansGridRemove.on('click', function() {
                Utils.confirmDialog().then(
                        function(result) {
                            for (var id in bridgeSpansGrid.selection) {
                                bridgeSpansGrid.store.remove(id);
                            }
                        },
                        function(result) {/*NOP*/}
                )
            });

            bridgePiersGridAdd.on('click', function() {
                bridgePiersGrid.addNewRow();
            });

            bridgePiersGridRemove.on('click', function() {
                Utils.confirmDialog({message: 'Вы уверены?'}).then(
                        function(result) {
                            for (var id in bridgePiersGrid.selection) {
                                bridgePiersGrid.store.remove(id);
                            }
                        },
                        function(result) {/*NOP*/}
                )
            });
        });


        registry.add(bridgesGrid);
        registry.add(bridgeSpansGrid);
        registry.add(bridgePiersGrid);

    });
</script>