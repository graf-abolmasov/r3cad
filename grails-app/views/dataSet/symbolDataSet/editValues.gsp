<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>${message(code: 'dataEntry.index.title', args: [dataSetInstance.title])}</title>
    <meta name="layout" content="dojo">
    <style>

    .field-km {
        width: 50px;
    }

    .field-pk {
        width: 50px;
    }

    .field-plus {
        width: 80px;
    }

    .field-railWay {
        width: 160px;
    }

    .field-symbolValue {
        width: 160px;
    }

    #main_grid .dgrid-cell {
        /*font-size: 1.1em;*/
        vertical-align: middle;
    }
    #distance_to_way_grid .dgrid-cell {
        /*font-size: 1.1em;*/
        vertical-align: middle;
    }

    #main_grid .field-symbolValue .dijitSelect .dijitButtonText {
        text-align: left;
    }

    #main_grid .field-symbolValue .dijitSelectLabel {
        width: 130px;
        -moz-box-sizing: border-box;
        overflow: hidden;
    }

    #distance_to_way_grid .field-railWay .dijitSelect .dijitButtonText {
        text-align: left;
    }

    #distance_to_way_grid .field-railWay .dijitSelectLabel {
        width: 130px;
        -moz-box-sizing: border-box;
        overflow: hidden;
    }
    </style>
</head>

<body class="claro">

    <script type="dojo/require">
        BorderContainer: "dijit/layout/BorderContainer",
        ContentPane: "dijit/layout/ContentPane",
        registry: "dijit/registry",
        Toolbar: "dijit/Toolbar",
        Button: "dijit/form/Button",
        ToolbarSeparator: "dijit/ToolbarSeparator"
    </script>

    <div data-dojo-type="dijit/layout/BorderContainer" style="min-width: 1em; margin: 0; border: 0; padding: 0; min-height: 1px; z-index: 0; width: 100%; height: 100%;"
         data-dojo-props="gutters:false">

        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'">
            <span id="toolbar1" data-dojo-type="dijit/Toolbar">
                <span data-dojo-type="dijit/form/Button" id="toolbar1.save" data-dojo-props="iconClass:'r3-small-icon saveIcon', showLabel:false">
                    <g:message code="default.editor.save"/>
                </span>
                <span data-dojo-type="dijit/form/Button" id="toolbar1.refresh" data-dojo-props="iconClass:'r3-small-icon refreshIcon', showLabel:false">
                    <g:message code="default.button.refresh.label"/>
                </span>
                <span data-dojo-type="dijit/ToolbarSeparator"></span>
                <span data-dojo-type="dijit/form/Button" id="toolbar1.add" data-dojo-props="iconClass:'r3-small-icon addIcon', showLabel:false">
                    <g:message code="default.button.add.label"/>
                </span>
                <span data-dojo-type="dijit/form/Button" id="toolbar1.remove" data-dojo-props="iconClass:'r3-small-icon removeIcon', showLabel:false">
                    <g:message code="default.button.remove.label"/>
                </span>
            </span>
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" style="height: 100%">
            <div id="main_grid" style="width: 100%; height: 100%"></div>
        </div>

        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'right'" style="width: 30%; height: 100%">
            <div data-dojo-type="dijit/layout/BorderContainer"
                 style="min-width: 1em; min-height: 1px; z-index: 0; width: 100%; height: 100%;"
                 data-dojo-props="gutters:true">

                %{--Габарит до пути--}%
                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'"
                     style="min-width: 1em; min-height: 120px; z-index: 0; height: 50%">
                    <div data-dojo-type="dijit/layout/BorderContainer" style="width: 100%; height: 100%;"
                         data-dojo-props="gutters:false">

                        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'">
                            <span id="toolbar2" data-dojo-type="dijit/Toolbar">
                                <span data-dojo-type="dijit/form/Button" id="toolbar2.add" data-dojo-props="iconClass:'r3-small-icon addIcon', showLabel:false">
                                    <g:message code="default.button.add.label"/>
                                </span>
                                <span data-dojo-type="dijit/form/Button" id="toolbar2.remove" data-dojo-props="iconClass:'r3-small-icon removeIcon', showLabel:false">
                                    <g:message code="default.button.remove.label"/>
                                </span>
                                <span data-dojo-type="dijit/ToolbarSeparator"></span>
                                <span style="margin: 2px; font-size:1.2em"><g:message code="DistanceToWay.label"/></span>
                            </span>
                        </div>

                        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" style="height: 100%">
                            <div id="distance_to_way_grid" style="width: 100%; height: 100%"></div>
                        </div>

                    </div>
                </div>
                %{--КОНЕЦ Габарит до пути--}%

                %{--Атрибуты знака--}%
                <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'bottom'"
                     style="min-width: 1em; min-height: 120px; z-index: 0; height: 50%">
                    <div data-dojo-type="dijit/layout/BorderContainer" style="width: 100%; height: 100%;"
                         data-dojo-props="gutters:false">

                        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'top'">
                            <span id="toolbar3" data-dojo-type="dijit/Toolbar">
                                <span data-dojo-type="dijit/form/Button" id="toolbar3.add" data-dojo-props="iconClass:'r3-small-icon addIcon', showLabel:false">
                                    <g:message code="default.button.add.label"/>
                                </span>
                                <span data-dojo-type="dijit/form/Button" id="toolbar3.remove" data-dojo-props="iconClass:'r3-small-icon removeIcon', showLabel:false">
                                    <g:message code="default.button.remove.label"/>
                                </span>
                                <span data-dojo-type="dijit/ToolbarSeparator"></span>
                                <span style="margin: 2px; font-size:1.2em"><g:message code="SymbolAttribute.s.label"/></span>
                            </span>
                        </div>

                        <div data-dojo-type="dijit/layout/ContentPane" data-dojo-props="region:'center'" style="height: 100%">
                            <div id="attributes_grid" style="width: 100%; height: 100%"></div>
                        </div>

                    </div>
                </div>
                %{--КОНЕЦ Атрибуты знака--}%

            </div>
        </div>

    </div>

    <g:render template="symbolDataSet/js_things"/>

</body>
</html>
