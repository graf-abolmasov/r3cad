<%@ page import="com.r3.dataset.Location; com.r3.drawing.style.TextAnchor; com.r3.dataset.PlanElementsDataEntryReadOnly; com.r3.mapping.DataItem; com.r3.dataset.PlanElementsDataEntryReadOnly; com.r3.dataset.DataEntry; com.r3.utils.Utils;com.r3.dataset.CurveType;" contentType="text/xml;charset=UTF-8" %>
<g:set var="curve" value="${styles?.lineStyles["df_black"]}"/>
<g:set var="curve_proj" value="${styles?.lineStyles["curve_in_plan_proj"]}"/>
<g:set var="text_curve" value="${styles?.textStyles["text_curve"]}"/>
<g:set var="centerY" value="${myHeight / 2.0}"/>
%{--Граничное значения угла (кривая-изгиб)
Это критическое значение угла должно настраиваться пользователем в минутах, в данном случае 10 минут!--}%
<g:set var="minAngle" value="${10.0 / 60}"/>

<g:each in="${values}" var="dataItem">
<%
    PlanElementsDataEntryReadOnly planElement = dataItem.value

    def x1 = drawingModel.X(dataItem.x)
    def x2 = drawingModel.X(new Location(planElement.endKm, planElement.endPk, planElement.endPlus))
%>

<g:set var="centerX" value="${(x1 + x2) / 2}"/>
<g:set var="drawingLength" value="${x2 - x1}"/>
<g:set var="ifBigElement" value="${drawingLength > 6}"/>

%{--Прямая--}%
<g:if test="${CurveType.STRAIGHT == planElement.elementType}">
    <drw:line renderer="${renderer}" style="${curve}" x1="${x1}" x2="${x2}" y1="${centerY}" y2="${centerY}"/>
    <g:if test="${x1 != 0}">
        <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                  y="${ifBigElement ? centerY - 1 : (centerY - 6)}" text-anchor="${TextAnchor.MIDDLE}"
                  writing-mode="lr-tb"><g:formatNumber number="${planElement.length / DataEntry.MM_IN_METER}"
                                                       maxFractionDigits="2"/></drw:text>
    </g:if>
