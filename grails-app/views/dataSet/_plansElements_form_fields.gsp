<div class="form-group checkbox ${hasErrors(bean: dataSetInstance, field: 'isCalculateField', 'has-error')} required">
    <label class="control-label" for="isCalculateField">
        <g:checkBox name="isCalculateField" value="${dataSetInstance.isCalculateField}"/>
        <g:message code="plansElementsDataSet.isCalculateField.label"/>
    </label>
</div>
