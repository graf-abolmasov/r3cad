<div class="form-group ${hasErrors(bean: dataSetInstance, field: 'countLayers', 'has-error')} required">
    <label class="control-label" for="countLayers"><g:message code="BallastDataSet.countLayers.label"/></label>
    <g:select id="countLayers" name="countLayers" from="${1..10}" value="${dataSetInstance.countLayers}"
              class="form-control" required=""/>
</div>
<g:each in="${0..dataSetInstance.countLayers-1}" var="i">
    <div class="form-group ${hasErrors(bean: dataSetInstance, field: 'name'+i, 'has-error')} required">
        <label class="control-label" for="name${i}"><g:message code="BallastDataSet.layerName.label" args="[i]"/></label>
        <g:textField id="name${i}" name="name${i}" value="${dataSetInstance."name${i}"}" class="form-control" required="" />
    </div>
</g:each>
