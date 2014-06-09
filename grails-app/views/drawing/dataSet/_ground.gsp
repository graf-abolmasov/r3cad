<%@ page import="com.r3.utils.Utils" contentType="text/xml;charset=UTF-8" %>
<%
    def minAlt = drawingModel.map.zeroY
    def p_x = drawingModel.X((values == null || values.isEmpty()) ? 0l : values.values.first().x)
    def p_y = 0;
%>
<g:each in="${values}" var="alt">
    <%
        def x = drawingModel.X(alt.x)
        def y = -Utils.scaleY(alt.value - 0.98 * minAlt)
    %>
    <drw:line renderer="${renderer}" x1="${p_x}" y1="${p_y}" x2="${x}" y2="${y}" style="${styles.lineStyles["ground"]}"/>
    <% p_x = x; p_y = y; %>
</g:each>
