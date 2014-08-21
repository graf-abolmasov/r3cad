<%@ page import="com.r3.RailWay; com.r3.Project; com.r3.dataset.DataSet" %>

<g:hiddenField name="id" value="${dataSetInstance?.id}" />
<g:hiddenField name="version" value="${dataSetInstance?.version}" />

<div class="form-group ${hasErrors(bean: dataSetInstance, field: 'title', 'has-error')} required">
    <label class="control-label" for="title"><g:message code="dataSet.title.label"/></label>
    <input id="title" name="title" class="form-control" type="text" value="${dataSetInstance.title}" required=""/>
</div>

<g:set var="railWays" value="${railWayInstances}"/>

<div class="form-group">
    <label class="control-label" for="railWay"><g:message code="dataSet.belongsTo.label"/></label>
    <g:select id="railWay" name="railWay.id" from="${railWays}" value="${dataSetInstance.railWayId}"
              optionKey="id" optionValue="label" noSelection="${[null:'Проекту']}" class="form-control"
              disabled="${dataSetInstance.metaInfo.belongsToForever ? 'true': 'false'}"/>
</div>
