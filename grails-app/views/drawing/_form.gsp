<%@ page import="com.r3.Project; com.r3.drawing.PageSizeFormat; com.r3.drawing.layout.Drawing" %>

<div class="row">
    <div class="col-sm-10">
        <div class="form-group ${hasErrors(bean: drawingInstance, field: 'name', 'has-error')} required">
            <label class="control-label" for="name"><g:message code="drawing.name.label"/></label>
            <input id="name" name="name" class="form-control" type="text" value="${drawingInstance.name}" required=""/>
        </div>
    </div>
    <div class="col-sm-2">
        <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.pageNumber', 'has-error')} required">
            <label class="control-label" for="pageNumber"><g:message code="drawing.pageNumber.label"/></label>
            <g:field name="pageNumber" class="form-control" type="number" min="1" value="${drawingInstance.pageNumber}" required=""/>
        </div>
    </div>
</div>

<div class="row">
    <div class="col-sm-6">
        <div class="form-group ${hasErrors(bean: drawingInstance, field: 'pageFormat', 'has-error')} required">
            <label class="control-label" for="pageFormat"><g:message code="drawing.pageFormat.label"/></label>
            <g:select name="pageFormat" from="${PageSizeFormat?.values()}" keys="${PageSizeFormat.values()*.name()}" required="" value="${drawingInstance?.pageFormat?.name()}" class="form-control"/>
        </div>
    </div>
    <div class="col-sm-6">
        <div class="form-group ${hasErrors(bean: drawingInstance, field: 'pageFormatCoefficient', 'has-error')} required">
            <label class="control-label" for="pageFormatCoefficient"><g:message code="drawing.pageFormatCoefficient.label"/></label>
            <input id="pageFormatCoefficient" name="pageFormatCoefficient" class="form-control" type="number" min="1" value="${drawingInstance.pageFormatCoefficient}" required=""/>
        </div>
    </div>
</div>

<fieldset class="embedded"><legend><g:message code="drawing.margin.label" /></legend>

    <div class="row">
        <div class="col-xs-3">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'margin.left', 'has-error')} required">
                <label class="control-label" for="margin.left"><g:message code="drawing.margin.left.label"/></label>
                <g:field name="margin.left" type="number" min="0" value="${drawingInstance.margin.left}" required="" class="form-control"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'margin.top', 'has-error')} required">
                <label class="control-label" for="margin.top"><g:message code="drawing.margin.top.label"/></label>
                <g:field name="margin.top" type="number" min="0" value="${drawingInstance.margin.top}" required="" class="form-control"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'margin.right', 'has-error')} required">
                <label class="control-label" for="margin.right"><g:message code="drawing.margin.right.label"/></label>
                <g:field name="margin.right" type="number" min="0" value="${drawingInstance.margin.right}" required="" class="form-control"/>
            </div>
        </div>
        <div class="col-xs-3">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'margin.bottom', 'has-error')} required">
                <label class="control-label" for="margin.bottom"><g:message code="drawing.margin.bottom.label"/></label>
                <g:field name="margin.bottom" type="number" min="0" value="${drawingInstance.margin.bottom}" required="" class="form-control"/>
            </div>
        </div>
    </div>

</fieldset> %{-- ОТСТУПЫ --}%

