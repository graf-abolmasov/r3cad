<%@ page import="org.springframework.validation.FieldError; com.r3.Project" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <title><g:message code="app.project.create.title" /></title>
</head>
<body>
<div class="page-header">
    <h2><g:message code="app.project.create.title" /></h2>
</div>
    <g:if test="${flash.message}">
        <div class="alert alert-info">${flash.message}</div>
    </g:if>

    <g:hasErrors bean="${projectInstance}">
        <g:eachError bean="${projectInstance}" var="error">
            <div class="alert alert-danger" <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></div>
        </g:eachError>
    </g:hasErrors>

    <g:form action="save" role="form">
        <g:render template="form"/>
        <g:submitButton name="create" class="btn btn-default" value="${message(code: 'default.button.create.label')}" />
    </g:form>
</body>
</html>
