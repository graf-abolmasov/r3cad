define([
    'dojo/dom',
    'dojo/store/JsonRest',
    'dojo/store/Cache',
    'dojo/store/Memory',
    'dojo/store/Observable',
    'dojo/_base/lang',
    'dojo/_base/declare',
    'dojo/dom-construct',
    'dgrid/OnDemandGrid',
    'dgrid/Selection',
    'dgrid/Keyboard',
    'dijit/Toolbar',
    'dijit/ToolbarSeparator',
    'dijit/form/Button',
    'r3/Utils',
    "put-selector/put",
    'dojo/json',
    'dijit/Tooltip',
    "dojo/request/xhr",
    'dijit/layout/BorderContainer',
    'dijit/layout/LayoutContainer',
    'dijit/layout/SplitContainer',
    'dijit/layout/ContentPane',
    'r3/editor/TableEditor',
    'dgrid/extensions/DijitRegistry',
    'i18n'
], function (dom, JsonRest, Cache, Memory, Observable, lang, declare, domConstruct, OnDemandGrid, Selection,
             Keyboard, Toolbar, ToolbarSeparator, Button, Utils, put, json, Tooltip, req, BorderContainer,
             LayoutContainer, SplitContainer, ContentPane, TableEditor) {

    function detailsOrientation(editor) {
        if (editor.orientation === 'right' || editor.orientation === undefined) {
            return {
                detailsRegion: 'right',
                detailsStyle: 'width: 30%',
                detailRegion: 'top',
                detailStyle: 'height: ' + 100 / editor.details.length + '%'
            }
        } else if (editor.orientation === 'bottom') {
            return {
                detailsRegion: 'bottom',
                detailsStyle: 'height: 30%',
                detailRegion: 'left',
                detailStyle: 'width: ' + 100 / editor.details.length + '%'
            }
        }
    }

    function _initDetails(editor, details) {
        var detailsEditor = new TableEditor({
            saveButton: false,
            revertButton: false,
            columns: details.columns,
            restUrl: details.restUrl,
            createUrl: details.createUrl,
            restUrlMapping: details.restUrlTemplate,
            createUrlMapping: details.createUrlTemplate,
            setMasterId: function(masterId) {
                if (masterId === undefined && masterId == null) {
                    this.createUrl = undefined;
                    this.grid.set('store', undefined);
                } else {
                    this.createUrl = this.createUrlMapping.replace('MASTER_ID', masterId);
                    var restUrl = this.restUrlMapping.replace('MASTER_ID', masterId);
                    this.grid.set('store', new Observable(
                        new JsonRest({
                            target: restUrl,
                            accepts: 'application/json',
                            headers: {'Content-Type': 'application/json', 'Accept': 'application/json'}
                        })
                    ))
                }
            }
        });
        var detailsContentPane = new ContentPane({
            region: details.region || detailsOrientation(editor).detailRegion,
            style: details.style || detailsOrientation(editor).detailStyle,
            layoutPriority: details.layoutPriority || 0
        });
        detailsContentPane.addChild(detailsEditor);
        editor.detailsLayoutContainer.addChild(detailsContentPane);
        return detailsEditor;
    }

    return declare([TableEditor], {
        postCreate: function () {
            this.inherited('postCreate', arguments);

            if (this.details === undefined || this.details == null || this.details.length == 0) {
                return
            }

            this.detailsLayoutContainer = new LayoutContainer({
                region: this.detailsRegion || detailsOrientation(this).detailsRegion,
                style: this.detailsStyle || detailsOrientation(this).detailsStyle
            });
            this.addChild(this.detailsLayoutContainer);

            this.detailsEditors = [];
            for (var i = 0; i < this.details.length; i++){
                this.detailsEditors.push(_initDetails(this, this.details[i]));
            }

            var editor = this;
            editor.grid.on("dgrid-select", function(event){
                var i = 0;
                if (!event.rows || event.rows.length > 1) {
                    for (i = 0; i < editor.detailsEditors.length; i++) {
                        editor.detailsEditors[i].setMasterId(undefined);
                    }
                    return;
                }

                var masterId = event.rows[0].data.id;
                for (i = 0; i < editor.detailsEditors.length; i++) {
                    editor.detailsEditors[i].setMasterId(masterId)
                }
            });

            this.grid.on("dgrid-deselect", function(event){
                for (i = 0; i < editor.detailsEditors.length; i++) {
                    editor.detailsEditors[i].setMasterId(undefined);
                }
            });
        },
        startup: function() {
            this.inherited(arguments);
            this.grid.startup();
        }
    });
});