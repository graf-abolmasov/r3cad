<%@ page contentType="text/xml;charset=UTF-8" %>
<drw:renderFor renderer="${renderer}" forRenderer="svg">
    <defs>
        <style type="text/css"><![CDATA[<g:render template="style" model="[renderer:renderer, styles:styles]"/>]]></style>
        %{--<!-- Рисование точки - PointParam -->--}%
        %{--<marker id="Point0" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<path class="pt1" d="M-3,0 L0,-3 3,0 0,3 z" stroke="red"/>--}%
        %{--</marker>--}%
        %{--<marker id="Point1" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<path  class="pt1" d="M-9,-9 L9,9 M-9,9 L9,-9" stroke="blue"/>--}%
        %{--</marker>--}%
        %{--<marker id="Point2" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<path  class="pt1" d="M-9,-9 L9,9 M-9,9 L9,-9 M-9,0 L9,0" stroke="red"/>--}%
        %{--</marker>--}%
        %{--<marker id="Point3" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<circle class="pt1" stroke="red" cx="0" cy="0" r="9"/>--}%
        %{--</marker>--}%
        %{--<marker id="Point4" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<path  class="pt1" d="M-9,-9 L9,-9 9,9 -9,9 z" stroke="fuchsia"/>--}%
        %{--</marker>--}%
        %{--<marker id="Point5" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<path  class="pt1" d="M0,-9 L9,9 -9,9 z" stroke="lime"/>--}%
        %{--</marker>--}%
        %{--<marker id="Point6" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<path  class="pt1" d="M-9,-9 L9,-9 9,9 -9,9 z L9,9 M9,-9 L-9,9" stroke="black"/>--}%
        %{--</marker>--}%
        %{--<marker id="Point7" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<path  class="pt1" d="M0,-9 L0,9 M-9,0 L9,0" stroke="black"/>--}%
        %{--</marker>--}%
        %{--<marker id="Point8" viewBox="-10 -10 20 20" markerWidth="5" markerHeight="5">--}%
            %{--<path  class="pt1" d="M0,-9 L0,9 M-9,0 L9,0" stroke="blue"/>--}%
        %{--</marker>--}%
        %{--<!-- Рисование штриховки - Hatch -->--}%
        %{--<pattern id="hatch1_45" width="20" height="20" patternUnits="userSpaceOnUse">--}%
            %{--<line class="lt2_025 " x1="1" y1="20" x2="20" y2="1" />--}%
            %{--<line class="lt2_025 " x1="0" y1="1" x2="1" y2="0" />--}%
        %{--</pattern>--}%
        %{--<!-- Рисование стрелок и засечек - DimPoint -->--}%
        %{--<marker id="DimPoint1" viewBox="-2 -12 29 24" markerWidth="44" markerHeight="36" orient="auto">--}%
            %{--<path class="lt2_025" stroke="black" d="M0,0 L20,-4 16,0 20,4 z M0,-10 L0,10 M0,0 L27,0"/>--}%
        %{--</marker>--}%
        %{--<marker id="DimPoint2" viewBox="-27 -12 29 24" markerWidth="44" markerHeight="36" orient="auto">--}%
            %{--<path class="lt2_025" stroke="black" d="M0,0 L-20,-4 -16,0 -20,4 z M0,-10 L0,10 M0,0 L-27,0"/>--}%
        %{--</marker>--}%
        %{--<marker id="ViewPoint" viewBox="-2 -12 29 24" markerWidth="87" markerHeight="72" orient="auto">--}%
            %{--<path class="lt2_025" stroke="black" d="M0,0 L20,-4 16,0 20,4 z M0,-10 L0,10 M0,0 L27,0"/>--}%
        %{--</marker>--}%
    </defs>
</drw:renderFor>
<drw:renderFor renderer="${renderer}" forRenderer="cad">
    <g:render template="style" model="[renderer:renderer, styles:styles]"/>
</drw:renderFor>