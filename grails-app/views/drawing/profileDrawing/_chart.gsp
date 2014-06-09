<%@ page import="com.r3.utils.Utils;" contentType="text/xml;charset=UTF-8" %>
<g:set var="chartHeight" value="${pageSize.bValue - drawingInstance.margin.bottom - drawingModel.tableHeight - 15}"/>
<drw:group renderer="${renderer}" x="${drawingInstance.margin.left + 20 + 105}" y="${chartHeight}">
    <g:set var="allRows" value="${drawingInstance.rows}"/>
    <g:each in="${allRows}" var="row">
        <g:if test="${row.visible}">
            <drw:group renderer="${renderer}" x="0" y="${offsety}">
                <g:render template="profileDrawing/chart_row" model="[row: row, chartHeight: chartHeight]"/>
            </drw:group>
        </g:if>
    </g:each>
</drw:group>
