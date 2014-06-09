<%@ page import="com.r3.utils.Utils" contentType="text/xml;charset=UTF-8" %>

<g:set var="mainWay" value="${drawingInstance.project.mappingWay()}"/>
<g:set var="leftWay" value="${mainWay?.leftRailWay}"/>
<g:set var="rightWay" value="${mainWay?.rightRailWay}"/>

<drw:line renderer="${renderer}" style="${styles.lineStyles["line_in_plan1"]}" x1="0" y1="${myHeight / 2.0}" x2="${drawingModel.width}" y2="${myHeight / 2.0}"/>
<g:if test="${leftWay}">
    <drw:line renderer="${renderer}" style="${styles.lineStyles["line_in_plan2"]}" x1="0" y1="${myHeight / 2.0 - 5.0}" x2="${drawingModel.width}" y2="${myHeight / 2.0 - 5.0}"/>
</g:if>
<g:if test="${rightWay}">
    <drw:line renderer="${renderer}" style="${styles.lineStyles["line_in_plan2"]}" x1="0" y1="${myHeight / 2.0 + 5.0}" x2="${drawingModel.width}" y2="${myHeight / 2.0 + 5.0}"/>
</g:if>
