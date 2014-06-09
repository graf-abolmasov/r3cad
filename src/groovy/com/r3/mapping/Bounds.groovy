package com.r3.mapping

import com.r3.Project
import com.r3.dataset.DataEntry
import com.r3.dataset.DataSet
import org.apache.commons.logging.LogFactory

import javax.validation.constraints.NotNull

public class Bounds implements Serializable {

    private static final LOGGER = LogFactory.getLog(this)

    private Long leftBoundId
    private Long rightBoundId

    private Integer leftBound
    private Integer rightBound

    public Bounds(@NotNull final Project project) {
        def processingTime = -System.currentTimeMillis()

        try {
            def allDataSets = project.dataSets
            if (allDataSets == null || allDataSets.isEmpty()) {
                setBounds(0, null, 0, null)
                return
            }

            DataEntry firstMostPicket = null
            for (DataSet ds in allDataSets) {
                def pickets = ds.values
                if (!pickets) {
                    continue
                }
                firstMostPicket = pickets.first()
                break
            }

            if (firstMostPicket == null) {
                setBounds(0, null, 0, null)
                return
            }

            DataEntry lastPicket = firstMostPicket
            for (DataSet ds in allDataSets) {
                def pickets = ds.values
                if (!pickets) {
                    continue
                }
                firstMostPicket = firstMostPicket < pickets.first() ? firstMostPicket : pickets.first()
                lastPicket = lastPicket > pickets.last() ? lastPicket : pickets.last()
            }
            setBounds(firstMostPicket.toPk(), firstMostPicket.id, lastPicket.toPk() + (lastPicket.plus > 0 ? 1 : 0), lastPicket.id)
        } finally {
            processingTime += System.currentTimeMillis()
            LOGGER.debug("Initialized bounds in $processingTime msec [name='${project.name}'; id=${project.id}]")
        }
    }

    public Bounds(Bounds bounds, Integer rightBound, Long rightBoundId) {
        this(bounds.leftBound, bounds.leftBoundId, rightBound, rightBoundId)
    }

    public Bounds(Integer leftBound, Long leftBoundId, Bounds bounds) {
        this(leftBound, leftBoundId, bounds.rightBound, bounds.rightBoundId)
    }

    public Bounds(Integer leftBound, Long leftBoundId, Integer rightBound, Long rightBoundId) {
        if (leftBoundId == null && rightBoundId == null) {
            setBounds(0, null, 0, null)
        } else if (rightBoundId == null) {
            setBounds(leftBound, leftBoundId, leftBound, leftBoundId)
        } else if (leftBoundId == null) {
            setBounds(rightBound, rightBoundId, rightBound, rightBoundId)
        } else {
            setBounds(leftBound, leftBoundId, rightBound, rightBoundId)
        }
    }

    public Integer getLeftBound() {
        return leftBound
    }

    public Integer getRightBound() {
        return rightBound
    }

    public Long getLeftBoundId() {
        return leftBoundId
    }

    public Long getRightBoundId() {
        return rightBoundId
    }

    private setBounds(Integer leftBound, Long leftBoundId, Integer rightBound, Long rightBoundId) {
        this.rightBoundId = rightBoundId
        this.leftBoundId = leftBoundId
        this.leftBound = leftBound
        this.rightBound = rightBound
    }
}
