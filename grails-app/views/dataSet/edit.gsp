<%@ page import="org.springframework.validation.FieldError; com.r3.drawing.layout.Drawing" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <title><g:message code="dataSet.edit.title" args="${[dataSetInstance.title]}"/></title>
</head>

<body>
    <div class="page-header"><h2><g:message code="dataSet.edit.header" args="${[dataSetInstance.title]}"/></h2></div>

    <g:render template="messages"/>

    <g:form action="update" id="${dataSetInstance.id}" controller="dataSet" role="form" method="PUT" params="[projectId: dataSetInstance.projectId]">

        <g:render template="common_form_fields"/>

        <g:render template="${dataSetType}_form_fields" model="[dataSetInstance: dataSetInstance]"/>

        <g:actionSubmit action="update" name="save" class="btn btn-primary" value="${message(code: 'default.button.update.label')}"/>
        <a href="#" onclick="window.close()" class="btn btn-default"><g:message code="default.button.close-window" /></a>
    </g:form>
    <div style="height: 25px"></div>
</body>
</html>