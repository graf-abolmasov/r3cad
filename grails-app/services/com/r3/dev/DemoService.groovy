package com.r3.dev

import com.r3.Organization
import com.r3.Project
import com.r3.RailWay
import com.r3.User
import com.r3.dataset.*
import com.r3.dataset.bridge.*
import com.r3.dataset.pillar.PillarDataEntry
import com.r3.dataset.pillar.PillarDataSet
import com.r3.dataset.pillar.PillarDistanceToWay
import com.r3.dataset.pillar.WireHeight
import com.r3.dataset.symbol.SymbolDataEntry
import com.r3.dataset.symbol.SymbolDataSet
import com.r3.dataset.symbol.SymbolDistanceToWay
import com.r3.dataset.turnout.*
import com.r3.drawing.PageSizeFormat
import com.r3.drawing.layout.*
import com.r3.drawing.style.LineStyle
import com.r3.drawing.style.TextStyle
import com.r3.drawing.symbol.ConventionalSymbol
import com.r3.utils.Utils
import grails.transaction.Transactional

@Transactional
class DemoService {

    def grailsApplication

    private static final Random rnd = new Random()

    private static synchronized double nextDouble(double lowBound = 0, double highBound = 100) {
        return lowBound + rnd.nextDouble() * (highBound - lowBound)
    }

    private static synchronized double nextInteger(int lowBound = 0, int highBound = 100) {
        return lowBound + rnd.nextInt(highBound - lowBound)
    }

    private static RailWay createRailWay(Project project, int number, boolean underRepair) {
        def railWay = saveInstance(new RailWay(project, "${Utils.toRomanNumber(number)}-й путь", number, underRepair))

        saveInstance(new BridgeDataSet(project, railWay, "Мосты"))
        saveInstance(new BallastDataSet(project, railWay, "Балласт"))
        saveInstance(new TurnoutDataSet(project, railWay, "Стрелочные переводы"))
        saveInstance(new SleepersDataSet(project, railWay, "Шпалы"))
        saveInstance(new GradientDataSet(project, railWay, 'Уклон'))
        saveInstance(new RailHeadDataSet(project, railWay, "Высота головки рельса"))
        saveInstance(new WayProfileDataSet(project, railWay, 'Профиль пути', true, true, true))
        saveInstance(new TopViewPlanDataSet(project, railWay, 'Развернутый план пути'))
        saveInstance(new SubgradeEdgeDataSet(project, railWay, "Бровки земляного полотна"))
        saveInstance(new PlanElementsDataSet(project, railWay, "Прямые и кривые в плане пути"))
        saveInstance(new InsulationJointDataSet(project, railWay, "Изостыки"))
        saveInstance(new IrregularPicketDataSet(project, railWay, "Неправильные пикеты"))

        return railWay
    }

    private Project createDemoProject(String name, Organization demoOrganization) {
        final Project project = saveInstance(new Project(name: name, organization: demoOrganization))

        final RailWay railWay1 = createRailWay(project, 1, false)
        final RailWay railWay2 = createRailWay(project, 2, true)
        final RailWay railWay3 = createRailWay(project, 3, false)

        saveInstance(new PicketsDataSet(project, 'Пикет'))
        saveInstance(new PillarDataSet(project, 'Опоры контактной сети'))
        saveInstance(new GroundLevelDataSet(project, 'Отметка земли'))
        saveInstance(new InterWaySpaceDataSet(project, "Междопутье существующее ${Utils.toRomanNumber(railWay1.number)} - " +
                "${Utils.toRomanNumber(railWay2.number)} путей", railWay1, railWay2)
        )
        saveInstance(new InterWaySpaceDataSet(project, "Междопутье существующее ${Utils.toRomanNumber(railWay2.number)} - " +
                "${Utils.toRomanNumber(railWay3.number)} путей", railWay2, railWay3)
        )

        saveInstance(new DoubleDataSet(project, railWay2, 'Очень необходимые данные пути'))
        saveInstance(new DoubleDataSet(project, railWay2, 'Не самые важные, но тоже нужные данные пути'))
        saveInstance(new DoubleDataSet(project, 'Какие-то данные проекта'))
        saveInstance(new DoubleDataSet(project, 'Еще какие-то данные проекта'))
        saveInstance(new SymbolDataSet(project, 'Знаки в проекте'))

        project.addToDrawings(createDrawing(new TrackLiningDiagramDrawing(name: 'Эпюра рихтовок', pageNumber: 2,
                project: project), PageSizeFormat.A4, 1))

        project.addToDrawings(createDrawing(new RailsLayoutDrawing(name: 'Раскладка плетей', pageNumber: 3,
                project: project), PageSizeFormat.A4, 1))

        project.addToDrawings(createDrawing(DefaultProfileDrawing.createDefault(project), PageSizeFormat.A2, 5))

        log.debug("Created demo project [Title='${project.name}', id=${project.id}]")
        return project
    }

