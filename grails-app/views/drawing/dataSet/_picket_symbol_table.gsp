<%@ page import="com.r3.RailWay; com.r3.dataset.SymbolDataEntryReadOnly; com.r3.utils.Utils" contentType="text/xml;charset=UTF-8" %>

<%
    RailWay mainWay = drawingInstance.project.mappingWay()
    RailWay leftWay = mainWay?.leftRailWay
    RailWay rightWay = mainWay?.rightRailWay
%>

<g:if test="${mainWay}">
    <g:each in="${values}" var="dataItem">
        <%
            SymbolDataEntryReadOnly symbol = dataItem.value
            Map<Integer, Integer> distanceToWays = symbol.distanceToWays

            def distanceToWay = distanceToWays[mainWay.number]
            def offset = 0

            if ((distanceToWay == null) && (leftWay != null)) {
                distanceToWay = distanceToWays[leftWay.number]
                offset = -5
            } else if ((distanceToWay == null) && (rightWay != null)) {
                distanceToWay = distanceToWays[rightWay.number]
                offset = 5
            }
        %>
        <g:if test="${distanceToWay != null}">
            <% offset += distanceToWay == 0 ?: (distanceToWay / Math.abs(distanceToWay) * 2.5) %>
            <drw:group renderer="${renderer}" x="${drawingModel.X(dataItem.x)}" y="${myHeight / 2.0 + offset}">
                <g:render template="symbol/${symbol.getTemplate(representationRole)}" model="${[value: symbol]}"/>
            </drw:group>
        </g:if>
    </g:each>
</g:if>