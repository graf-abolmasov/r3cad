class UrlMappings {

    static mappings = {

        "/i18n/init.js"(controller: 'i18N', action: 'init')
        "/i18n/$i18nFileName"(controller: 'I18N', action: 'index') {
            constraints {
                i18nFileName(matches: /messages.*\.properties/)
            }
        }

        "/$projectId/railWays"(resources: 'railWay')
        "/$projectId/drawings/$action?/$id"(controller: 'drawing')

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

        // *************************
        // *                       *
        // * Legacy code down here *
        // *                       *
        // *************************



        name exportSvg: "/export/${id}/full.svg"(controller: 'drawing', action: 'exportSvg')

        name exportLisp: "/export/${id}/full.lsp"(controller: 'drawing', action: 'exportLisp')

        name exportPdf: "/export/${id}/full.pdf"(controller: 'drawing', action: 'exportPdf')

        name userTypeAheadHelper: "/helpers/users/${id}"(controller: 'user', action: 'ajaxTypeAhead')

        /**
         * REST API Mapping
         */

    }
}
