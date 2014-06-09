<%@ page contentType="text/plain;charset=utf-8" %>
(Command "ПОКАЗАТЬ" "МАСШТАБ" "1000")
(command "LWDISPLAY" "1")
<g:render template="defs" />
<g:render template="${drawingInstance.template}" />
(command "ПОКАЗАТЬ" "ВСЕ")
