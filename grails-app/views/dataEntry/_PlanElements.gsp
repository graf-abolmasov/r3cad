<%@ page import="com.r3.dataset.PlanElementsDataEntry; com.r3.dataset.CurveType; com.r3.dataset.Location" %>
<script type="text/javascript">
    require([
        "dgrid/editor",
        "r3/editor/TableEditor",
        "r3/editor/NumericEditableColumn",
        "r3/editor/SelectEditableColumn",
        'i18n',
        'dojo/dom-construct',
        "dojo/domReady!"
    ], function (Editor, TableEditor, NumericColumn, SelectColumn, i18n) {

        new TableEditor({
            columns: [
                numericColumn1('location_km',     {constraints: {min: 0, places: 0}}),
                numericColumn1('location_pk',     {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn1('location_plus',   {constraints: {min: 0}}),
                selectColumn('elementType', i18n.enums.CurveType, function (data) {
                    if (data.elementType == '${CurveType.STRAIGHT.name()}') {
                        data.angle = 0;
                        data.angleAsString = '0°0\'0\"';
                        data.radius = 0;
                        data.h = 0;
                        data.t1 = 0;
                        data.t2 = 0;
                        data.l1 = 0;
                        data.l2 = 0;
                    }
                    return data.elementType
                }),
                angleColumn('angleAsString'),
                numericColumn2('radius', {constraints: {places: 0}}),
                numericColumn2('h', {constraints: {places: 0}}),
                numericColumn2('t1', {smallDelta: 0.01}),
                numericColumn2('t2', {smallDelta: 0.01}),
                numericColumn3('length', {smallDelta: 0.001}),
                numericColumn2('endKm', {constraints: {min: 0, places: 0}}),
                numericColumn2('endPk', {constraints: {min: 0, max: 9, places: 0}}),
                numericColumn3('endPlus', {constraints: {min: 0}}),
                numericColumn3('l1', {smallDelta: 1, constraints: {min: 0, places: 0}}),
                numericColumn3('l2', {smallDelta: 1, constraints: {min: 0, places: 0}})
            ]
        }, 'PlanElementsDataEntriesTableEditor').startup();

        function numericColumn1(field, editorArgs) {
            return NumericColumn('DataEntry.', field, '.label', editorArgs);
        }

        function numericColumn2(field, editorArgs, setFunction) {
            return NumericColumn('PlanElementsDataEntry.', field, '.label', editorArgs, undefined, setFunction);
        }

        function selectColumn(field, selectOptions, setFunction) {
            return SelectColumn('PlanElementsDataEntry.', field, '.label', selectOptions, undefined, setFunction);
        }

        function numericColumn3(field, editorArgs) {
            return numericColumn2(field, editorArgs, function(data) {
                data[field] = data[field] * ${com.r3.dataset.DataEntry.MM_IN_METER};
            });
        }

        function angleColumn(field) {
            return Editor({
                label: i18n.t('PlanElementsDataEntry.' +  field + '.label'),
                field: field,
                editOn:'click',
                sortable: false,
                autoSave: true,
                set: function (data) {
                    var degVsMinSec= data.angleAsString.split('°');
                    var deg = degVsMinSec[0];
                    var minSec = degVsMinSec[1];
                    var minVsSec = minSec.split('\'')[0];
                    var min = minVsSec;
                    var sec = minVsSec[1].split('\"')[0];
                    data.angle = parseFloat(deg) +
                            parseFloat(min / ${PlanElementsDataEntry.MINUTES_IN_GRAD}) +
                            parseFloat(sec / ${PlanElementsDataEntry.SECONDS_IN_GRAD});
                    return data.angleAsString;
                }
            })
        }


        %{--<g:if test="${dataSetInstance.isCalculateField}" >--}%
        %{--numericColumn2('t1', {}, undefined, function (data) {--}%
            %{--if (data.elementType == '${CurveType.STRAIGHT.name()}') {--}%
                %{--data.t1 = 0;--}%
            %{--} else {--}%
                %{--y = data.angle;--}%
                %{--r = data.radius;--}%
                %{--l = data.l1;//${Math.PI} * 2 * data.radius * data.angle / ${PlanElementsDataEntry.ROUND_ANGLE} +data.l1 / 2 + data.l2 / 2--}%
                %{--m = 0.5 * l * (1 - l * l / (120 * r * r))--}%
                %{--p = (l * l / (24 * r * r)) * (1 - l * l / (112 * r * r))--}%
                %{--tp = p * Math.tan((${Math.PI} * y / 180) / 2)--}%
                %{--t = r * Math.tan((${Math.PI} * y / 180) / 2);--}%
                %{--b=l/(2*r);--}%
                %{--xkpk = l * (1 - l * l / (40 * r * r));--}%
                %{--ykpk=(l*l/(6*r))*(1-l/(56*r));--}%
                %{--m=xkpk-r*Math.sin(${Math.PI} * b / 180);--}%
                %{--p=ykpk-r*(1-Math.cos(${Math.PI} * b / 180));--}%
                %{--tp=p*Math.tan((${Math.PI} * y / 180) / 2);--}%
                %{--tc=t+m/2+tp;--}%
                %{--data.t1=tc--}%
            %{--}--}%
            %{--return data.t1 ? Math.round(data.t1 * ${Location.MM_IN_METER}) : 0;--}%
        %{--}),--}%
                %{--numericColumn2('t2', {}, undefined, function (data) {--}%
                    %{--if (data.elementType == '${CurveType.STRAIGHT.name()}') {--}%
                        %{--data.t2 = 0--}%
                    %{--}--}%
                    %{--else {--}%
                        %{--y = data.angle--}%
                        %{--r = data.radius--}%
                        %{--l = data.l2//${Math.PI} * 2 * data.radius * data.angle / ${PlanElementsDataEntry.ROUND_ANGLE} +data.l1 / 2 + data.l2 / 2--}%
                        %{--m = 0.5 * l * (1 - l * l / (120 * r * r))--}%
                        %{--p = (l * l / (24 * r * r)) * (1 - l * l / (112 * r * r))--}%
                        %{--tp = p * Math.tan((${Math.PI} * y / 180) / 2)--}%
                        %{--t = r * Math.tan((${Math.PI} * y / 180) / 2)--}%
                        %{--b=l/(2*r)--}%
                        %{--xkpk=l*(1-l*l/(40*r*r))--}%
                        %{--ykpk=(l*l/(6*r))*(1-l/(56*r))--}%
                        %{--m=xkpk-r*Math.sin(${Math.PI} * b / 180)--}%
                        %{--p=ykpk-r*(1-Math.cos(${Math.PI} * b / 180))--}%
                        %{--tp=p*Math.tan((${Math.PI} * y / 180) / 2)--}%
                        %{--tc=t+m/2+tp--}%
                        %{--data.t2=tc--}%
                    %{--}--}%
                    %{--return data.t2 ? Math.round(data.t2 * ${Location.MM_IN_METER}) : 0--}%
                %{--}),--}%
                %{--numericColumn2('length', {smallDelta: 0.001}, undefined, function (data) {--}%
                    %{--if (data.elementType == '${CurveType.STRAIGHT.name()}') {--}%
                        %{--data.length = (data.endKm * ${Location.METER_IN_KM}+data.endPk * ${Location.METER_IN_PK}+data.endPlus) - (data.km * ${Location.METER_IN_KM}+data.pk * ${Location.METER_IN_PK}+data.plus /${Location.MM_IN_METER})--}%
                    %{--}--}%
                    %{--else {--}%
                        %{--data.length = ${Math.PI} * 2 * data.radius * data.angle / ${PlanElementsDataEntry.ROUND_ANGLE} +data.l1 / 2 + data.l2 / 2--}%
                    %{--}--}%
                    %{--return data.length ? Math.round(data.length * ${Location.MM_IN_METER}) : 0;--}%
                %{--})--}%
        %{--</g:if>--}%
    });
</script>