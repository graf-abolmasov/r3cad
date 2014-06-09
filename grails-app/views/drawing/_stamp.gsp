<%@ page import="com.r3.drawing.style.DominantBaseline; com.r3.drawing.style.TextAnchor" %>
<g:set var="txt_nar" value="${styles.textStyles["text_narrow"]}"/>
<g:set var="txt_norm" value="${styles.textStyles["text_normal"]}"/>
<g:set var="frame" value="${styles.lineStyles["frame"]}"/>
<g:set var="df_black" value="${styles.lineStyles["df_black"]}"/>
<g:set var="stampData" value="${drawingInstance.stampData}"/>
<drw:group renderer="${renderer}">
    %{--<!--Page Border-->--}%
    <drw:rect renderer="${renderer}" x="${0.0}" y="${0.0}" width="${pageSize.aValue}"
              height="${pageSize.bValue}" style="${df_black}"/>

    %{--<!--Stamp Border-->--}%
    <drw:rect renderer="${renderer}" x="${20.0}" y="${5.0}" width="${pageSize.aValue - 25}"
              height="${pageSize.bValue - 10}" style="${frame}"/>

    %{--<!--Right Bottom stamp table-->--}%
    <drw:group renderer="${renderer}" x="${pageSize.aValue - 190}" y="${pageSize.bValue - 60}">
        <drw:rect renderer="${renderer}" x="${0.0}" y="${0.0}" width="${185}" height="${55}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${10.0}" y1="${0.0}" x2="${10.0}" y2="${25.0}" style="${df_black}"/>
        <drw:line renderer="${renderer}" x1="${20.0}" y1="${0.0}" x2="${20.0}" y2="${55.0}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${30.0}" y1="${0.0}" x2="${30.0}" y2="${25.0}" style="${df_black}"/>
        <drw:line renderer="${renderer}" x1="${40.0}" y1="${0.0}" x2="${40.0}" y2="${55.0}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${55.0}" y1="${0.0}" x2="${55.0}" y2="${55.0}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${65.0}" y1="${0.0}" x2="${65.0}" y2="${55.0}" style="${frame}"/>

        <drw:line renderer="${renderer}" x1="${0}" y1="5" x2="${65.0}" y2="5" style="${df_black}"/>
        <drw:line renderer="${renderer}" x1="${0}" y1="${10.0}" x2="${65.0}" y2="${10.0}" style="${df_black}"/>
        <drw:line renderer="${renderer}" x1="${0}" y1="${15.0}" x2="${65.0}" y2="${15.0}" style="${df_black}"/>

        <drw:line renderer="${renderer}" x1="${0}" y1="${20.0}" x2="${65.0}" y2="${20.0}" style="${frame}"/>
        <drw:text renderer="${renderer}" x="${1.0}" y="${22.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.changed" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${10.5}" y="${22.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.members-count" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${21.0}" y="${22.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.page" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${31.0}" y="${22.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.doc-num" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${41.0}" y="${22.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.signature" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${56.0}" y="${22.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.date" />
        </drw:text>
        <drw:line renderer="${renderer}" x1="${0}" y1="${25.0}" x2="${65.0}" y2="${25.0}" style="${frame}"/>
        <drw:text renderer="${renderer}" x="${1.0}" y="${27.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stampData.designer.label.stamp" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${1.0}" y="${32.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stampData.supervisor.label.stamp" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${1.0}" y="${37.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stampData.mainSpecialist.label.stamp" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${1.0}" y="${42.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stampData.departmentHead.label.stamp" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${1.0}" y="${47.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stampData.gostInspector.label.stamp" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${1.0}" y="${52.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stampData.chief.label.stamp" />
        </drw:text>

        <g:if test="${stampData}">
            <g:if test="${stampData.designer}">
                <drw:text renderer="${renderer}" x="${21.0}" y="${27.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                    ${stampData.designer}
                </drw:text>
            </g:if>
            <g:if test="${stampData.supervisor}">
                <drw:text renderer="${renderer}" x="${21.0}" y="${32.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                    ${stampData.supervisor}
                </drw:text>
            </g:if>
            <g:if test="${stampData.mainSpecialist}">
                <drw:text renderer="${renderer}" x="${21.0}" y="${37.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                    ${stampData.mainSpecialist}
                </drw:text>
            </g:if>
            <g:if test="${stampData.departmentHead}">
                <drw:text renderer="${renderer}" x="${21.0}" y="${42.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                    ${stampData.departmentHead}
                </drw:text>
            </g:if>
            <g:if test="${stampData.gostInspector}">
                <drw:text renderer="${renderer}" x="${21.0}" y="${47.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                    ${stampData.gostInspector}
                </drw:text>
            </g:if>
            <g:if test="${stampData.gostInspector}">
                <drw:text renderer="${renderer}" x="${21.0}" y="${52.5}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                    ${stampData.chief}
                </drw:text>
            </g:if>

            <drw:text renderer="${renderer}" x="${60.0}" y="${27.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                <g:formatDate formatName="drawing.stamp.date-format" date="${stampData.designerDate}" />
            </drw:text>
            <drw:text renderer="${renderer}" x="${60.0}" y="${32.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                <g:formatDate formatName="drawing.stamp.date-format" date="${stampData.supervisorDate}" />
            </drw:text>
            <drw:text renderer="${renderer}" x="${60.0}" y="${37.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                <g:formatDate formatName="drawing.stamp.date-format" date="${stampData.mainSpecialistDate}" />
            </drw:text>
            <drw:text renderer="${renderer}" x="${60.0}" y="${42.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                <g:formatDate formatName="drawing.stamp.date-format" date="${stampData.departmentHeadDate}" />
            </drw:text>
            <drw:text renderer="${renderer}" x="${60.0}" y="${47.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                <g:formatDate formatName="drawing.stamp.date-format" date="${stampData.gostInspectorDate}" />
            </drw:text>
            <drw:text renderer="${renderer}" x="${60.0}" y="${52.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
                <g:formatDate formatName="drawing.stamp.date-format" date="${stampData.chiefDate}" />
            </drw:text>
        </g:if>

        <drw:line renderer="${renderer}" x1="${0}" y1="${30.0}" x2="${65.0}" y2="${30.0}" style="${df_black}"/>
        <drw:line renderer="${renderer}" x1="${0}" y1="${35.0}" x2="${65.0}" y2="${35.0}" style="${df_black}"/>
        <drw:line renderer="${renderer}" x1="${0}" y1="${40.0}" x2="${65.0}" y2="${40.0}" style="${df_black}"/>
        <drw:line renderer="${renderer}" x1="${0}" y1="${45.0}" x2="${65.0}" y2="${45.0}" style="${df_black}"/>
        <drw:line renderer="${renderer}" x1="${0}" y1="${50.0}" x2="${65.0}" y2="${50.0}" style="${df_black}"/>

        <drw:line renderer="${renderer}" x1="${65.0}" y1="${10.0}" x2="${185.0}" y2="${10.0}" style="${frame}"/>
        <drw:text renderer="${renderer}" x="${125.0}" y="${5.0}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            14770-ТКР.ПЖ
        </drw:text>
        <drw:text renderer="${renderer}" x="${125.0}" y="${17.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            ${drawingInstance.project.name}
        </drw:text>
        <drw:line renderer="${renderer}" x1="${65.0}" y1="${25.0}" x2="${185.0}" y2="${25.0}" style="${frame}"/>
        <drw:text renderer="${renderer}" x="${95.0}" y="${32.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            Железнодорожные пути
        </drw:text>
        <drw:line renderer="${renderer}" x1="${65.0}" y1="${40.0}" x2="${185.0}" y2="${40.0}" style="${frame}"/>
        <drw:text renderer="${renderer}" x="${95.0}" y="${47.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            ${drawingInstance.name}
        </drw:text>
        <drw:text renderer="${renderer}" x="${160.0}" y="${47.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            ${drawingInstance.project.organization.name}
        </drw:text>

        <drw:line renderer="${renderer}" x1="${135.0}" y1="${25.0}" x2="${135.0}" y2="${55.0}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${150.0}" y1="${25.0}" x2="${150.0}" y2="${40.0}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${165.0}" y1="${25.0}" x2="${165.0}" y2="${40.0}" style="${frame}"/>
        <drw:text renderer="${renderer}" x="${142.5}" y="${27.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.stage" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${157.7}" y="${27.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.page" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${175.0}" y="${27.5}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            <g:message code="drawing.stamp.pages" />
        </drw:text>
        <drw:line renderer="${renderer}" x1="${135.0}" y1="${30.0}" x2="${185.0}" y2="${30.0}" style="${frame}"/>
        <drw:text renderer="${renderer}" x="${142.5}" y="${35.0}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            П
        </drw:text>
        <drw:text renderer="${renderer}" x="${157.7}" y="${35.0}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            ${drawingInstance.pageNumber}
        </drw:text>
        <drw:text renderer="${renderer}" x="${175.0}" y="${35.0}" text-anchor="${TextAnchor.MIDDLE}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" style="${txt_nar}">
            3
        </drw:text>
    </drw:group>

    %{--<!--Left stamp table-->--}%
    <drw:group renderer="${renderer}" y="${pageSize.bValue - 155}">
        <drw:rect renderer="${renderer}" x="${0.0}" y="${0.0}" width="${20}" height="${65}" style="${df_black}"/>
        <drw:rect renderer="${renderer}" x="${5.0}" y="${0.0}" width="${5}" height="${65}" style="${df_black}"/>
        <drw:rect renderer="${renderer}" x="${15.0}" y="${0.0}" width="${5}" height="${65}" style="${df_black}"/>
        <drw:text renderer="${renderer}" x="${2.5}" y="${60.0}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" rotate="${-90.0}" style="${txt_norm}">
            <g:message code="drawing.stamp.agreed" />
        </drw:text>

        <drw:rect renderer="${renderer}" x="${5.0}" y="${10.0}" width="${15}" height="${30}" style="${df_black}"/>
        <drw:rect renderer="${renderer}" x="${5.0}" y="${25.0}" width="${15}" height="${40}" style="${df_black}"/>

        <drw:rect renderer="${renderer}" x="${8.0}" y="${65.0}" width="${12}" height="${85}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${8.0}" y1="${90.0}" x2="${20.0}" y2="${90.0}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${8.0}" y1="${125.0}" x2="${20.0}" y2="${125.0}" style="${frame}"/>
        <drw:line renderer="${renderer}" x1="${13.0}" y1="${65.0}" x2="${13.0}" y2="${150.0}" style="${frame}"/>
        <drw:text renderer="${renderer}" x="${10.5}" y="${145.0}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" rotate="${-90.0}" style="${txt_norm}">
            <g:message code="drawing.stamp.inv-num" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${10.5}" y="${120.0}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" rotate="${-90.0}" style="${txt_norm}">
            <g:message code="drawing.stamp.sign-date" />
        </drw:text>
        <drw:text renderer="${renderer}" x="${10.5}" y="${85.0}" text-anchor="${TextAnchor.START}" writing-mode="lr-tb" dominant-baseline="${DominantBaseline.MIDDLE}" rotate="${-90.0}" style="${txt_norm}">
            <g:message code="drawing.stamp.inv-num2" />
        </drw:text>
    </drw:group>

    <drw:text renderer="${renderer}" x="${pageSize.aValue - 6}" y="${pageSize.bValue - 2.5}"
              text-anchor="${TextAnchor.END}" writing-mode="lr-tb"
              dominant-baseline="${DominantBaseline.MIDDLE}"
              style="${txt_norm}"><g:message code="drawing.stamp.page-format" args="${[drawingInstance.pageFormat, drawingInstance.pageFormatCoefficient]}" /></drw:text>
</drw:group>