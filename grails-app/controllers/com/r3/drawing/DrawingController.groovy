package com.r3.drawing

import com.r3.drawing.layout.Drawing
import com.r3.drawing.layout.ProfileDrawing
import com.r3.drawing.layout.StampData
import com.r3.drawing.renderer.CadRenderer
import com.r3.drawing.renderer.Renderer
import com.r3.drawing.renderer.SvgRenderer
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.StylePallet
import com.r3.drawing.style.TextStyle

class DrawingController {

    private static final Random rnd = new Random()

    def show(Long id) {
        withDrawingInstance(id) { Drawing drawingInstance ->
            withFormat {
                embeddedSvg {
                    render(view: 'show.embedded', model: prepareModel(drawingInstance, new SvgRenderer()))
                }
                svg {
                    render(view: 'show.svg', model: prepareModel(drawingInstance, new SvgRenderer()))
                }
                pdf {
                    render(view: 'show.svg', model: prepareModel(drawingInstance, new SvgRenderer()))
                }
                lisp {
                    render(view: 'show.lisp', model: prepareModel(drawingInstance, new CadRenderer()))
                }
            }
        }
    }

    /*FOR LOAD TEST ONLY*/

    def randomDrawing() {
        def profileDrawings = ProfileDrawing.findAll()
        def drawingInstance = profileDrawings.get(rnd.nextInt(profileDrawings.size()))
        return prepareModel(drawingInstance, new SvgRenderer())
    }


    def edit(Long id) {
        withDrawingInstance(id) { Drawing drawingInstance ->
            return [drawingInstance: drawingInstance]
        }
    }

    def update(Long id, Long version) {
        withDrawingInstance(id) { Drawing drawingInstance ->
            if (version != null) {
                if (drawingInstance.version > version) {
                    drawingInstance.errors.rejectValue("version", "default.optimistic.locking.failure")
                    render(view: "edit", model: [drawingInstance: drawingInstance])
                    return
                }
            }

            bindData(drawingInstance, params, [exclude: ['project', 'rootTableRow',
                                                         'stampData.designerDate', 'stampData.supervisorDate', 'stampData.mainSpecialistDate',
                                                         'stampData.departmentHeadDate', 'stampData.gostInspectorDate', 'stampData.chiefDate']])
            bindDates(drawingInstance)

            if (!drawingInstance.save()) {
                render(view: "edit", model: [drawingInstance: drawingInstance])
                return
            }

            flash.message = message(code: 'app.drawing.edit.success')
            redirect(action: "edit", id: drawingInstance.id, params: [ok: true])
        }
    }

    private static Map prepareModel(Drawing drawing, Renderer renderer) {

        final List<LineStyle> allLineStyles = LineStyle.findAll()
        final HashMap<String, LineStyle> lineStyles = new HashMap<String, LineStyle>(allLineStyles.size())
        for (LineStyle lineStyle : allLineStyles) {
            lineStyles[lineStyle.name] = lineStyle
        }

        final List<TextStyle> allTextStyles = TextStyle.findAll()
        final HashMap<String, TextStyle> textStyles = new HashMap<String, TextStyle>(allTextStyles.size())
        for (TextStyle textStyle : allTextStyles) {
            textStyles[textStyle.name] = textStyle
        }

        final StylePallet styles = new StylePallet(lineStyles, textStyles)

        return [styles              : styles,
                drawingInstance     : drawing,
                projectInstance     : drawing.project,
                organizationInstance: drawing.project.organization,
                pageSize            : drawing.pageSize,
                drawingModel        : drawing.model,
                renderer            : renderer
        ]
    }

    private def withDrawingInstance(Long id, Closure c) {
        def drawingInstance = Drawing.get(id)
        if (drawingInstance) {
            c.call drawingInstance
        } else {
            response.sendError(404)
        }
    }

    private void bindDates(Drawing drawingInstance) {
        def dateFormat = message(code: 'drawing.stampData.dateFormat').toString()
        if (drawingInstance.stampData == null) {
            drawingInstance.stampData = new StampData()
        }
        drawingInstance.stampData.designerDate = params.date('stampData.designerDate', dateFormat)
        drawingInstance.stampData.supervisorDate = params.date('stampData.supervisorDate', dateFormat)
        drawingInstance.stampData.mainSpecialistDate = params.date('stampData.mainSpecialistDate', dateFormat)
        drawingInstance.stampData.departmentHeadDate = params.date('stampData.departmentHeadDate', dateFormat)
        drawingInstance.stampData.gostInspectorDate = params.date('stampData.gostInspectorDate', dateFormat)
        drawingInstance.stampData.chiefDate = params.date('stampData.chiefDate', dateFormat)
    }
}
