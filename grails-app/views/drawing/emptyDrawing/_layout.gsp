<%@ page import="com.r3.drawing.renderer.SvgRenderer" contentType="text/xml;charset=UTF-8" %>
<drw:group renderer="${renderer}">
    <drw:renderFor renderer="${renderer}" forRenderer="${SvgRenderer.ALIAS}">
        <drw:rect renderer="${renderer}" x="-2" y="-2" width="${pageSize.aValue + 4}"
                  height="${pageSize.bValue + 4}" fill="white"/>
    </drw:renderFor>
    <g:render template="stamp" />
</drw:group>