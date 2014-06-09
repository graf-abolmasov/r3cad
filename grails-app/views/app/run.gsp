<%@ page import="com.r3.feedback.RefreshProjectTreeEvent" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="dojo"/>
    <title><g:message code="app.title" args="${[projectInstance.name.encodeAsHTML()]}"/></title>
</head>

<body class="claro" id="r3app">

    <script type="dojo/require">
        Utils: "r3/Utils",
        Memory: "dojo/store/Memory",
        BorderContainer: "dijit/layout/BorderContainer",
        ContentPane: "dijit/layout/ContentPane",
        MenuBar: "dijit/MenuBar",
        PopupMenuBarItem: "dijit/PopupMenuBarItem",
        MenuItem: "dijit/MenuItem",
        Menu: "dijit/Menu",
        TabContainer: "dijit/layout/TabContainer",
        MenuSeparator: "dijit/MenuSeparator",
        Tree: "dijit/Tree",
        AccordionContainer: "dijit/layout/AccordionContainer",
        registry: "dijit/registry",
        query: "dojo/query!css2"
    </script>

    <div data-dojo-type="dijit/layout/BorderContainer" design="headline" persist="false" gutters="true"
         style="min-width: 1em; min-height: 1px; z-index: 0; width: 100%; height: 100%;">

        <g:render template="menu_bar" model="[projectInstance: projectInstance]" />

        %{--<g:render template="bottom_console" model="[projectInstance: projectInstance]"/>--}%

        <g:render template="left_panel" model="[projectInstance: projectInstance]" />

        %{--<g:render template="right_panel" model="[projectInstance: projectInstance]"/>--}%

        <g:render template="center_frame" model="[projectInstance: projectInstance]" />

    </div>

<g:javascript>
    var request = {
        url: '${createLink(uri: '/feedback/' + projectInstance.id, base: '//' + request.getLocalAddr() + ':8080' + request.getContextPath())}',
        transport: 'websocket',
        fallbackTransport: 'long-polling',
        enableProtocol: true,
        trackMessageLength: true
    };

    request.onMessage = function (response) {
        var data = JSON.parse(response.responseBody);
        if (data.action == '${RefreshProjectTreeEvent.ACTION}') {
            var tree = registry.byId('project_tree');

            tree.dndController.selectNone(); // As per the answer below
            tree.model.store.clearOnClose = true;

            tree._itemNodesMap = {};
            tree.rootNode.state = "UNCHECKED";
            tree.model.root.children = null;

            tree.rootNode.destroyRecursive();

            tree.model.constructor(tree.model);

            tree.postMixInProperties();
            tree._load();
        }
    };
</g:javascript>
</body>
</html>

