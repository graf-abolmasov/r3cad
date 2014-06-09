<%@ page import="com.r3.drawing.style.DominantBaseline; com.r3.drawing.style.TextAnchor; com.r3.utils.Utils" contentType="text/xml;charset=UTF-8" %>
<g:set var="df_blue" value="${styles.lineStyles["df_blue"]}"/>
<g:if test="${row.children}">
    <% def offsety = 0 %>
    <drw:rect renderer="${renderer}" style="${df_blue}" x="${8 * level}" width="${100 - 8 * level}"
              height="${myHeight}"/>
    <drw:rect renderer="${renderer}" style="${df_blue}" width="${8 * level}" height="${myHeight}"/>
    <drw:text renderer="${renderer}" text-anchor="${TextAnchor.MIDDLE}" x="${8 * level + 5}" y="${myHeight / 2.0}"
              style="${styles.textStyles["text_normal"]}" rotate="-90">${row.label.encodeAsHTML()}</drw:text>
    <g:each in="${row.children}" var="childArea">
        <g:if test="${childArea.visible}">
            <% def childHeight = childArea.fullHeight %>
            <drw:group renderer="${renderer}" x="0" y="${offsety}">
                <g:render template="profileDrawing/table_row" model="[level: level + 1, row: childArea, myHeight: childHeight]"/>
            </drw:group>
            <% offsety += childHeight %>
        </g:if>
    </g:each>
</g:if>
<g:else>
    <drw:rect renderer="${renderer}" style="${df_blue}" x="${8*level}" width="${100-8*level}" height="${myHeight}"/>
    <drw:text renderer="${renderer}" text-anchor="${TextAnchor.START}" dominant-baseline="${DominantBaseline.MIDDLE}"
              x="${Math.max(13, 8 * level + 2)}" y="${myHeight / 2.0}"
              style="${styles.textStyles["text_normal"]}">${row.label.encodeAsHTML()}</drw:text>
    <drw:group renderer="${renderer}" x="105" y="0">
        <drw:line renderer="${renderer}" style="${df_blue}" x1="0" y1="0" x2="${drawingModel.width}" y2="0"/>
        <drw:line renderer="${renderer}" style="${df_blue}" x1="0" y1="${myHeight}" x2="${drawingModel.width}" y2="${myHeight}"/>
        <g:each in="${row.dataSets}" var="dataSet">
            <g:if test="${dataSet.visible}">
                <g:render template="dataSet/${dataSet.dataSet.getTemplate(row.representationRole)}"
                          model="${[values: drawingModel.map.dataSet(dataSet.dataSet.id),
                                    myHeight: myHeight, representationRole: row.representationRole]}"/>
            </g:if>
        </g:each>
    </drw:group>
</g:else>
