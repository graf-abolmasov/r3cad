<%@ page import="com.r3.drawing.area.StackedDrawArea; com.r3.drawing.area.PlainDrawArea; com.r3.dataset.DoubleDataSet; com.r3.drawing.layout.ProfileDrawing; com.r3.drawing.area.PlainDrawArea; com.r3.Project; grails.util.GrailsUtil; com.r3.dataset.GradientDataSet; com.r3.RailWay; com.r3.drawing.area.StackedDrawArea" %>
<div id="project_tree_div_container"></div>

<script type="text/javascript">
    require([
        "dojo/store/JsonRest",
        "dijit/tree/ObjectStoreModel",
        "dijit/Tree",
        'r3/Utils',
        "dojo/ready",
        'dijit/registry',
        "dojo/_base/window",
        "dojo/store/Observable",
        "dijit/tree/dndSource",
        "dijit/Menu",
        "dijit/MenuItem",
        "dojo/query!css2",
        "dojo/domReady!"
    ], function (JsonRest, ObjectStoreModel, Tree, Utils, ready, registry, window, Observable, dndSource) {
        ready(function () {
            var restStore = new JsonRest({
                target: '${createLink(controller: 'projectTree', action: 'children', params: [projectId: projectInstance.id])}/',
                getBeforePut: false,
                getChildren: function (object) {
                    return this.query({identity: object['identity']});
                },
                getIdentity: function (node) {
                    return node['identity']
                }
            });

            var treeModel = new ObjectStoreModel({
                store: restStore,
                labelAttr: 'label',
                query: '',
                mayHaveChildren: function (item) {
                    return !item['leaf'];
                },
                pasteItem: function(childItem, oldParentItem, newParentItem, bCopy, insertIndex, before){
                    childItem.copy = bCopy;
                    if (newParentItem) {
                        childItem.newParent = newParentItem;
                    }
                    if (oldParentItem) {
                        childItem.oldParent = oldParentItem;
                    }
                    if (before) {
                        childItem.before = before;
                    }
                    this.store.put(childItem, { overwrite: true }).then(function() {
                        tree.refresh();
                    });
                }
            });

            var tree = new Tree({
                id:'project_tree',
                model: treeModel,
                openOnClick: true,
                persist: true,
                showRoot: false,
                style: {height: '100%'},
                betweenThreshold: 5,
                dndController: dndSource,
                onDblClick: function (item) {
                    if (item.editorUrl) {
                        Utils.openInExtWindow(item.editorUrl, item.id, item.editorWidth, item.editorHeight);
                    }
                },
                getIconClass: function (/*dojo.store.Item*/ item, /*Boolean*/ opened) {
                    if ((item.iconClass === undefined)) {
                        return opened ? " dijitFolderOpened " : " dijitFolderClosed "
                    }

                    return ' r3-small-icon ' + item.iconClass;
                },
                getLabelClass: function (/*dojo.store.Item*/ item, /*Boolean*/ opened) {
                    var result = '';
                    if (!item.visible) {
                        result += ' projectTreeNonVisibleNode '
                    }
                    if (!item.editorUrl) {
                        result += ' projectTreeNonEditableNode '
                    }
                    return result;

                },
                refresh: function() {
                    this.dndController.selectNone(); // As per the answer below
                    this.model.store.clearOnClose = true;

                    this._itemNodesMap = {};
                    this.rootNode.state = "UNCHECKED";
                    this.model.root.children = null;

                    this.rootNode.destroyRecursive();

                    this.model.constructor(tree.model);

                    this.postMixInProperties();
                    this._load();
                }
//                getRowClass: function (item, opened) {
//                    return item.itemClass.substring(item.itemClass.lastIndexOf('.') + 1, item.itemClass.length);
//                }
//                checkItemAcceptance: function(target, source, position) {
//                    if (source && target) {
//                        var s_nodes = source.anchor;  //get the dragged tree row's div
//                        var s_id = s_nodes.id;          //get the id of the dragged div
//                        var s_dragDndItem = source.getItem(s_id);   //get the dnd item for the dragged div
//                        var s_dragTreeNode = s_dragDndItem.data;    //get the treenode of the dragged div
//                        var s_dragItem = s_dragTreeNode.item;       //get the store item bound to the dragged treenode
//
//                        var inspectParent = (position !== undefined && position != null && position != 'over');
//                        var t_dragTreeNode = dijit.getEnclosingWidget(target);
//                        if (inspectParent) {
//                            t_dragTreeNode = dijit.getEnclosingWidget(t_dragTreeNode.domNode.parentNode);
//                        }
//                        var t_dragItem = t_dragTreeNode.item;       //get the store item bound to the dragged treenode
//
//                        if (s_dragItem.itemClass == '${StackedDrawArea.class.name}') {
//                            return t_dragItem.itemClass == '${StackedDrawArea.class.name}'
//                                    || t_dragItem.itemClass == '${ProfileDrawing.class.name}'
//                        }
//                        if (s_dragItem.itemClass.indexOf('DataSet') != -1 ) {
//                            return t_dragItem.itemClass == '${StackedDrawArea.class.name}'
//                                    || t_dragItem.itemClass == '${Project.class.name}'
//                                    || t_dragItem.itemClass == '${RailWay.class.name}'
//                                    || t_dragItem.itemClass == '${PlainDrawArea.class.name}'
//                        }
//                    }
//                    return false
//                }


            }, "project_tree_div_container");

            tree.startup();

            %{--var menu = new Menu({--}%
                %{--targetNodeIds: ["project_tree"],--}%
                %{--selector: ".${DoubleDataSet.simpleName}"--}%
            %{--});--}%
            %{--menu.addChild(new MenuItem({--}%
                %{--label: "Удалить",--}%
                %{--iconClass: "r3-small-icon removeIcon",--}%
                %{--onClick: function(evt){--}%
                    %{--var tn = dijit.getEnclosingWidget(menu.currentTarget);--}%
                    %{--restStore.remove(restStore.getIdentity(tn.item));--}%
                %{--}--}%
            %{--}));--}%

        });
    });
</script>