<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="bootstrap"/>
    <title><g:message code="app.project.open.title" /></title>
</head>
<body>
<div class="page-header">
    <h2><g:message code="app.project.open.title" /></h2>
</div>
<div class="list-group">
    <g:each in="${projectInstanceList}" var="projectInstance">
        <g:link class="list-group-item" controller="app" action="run" params="${[projectId: projectInstance.id]}">${projectInstance.name.encodeAsHTML()}</g:link>
    </g:each>
</div>
%{--<g:link class="btn btn-default" controller="project" action="create"><span class="glyphicon glyphicon-plus"></span> <g:message code="app.project.open.button.create"/></g:link>--}%
<g:link elementId="newProject" class="btn btn-default" controller="project" action="createDemo" params="[count: 1]"><span class="glyphicon glyphicon-plus"></span> Новый демо-проект</g:link>
</body>
</html>
