<%@ page import="com.r3.drawing.style.TextAnchor; com.r3.mapping.DataItem; com.r3.utils.Utils;" contentType="text/xml;charset=UTF-8"%>
<%
    List<DataItem> gradients = new LinkedList<DataItem>(values.values)
    def height = row.height
    def half_height = row.height / 2.0
%>

<g:if test="${gradients && gradients.size() > 1}">
    <%
        def prevValue = gradients.pollFirst()
        def prevPx = drawingModel.X(prevValue.x)
        def grad = prevValue.value
    %>

    <g:each in="${gradients}" var="dataItem">
        <% def px = drawingModel.X(dataItem.x) %>
        <drw:line renderer="${renderer}" style="${styles.lineStyles["df_blue"]}" x1="${prevPx}" y1="0" x2="${prevPx}" y2="${height}"/>
        <g:if test="${grad > 0}">
            <drw:line renderer="${renderer}" style="${styles.lineStyles["df_blue"]}" x1="${prevPx}" y1="0" x2="${px}" y2="${height}"/>
            <drw:text renderer="${renderer}" text-anchor="${TextAnchor.END}" style="${styles.textStyles["text_grad"]}"
                      writing-mode="lr-tb" x="${px - 0.2}" y="2"><g:formatNumber number="${grad}" maxFractionDigits="1"/></drw:text>
        </g:if>
        <g:elseif test="${grad == 0}">
            <drw:line renderer="${renderer}" style="${styles.lineStyles["df_blue"]}" x1="${prevPx}" y1="${half_height}" x2="${px}" y2="${half_height}"/>
            <drw:text renderer="${renderer}" text-anchor="${TextAnchor.MIDDLE}" style="${styles.textStyles["text_grad"]}"
                      writing-mode="lr-tb" x="${(px + prevPx) / 2.0}" y="2"><g:formatNumber number="${grad}" maxFractionDigits="1"/></drw:text>
        </g:elseif>
        <g:else>
            <drw:line renderer="${renderer}" style="${styles.lineStyles["df_blue"]}" x1="${prevPx}" y1="${height}" x2="${px}" y2="0"/>
            <drw:text renderer="${renderer}" text-anchor="${TextAnchor.START}" style="${styles.textStyles["text_grad"]}"
                      writing-mode="lr-tb" x="${prevPx + 0.2}" y="2"><g:formatNumber number="${grad}" maxFractionDigits="1" minFractionDigits="1"/></drw:text>
        </g:else>
        <%
            prevPx = px;
            grad = dataItem.value
        %>
    </g:each>
</g:if>
