<%@ page import="com.r3.tree.ProjectTreeNode" %>
<span data-dojo-type="dijit/MenuBar" tabIndex="0" region="top" splitter="false" maxSize="Infinity">
    <span data-dojo-type="dijit/PopupMenuBarItem" label="${message(code:'app.menu.project')}" iconClass="dijitNoIcon">
        <span data-dojo-type="dijit/Menu">
            <span data-dojo-type="dijit/MenuItem" label="${message(code:'app.menu.project.open')}" onclick="Utils.openSameWindow('${createLink(mapping: 'app', action: 'open')}')"></span>
            <span data-dojo-type="dijit/MenuSeparator"></span>
            %{--<span data-dojo-type="dijit/MenuItem" label="${message(code:'app.menu.project.history')}" onclick="notImplementedYet()"></span>--}%
            <span data-dojo-type="dijit/MenuItem" label="${message(code:'app.menu.project.properties')}"
                  data-dojo-props="iconClass:'r3-small-icon settingsIcon'"
                  onclick="Utils.openSameWindow('${createLink(controller: 'project', action: 'edit', id: projectInstance.id)}')"></span>
            %{--<span data-dojo-type="dijit/MenuItem" label="${message(code:'app.menu.project.info')}" onclick="notImplementedYet()"></span>--}%
        </span>
    </span>

    <span data-dojo-type="dijit/PopupMenuBarItem" label="${message(code: 'app.menu.data')}" iconClass="dijitNoIcon">
        <span data-dojo-type="dijit/Menu">
            <g:each in="${projectInstance.dataSets}" var="dataSet">
                    <g:if test="${!dataSet.railWay}">
                        <span data-dojo-type="dijit/PopupMenuItem" label="${dataSet.title.encodeAsHTML()}"
                              data-dojo-props="iconClass:'r3-small-icon ${dataSet.metaInfo.calculated ? 'calculator-icon ' : 'table-edit-icon '}'">
                            <span data-dojo-type="dijit/Menu">
                                <g:if test="${!dataSet.metaInfo.calculated}">
                                    <span data-dojo-type="dijit/MenuItem"
                                          label="${message(code: 'app.menu.data.editValues')}"
                                          onclick="Utils.openInExtWindow('${createLink(controller: 'dataSet', action: 'editValues', id: dataSet.id)}', '${ProjectTreeNode.getIdentity(dataSet)}', 800, 600)"></span>
                                    <span data-dojo-type="dijit/MenuSeparator"></span>
                                </g:if>
                                <span data-dojo-type="dijit/MenuItem" label="${message(code:'app.menu.data.settings')}"
                                      data-dojo-props="iconClass:'r3-small-icon settingsIcon'"
                                      onclick="Utils.openInExtWindow('${createLink(controller: 'dataSet', action: 'edit', id: dataSet.id)}', '${ProjectTreeNode.getIdentity(dataSet)}', 800, 600)"></span>
                            </span>
                        </span>
                    </g:if>
            </g:each>

            <span data-dojo-type="dijit/MenuSeparator"></span>

            <g:each in="${projectInstance.railWays}" var="railWay">
                <span data-dojo-type="dijit/PopupMenuItem" label="${railWay.label}">
                    <span data-dojo-type="dijit/Menu">
                        <g:each in="${railWay.dataSets}" var="dataSet">
                            <span data-dojo-type="dijit/PopupMenuItem" label="${dataSet.title.encodeAsHTML()}"
                                  data-dojo-props="iconClass:'r3-small-icon ${dataSet.metaInfo.calculated ? 'calculator-icon ' : 'table-edit-icon '}'">
                                <span data-dojo-type="dijit/Menu">
                                    <g:if test="${!dataSet.metaInfo.calculated}">
                                        <span data-dojo-type="dijit/MenuItem"
                                              label="${message(code: 'app.menu.data.editValues')}"
                                              onclick="Utils.openInExtWindow('${createLink(controller: 'dataSet', action: 'editValues', id: dataSet.id)}', '${ProjectTreeNode.getIdentity(dataSet)}', 800, 600)"></span>
                                        <span data-dojo-type="dijit/MenuSeparator"></span>
                                    </g:if>
                                    <span data-dojo-type="dijit/MenuItem" label="${message(code:'app.menu.data.settings')}"
                                          data-dojo-props="iconClass:'r3-small-icon settingsIcon'"
                                          onclick="Utils.openInExtWindow('${createLink(controller: 'dataSet', action: 'edit', id: dataSet.id)}', '${ProjectTreeNode.getIdentity(dataSet)}', 800, 600)"></span>
                                </span>
                            </span>
                        </g:each>
                    </span>
                </span>
            </g:each>

            <span data-dojo-type="dijit/MenuSeparator"></span>

            <span data-dojo-type="dijit/MenuItem" label="${message(code: 'app.menu.data.dataSets')}"
                  onclick="Utils.openInExtWindow('${createLink(controller: 'project', action: 'editDataSets', id: projectInstance.id)}', 'DataSetsTableEditor', 800, 600)"></span>
            <span data-dojo-type="dijit/MenuItem" label="${message(code: 'app.menu.data.railWays')}"
                  onclick="Utils.openInExtWindow('${createLink(controller: 'project', action: 'editRailWays', id: projectInstance.id)}', 'RailWaysTableEditor', 300, 200)"></span>
        </span>
    </span>

    <span data-dojo-type="dijit/PopupMenuBarItem" label="${message(code: 'app.menu.drawings')}" iconClass="dijitNoIcon">
        <span data-dojo-type="dijit/Menu">
            <g:each in="${projectInstance.drawings}" var="drawing">
                <span data-dojo-type="dijit/PopupMenuItem" label="${drawing.name}">
                    <span data-dojo-type="dijit/Menu">
                        %{--<span data-dojo-type="dijit/MenuItem" label="${message(code: 'app.menu.drawings.layout')}" onclick="notImplementedYet()"></span>--}%
                        <span data-dojo-type="dijit/MenuItem" label="${message(code: 'app.menu.drawings.preferences')}"
                              data-dojo-props="iconClass:'r3-small-icon settingsIcon'"
                              onclick="Utils.openInExtWindow('${createLink(controller: 'drawing', action: 'edit', id: drawing.id, params: [projectId: projectInstance.id])}', '${drawing.getClass().simpleName}Editor', 800, 950)"></span>

                        <span data-dojo-type="dijit/PopupMenuItem" label="${message(code:'app.menu.export')}" iconClass="dijitNoIcon">
                            <span data-dojo-type="dijit/Menu">
                                %{--<span data-dojo-type="dijit/MenuItem" label="${message(code:message(code:"app.menu.export.as.pdf"))}"--}%
                                      %{--data-dojo-props="iconClass:'r3-small-icon exportAsPdfIcon'"--}%
                                      %{--onclick="Utils.openInExtWindow('${createLink(mapping: 'exportPdf', id: drawing.id)}', 'full.pdf', Math.round(screen.width*0.8), Math.round(screen.height*0.8))"></span>--}%
                                <span data-dojo-type="dijit/MenuItem" label="${message(code:'app.menu.export.as_svg')}"
                                      data-dojo-props="iconClass:'r3-small-icon exportAsSvgIcon'"
                                      onclick="Utils.openInExtWindow('${createLink(controller: 'drawing', action: 'show', params: [projectId: projectInstance.id, format: 'svg'], id: drawing.id)}', '${drawing.id}.svg', Math.round(screen.width*0.8), Math.round(screen.height*0.8))" ></span>
                                <span data-dojo-type="dijit/MenuItem" label="${message(code:message(code:"app.menu.export.as.lsp"))}"
                                      onclick="Utils.openInExtWindow('${createLink(controller: 'drawing', action: 'show', params: [projectId: projectInstance.id, format: 'lisp'], id: drawing.id)}', '${drawing.id}.lsp', Math.round(screen.width*0.8), Math.round(screen.height*0.8))"></span>
                            </span>
                        </span>
                    </span>
                </span>
            </g:each>
        </span>
    </span>

    %{--<span data-dojo-type="dijit/PopupMenuBarItem" label="${message(code: 'app.menu.preferences')}" iconClass="dijitNoIcon">--}%
        %{--<span data-dojo-type="dijit/Menu">--}%
            %{--<span data-dojo-type="dijit/MenuItem" label="${message(code: 'app.menu.preferences.interface')}" onclick="notImplementedYet()"></span>--}%
        %{--</span>--}%
    %{--</span>--}%

    <div data-dojo-type="dijit/PopupMenuBarItem" label="${message(code:message(code:"app.menu.refresh"))}">
        <div data-dojo-type="dijit/Menu">
            <g:each in="${projectInstance.drawings}" var="drawing">
                <span data-dojo-type="dijit/MenuItem" label="${drawing.name}"
                      onclick="registry.byId('${drawing.class.simpleName}_${drawing.id}_container').refresh();"></span>
            </g:each>
        </div>
    </div>

</span>

<script>
    function notImplementedYet() {
        alert('${message(code: 'not.implemented.yet')}')
    }
</script>