    private static Organization findOrCreateDemoOrganization() {
        Organization demoOrganization = Organization.findByName('ООО "Проектировщики от Бога"')
        if (!demoOrganization) {
            demoOrganization = saveInstance(new Organization(name: 'ООО "Проектировщики от Бога"',
                    address: "7 небо", phone: "+7-777-7-777-777", licenceAvailable: 10))

            createUser(demoOrganization, 'Иван', 'Иванович', 'Иванов', 'manager')

            createUser(demoOrganization, 'Леон', 'Алимухамбетов', 'Владимир', 'chief')
            createUser(demoOrganization, 'Павел', 'Викторович', 'Аболмасов', 'designer')
            createUser(demoOrganization, 'Евгений', 'Александрович', 'Костин', 'prospector')

            createUser(demoOrganization, 'Иван', 'Иванович', 'Иванов', 'manager_another')
            createUser(demoOrganization, 'Иван', 'Иванович', 'Иванов', 'chief_another')
            createUser(demoOrganization, 'Иван', 'Иванович', 'Иванов', 'designer_another')
            createUser(demoOrganization, 'Иван', 'Иванович', 'Иванов', 'prospector_another')

            createDefaultStyles()
        }

        return demoOrganization
    }

    void createDemoData(int count = 1) {
        def demoOrganization = findOrCreateDemoOrganization()
        int offset = Project.countByOrganization(demoOrganization)
        for (int i = offset; i < offset + count; i++) {
            def project = createDemoProject("Демонстрационный проект $i", demoOrganization)
            fillDemoData(project)
        }
    }

    private static void createDefaultStyles() {
        def black = Utils.rgb2Color(0, 0, 0)
        saveInstance(new LineStyle(name: 'df_black', strokeColor: black))
        saveInstance(new LineStyle(name: 'df_blue', strokeColor: Utils.rgb2Color(0, 0, 255)))
        saveInstance(new LineStyle(name: 'ground', strokeColor: Utils.rgb2Color(80, 30, 0), strokeDashArray: '3, 1, 1, 1'))
        saveInstance(new LineStyle(name: 'frame', strokeWidth: 0.60, strokeColor: black))
        saveInstance(new LineStyle(name: 'way_profile', strokeColor: black))

        saveInstance(new LineStyle(name: 'df_red', strokeColor: Utils.rgb2Color(255, 0, 0)))
        saveInstance(new LineStyle(name: 'curve_in_plan_proj', strokeWidth: 0.50, strokeColor: black))
        saveInstance(new LineStyle(name: 'line_in_plan1', strokeWidth: 0.60, strokeColor: black))
        saveInstance(new LineStyle(name: 'line_in_plan2', strokeWidth: 0.35, strokeColor: black))
        saveInstance(new LineStyle(name: 'teckstyle', strokeWidth: 0.60, strokeColor: black))
        saveInstance(new LineStyle(name: 'graph_GR_proj', strokeWidth: 0.80, strokeColor: black))
        saveInstance(new LineStyle(name: 'soil_change', strokeWidth: 0.60, strokeColor: black))

        saveInstance(new TextStyle(name: 'text_normal', font: 'eskd_kr.shx', size: 2.5, sparsity: 1.3, oblique: true))
        saveInstance(new TextStyle(name: 'text_leader', font: 'eskd_kr.shx', size: 1.8))
        saveInstance(new TextStyle(name: 'text_grad', font: 'eskd_kr.shx', size: 2.0))
        saveInstance(new TextStyle(name: 'text_curve', font: 'eskd_kr.shx', size: 2.0))
        saveInstance(new TextStyle(name: 'text_grad1', font: 'eskd_kr.shx', color: Utils.rgb2Color(255, 0, 0), size: 2.0))
        saveInstance(new TextStyle(name: 'text_narrow', font: 'eskd_kr.shx', size: 2.5))
        saveInstance(new TextStyle(name: 'style1', font: 'Arial', size: 6, sparsity: 0.7))
    }

