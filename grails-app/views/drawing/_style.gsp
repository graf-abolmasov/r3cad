<%@ page contentType="text/css;charset=UTF-8" %>
<drw:renderFor renderer="${renderer}" forRenderer="svg">
        line,
        rect,
        circle,
        ellipse,
        path,
        text {
          vector-effect: non-scaling-stroke;
        }

        @font-face {
            font-family: 'OpenGostTypeA-Regular';
            src: url('${asset.assetPath(src: 'r3/font/Eskd_normal.ttf')}') format('truetype');
            font-style: normal;
        }

        @font-face {
            font-family: 'OpenGostTypeA-Regular';
            src: url('${asset.assetPath(src: 'r3/font/Eskd_oblique.ttf')}') format('truetype');
            font-style: oblique;
        }

        text {fill: black; font-family: OpenGostTypeA-Regular ; font-weight: 400; text-decoration: none;}
</drw:renderFor>
<drw:renderFor renderer="${renderer}" forRenderer="cad">
    (COMMAND "_DEL" "temp.lin")
</drw:renderFor>
<g:each in="${styles.lineStyles}" var="lineStyle">
    <drw:lineStyle renderer="${renderer}" style="${lineStyle.value}"/>
</g:each>
<g:each in="${styles.textStyles}" var="textStyle">
    <drw:textStyle renderer="${renderer}" style="${textStyle.value}"/>
</g:each>