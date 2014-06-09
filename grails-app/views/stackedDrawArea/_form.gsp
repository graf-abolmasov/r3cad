<%@ page import="com.r3.drawing.area.RepresentationRole; com.r3.drawing.area.StackedDrawArea" %>

<div class="form-group ${hasErrors(bean: stackedDrawAreaInstance, field: 'label', 'has-error')} required">
    <label class="control-label" for="label"><g:message code="stackedDrawArea.label.label"/></label>
    <input id="label" name="label" class="form-control" type="text" value="${stackedDrawAreaInstance.label}" required=""/>
</div>

<div class="form-group ${hasErrors(bean: stackedDrawAreaInstance, field: 'representationRole', 'has-error')}">
    <label class="control-label" for="representationRole"><g:message code="stackedDrawArea.representationRole.label"/></label>
    <g:select id="representationRole" name="representationRole" from="${RepresentationRole.values()}" value="${stackedDrawAreaInstance.representationRole?.name()}"
              optionKey="name" class="form-control" noSelection="${['': message(code: 'com.r3.drawing.area.RepresentationRole.UNDEFINED')]}"/>
</div>

<div class="form-group ${hasErrors(bean: stackedDrawAreaInstance, field: 'height', 'has-error')} required">
    <label class="control-label" for="height"><g:message code="stackedDrawArea.height.label"/></label>
    <input id="height" name="height" class="form-control" type="number" min="0" value="${stackedDrawAreaInstance.height}" required=""/>
</div>

<div class="checkbox ${hasErrors(bean: stackedDrawAreaInstance, field: 'visible', 'has-error')} required">
    <label class="control-label" for="visible">
        <g:checkBox name="visible" value="${stackedDrawAreaInstance?.visible}"/>
        <g:message code="stackedDrawArea.visible.label"/>
    </label>
</div>
