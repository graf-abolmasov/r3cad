<%@ page contentType="text/xml;charset=UTF-8" %>
<g:if test="${row.visible}">
    <g:render template="dataSet/${row.dataSet.getTemplate(row.representationRole)}"
              model="${[values: drawingModel.map.dataSet(row.dataSet.id),
                      representationRole: row.representationRole, myHeight:chartHeight]}"/>
</g:if>
