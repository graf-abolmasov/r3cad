<%@ page import="com.r3.drawing.area.RepresentationRole; com.r3.drawing.area.PlainDrawArea" %>

<div class="form-group ${hasErrors(bean: plainDrawAreaInstance, field: 'label', 'has-error')} required">
    <label class="control-label" for="label"><g:message code="plainDrawArea.label.label"/></label>
    <input id="label" name="label" class="form-control" type="text" value="${plainDrawAreaInstance.label}" required=""/>
</div>

<div class="form-group ${hasErrors(bean: plainDrawAreaInstance, field: 'representationRole', 'has-error')}">
    <label class="control-label" for="representationRole"><g:message code="plainDrawArea.representationRole.label"/></label>
    <g:select id="representationRole" name="representationRole" from="${RepresentationRole.values()}" value="${plainDrawAreaInstance.representationRole?.name()}"
              optionKey="name" class="form-control" noSelection="${['': message(code: 'com.r3.drawing.area.RepresentationRole.UNDEFINED')]}"/>
</div>

<div class="form-group ${hasErrors(bean: plainDrawAreaInstance, field: 'dataSet', 'has-error')} required">
    <label class="control-label" for="dataSet"><g:message code="dataSet.label"/></label>
    <g:select id="dataSet" name="dataSet.id" from="${dataSets}" value="${plainDrawAreaInstance.dataSet?.id}"
              optionKey="id" optionValue="title" class="form-control" required=""/>
</div>

<div class="checkbox ${hasErrors(bean: plainDrawAreaInstance, field: 'visible', 'has-error')} required">
    <label class="control-label" for="visible">
        <g:checkBox name="visible" value="${plainDrawAreaInstance?.visible}"/>
        <g:message code="plainDrawArea.visible.label"/>
    </label>
</div>
