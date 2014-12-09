<%@ page import="javax.servlet.http.HttpServletResponse; org.springframework.validation.FieldError; com.r3.drawing.layout.Drawing" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="bootstrap"/>
        <title>${drawingInstance.name} - <g:message code="app.drawing.edit.title"/></title>
    </head>

    <body>
        <div class="page-header"><h2><g:message code="app.drawing.edit.title"/></h2></div>

        <g:if test="${flash.message}">
            <div class="alert alert-success">${flash.message}<a href="#" onclick="window.close()" class="btn btn-success btn-xs pull-right"><g:message code="app.drawing.edit.button.close" /></a></div>
        </g:if>

        <g:hasErrors bean="${drawingInstance}">
            <g:eachError bean="${drawingInstance}" var="error">
                <div class="alert alert-danger" <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></div>
            </g:eachError>
        </g:hasErrors>

        <g:form action="update" id="${drawingInstance.id}" role="form" method="PUT" params="[projectId: drawingInstance.projectId]">
            <g:hiddenField name="id" value="${drawingInstance?.id}" />
            <g:hiddenField name="version" value="${drawingInstance?.version}" />
            <g:render template="form"/>
            <g:submitButton action="update" name="save" class="btn btn-primary" value="${message(code: 'default.button.update.label')}"/>
            <a href="#" onclick="window.close()" class="btn btn-default"><g:message code="app.drawing.edit.button.close" /></a>
        </g:form>
        <div style="height: 25px"></div>
    </body>
</html>