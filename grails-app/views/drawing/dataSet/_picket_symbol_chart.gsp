<%@ page import="com.r3.dataset.SymbolDataEntryReadOnly; com.r3.utils.Utils" contentType="text/xml;charset=UTF-8" %>

<g:set var="lineStyle" value="${styles.lineStyles["df_black"]}"/>
<g:set var="text_leader" value="${styles.textStyles["text_leader"]}"/>
<g:set var="minAlt" value="${drawingModel.map.zeroY}"/>

<g:each in="${values}" var="dataItem">
    <%
        SymbolDataEntryReadOnly symbol = dataItem.value
        def y = -myHeight + 30
        def x = drawingModel.X(dataItem.x)
    %>
    <drw:line renderer="${renderer}" x1="${x}" x2="${x}" y1="${y}" y2="${y + 30}" style="${lineStyle}"/>
    <drw:text renderer="${renderer}" x="${x + text_leader.size + 1}" y="${y + 28}" rotate="${-90}"
              style="${text_leader}">${symbol.getLocationLabel()}</drw:text>
    <drw:text renderer="${renderer}" x="${x - 1}" y="${y + 28}" rotate="${-90}"
              style="${text_leader}">${symbol.label}</drw:text>
    <drw:group renderer="${renderer}" x="${x}" y="${y}">
        <g:render template="symbol/${symbol.getTemplate(representationRole)}" model="${[value: symbol]}"/>
    </drw:group>
</g:each>