<fieldset class="embedded"><legend><g:message code="drawing.stampData.label" default="Stamp Data" /></legend> %{--Штамп--}%

    <div class="row">
        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.designer', 'has-error')} ">
                <label for="stampData.designer" class="control-label"><g:message code="drawing.stampData.designer.label"/></label>
                <g:textField name="stampData.designer" value="${drawingInstance.stampData?.designer}" class="form-control user-typeahead" autocomplete="off" spellcheck="false"/>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.designerDate', 'has-error')} ">
                <label for="stampData.designerDate" class="control-label"><g:message code="drawing.stampData.designerDate.label" /></label>

                <div class="input-group">
                    <g:textField name="stampData.designerDate" type="text" class="form-control date-field"
                                 value="${formatDate(formatName: 'drawing.stampData.dateFormat', date: drawingInstance.stampData?.designerDate)}" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
    </div>%{--Разработчик--}%

    <div class="row">
        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.supervisor', 'has-error')} ">
                <label for="stampData.supervisor" class="control-label"><g:message code="drawing.stampData.supervisor.label"/></label>
                <g:textField name="stampData.supervisor" value="${drawingInstance.stampData?.supervisor}" class="form-control user-typeahead" autocomplete="off" spellcheck="false"/>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.supervisorDate', 'has-error')} ">
                <label for="stampData.supervisorDate" class="control-label"><g:message code="drawing.stampData.supervisorDate.label" /></label>
                <div class="input-group">
                    <g:textField name="stampData.supervisorDate" type="text" class="form-control date-field"
                                 value="${formatDate(formatName: 'drawing.stampData.dateFormat', date: drawingInstance.stampData?.supervisorDate)}" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
    </div>%{--Проверил--}%

    <div class="row">
        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.mainSpecialist', 'has-error')} ">
                <label for="stampData.mainSpecialist" class="control-label"><g:message code="drawing.stampData.mainSpecialist.label"/></label>
                <g:textField name="stampData.mainSpecialist" value="${drawingInstance.stampData?.mainSpecialist}" class="form-control user-typeahead" autocomplete="off" spellcheck="false"/>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.mainSpecialistDate', 'has-error')} ">
                <label for="stampData.mainSpecialistDate" class="control-label"><g:message code="drawing.stampData.mainSpecialistDate.label" /></label>
                <div class="input-group">
                    <g:textField name="stampData.mainSpecialistDate" type="text" class="form-control date-field"
                                 value="${formatDate(formatName: 'drawing.stampData.dateFormat', date: drawingInstance.stampData?.mainSpecialistDate)}" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
    </div>%{--Главный специалист--}%

    <div class="row">
        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.departmentHead', 'has-error')} ">
                <label for="stampData.departmentHead" class="control-label"><g:message code="drawing.stampData.departmentHead.label"/></label>
                <g:textField name="stampData.departmentHead" value="${drawingInstance.stampData?.departmentHead}" class="form-control user-typeahead" autocomplete="off" spellcheck="false"/>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.departmentHeadDate', 'has-error')} ">
                <label for="stampData.departmentHeadDate" class="control-label"><g:message code="drawing.stampData.departmentHeadDate.label" /></label>
                <div class="input-group">
                    <g:textField name="stampData.departmentHeadDate" type="text" class="form-control date-field"
                                 value="${formatDate(formatName: 'drawing.stampData.dateFormat', date: drawingInstance.stampData?.departmentHeadDate)}" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
    </div>%{--Нач. отдела--}%

    <div class="row">
        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.gostInspector', 'has-error')} ">
                <label for="stampData.gostInspector" class="control-label"><g:message code="drawing.stampData.gostInspector.label"/></label>
                <g:textField name="stampData.gostInspector" value="${drawingInstance.stampData?.gostInspector}" class="form-control user-typeahead" autocomplete="off" spellcheck="false"/>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.gostInspectorDate', 'has-error')} ">
                <label for="stampData.gostInspectorDate" class="control-label"><g:message code="drawing.stampData.gostInspectorDate.label" /></label>
                <div class="input-group">
                    <g:textField name="stampData.gostInspectorDate" type="text" class="form-control date-field"
                                 value="${formatDate(formatName: 'drawing.stampData.dateFormat', date: drawingInstance.stampData?.gostInspectorDate)}" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
    </div>%{--Нормоконтролер--}%

    <div class="row">
        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.chief', 'has-error')} ">
                <label for="stampData.chief" class="control-label"><g:message code="drawing.stampData.chief.label"/></label>
                <g:textField name="stampData.chief" value="${drawingInstance.stampData?.chief}" class="form-control user-typeahead" autocomplete="off" spellcheck="false"/>
            </div>
        </div>

        <div class="col-sm-6">
            <div class="form-group ${hasErrors(bean: drawingInstance, field: 'stampData.chiefDate', 'has-error')} ">
                <label for="stampData.chiefDate" class="control-label"><g:message code="drawing.stampData.chiefDate.label" /></label>
                <div class="input-group">
                    <g:textField name="stampData.chiefDate" type="text" class="form-control date-field"
                                 value="${formatDate(formatName: 'drawing.stampData.dateFormat', date: drawingInstance.stampData?.chiefDate)}" />
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
            </div>
        </div>
    </div>%{--ГИП--}%

</fieldset> %{-- Фамилии в штампе--}%

<g:javascript>
    $('.date-field').datepicker({
        format: "${message(code: 'drawing.stampData.dateFormat').toString().toLowerCase()}",
        todayBtn: 'linked',
        autoclose: true,
        language: "ru"
    });

    $('.user-typeahead').typeahead({
        name: 'users',
        prefetch: '${createLink(controller: 'user', params: [projectId: drawingInstance.projectId])}',
        ttl: 1000,
        template: '<p>{{fullName}}</p>',
        engine: Hogan
    });
</g:javascript>