</g:if>
%{--Полная кривая Лево--}%
<g:elseif test="${CurveType.LEFT_FULL_CURVE == planElement.elementType}">
%{--Проверка кривая или изгиб--}%
    <g:if test="${planElement.angle > minAngle}">
    %{--Плюсовка в начале--}%
        <drw:line renderer="${renderer}" style="${curve}" x1="${x1}" x2="${x1}" y1="${centerY}" y2="${0}"/>
        <drw:text renderer="${renderer}" style="${text_curve}"
                  x="${ifBigElement ? x1 + 1 + text_curve.size : (x1 - 1)}"
                  y="${centerY / 2}" rotate="-90" text-anchor="${TextAnchor.MIDDLE}"
                  writing-mode="lr-tb"><g:formatNumber number="${planElement.plus / DataEntry.MM_IN_METER}"
                                                       maxFractionDigits="2"/></drw:text>
    %{--Сама кривая--}%
        <drw:line renderer="${renderer}" style="${curve}" x1="${x1}" y1="${centerY}" x2="${x1}" y2="${centerY + 2}"/>
        <g:if test="${ifBigElement}">
            <drw:circleArc renderer="${renderer}" style="${curve}" x1="${x1}" y1="${centerY + 2}" x2="${x1 + 3}"
                           y2="${centerY + 5}"/>
            <drw:line renderer="${renderer}" style="${curve}" x1="${x1 + 3}" y1="${centerY + 5}" x2="${x2 - 3}"
                      y2="${centerY + 5}"/>
            <drw:circleArc renderer="${renderer}" style="${curve}" x1="${x2 - 3}" y1="${centerY + 5}" x2="${x2}"
                           y2="${centerY + 2}"/>
        </g:if>
        <g:else>
            <drw:circleArc renderer="${renderer}" style="${curve}"
                           x1="${x1}" y1="${centerY + 2}"
                           x2="${x2}" y2="${centerY + 2}"
                           radius="${(drawingLength) / 2}"/>
        </g:else>
        <drw:line renderer="${renderer}" style="${curve}" x1="${x2}" y1="${centerY}" x2="${x2}" y2="${centerY + 2}"/>
        <drw:text renderer="${renderer}" style="${text_curve}"
                  x="${centerX}" y="${centerY + 6 + text_curve.size}"
                  text-anchor="${TextAnchor.MIDDLE}"
                  writing-mode="lr-tb">h=<g:formatNumber number="${planElement.h}" maxFractionDigits="0"/></drw:text>
    %{--Плюсовка в конце--}%
        <drw:line renderer="${renderer}" style="${curve}" x1="${x2}" x2="${x2}"
                  y1="${centerY}" y2="${0}"/>
        <drw:text renderer="${renderer}" style="${text_curve}" x="${x2 - 1}" y="${centerY / 2}"
                  rotate="-90" text-anchor="${TextAnchor.MIDDLE}"
                  writing-mode="lr-tb"><g:formatNumber number="${planElement.endPlus / DataEntry.MM_IN_METER}"
                                                       maxFractionDigits="2"/></drw:text>

    %{--Вывод информационных полей в зависимости от размера--}%
        <g:set var="curentY" value="${centerY + 5}"/>
    %{--Если умещается только в один столбец--}%
        <g:if test="${drawingLength < text_curve.size * 16}">

            <g:if test="${drawingLength < text_curve.size * 8}">
                <% curentY = centerY %>
            </g:if>
            <g:if test="${planElement.l2 != 0}">
                <drw:text renderer="${renderer}" style="${text_curve}"
                          x="${centerX}" y="${curentY}" text-anchor="${TextAnchor.MIDDLE}"
                          writing-mode="lr-tb">l2-<g:formatNumber number="${planElement.l2}"
                                                                  maxFractionDigits="0"/></drw:text>
                <% curentY -= text_curve.size %>
            </g:if>
            <g:if test="${planElement.l1 != 0}">
                <drw:text renderer="${renderer}" style="${text_curve}"
                          x="${centerX}" y="${curentY}" text-anchor="${TextAnchor.MIDDLE}"
                          writing-mode="lr-tb">l1-<g:formatNumber number="${planElement.l1}"
                                                                  maxFractionDigits="0"/></drw:text>
                <% curentY -= text_curve.size %>
            </g:if>
            <drw:text renderer="${renderer}" style="${text_curve}"
                      x="${centerX}" y="${curentY}" text-anchor="${TextAnchor.MIDDLE}"
                      writing-mode="lr-tb">K-<g:formatNumber number="${planElement.length / DataEntry.MM_IN_METER}"
                                                             maxFractionDigits="2"/></drw:text>
            <% curentY -= text_curve.size %>
            <g:if test="${planElement.t2 != 0}">
                <drw:text renderer="${renderer}" style="${text_curve}"
                          x="${centerX}" y="${curentY}" text-anchor="${TextAnchor.MIDDLE}"
                          writing-mode="lr-tb">T2-<g:formatNumber number="${planElement.t2 / DataEntry.MM_IN_METER}"
                                                                  maxFractionDigits="2"/></drw:text>
                <% curentY -= text_curve.size %>
            </g:if>
            <g:if test="${planElement.t1 != 0}">
                <drw:text renderer="${renderer}" style="${text_curve}"
                          x="${centerX}" y="${curentY}" text-anchor="${TextAnchor.MIDDLE}"
                          writing-mode="lr-tb">T1-<g:formatNumber number="${planElement.t1 / DataEntry.MM_IN_METER}"
                                                                  maxFractionDigits="2"/></drw:text>
                <% curentY -= text_curve.size %>
            </g:if>
            <drw:text renderer="${renderer}" style="${text_curve}"
                      x="${centerX}" y="${curentY}" text-anchor="${TextAnchor.MIDDLE}"
                      writing-mode="lr-tb">R-<g:formatNumber number="${planElement.radius}"
                                                             maxFractionDigits="0"/></drw:text>
            <% curentY -= text_curve.size %>
            <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                      y="${curentY}" text-anchor="${TextAnchor.MIDDLE}"
                      writing-mode="lr-tb">y-${planElement.getAngleAsString()}</drw:text>
        </g:if>
    %{--Если умещается в два столбца--}%
        <g:else>
            <g:if test="${(planElement.l2 != 0) && (planElement.l1 != 0)}">
                <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                          y="${curentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">l1-<g:formatNumber
                        number="${planElement.l1}" maxFractionDigits="0"/> l2-<g:formatNumber
                        number="${planElement.l2}" maxFractionDigits="0"/></drw:text>
                <% curentY -= text_curve.size %>
            </g:if>
            <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                      y="${curentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">K-<g:formatNumber
                    number="${planElement.length / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>
            <% curentY -= text_curve.size %>
            <g:if test="${(planElement.t2 != 0) && (planElement.t1 != 0)}">
                <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                          y="${curentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">T1-<g:formatNumber
                        number="${planElement.t1 / DataEntry.MM_IN_METER}"
                        maxFractionDigits="2"/> T2-<g:formatNumber
                        number="${planElement.t2 / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>
                <% curentY -= text_curve.size %>
            </g:if>
            <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                      y="${curentY}" text-anchor="${TextAnchor.MIDDLE}"
                      writing-mode="lr-tb">y-${planElement.getAngleAsString()} R-<g:formatNumber
                    number="${planElement.radius}" maxFractionDigits="0"/></drw:text>
        </g:else>
    </g:if>
