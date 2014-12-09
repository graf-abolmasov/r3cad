<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><g:layoutTitle default="Rail Road Repair"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <asset:stylesheet src="application-dojo.css"/>

    <g:javascript>
        dojoConfig = {
            async: true,
            isDebug: 1,
            parseOnLoad: true,
            has: {
                "dojo-firebug": true,
                "dojo-debug-messages": true
            },
            packages: [
                { name: 'dgrid',        location: '${g.assetPath(src: 'dgrid')}' },
                { name: 'xstyle',       location: '${g.assetPath(src: 'xstyle')}' },
                { name: 'put-selector', location: '${g.assetPath(src: 'put-selector')}' },
                { name: 'r3',           location: '${g.assetPath(src: 'r3')}' },
                { name: 'dojo',         location: '${g.assetPath(src: 'dojo')}' },
                { name: 'dijit',        location: '${g.assetPath(src: 'dijit')}' },
                { name: 'dojox',        location: '${g.assetPath(src: 'dojox')}' },
                { name: 'gridx',        location: '${g.assetPath(src: 'gridx')}' },
                { name: 'i18n',         location: '${g.createLink(uri: "/i18n")}', main: 'init'}
            ]
        }
    </g:javascript>

    <asset:javascript src="application-dojo.js"/>

    <g:layoutHead/>
</head>
<body class="claro">
    <div id="loadingOverlay" class="loadingOverlay pageOverlay">
        <div class="loadingMessage">${message(code: 'app.loading')}</div>
    </div>
<script type="text/javascript">
    require([
        'dojo/parser'
    ]);
</script>
    <g:layoutBody/>
    <script>
        require(["dojo/dom","dojo/dom-style", "dojo/_base/fx", 'dojo/ready'], function(dom, domStyle, fx, ready) {
            ready(function() {
                fx.fadeOut({
                    node: dom.byId("loadingOverlay"),
                    onEnd: function(node){
                        domStyle.set(node, 'display', 'none');
                    }
                }).play();
            });
        });
    </script>
</body>
</html>
