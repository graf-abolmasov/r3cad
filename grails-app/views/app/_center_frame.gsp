<div data-dojo-type="dijit/layout/TabContainer" region="center" tabStrip="true" id="drawingsTabContainer">
    <g:each in="${projectInstance.drawings}" var="drawing">
        <div data-dojo-type="dijit/layout/ContentPane" extractContent="false" preventCache="false" preload="false"
             refreshOnShow="false" region="center" splitter="false" maxSize="Infinity" title="${drawing.name}">
            <div id="${drawing.class.simpleName}_${drawing.id}_container" class="drawing_container" style="height: 100%; width: 100%;"></div>
        </div>
    </g:each>
</div>

<g:javascript>
    require([
        'dojo/ready',
        'r3/drawing/DrawingScene',
        'dijit/registry'
    ], function (ready, DrawingScene, registry) {
        ready(function() {
            var initDrawing = function (id, targetLink) {
               var scene = new DrawingScene({
                    id: id,

                    loadingText: '<g:message code="app.drawing.is.loading.please.wait" />',

                    targetUrl: targetLink
                });

                registry.add(scene);

                scene.refresh();
            };

            <g:each in="${projectInstance.drawings}" var="drawing">
                initDrawing('${drawing.class.simpleName}_${drawing.id}_container',
                            '${createLink(controller: "drawing", action: 'show', id: drawing.id, params: [format: 'embeddedSvg', projectId: projectInstance.id])}')
            </g:each>
        })
    });
</g:javascript>