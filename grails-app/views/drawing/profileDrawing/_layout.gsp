<%@ page import="com.r3.drawing.style.DominantBaseline; com.r3.drawing.style.TextAnchor; com.r3.drawing.renderer.SvgRenderer" contentType="text/xml;charset=UTF-8" %>
<drw:group renderer="${renderer}">
    <drw:renderFor renderer="${renderer}" forRenderer="${SvgRenderer.ALIAS}">
        <!--Нужно, чтобы избавиться от артифактов по краям чертежа. Прямоугольник чуть большечего размера, чем лист-->
        <drw:rect renderer="${renderer}" x="-2" y="-2" width="${pageSize.aValue + 4}"
                  height="${pageSize.bValue + 4}" fill="white"/>
    </drw:renderFor>
    <!--Штамп-->
    <g:render template="stamp" />
    <!--Таблица-->
    <g:render template="profileDrawing/table" />
    <!--График-->
    <g:render template="profileDrawing/chart" />

    <g:if test="${request['__START_TIME__']}">
        <drw:text renderer="${renderer}" x="${10}" y="${pageSize.bValue - 2.5}"
                  text-anchor="${TextAnchor.START}" writing-mode="lr-tb"
                  dominant-baseline="${DominantBaseline.MIDDLE}"
                  style="${styles.textStyles["text_narrow"]}">Generated in ${System.currentTimeMillis() - request['__START_TIME__']} msec by R3 CAD ©</drw:text>
    </g:if>
</drw:group>