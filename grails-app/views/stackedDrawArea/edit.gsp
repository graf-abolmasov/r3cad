<%@ page import="javax.servlet.http.HttpServletResponse; org.springframework.validation.FieldError; com.r3.drawing.layout.Drawing" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <title>${stackedDrawAreaInstance.label} - <g:message code="stackedDrawArea.edit.title"/></title>
</head>

<body>
<div class="page-header"><h2><g:message code="stackedDrawArea.edit.title"/></h2></div>

<g:if test="${params.ok}">
    <div class="alert alert-success"><g:message code="default.update.success" /><a href="#" onclick="window.close()" class="btn btn-success btn-xs pull-right"><g:message code="default.button.close-window" /></a></div>
</g:if>

<g:hasErrors bean="${stackedDrawAreaInstance}">
    <g:eachError bean="${stackedDrawAreaInstance}" var="error">
        <div class="alert alert-danger" <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></div>
    </g:eachError>
</g:hasErrors>

<g:form action="update" role="form" method="post">
    <g:hiddenField name="id" value="${stackedDrawAreaInstance?.id}" />
    <g:hiddenField name="version" value="${stackedDrawAreaInstance?.version}" />
    <g:render template="form"/>
    <g:submitButton name="save" class="btn btn-primary" value="${message(code: 'default.button.update.label')}"/>
    <a href="#" onclick="window.close()" class="btn btn-default"><g:message code="default.button.close-window" /></a>
</g:form>
<div style="height: 25px"></div>
</body>
</html>