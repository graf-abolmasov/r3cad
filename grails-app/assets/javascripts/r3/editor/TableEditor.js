define([
    'dojo/dom',
    'dojo/store/JsonRest',
    'dojo/store/Memory',
    'dojo/store/Observable',
    'dojo/_base/declare',
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
    'dijit/layout/ContentPane',
    'dgrid/extensions/DijitRegistry',
    'i18n'
], function (dom, JsonRest, Memory, Observable, declare, OnDemandGrid, Selection, Keyboard, Toolbar,
             ToolbarSeparator, Button, Utils, put, json, Tooltip, req, BorderContainer, ContentPane,
             DijitRegistry, i18n) {

    function _initToolBar(editor) {
        var EditorToolBar = declare([Toolbar], {
            postCreate: function () {
                this.inherited('postCreate', arguments);

                if (editor.saveButton != false) {
                    if (typeof (editor.saveButton) !== 'object') {
                        editor.saveButton = new Button({
                            label: i18n.t('tableEditor.toolbar.save'),
                            iconClass: 'r3-small-icon save-icon',
                            showLabel: false,
                            onClick: function () {
                                editor.grid.save();
                            }
                        });
                    }
                    this.addChild(editor.saveButton);
                }

                if (editor.revertButton != false) {
                    if (typeof (editor.revertButton) !== 'object') {
                        editor.revertButton = new Button({
                            label: i18n.t('tableEditor.toolbar.refresh'),
                            showLabel: false,
                            iconClass: 'r3-small-icon refresh-icon',
                            onClick: function () {
                                editor.grid.revert();
                            }
                        });
                    }
                    this.addChild(editor.revertButton);
                }

                if (editor.saveButton || editor.revertButton) {
                    this.addChild(new ToolbarSeparator());
                }

                if (editor.addButton != false) {
                    if (typeof (editor.addButton) !== 'object') {
                        editor.addButton = new Button({
                            label: i18n.t('tableEditor.toolbar.add'),
                            showLabel: false,
                            iconClass: 'r3-small-icon add-icon',
                            onClick: function () {
                                editor.addButton.set('disabled', true);
                                editor.addNewRow();
                            }
                        });
                    }
                    this.addChild(editor.addButton);
                }

                if (editor.removeButton != false) {
                    if (typeof (editor.removeButton) !== 'object') {
                        editor.removeButton = new Button({
                            label: i18n.t('tableEditor.toolbar.remove'),
                            showLabel: false,
                            iconClass: 'r3-small-icon remove-icon',
                            onClick: function () {
                                Utils.confirmDialog().then(
                                    function (result) {
                                        for (var id in editor.grid.selection) {
                                            editor.grid.store.remove(id);
                                        }
                                    }
                                )
                            }
                        });
                    }
                    this.addChild(editor.removeButton);
                }
            }
        });
        editor.toolbar = new EditorToolBar();
        editor.toolbarPane = new ContentPane({ region: 'top' });
        editor.toolbarPane.addChild(editor.toolbar);
        editor.addChild(editor.toolbarPane);
    }

    function _initGrid(editor) {
        var errorColumn = [
            { label: '', field: 'errors', sortable: false, showHeader: false }
        ];

        var columns = errorColumn.concat(editor.columns);

        var EditorGrid = declare([OnDemandGrid, Selection, Keyboard, DijitRegistry], {
            getBeforePut: true,
            columns: columns,
            loadingMessage: i18n.t('default.loading'),
            noDataMessage: i18n.t('default.noData'),
            store: editor.restUrl ? new Observable(
                new JsonRest({
                    target: editor.restUrl,
                    accepts: 'application/json',
                    headers: {'Content-Type': 'application/json', 'Accept': 'application/json'}
                })
            ) : undefined
        });

        editor.grid = new EditorGrid();
        editor.grid.on("dgrid-error", function (err) {
            try {
                var obj = json.parse(err.error.responseText);
                var row = err.grid.row(err.error.id);

                //Add Tooltip of record
                var errors = '<ul>';
                if (obj && obj.errors) {
                    obj.errors.forEach(function (item) {
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
                console.log(err);
            }
        });

        editor.contentPane = new ContentPane({ region: 'center' });
        editor.contentPane.addChild(editor.grid);
        editor.addChild(editor.contentPane);
    }

    return declare([BorderContainer], {
        columns: [
            {label: 'Id', field: 'id', sortable: false},
            {label: 'Class', field: 'class', sortable: false}
        ],
        createUrl: TableEditorConfig.createUrl,
        restUrl: TableEditorConfig.restUrl,

        postCreate: function () {
            this.inherited('postCreate', arguments);

            _initToolBar(this);
            _initGrid(this);
        },

        addNewRow: function () {
            if (this.createUrl === undefined || this.createUrl == null || this.createUrl == '') {
                return
            }

            var editor = this;
            req(editor.createUrl, {
                handleAs: 'json',
                headers: {'Content-Type': 'application/json', 'Accept': 'application/json'}
            }).then(function (data) {
                editor.grid.store.add(data);
                setTimeout(function () {
                    editor.addButton.set('disabled', false)
                }, 150);
            });
        },

        startup: function () {
            this.inherited(arguments);
            this.grid.startup();
        }
    });
});