<%@ page import="org.springframework.validation.FieldError" %>
<g:if test="${flash.message}">
    <div class="alert alert-success">${flash.message}<a href="#" onclick="window.close()" class="btn btn-success btn-xs pull-right"><g:message code="default.button.close-window" /></a></div>
</g:if>

<g:hasErrors bean="${dataSetInstance}">
    <g:eachError bean="${dataSetInstance}" var="error">
        <div class="alert alert-danger" <g:if test="${error in FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></div>
    </g:eachError>
</g:hasErrors>