%{--Отрисовка изгиба--}%
    <g:else>
        <drw:line renderer="${renderer}" style="${curve}" x1="${x1}" y1="${centerY}"
                  x2="${centerX}" y2="${centerY + 5}"/>
        <drw:line renderer="${renderer}" style="${curve}" x1="${centerX}"
                  y1="${centerY + 5}" x2="${x2}" y2="${centerY}"/>
        <drw:line renderer="${renderer}" style="${curve}" x1="${centerX}"
                  y1="${centerY + 5}" x2="${centerX}" y2="${0}"/>
        <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX - 1}"
                  y="${centerY / 2}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb"
                  rotate="-90"><g:formatNumber number="${planElement.endPlus / DataEntry.MM_IN_METER}"
                                               maxFractionDigits="2"/></drw:text>
        <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX + 1 + text_curve.size}"
                  y="${centerY / 2}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb"
                  rotate="-90">${planElement.getAngleAsString()}</drw:text>
    </g:else>
</g:elseif>
%{--Полная кривая Право--}%
<g:else>
%{--Проверка кривая или изгиб--}%
    <g:if test="${planElement.angle > minAngle}">
    %{--Плюсовка в начале--}%
        <drw:line renderer="${renderer}" style="${curve}" x1="${x1}" x2="${x1}"
                  y1="${centerY}" y2="${0}"/>
        <drw:text renderer="${renderer}" style="${text_curve}"
                  x="${ifBigElement ? x1 + 1 + text_curve.size : (x1 - 1)}" y="${centerY / 2}"
                  text-anchor="${TextAnchor.MIDDLE}"
                  writing-mode="lr-tb" rotate="-90"><g:formatNumber
                number="${planElement.plus / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>
    %{--Отрисовка кривой--}%
        <drw:line renderer="${renderer}" style="${curve}" x1="${x1}" y1="${centerY}"
                  x2="${x1}" y2="${centerY - 2}"/>
        <g:if test="${ifBigElement}">
            <drw:circleArc renderer="${renderer}" style="${curve}" x1="${x1 + 3}"
                           y1="${centerY - 5}" x2="${x1}" y2="${centerY - 2}"/>
            <drw:line renderer="${renderer}" style="${curve}" x1="${x1 + 3}"
                      y1="${centerY - 5}" x2="${x2 - 3}" y2="${centerY - 5}"/>
            <drw:circleArc renderer="${renderer}" style="${curve}" x1="${x2}"
                           y1="${centerY - 2}" x2="${x2 - 3}" y2="${centerY - 5}"/>
        </g:if>
        <g:else>
            <drw:circleArc renderer="${renderer}" style="${curve}" x1="${x2}"
                           y1="${centerY - 2}" x2="${x1}" y2="${centerY - 2}" radius="${(drawingLength) / 2}"/>
        </g:else>
        <drw:line renderer="${renderer}" style="${curve}" x1="${x2}" y1="${centerY}"
                  x2="${x2}" y2="${centerY - 2}"/>
        <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                  y="${centerY - 6}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">h=<g:formatNumber
                number="${planElement.h}" maxFractionDigits="0"/></drw:text>
    %{--Плюсовка в конце--}%
        <drw:line renderer="${renderer}" style="${curve}" x1="${x2}" x2="${x2}"
                  y1="${centerY}" y2="${0}"/>
        <drw:text renderer="${renderer}" style="${text_curve}" x="${x2 - 1}" y="${centerY / 2}"
                  text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" rotate="-90"><g:formatNumber
                number="${planElement.endPlus / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>

    %{--Вывод информационных полей в зависимости от размера--}%
        <g:set var="currentY" value="${centerY - 5 + text_curve.size}"/>
    %{--Если умещается только в один столбец--}%
        <g:if test="${drawingLength < 16 * text_curve.size}">
            <g:if test="${drawingLength < 8 * text_curve.size}">
                <% currentY = centerY + text_curve.size %>
            </g:if>
            <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                      y="${currentY}" text-anchor="${TextAnchor.MIDDLE}"
                      writing-mode="lr-tb">y-${planElement.getAngleAsString()}</drw:text>
            <% currentY += text_curve.size %>
            <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                      y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">R-<g:formatNumber
                    number="${planElement.radius}" maxFractionDigits="0"/></drw:text>
            <% currentY += text_curve.size %>
            <g:if test="${planElement.t1 != 0}">
                <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                          y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">T1-<g:formatNumber
                        number="${planElement.t1 / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>
                <% currentY += text_curve.size %>
            </g:if>
            <g:if test="${planElement.t2 != 0}">
                <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                          y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">T2-<g:formatNumber
                        number="${planElement.t2 / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>
                <% currentY += text_curve.size %>
            </g:if>
            <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                      y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">K-<g:formatNumber
                    number="${planElement.length / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>
            <% currentY += text_curve.size %>
            <g:if test="${planElement.l1 != 0}">
                <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                          y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">l1-<g:formatNumber
                        number="${planElement.l1}" maxFractionDigits="0"/></drw:text>
                <% currentY += text_curve.size %>
            </g:if>
            <g:if test="${planElement.l2 != 0}">
                <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                          y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">l2-<g:formatNumber
                        number="${planElement.l2}" maxFractionDigits="0"/></drw:text>
                <% currentY += text_curve.size %>
            </g:if>
        </g:if>
    %{--Если умещается в два столбца--}%
        <g:else>
            <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                      y="${currentY}" text-anchor="${TextAnchor.MIDDLE}"
                      writing-mode="lr-tb">y-${planElement.getAngleAsString()} R-<g:formatNumber
                    number="${planElement.radius}" maxFractionDigits="0"/></drw:text>
            <% currentY += text_curve.size %>
            <g:if test="${(planElement.t2 != 0) && (planElement.t1 != 0)}">
                <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                          y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">T1-<g:formatNumber
                        number="${planElement.t1 / DataEntry.MM_IN_METER}"
                        maxFractionDigits="2"/> T2-<g:formatNumber
                        number="${planElement.t2 / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>
                <% currentY += text_curve.size %>
            </g:if>
            <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                      y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">K-<g:formatNumber
                    number="${planElement.length / DataEntry.MM_IN_METER}" maxFractionDigits="2"/></drw:text>
            <% currentY += text_curve.size %>
            <g:if test="${(planElement.l2 != 0) && (planElement.l1 != 0)}">
                <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX}"
                          y="${currentY}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb">l1-<g:formatNumber
                        number="${planElement.l1}" maxFractionDigits="0"/> l2-<g:formatNumber
                        number="${planElement.l2}" maxFractionDigits="0"/></drw:text>
                <% currentY += text_curve.size %>
            </g:if>
        </g:else>
    </g:if>
%{--Отрисовка изгиба--}%
    <g:else>
        <drw:line renderer="${renderer}" style="${curve}" x1="${x1}" y1="${centerY}"
                  x2="${centerX}" y2="${centerY - 5}"/>
        <drw:line renderer="${renderer}" style="${curve}" x1="${centerX}"
                  y1="${centerY - 5}" x2="${x2}" y2="${centerY}"/>
        <drw:line renderer="${renderer}" style="${curve}" x1="${centerX}"
                  y1="${centerY - 5}" x2="${centerX}" y2="${0}"/>
        <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX - 1}"
                  y="${centerY / 2}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb"
                  rotate="-90"><g:formatNumber number="${planElement.endPlus / DataEntry.MM_IN_METER}"
                                               maxFractionDigits="2"/></drw:text>
        <drw:text renderer="${renderer}" style="${text_curve}" x="${centerX + 1 + text_curve.size}"
                  y="${centerY / 2}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb"
                  rotate="-90">${planElement.getAngleAsString()}</drw:text>
    </g:else>
</g:else>

</g:each>