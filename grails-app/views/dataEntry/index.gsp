<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
  <head>
    <title>${message(code: 'dataEntry.index.title', args: [dataSetInstance.title])}</title>
    <meta content='dojo' name='layout' />
  </head>
  <body>
    <div class='table-editor' id='${dataSetType}DataEntriesTableEditor'></div>
    <script type='text/javascript'>
        TableEditorConfig = {
          restUrl: "${createLink(resource: 'dataSet/dataEntry', action: 'index',
                    dataSetId: dataSetInstance.id, params: [projectId: dataSetInstance.projectId])}/",
          createUrl: "${createLink(resource: 'dataSet/dataEntry', action: 'create',
                    dataSetId: dataSetInstance.id, params: [projectId: dataSetInstance.projectId])}"
        };
    </script>
    <g:render model='[dataSetInstance: dataSetInstance]' template='${dataSetType}' />
  </body>
</html>