<%@ page import="com.r3.Project" %>

<div class="form-group ${hasErrors(bean: projectInstance, field: 'name', 'has-error')} ">
    <label class="control-label" for="name"><g:message code="project.name.label"/></label>
    <g:textField name="name" class="form-control" value="${projectInstance?.name}"/>
</div>

<g:if test="${!(projectInstance.id)}">
    <div class="form-group">
        <label class="control-label" for="waysCount"><g:message code="app.project.create.waysCount.label"/></label>
        <input name="waysCount" type="number" min="2" max="5" class="form-control" value="2" id="waysCount"/>
    </div>
</g:if>