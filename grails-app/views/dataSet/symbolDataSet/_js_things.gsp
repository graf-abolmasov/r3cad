<%@ page import="com.r3.dataset.Location; com.r3.dataset.symbol.SymbolDistanceToWay; com.r3.dataset.symbol.SymbolDataEntry; com.r3.drawing.symbol.ConventionalSymbol; com.r3.RailWay" %>
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
    ], function(declare, OnDemandGrid, Editor, Keyboard, Selection, JsonRest, Observable, NumberSpinner,
                TextBox, Select, ObjectStore, Memory, req, registry, dom, ready, Utils, put, json, Tooltip) {
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

        var symbolOptionsDataStore = new Memory({
            data: [
<%
            def listOfSymbols = ConventionalSymbol.findAll()

            if (listOfSymbols) {
                def it = listOfSymbols.iterator()
                def s = it.next()
                out.append("{id:${s.id}, label:'${s.name}'}")
                while (it.hasNext()) {
                    s = it.next()
                    out.append(",{id:${s.id}, label:'${s.name}'}")
                }
            }
%>
            ]
        });

        var distanceToWayUrl = '${createLink(mapping: 'restDistanceToWay', params: [deid:666, dataEntryClass:SymbolDataEntry.name])}/';

        var distanceToWayGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                if (!(this.store)) {
                    return;
                }
                this.store.add({class:'${SymbolDistanceToWay.name}'});
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
                    }), style: { width: "120px" }, sortByLabel: false},
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

        var attributesUrl = '';

        var attributesGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                if (!(this.store)) {
                    return;
                }
                this.store.add({});
            }}))({
            columns: [
                { label: '', field: 'errors', sortable: false, showHeader:false}

                ,Editor({
                    label: '${message(code: 'SymbolAttribute.name.label')}',
                    field: 'railWay',
                    sortable: false,
                    autoSave: true,
                    editorArgs: {store: new ObjectStore({
                        objectStore: railWaysDataStore,
                        labelProperty: "label"
                    }), style: { width: "120px" }, sortByLabel: false},
                    set: function(data) {
                        return {id: data.railWay, class:'${RailWay.name}'};
                    }
                }, Select)

                ,Editor({
                    label: '${message(code: 'SymbolAttribute.value.label')}',
                    field: 'value',
                    editOn: 'click',
                    sortable: false,
                    editorArgs: { style: { 'width': '99%' }, constraints: {min: 0, places: 0} },
                    autoSave: true
                }, TextBox)
            ]

        }, "attributes_grid");

        attributesGrid.on("dgrid-error", function(err) {
            handleStoreError(err, json, Tooltip, put);
        });

        var mainGrid = new (declare([OnDemandGrid, Keyboard, Selection], {
            addNewRow: function() {
                var editor = this;
                req('${createLink(controller: 'dataEntry', action: 'create', params: [dataEntryClass:SymbolDataEntry.class.name, dsid: dataSetInstance.id])}', {
                    handleAs: 'json'
                }).then(function (data) {
                    editor.grid.store.add(data);
                });
            }}))({
            store: new Observable(new JsonRest({target: '${createLink(mapping: 'restDataEntry', params: [dsid:dataSetInstance.id, dataEntryClass:SymbolDataEntry.class.name])}/'})),
            cellNavigation: false,
            columns: [
                { label: '', field: 'errors', sortable: false, showHeader:false}
                ,<g:render template="km_pk_plus_columns" />

                ,Editor({
                    label: '${message(code: 'symbolDataEntry.symbolValue.label')}',
                    field: 'symbolValue',
                    editorArgs: {store: new ObjectStore({
                        objectStore: symbolOptionsDataStore,
                        labelProperty: "label"
                    }), style: { width: "120px" }, sortByLabel: false},
                    sortable: false,
                    autoSave: false,
                    get: function(data) {
                        return data.symbolValue;
                    },
                    set: function(data) {
                        return {id:  data.symbolValue, class: '${ConventionalSymbol.class.name}'};
                    }
                }, Select)

                ,Editor({
                    label:'${message(code: 'symbolDataEntry.label.label')}',
                    field:'label',
                    editOn:'click',
                    editorArgs: { style: { 'width': '99%' } },
                    sortable: false,
                    autoSave: false
                }, TextBox)
            ]
        }, "main_grid");

        mainGrid.on("dgrid-select", function(event){
            if (!event.rows || event.rows.length > 1) {
                distanceToWayGrid.set('store', undefined);
                attributesGrid.set('store', undefined);
                return;
            }

            var pillarId = event.rows[0].data.id;
            distanceToWayGrid.set('store', new Observable(new JsonRest({target: distanceToWayUrl.replace('666', pillarId)})));
            attributesGrid.set('store', new Observable(new JsonRest({target: attributesUrl.replace('666', pillarId)})));
        });

        mainGrid.on("dgrid-deselect", function(event){
            distanceToWayGrid.set('store', undefined);
            attributesGrid.set('store', undefined);
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

            var attributesGridRemove = registry.byId('toolbar3.remove');
            var attributesGridAdd = registry.byId('toolbar3.add');

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

            attributesGridAdd.on('click', function() {
                attributesGrid.addNewRow();
            });

            attributesGridRemove.on('click', function() {
                Utils.confirmDialog({message: 'Вы уверены?'}).then(
                        function(result) {
                            for (var id in attributesGrid.selection) {
                                attributesGrid.store.remove(id);
                            }
                        },
                        function(result) {/*NOP*/}
                )
            });
        });

        registry.add(mainGrid);
        registry.add(distanceToWayGrid);
        registry.add(attributesGrid);

        mainGrid.startup();
        distanceToWayGrid.startup();
        attributesGrid.startup();

    });

</script>