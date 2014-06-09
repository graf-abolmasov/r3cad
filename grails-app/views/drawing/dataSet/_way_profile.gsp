<%@ page import="com.r3.utils.Utils" contentType="text/xml;charset=UTF-8" %>
<%
    def p_x = drawingModel.X((values == null || values.isEmpty()) ? 0l : values.values.first().x);
    def p_y = 0;
%>
<g:each in="${values}" var="alt">
    <%
        def x = drawingModel.X(alt.x)
        def y = -Utils.scaleY(alt.value - 0.98 * drawingModel.map.zeroY)
    %>
    <drw:line renderer="${renderer}" x1="${x}" y1="${0}" x2="${x}" y2="${y}" style="${styles.lineStyles["way_profile"]}"/>
    <drw:line renderer="${renderer}" x1="${p_x}" y1="${p_y}" x2="${x}" y2="${y}" style="${styles.lineStyles["df_black"]}"/>
    <% p_x = x; p_y = y; %>
</g:each>
