<%@ page import="com.r3.drawing.style.TextAnchor; com.r3.RailWay; com.r3.utils.Utils" contentType="text/xml;charset=UTF-8" %>
<g:set var="width" value="${105 + drawingModel.width}"/>
<drw:group renderer="${renderer}"
           x="${drawingInstance.margin.left + 20}"
           y="${pageSize.bValue - drawingInstance.margin.bottom - drawingModel.tableHeight - 25}">
    <drw:text renderer="${renderer}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb"
              style="${styles.textStyles["text_normal"]}" x="50" y="4">Масштаб по горизонтали 1:10000</drw:text>
    <drw:text renderer="${renderer}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb"
              style="${styles.textStyles["text_normal"]}" x="50" y="9">Масштаб по вертикали 1:100</drw:text>

    <% def offsety = 10 %>
    <g:set var="allRows" value="${drawingInstance.rootTableRow.children}"/>
    <g:each in="${allRows}" var="row">
        <g:if test="${row.visible}">
            <% def childHeight = row.fullHeight %>
            <drw:group renderer="${renderer}" x="0" y="${offsety}">
                <g:render template="profileDrawing/table_row" model="[level:0, row: row, myHeight: childHeight]"/>
            </drw:group>
            <% offsety += childHeight %>
        </g:if>
    </g:each>

</drw:group>