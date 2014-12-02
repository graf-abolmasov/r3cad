<%@ page contentType="image/svg+xml;charset=UTF-8" %><?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE svg PUBLIC "-//W3C//-DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
<svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="${pageSize.aValue}mm" height="${pageSize.bValue}mm" viewBox="0 0 ${pageSize.aValue} ${pageSize.bValue}">
    <title>${drawingInstance.project.name.encodeAsHTML()}</title>
    <g:render template="defs"/>
    <g:render template="${drawingInstance.template}"/>
</svg>

