<g:set var="railWays" value="${dataSetInstance.project.railWays}"/>

<div class="form-group ${hasErrors(bean: dataSetInstance, field: 'firstRailWay', 'has-error')} required">
    <label class="control-label" for="firstRailWay"><g:message code="interWaySpaceDataSet.firstRailWay.label"/></label>
    <g:select id="firstRailWay" name="firstRailWay.id" from="${railWays}" value="${dataSetInstance.firstRailWayId}"
              optionKey="id" optionValue="label" class="form-control" required="" readonly="true" disabled="true"/>
</div>

<div class="form-group ${hasErrors(bean: dataSetInstance, field: 'firstRailWay', 'has-error')} required">
    <label class="control-label" for="secondRailWay"><g:message code="interWaySpaceDataSet.secondRailWay.label"/></label>
    <g:select id="secondRailWay" name="secondRailWay.id" from="${railWays}" value="${dataSetInstance.secondRailWayId}"
              optionKey="id" optionValue="label" class="form-control" required="" readonly="true" disabled="true"/>
</div>