    private void fillDemoData(Project project) {
        def processingTime = -System.currentTimeMillis()

        def railWay1 = RailWay.findByNumberAndProject(1, project)
        def railWay2 = RailWay.findByNumberAndProject(2, project)
        def railWay3 = RailWay.findByNumberAndProject(3, project)

        def line2PicketDoubleDataSet1 = DoubleDataSet.findByTitleAndProject("Очень необходимые данные пути", project)
        def line2PicketDoubleDataSet2 = DoubleDataSet.findByTitleAndProject("Не самые важные, но тоже нужные данные пути", project)

        def projectCustomDataSet1 = DoubleDataSet.findByTitleAndProject("Какие-то данные проекта", project)
        def projectCustomDataSet2 = DoubleDataSet.findByTitleAndProject("Еще какие-то данные проекта", project)

        def groundLevelDataSet = GroundLevelDataSet.findByProject(project)

        def interWay12DataSet = InterWaySpaceDataSet.findByProjectAndFirstRailWay(project, railWay1)
        def interWay23DataSet = InterWaySpaceDataSet.findByProjectAndFirstRailWay(project, railWay2)

        def pillarDataSet = PillarDataSet.findByProject(project)

        def projectSymbolDataSet = SymbolDataSet.findByProject(project)

        def railHeadAltsDataSet1 = RailHeadDataSet.findByRailWay(railWay1)
        def railHeadAltsDataSet2 = RailHeadDataSet.findByRailWay(railWay2)
        def railHeadAltsDataSet3 = RailHeadDataSet.findByRailWay(railWay3)

        def subgradeEdgeDataSet1 = SubgradeEdgeDataSet.findByRailWay(railWay1)
        def subgradeEdgeDataSet3 = SubgradeEdgeDataSet.findByRailWay(railWay3)

        def insulationJointDataSet2 = InsulationJointDataSet.findByRailWay(railWay2)

        def plansElementsDataSet2 = PlanElementsDataSet.findByRailWay(railWay2)

        def ballastDataSet2 = BallastDataSet.findByRailWay(railWay2)

        def turnoutDataSet1 = TurnoutDataSet.findByRailWay(railWay1)
        def turnoutDataSet2 = TurnoutDataSet.findByRailWay(railWay2)

        def sleepersDataSet1 = SleepersDataSet.findByRailWay(railWay1)
        def sleepersDataSet2 = SleepersDataSet.findByRailWay(railWay2)
        def sleepersDataSet3 = SleepersDataSet.findByRailWay(railWay3)

        def irregularPicketsDataSet2 = IrregularPicketDataSet.findByRailWay(railWay2)

        def bridgeDataSet = BridgeDataSet.findByRailWay(railWay2)

        def mainContext = grailsApplication.mainContext
        def inputStream = mainContext.getResource('/data/rha_line_1.csv').inputStream
        inputStream.toCsvReader('charset': 'UTF-8', 'skipLines': 1).eachLine { tokens ->
            final Object token_0 = tokens[0]
            final Object token_1 = tokens[1]
            final Integer token_2 = Math.round(Double.valueOf(tokens[2]) * Location.MM_IN_METER)
            final Double token_3 = Double.valueOf(tokens[3])
            railHeadAltsDataSet1.addToValues(location: [km: token_0, pk: token_1, plus: token_2], doubleValue: token_3)
            subgradeEdgeDataSet1.addToValues(location: [km: token_0, pk: token_1, plus: token_2],
                    leftValue: (token_3 - nextDouble(0, 1)) * Location.MM_IN_METER,
                    leftLeft: nextDouble(3, 4) * Location.MM_IN_METER, rightRight: 0, rightValue: 0)
            subgradeEdgeDataSet3.addToValues(location: [km: token_0, pk: token_1, plus: token_2],
                    rightValue: (token_3 - nextDouble(0, 1)) * Location.MM_IN_METER,
                    rightRight: nextDouble(3, 4) * Location.MM_IN_METER, leftValue: 0, leftLeft: 0)
        }

        saveInstance(railHeadAltsDataSet1)
        saveInstance(subgradeEdgeDataSet1)
        saveInstance(subgradeEdgeDataSet3)

        inputStream = mainContext.getResource('/data/rha_line_2.csv').inputStream
        inputStream.toCsvReader('charset': 'UTF-8', 'skipLines': 1).eachLine { tokens ->
            final Object token_0 = tokens[0]
            final Object token_1 = tokens[1]
            final Integer token_2 = Math.round(Double.valueOf(tokens[2]) * Location.MM_IN_METER)
            final Double token_3 = Double.valueOf(tokens[3])
            line2PicketDoubleDataSet1.addToValues(location: [km: token_0, pk: token_1, plus: token_2], doubleValue: nextDouble())
            line2PicketDoubleDataSet2.addToValues(location: [km: token_0, pk: token_1, plus: token_2], doubleValue: nextDouble())
            railHeadAltsDataSet2.addToValues(location: [km: token_0, pk: token_1, plus: token_2], doubleValue: token_3)
            groundLevelDataSet.addToValues(location: [km: token_0, pk: token_1, plus: 0], doubleValue: token_3 - nextDouble(1, 3))
        }
        saveInstance(line2PicketDoubleDataSet1)
        saveInstance(line2PicketDoubleDataSet2)
        saveInstance(railHeadAltsDataSet2)
        saveInstance(groundLevelDataSet)

        inputStream = mainContext.getResource('/data/insulation_joint_2.csv').inputStream
        inputStream.toCsvReader('charset': 'UTF-8', 'skipLines': 1).eachLine { tokens ->
            final Object token_0 = tokens[0]
            final Object token_1 = tokens[1]
            final Integer token_2 = Math.round(Double.valueOf(tokens[2]) * Location.MM_IN_METER)
            final Object token_3 = tokens[3]
            final InsulationJointUsingApATek token_4 = tokens[4] == 'USE' ?
                    InsulationJointUsingApATek.USE : InsulationJointUsingApATek.DO_NOT_USE
            insulationJointDataSet2.addToValues(location: [km: token_0, pk: token_1, plus: token_2], name: token_3, useApATek: token_4)
        }
        saveInstance(insulationJointDataSet2)

        inputStream = mainContext.getResource('/data/curve_line_2.csv').inputStream
        inputStream.toCsvReader('charset': 'UTF-8', 'skipLines': 1).eachLine { tokens ->
            final Object token_0 = tokens[0]
            final Object token_1 = tokens[1]
            final Integer token_2 = Math.round(Double.valueOf(tokens[2]) * Location.MM_IN_METER)
            final CurveType token_3 = tokens[3] == 'STRAIGHT' ? CurveType.STRAIGHT : tokens[3] == 'RIGHT_FULL_CURVE' ? CurveType.RIGHT_FULL_CURVE : CurveType.LEFT_FULL_CURVE
            final Integer token_4 = Integer.valueOf(tokens[4])
            final Integer token_5 = Integer.valueOf(tokens[5])
            final Double token_6 = Double.valueOf(tokens[6])

            final Integer token_7 = Math.round(Double.valueOf(tokens[7]))
            final Object token_8 = tokens[8]

            final Integer token_9 = Math.round(Double.valueOf(tokens[9]) * Location.MM_IN_METER)
            final Integer token_10 = Math.round(Double.valueOf(tokens[10]) * Location.MM_IN_METER)
            final Integer token_11 = Math.round(Double.valueOf(tokens[11]) * Location.MM_IN_METER)

            final Object token_12 = tokens[12]
            final Object token_13 = tokens[13]
            final Integer token_14 = Math.round(Double.valueOf(tokens[14]) * Location.MM_IN_METER)

            final Integer token_15 = Math.round(Double.valueOf(tokens[15]))
            final Integer token_16 = Math.round(Double.valueOf(tokens[16]))

            plansElementsDataSet2.addToValues(new PlanElementsDataEntry(location: [km: token_0, pk: token_1, plus: token_2],
                    elementType: token_3, angle: PlanElementsDataEntry.toDegrees(token_4, token_5, token_6),
                    radius: token_7, h: token_8, t1: token_9, t2: token_10, length: token_11, endKm: token_12, endPk: token_13,
                    endPlus: token_14, l1: token_15, l2: token_16))
        }
        saveInstance(plansElementsDataSet2)

        ballastDataSet2.countLayers = 3
        ballastDataSet2.name0 = 'Щебень'
        ballastDataSet2.name1 = 'Щебень крупный'
        ballastDataSet2.name2 = 'Щебень мелкий'
        inputStream = mainContext.getResource('/data/ballast_2.csv').inputStream
        inputStream.toCsvReader('charset': 'UTF-8', 'skipLines': 1).eachLine { tokens ->
            final Object token_0 = tokens[0]
            final Object token_1 = tokens[1]
            final Integer token_2 = Math.round(Double.valueOf(tokens[2]) * Location.MM_IN_METER)
            final Integer token_3 = Math.round(Double.valueOf(tokens[3] ?: 0) * Location.MM_IN_CM)
            final Integer token_4 = Math.round(Double.valueOf(tokens[4] ?: 0) * Location.MM_IN_CM)
            final Integer token_5 = Math.round(Double.valueOf(tokens[5] ?: 0) * Location.MM_IN_CM)
            ballastDataSet2.addToValues(location: [km: token_0, pk: token_1, plus: token_2], layer0: token_3, layer1: token_4, layer2: token_5)
        }
        saveInstance(ballastDataSet2)

        turnoutDataSet1.addToValues(new TurnoutDataEntry(location: [km: 1297, pk: 8, plus: 56.50 * Location.MM_IN_METER], number: 2, numberSecond: 4,
                turnoutType: TurnoutType.DESCENT, direct: Direct.DIRECT, orientation: Orientation.RIGHT,
                model: Model.ONE_ELEVENTH, sleepersType: SleepersType.CONCRETE, railsType: RailsType.NONE,
                controlType: ControlType.ELECTRICAL,
                stockRailJointLocation: [km: 1297, pk: 8, plus: 42.35 * Location.MM_IN_METER],
                tongueLocation: [km: 1297, pk: 8, plus: 42.23 * Location.MM_IN_METER], tongueRailHeadValue: 88.24,
                tailLocation: [km: 1297, pk: 8, plus: 77.3 * Location.MM_IN_METER], tailRailHeadValue: 88.28,
                afterCrossLinkLocation: [km: 1297, pk: 8, plus: 83.75 * Location.MM_IN_METER]))
        turnoutDataSet1.addToValues(new TurnoutDataEntry(location: [km: 1298, pk: 0, plus: 52.6 * Location.MM_IN_METER], number: 12, numberSecond: 10,
                turnoutType: TurnoutType.DESCENT, direct: Direct.BACK, orientation: Orientation.LEFT,
                model: Model.ONE_ELEVENTH, sleepersType: SleepersType.WOODEN, railsType: RailsType.NONE,
                controlType: ControlType.ELECTRICAL, stockRailJointLocation: [km: 1298, pk: 0, plus: 66.6 * Location.MM_IN_METER],
                tongueLocation: [km: 1298, pk: 0, plus: 63.85 * Location.MM_IN_METER], tongueRailHeadValue: 88.3,
                tailLocation: [km: 1298, pk: 0, plus: 33.3 * Location.MM_IN_METER], tailRailHeadValue: 88.25,
                afterCrossLinkLocation: [km: 1298, pk: 0, plus: 88.25 * Location.MM_IN_METER]))
        turnoutDataSet1.addToValues(new TurnoutDataEntry(location: [km: 1298, pk: 0, plus: 80.67 * Location.MM_IN_METER], number: 16,
                turnoutType: TurnoutType.NORMAL, direct: Direct.DIRECT, orientation: Orientation.LEFT,
                model: Model.ONE_ELEVENTH, sleepersType: SleepersType.WOODEN, railsType: RailsType.NONE, controlType: ControlType.ELECTRICAL,
                stockRailJointLocation: [km: 1298, pk: 0, plus: 66.6 * Location.MM_IN_METER],
                tongueLocation: [km: 1298, pk: 0, plus: 69.4 * Location.MM_IN_METER], tongueRailHeadValue: 88.28,
                tailLocation: [km: 1298, pk: 1, plus: 0 * Location.MM_IN_METER], tailRailHeadValue: 88.26))
        turnoutDataSet2.addToValues(new TurnoutDataEntry(location: [km: 1297, pk: 9, plus: 9 * Location.MM_IN_METER], number: 4, numberSecond: 2,
                turnoutType: TurnoutType.DESCENT, direct: Direct.BACK, orientation: Orientation.RIGHT,
                model: Model.ONE_ELEVENTH, sleepersType: SleepersType.CONCRETE, railsType: RailsType.P65,
                controlType: ControlType.ELECTRICAL,
                stockRailJointLocation: [km: 1297, pk: 9, plus: 23.08 * Location.MM_IN_METER],
                tongueLocation: [km: 1297, pk: 9, plus: 20.28 * Location.MM_IN_METER], tongueRailHeadValue: 88.26,
                tailLocation: [km: 1297, pk: 8, plus: 88.2 * Location.MM_IN_METER], tailRailHeadValue: 88.32))
        turnoutDataSet2.addToValues(new TurnoutDataEntry(location: [km: 1297, pk: 9, plus: 98.3 * Location.MM_IN_METER], number: 10, numberSecond: 12,
                turnoutType: TurnoutType.DESCENT, direct: Direct.DIRECT, orientation: Orientation.LEFT,
                model: Model.ONE_ELEVENTH, sleepersType: SleepersType.CONCRETE, railsType: RailsType.P65,
                controlType: ControlType.ELECTRICAL,
                stockRailJointLocation: [km: 1297, pk: 9, plus: 84.2 * Location.MM_IN_METER],
                tongueLocation: [km: 1297, pk: 9, plus: 87 * Location.MM_IN_METER], tongueRailHeadValue: 88.2,
                tailLocation: [km: 1298, pk: 0, plus: 17.6 * Location.MM_IN_METER], tailRailHeadValue: 88.21))
        saveInstance(turnoutDataSet2)

        sleepersDataSet1.addToValues(location: [km: 1283, pk: 0, plus: 0], endLocation: [km: 1298, pk: 3, plus: 0], sleepersType: SleepersType.COMBO)
        sleepersDataSet2.addToValues(location: [km: 1283, pk: 0, plus: 0], endLocation: [km: 1290, pk: 0, plus: 20 * Location.MM_IN_METER], sleepersType: SleepersType.CONCRETE)
        sleepersDataSet3.addToValues(location: [km: 1290, pk: 0, plus: 20 * Location.MM_IN_METER], endLocation: [km: 1298, pk: 3, plus: 0], sleepersType: SleepersType.WOODEN)
        saveInstance(sleepersDataSet1)

        def firstPicket = railHeadAltsDataSet1.values.first()
        def lastPicket = railHeadAltsDataSet1.values.last()

        def firstMm = firstPicket.toPk() * Location.MM_IN_PK + firstPicket.plus
        def lastMm = lastPicket.toPk() * Location.MM_IN_PK + lastPicket.plus
        def length = lastMm - firstMm

        railHeadAltsDataSet1.values.eachWithIndex { it, idx ->
            def value = Math.sin((it.toPk() * Location.MM_IN_PK) / length * 10 * Math.PI) * 6 + 75
            railHeadAltsDataSet3.addToValues(location: [km: it.km, pk: it.pk, plus: 0], doubleValue: value)
            interWay12DataSet.addToValues(location: [km: it.km, pk: it.pk, plus: 0], doubleValue: nextDouble(5, 10))
            interWay23DataSet.addToValues(location: [km: it.km, pk: it.pk, plus: 0], doubleValue: nextDouble(10, 20))
            projectCustomDataSet1.addToValues(location: [km: it.km, pk: it.pk, plus: it.plus], doubleValue: nextDouble())
            projectCustomDataSet2.addToValues(location: [km: it.km, pk: it.pk, plus: it.plus], doubleValue: nextDouble())

            def pillar = new PillarDataEntry(location: [km: it.km, pk: it.pk, plus: nextDouble() * Location.MM_IN_METER], number: idx)
            pillar.addToWireHeights(new WireHeight(railWay: railWay2, height: nextInteger(6000, 6100)))
            pillar.addToDistancesToWays(new PillarDistanceToWay(railWay: railWay2, distance: nextDouble(3.5, 4.5) * Location.MM_IN_METER))
            pillarDataSet.addToValues(pillar)
        }
        saveInstance(pillarDataSet)

        irregularPicketsDataSet2.addToValues(location: [km: 1283, pk: 2, doubleValue: 35.8])
        irregularPicketsDataSet2.addToValues(location: [km: 1285, pk: 4, doubleValue: 95.4])
        irregularPicketsDataSet2.addToValues(location: [km: 1288, pk: 8, doubleValue: 108.7])
        irregularPicketsDataSet2.addToValues(location: [km: 1289, pk: 0, doubleValue: 101.6])
        irregularPicketsDataSet2.addToValues(location: [km: 1295, pk: 3, doubleValue: 87.9])
        saveInstance(irregularPicketsDataSet2)

        def star_symbol = ConventionalSymbol.findByKey('star')
        def super_star_symbol = ConventionalSymbol.findByKey('super_star')
        def java_forward_symbol = ConventionalSymbol.findByKey('java_forward')
        def java_reward_symbol = ConventionalSymbol.findByKey('java_reward')
        def dojo_symbol = ConventionalSymbol.findByKey('dojo')
        def tomcat_symbol = ConventionalSymbol.findByKey('tomcat')

        def symbolDataEntry = new SymbolDataEntry(location: [km: 1283, pk: 2, plus: 21000], symbolValue: star_symbol, label: 'Star')
        symbolDataEntry.addToDistancesToWays(new SymbolDistanceToWay(railWay: railWay1, distance: 23894))
        projectSymbolDataSet.addToValues(symbolDataEntry)

        symbolDataEntry = new SymbolDataEntry(location: [km: 1285, pk: 4, plus: 90014], symbolValue: super_star_symbol, label: 'Superstar')
        symbolDataEntry.addToDistancesToWays(railWay: railWay1, distance: 568)
        projectSymbolDataSet.addToValues(symbolDataEntry)

        symbolDataEntry = new SymbolDataEntry(location: [km: 1288, pk: 8, plus: 65761], symbolValue: dojo_symbol, label: 'Доджо 1.9')
        projectSymbolDataSet.addToValues(symbolDataEntry)

        symbolDataEntry = new SymbolDataEntry(location: [km: 1289, pk: 0, plus: 48631], symbolValue: java_forward_symbol, label: 'Джава 1.7 (По ходу)')
        symbolDataEntry.addToDistancesToWays(railWay: railWay1, distance: -39841)
        projectSymbolDataSet.addToValues(symbolDataEntry)

        symbolDataEntry = new SymbolDataEntry(location: [km: 1287, pk: 0, plus: 3894], symbolValue: java_reward_symbol, label: 'Джава 1.7 (Против хода)')
        symbolDataEntry.addToDistancesToWays(new SymbolDistanceToWay(railWay: railWay2, distance: 28973))
        projectSymbolDataSet.addToValues(symbolDataEntry)

        symbolDataEntry = new SymbolDataEntry(location: [km: 1295, pk: 3, plus: 18459], symbolValue: tomcat_symbol, label: 'Томкат 7')
        symbolDataEntry.addToDistancesToWays(railWay: railWay3, distance: 765)
        symbolDataEntry.addToDistancesToWays(railWay: railWay1, distance: 54678)
        projectSymbolDataSet.addToValues(symbolDataEntry)
        saveInstance(projectSymbolDataSet)

        inputStream = grailsApplication.mainContext.getResource('/data/bridges_2.csv').inputStream
        inputStream.toCsvReader('charset': 'UTF-8', 'skipLines': 1).eachLine { tokens ->
            final Object token_1 = tokens[1]
            final Object token_2 = tokens[2]
            final Integer token_3 = Math.round(Double.valueOf(tokens[3]) * Location.MM_IN_METER)
            final Object token_4 = tokens[4]
            final Object token_5 = tokens[5]
            final Integer token_6 = Math.round(Double.valueOf(tokens[6]) * Location.MM_IN_METER)
            final Double token_7 = Double.valueOf(tokens[7])
            final Integer token_8 = Math.round(Double.valueOf(tokens[8]) * Location.MM_IN_METER)
            final FlooringType token_9 = tokens[9] == 'BALLAST_DECK' ? FlooringType.ON_BALLAST : FlooringType.ON_CONCRETE_SLABS

            final SpansType token_11 = tokens[11] == 'FERROCONCRETE' ? SpansType.REINFORCED_CONCRETE : SpansType.METALLIC
            final Integer token_12 = Math.round(Double.valueOf(tokens[12]) * Location.MM_IN_METER)
            final MovementType token_13 = tokens[13] == 'UP' ? MovementType.UP : MovementType.DOWN
            final Integer token_15 = Math.round(Double.valueOf(tokens[15]) * Location.MM_IN_METER)
            final Integer token_16 = Math.round(Double.valueOf(tokens[16]) * Location.MM_IN_CM)

            def bridge = new BridgeDataEntry(location: [km: token_1, pk: token_2, plus: token_3],
                    axisLocation: [km: token_1, pk: token_2, plus: Math.round((token_6 - token_3) / 2)],
                    endLocation: [km: token_4, pk: token_5, plus: token_6], axisRailHeadValue: token_7,
                    length: token_8, flooringType: token_9)
            bridge.addToSpansValues(spansType: token_11, length: token_12, movementType: token_13, lumenLength: token_15, ballast: token_16)
            bridgeDataSet.addToValues(bridge)
        }
        saveInstance(bridgeDataSet)

        project.mapByWay = railWay2
        saveInstance(project)

        processingTime += System.currentTimeMillis()
        log.debug("Filled demo project with data in $processingTime msec. [Title='${project.name}', id=${project.id}]")
    }

    private static Drawing createDrawing(Drawing drawing, PageSizeFormat pageFormat, int pageFormatCoefficient) {
        def calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        def deadLine = calendar.getTime()
        drawing.pageFormat = pageFormat
        drawing.pageFormatCoefficient = pageFormatCoefficient
        drawing.stampData = new StampData(designer: 'Алимухамбетов', designerDate: deadLine,
                supervisor: 'Костин', supervisorDate: deadLine,
                mainSpecialist: 'Аболмасов', mainSpecialistDate: deadLine,
                departmentHead: '', departmentHeadDate: deadLine,
                gostInspector: '', gostInspectorDate: deadLine,
                chief: '', chiefDate: deadLine,
        )
        return saveInstance(drawing)
    }

    private static <T> T saveInstance(T instance) {
        instance.save(failOnError: true, flush: true)
    }

    private static User createUser(Organization organization,
                                   String firstName, String middleName, String lastName, String login) {
        final User user = new User(firstName: firstName, middleName: middleName,
                lastName: lastName, login: login, organization: organization)
        organization.addToUsers(user)
        return saveInstance(user)
    }
}
