<%@ page import="com.r3.drawing.style.DominantBaseline; com.r3.drawing.style.TextAnchor; com.r3.utils.Utils;" contentType="text/xml;charset=UTF-8" %>
<g:each in="${values}" var="dataItem">
    <%def x = drawingModel.X(dataItem.x)%>
    <drw:text renderer="${renderer}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb"
              dominant-baseline="${DominantBaseline.MIDDLE}" x="${x}" y="6" style="${styles.textStyles["text_grad"]}"
              rotate="-90"><g:formatNumber number="${dataItem.value}" maxFractionDigits="2" minFractionDigits="2"/></drw:text>
</g:each>