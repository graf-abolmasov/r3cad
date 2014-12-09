class UrlMappings {

    static mappings = {

        "/i18n/init.js"(controller: 'I18N', action: 'init')
        "/i18n/$i18nFileName"(controller: 'I18N', action: 'index') {
            constraints {
                i18nFileName(matches: /messages.*\.properties/)
            }
        }

        "/$projectId/railWays"(resources: 'railWay')
        "/$projectId/drawings/$action?/$id(.$format)?"(controller: 'drawing')

        "/$projectId/dataSets"(resources: 'dataSet') {
            "/dataEntries"(resources: 'dataEntry') {
                "/distancesToWays"(resources: 'distanceToWay')
                "/bridgeSpans"(resources: 'bridgeSpan')
                "/wireHeights"(resources: 'wireHeight')
                "/bridgePiers"(resources: 'bridgePierHeight')
            }
        }

        "/"(controller: 'project', action: 'index')
        "/project/$action"(controller: 'project')
        "/$projectId/run"(controller: 'app', action: 'run')

        "/$projectId/tree"(controller: 'projectTree', action: 'children')
        "/$projectId/tree/$identity"(controller: 'projectTree', parseRequest: true) {
            action = [GET: 'children', PUT: 'move']
        }

        "500"(view: '/error')
        "/test/randomDrawing"(controller: 'drawing', action: 'randomDrawing')

        "/$projectId/users"(controller: 'user', action: 'index')
    }
}
