<%@ page import="com.r3.dataset.Location; com.r3.dataset.bridge.BridgeDataEntry" %>
,Editor({
    label: '${message(code: 'BridgeDataEntry.km.label')}',
    field: 'km',
    editOn: 'false',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, places: 0}},
    sortable: false,
    autoSave: false,
    set: function (data) {
        length=data.length
        dkm=Math.round(length/${Location.METER_IN_KM})
        dpk=Math.round((length-dkm*${Location.METER_IN_KM})/${Location.METER_IN_PK})
        dplus=(length-dkm*${Location.METER_IN_KM}-dpk*${Location.METER_IN_PK})
        data.plus=data.endLocation_plus-dplus
        if (data.plus < 0){
            data.plus=data.plus+100
            data.pk=data.endLocation_pk - dpk - 1
        }
        else{
            data.pk=data.endLocation_pk - dpk
        }
        if (data.pk<0){
            data.pk=data.pk+10
            data.km=data.endLocation_km - dkm - 1
        }
        else {
            data.km=data.endLocation_km - dkm
        }

        halfLength=data.length/2
        dkm=Math.round(halfLength/${Location.METER_IN_KM})
        dpk=Math.round((halfLength-dkm*${Location.METER_IN_KM})/${Location.METER_IN_PK})
        dplus=(halfLength-dkm*${Location.METER_IN_KM}-dpk*${Location.METER_IN_PK})
        data.axisLocation_plus=data.endLocation_plus-dplus
        if (data.axisLocation_plus<0){
            data.axisLocation_plus=data.axisLocation_plus+100
            data.axisLocation_pk=data.endLocation_pk - dpk - 1
        }
        else{
            data.axisLocation_pk=data.endLocation_pk - dpk
        }
        if (data.axisLocation_pk<0){
            data.axisLocation_pk=data.axisLocation_pk+10
            data.axisLocation_km=data.endLocation_km - dkm - 1
        }
        else {
            data.axisLocation_km=data.endLocation_km - dkm
        }

        return data.km
   }
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.pk.label')}',
    field: 'pk',
    editOn: 'false',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, max: 9, places: 0}},
    sortable: false,
    autoSave: false
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.plus.label')}',
    field: 'plus',
    editOn: 'false',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0}},
    sortable: false,
    autoSave: false,
    set: function (data) {
        return data.plus ? Math.round(data.plus * ${Location.MM_IN_METER}) : 0;
    }
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.axisLocation.km.label')}',
    field: 'axisLocation_km',
    editOn: 'false',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, places: 0}},
    sortable: false,
    autoSave: false,
    get: function(data) {
        return data.axisLocation.km
    },
    set: function(data) {
        data.axisLocation.km=data.axisLocation_km;
        return data.axisLocation_km;
    }
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.axisLocation.pk.label')}',
    field: 'axisLocation_pk',
    editOn: 'false',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, max: 9, places: 0}},
    sortable: false,
    autoSave: false,
    get: function(data) {
        return data.axisLocation.pk;
    },
    set: function(data) {
        data.axisLocation.pk=data.axisLocation_pk;
        return data.axisLocation_pk;
    }
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.axisLocation.plus.label')}',
    field: 'axisLocation_plus',
    editOn: 'false',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0}},
    sortable: false,
    autoSave: false,
    get: function(data) {
        return data.axisLocation_plus;
    },
    set: function (data) {
        data.axisLocation.plus = data.axisLocation_plus ? Math.round(data.axisLocation_plus * ${Location.MM_IN_METER}):0;
        return data.axisLocation_plus ? Math.round(data.axisLocation_plus * ${Location.MM_IN_METER}):0
    }
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.endLocation.km.label')}',
    field: 'endLocation_km',
    editOn: 'click',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, places: 0}},
    sortable: false,
    autoSave: false,
    get: function(data) {
        return data.endLocation.km
    },
    set: function(data) {
        data.endLocation.km=data.endLocation_km;
        return data.endLocation_km;
    }
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.endLocation.pk.label')}',
    field: 'endLocation_pk',
    editOn: 'click',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0, max: 9, places: 0}},
    sortable: false,
    autoSave: false,
    get: function(data) {
        return data.endLocation.pk;
    },
    set: function(data) {
        data.endLocation.pk=data.endLocation_pk;
        return data.endLocation_pk;
    }
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.endLocation.plus.label')}',
    field: 'endLocation_plus',
    editOn: 'click',
    editorArgs: {style: { 'width': "99%" }, constraints: {min: 0}},
    sortable: false,
    autoSave: false,
    get: function(data) {
        return data.endLocation_plus;
    },
    set: function (data) {
        data.endLocation.plus = data.endLocation_plus ? Math.round(data.endLocation_plus * ${Location.MM_IN_METER}):0;
        return data.endLocation_plus ? Math.round(data.endLocation_plus * ${Location.MM_IN_METER}):0
    }
}, NumberSpinner)

,Editor({
    label: '${message(code: 'BridgeDataEntry.length.label')}',
    field: 'length',
    editOn: 'click',
    sortable: false,
    editorArgs: { style: { 'width': '99%' }, smallDelta:0.01, constraints: {min:0, places:2} },
    autoSave: false,
    set: function (data) {
        return data.length ? Math.round(data.length * ${Location.MM_IN_METER}) : 0;
    }
}, NumberSpinner)