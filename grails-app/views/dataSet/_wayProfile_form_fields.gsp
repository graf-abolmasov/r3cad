<div class="form-group checkbox ${hasErrors(bean: dataSetInstance, field: 'aggregateRailHeadDataSet', 'has-error')} required">
    <label class="control-label" for="aggregateRailHeadDataSet">
        <g:checkBox name="aggregateRailHeadDataSet" value="${dataSetInstance?.aggregateRailHeadDataSet}"/>
        <g:message code="WayProfileDataSet.aggregateRailHeadDataSet.label"/>
    </label>
</div>

<div class="form-group checkbox ${hasErrors(bean: dataSetInstance, field: 'aggregateBridgeDataSet', 'has-error')} required">
    <label class="control-label" for="aggregateBridgeDataSet">
        <g:checkBox name="aggregateBridgeDataSet" value="${dataSetInstance?.aggregateBridgeDataSet}"/>
        <g:message code="WayProfileDataSet.aggregateBridgeDataSet.label"/>
    </label>
</div>

<div class="form-group checkbox ${hasErrors(bean: dataSetInstance, field: 'aggregateTurnoutDataSet', 'has-error')} required">
    <label class="control-label" for="aggregateTurnoutDataSet">
        <g:checkBox name="aggregateTurnoutDataSet" value="${dataSetInstance?.aggregateTurnoutDataSet}"/>
        <g:message code="WayProfileDataSet.aggregateTurnoutDataSet.label"/>
    </label>
</div>
