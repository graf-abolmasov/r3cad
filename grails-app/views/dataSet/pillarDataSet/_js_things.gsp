<%@ page import="com.r3.dataset.pillar.IronCrampAnchorType; com.r3.dataset.pillar.WireSuspensionType; com.r3.dataset.pillar.PillarType; com.r3.dataset.Location; com.r3.dataset.pillar.WireHeight; com.r3.dataset.pillar.PillarDistanceToWay; com.r3.dataset.DistanceToWay; com.r3.dataset.pillar.PillarDataEntry; com.r3.RailWay" %>
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
        "dijit/form/NumberTextBox",
        "r3/editor/MmAsMeterTextBox",
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
    ], function(declare, OnDemandGrid, Editor, Keyboard, Selection, JsonRest, Observable, NumberSpinner, MmAsMeterTextBox, TextBox, Select,
                ObjectStore, Memory, req, registry, dom, ready, Utils, put, json, Tooltip) {
        var railWaysDataStore = new Memory({
            data: [
<%
            def railWays = RailWay.findAllByProject(dataSetInstance.project)
            if (railWays) {
                def it = railWays.iterator()
                def rw = it.next()
                out.append("{id:${rw.id}, label:'${rw.label}'}")
                while (it.hasNext()) {
                    rw = it.next()
                    out.append(",{id:${rw.id}, label:'${rw.label}'}")
                }
            }
%>
            ]
        });

        var distanceToWayUrl = '${createLink(mapping: 'restDistanceToWay', params: [deid:666, dataEntryClass:PillarDataEntry.name])}/';

        var distanceToWayGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                if (!(this.store)) {
                    return;
                }
                this.store.add({class:'${PillarDistanceToWay.name}'});
            }}))({
            columns: [
                { label: '', field: 'errors', sortable: false, showHeader:false}

                ,Editor({
                    label: '${message(code: 'DistanceToWay.railWay.label')}',
                    field: 'railWay',
                    sortable: false,
                    autoSave: true,
                    editorArgs: {store: new ObjectStore({
                        objectStore: railWaysDataStore,
                        labelProperty: "label"
                    }), style: { width: "50px" }, sortByLabel: false},
                    set: function(data) {
                        return {id: data.railWay, class:'${RailWay.name}'};
                    }
                }, Select)

                ,Editor({
                    label: '${message(code: 'DistanceToWay.distance.label')}',
                    field: 'distance',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, smallDelta: 0.1 },
                    autoSave: true,
                    set: function(data) {
                        return data.distance * ${Location.MM_IN_METER};
                    }
                }, NumberSpinner)
            ]

        }, "distance_to_way_grid");

        distanceToWayGrid.on("dgrid-error", function(err) {
            handleStoreError(err, json, Tooltip, put);
        });

        var wireHeightUrl = '${createLink(mapping: 'restWireHeight', params: [projectId:666])}/';

        var wireHeightGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                if (!(this.store)) {
                    return;
                }
                this.store.add({});
            }}))({
            columns: [
                { label: '', field: 'errors', sortable: false, showHeader:false}

                ,Editor({
                    label: '${message(code: 'WireHeight.railWay.label')}',
                    field: 'railWay',
                    sortable: false,
                    autoSave: true,
                    editorArgs: {store: new ObjectStore({
                        objectStore: railWaysDataStore,
                        labelProperty: "label"
                    }), style: { width: "50px" }, sortByLabel: false},
                    set: function(data) {
                        return {id: data.railWay, class:'${RailWay.name}'};
                    }
                }, Select)

                ,Editor({
                    label: '${message(code: 'WireHeight.height.label')}',
                    field: 'height',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, constraints: {min: 0, places: 0} },
                    autoSave: true
                }, NumberSpinner)
            ]

        }, "wire_height_grid");

        wireHeightGrid.on("dgrid-error", function(err) {
            handleStoreError(err, json, Tooltip, put);
        });

        var pillarTypeOptionsData = [
            { id: '', label: '${message(code:"default.select.not_selected")}'}
            ,{ id: '${PillarType.STEEL.name()}', label: '${message(code:"${PillarType.name}.STEEL")}'}
            ,{ id: '${PillarType.REINFORCED_CONCRETE.name()}', label: '${message(code:"${PillarType.name}.REINFORCED_CONCRETE")}'}
        ];

        var wireSuspensionTypeOptionsData = [
            { id: '', label: '${message(code:"default.select.not_selected")}'}
            ,{ id: '${WireSuspensionType.ABSENT.name()}', label: '${message(code:"${WireSuspensionType.name}.ABSENT")}'}
            ,{ id: '${WireSuspensionType.FLEXIBLE.name()}', label: '${message(code:"${WireSuspensionType.name}.FLEXIBLE")}'}
            ,{ id: '${WireSuspensionType.PORTAL_CONSTRUCTION.name()}', label: '${message(code:"${WireSuspensionType.name}.PORTAL_CONSTRUCTION")}'}
            ,{ id: '${WireSuspensionType.USUAL.name()}', label: '${message(code:"${WireSuspensionType.name}.USUAL")}'}
        ];

        var ironCrampAnchorTypeOptionsData = [
            { id: '', label: '${message(code:"default.select.not_selected")}'}
            ,{ id: '${IronCrampAnchorType.SINGLE.name()}', label: '${message(code:"${IronCrampAnchorType.name}.SINGLE")}'}
            ,{ id: '${IronCrampAnchorType.DOUBLE.name()}', label: '${message(code:"${IronCrampAnchorType.name}.DOUBLE")}'}
        ];

        var mainGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                var grid = this;
                req('${createLink(controller: 'dataEntry', action: 'create', params: [dataEntryClass:PillarDataEntry.name, dsid: dataSetInstance.id])}', {
                    handleAs: 'json'
                }).then(function (data) {
                    grid.store.add(data);
                });
            }}))({
            store: new Observable(new JsonRest({target: '${createLink(mapping: 'restDataEntry', params: [dsid:dataSetInstance.id, dataEntryClass:PillarDataEntry.name])}/'})),
            columns: [
                { label: '', field: 'errors', sortable: false, showHeader:false}

                ,Editor({
                    label: '${message(code: 'dataEntry.km.label')}',
                    field: 'km',
//                    editOn: 'click',
                    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, places: 0}},
                    sortable: false,
                    autoSave: false
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'dataEntry.pk.label')}',
                    field: 'pk',
//                    editOn: 'click',
                    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, max: 9, places: 0}},
                    sortable: false,
                    autoSave: false
                }, NumberSpinner)

                ,Editor({
                    label: '${message(code: 'dataEntry.plus.label')}',
                    field: 'plus',
//                    editOn: 'click,dgrid-cellfocusin',
                    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0}},
                    sortable: false,
                    autoSave: false
                    %{--set: function (data) {--}%
                        %{--return data.plus ? Math.round(data.plus * ${Location.MM_IN_METER}) : 0;--}%
                    %{--}--}%
                }, MmAsMeterTextBox)

                ,Editor({
                    label: '${message(code: 'PillarDataEntry.type.label')}',
                    field: 'type',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: pillarTypeOptionsData }),
                        labelProperty: "label"
                    }),
                    style: { width: "99%" }, sortByLabel: false},
                    sortable: false,
                    autoSave: false,
                    set: function(data) {
                        return '' == data.type ? null : data.type;
                    }
                }, Select)

                ,Editor({
                    label: '${message(code: 'PillarDataEntry.number.label')}',
                    field: 'number',
//                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' } },
                    autoSave: false
                }, TextBox)

                ,Editor({
                    label: '${message(code: 'PillarDataEntry.alternativeName.label')}',
                    field: 'alternativeName',
//                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' } },
                    autoSave: false
                }, TextBox)

                ,Editor({
                    label: '${message(code: 'PillarDataEntry.leftWireSuspensionType.label')}',
                    field: 'leftWireSuspensionType',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: wireSuspensionTypeOptionsData }),
                        labelProperty: "label"
                    }),
                        style: { width: "99%" }, sortByLabel: false},
                    sortable: false,
                    autoSave: false,
                    set: function(data) {
                        return '' == data.leftWireSuspensionType ? null : data.leftWireSuspensionType;
                    }

                }, Select)

                ,Editor({
                    label: '${message(code: 'PillarDataEntry.rightWireSuspensionType.label')}',
                    field: 'rightWireSuspensionType',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: wireSuspensionTypeOptionsData }),
                        labelProperty: "label"
                    }),
                        style: { width: "99%" }, sortByLabel: false},
                    sortable: false,
                    autoSave: false,
                    set: function(data) {
                        return '' == data.rightWireSuspensionType ? null : data.rightWireSuspensionType;
                    }
                }, Select)

                ,Editor({
                    label: '${message(code: 'PillarDataEntry.beforeIronCrampAnchorType.label')}',
                    field: 'beforeIronCrampAnchorType',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: ironCrampAnchorTypeOptionsData }),
                        labelProperty: "label"
                    }),
                        style: { width: "99%" }, sortByLabel: false},
                    sortable: false,
                    autoSave: false,
                    set: function(data) {
                        return '' == data.beforeIronCrampAnchorType ? null : data.beforeIronCrampAnchorType;
                    }
                }, Select)

                ,Editor({
                    label: '${message(code: 'PillarDataEntry.afterIronCrampAnchorType.label')}',
                    field: 'afterIronCrampAnchorType',
                    editorArgs: {store: new ObjectStore({
                        objectStore: new Memory({ data: ironCrampAnchorTypeOptionsData }),
                        labelProperty: "label"
                    }),
                        style: { width: "99%" }, sortByLabel: false},
                    sortable: false,
                    autoSave: false,
                    set: function(data) {
                        return '' == data.afterIronCrampAnchorType ? null : data.afterIronCrampAnchorType;
                    }
                }, Select)
            ]
        }, "main_grid");

        mainGrid.on("dgrid-select", function(event){
            if (!event.rows || event.rows.length > 1) {
                distanceToWayGrid.set('store', undefined);
                wireHeightGrid.set('store', undefined);
                return;
            }

            var pillarId = event.rows[0].data.id;
            distanceToWayGrid.set('store', new Observable(new JsonRest({target: distanceToWayUrl.replace('666', pillarId)})));
            wireHeightGrid.set('store', new Observable(new JsonRest({target: wireHeightUrl.replace('666', pillarId)})));
        });

        mainGrid.on("dgrid-deselect", function(event){
            distanceToWayGrid.set('store', undefined);
            wireHeightGrid.set('store', undefined);
        });

        mainGrid.on("dgrid-error", function(err) {
            handleStoreError(err, json, Tooltip, put);
        });

        ready(function() {
            var mainGridSave = registry.byId('toolbar1.save');
            var mainGridRefresh = registry.byId('toolbar1.refresh');
            var mainGridRemove = registry.byId('toolbar1.remove');
            var mainGridAdd = registry.byId('toolbar1.add');

            var distanceToWayGridRemove = registry.byId('toolbar2.remove');
            var distanceToWayGridAdd = registry.byId('toolbar2.add');

            var wireHeightGridRemove = registry.byId('toolbar3.remove');
            var wireHeightGridAdd = registry.byId('toolbar3.add');

            mainGridSave.on('click', function() {
                mainGrid.save();
            });

            mainGridRefresh.on('click', function() {
                mainGrid.revert();
            });

            mainGridAdd.on('click', function() {
                mainGrid.addNewRow();
            });

            mainGridRemove.on('click', function() {
                Utils.confirmDialog().then(
                        function(result) {
                            for (var id in mainGrid.selection) {
                                mainGrid.store.remove(id);
                            }
                        },
                        function(result) {/*NOP*/}
                )
            });

            distanceToWayGridAdd.on('click', function() {
                distanceToWayGrid.addNewRow();
            });

            distanceToWayGridRemove.on('click', function() {
                Utils.confirmDialog().then(
                        function(result) {
                            for (var id in distanceToWayGrid.selection) {
                                distanceToWayGrid.store.remove(id);
                            }
                        },
                        function(result) {/*NOP*/}
                )
            });

            wireHeightGridAdd.on('click', function() {
                wireHeightGrid.addNewRow();
            });

            wireHeightGridRemove.on('click', function() {
                Utils.confirmDialog({message: 'Вы уверены?'}).then(
                        function(result) {
                            for (var id in wireHeightGrid.selection) {
                                wireHeightGrid.store.remove(id);
                            }
                        },
                        function(result) {/*NOP*/}
                )
            });
        });


        registry.add(mainGrid);
        registry.add(distanceToWayGrid);
        registry.add(wireHeightGrid);

    });
</script>