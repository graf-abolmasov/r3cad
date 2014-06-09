<%@ page import="com.r3.dataset.bridge.EnterType" %>
<div class="form-group ${hasErrors(bean: dataSetInstance, field: 'enterType', 'has-error')} required">
    <label class="control-label" for="enterType"><g:message code="BridgeDataSet.enterType.label"/></label>
    <g:select id="enterType" name="enterType"
              from="${EnterType.values()}"
              keys="${EnterType.values()}"
              value="${dataSetInstance.enterType}"
              class="form-control" required="" />
</div>
