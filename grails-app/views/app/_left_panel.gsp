<div data-dojo-type="dijit/layout/AccordionContainer" id="left_bar" extractContent="false" preventCache="false"
     preload="false" refreshOnShow="false" doLayout="true" region="left" splitter="true" maxSize="Infinity"
     style="width: 250px;">
        <div data-dojo-type="dijit/layout/ContentPane" title="${message(code: 'app.tree.project')}" selected="true">
            <g:render template="project_tree" model="[projectInstance: projectInstance]"/>
        </div>
        %{--<div data-dojo-type="dijit/layout/ContentPane" title="${message(code: 'app.tree.layout')}" >--}%
            %{--<g:render template="layout_tree" model="[projectInstance: projectInstance]"/>--}%
        %{--</div>--}%
</div>