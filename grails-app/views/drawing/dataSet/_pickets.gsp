<%@ page import="com.r3.dataset.Location; com.r3.drawing.style.TextAnchor; com.r3.utils.Utils" contentType="text/xml;charset=UTF-8" %>
<%def text_narrow = styles.textStyles["text_narrow"]%>
<g:each in="${(drawingModel.map.boundPickets.aValue..drawingModel.map.boundPickets.bValue)}" var="pk">
    <drw:text renderer="${renderer}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb"
              x="${drawingModel.X(new Location(0, pk, 0))}" y="4"
              style="${text_narrow}">${(pk % 10 == 0) ? (pk) : (pk % 10)}</drw:text>
</g:each>
