package com.r3.mapping;

import com.r3.Project;
import com.r3.RailWay;
import com.r3.dataset.IrregularPicketDataEntry;
import com.r3.dataset.IrregularPicketDataSet;
import com.r3.dataset.Location
import org.apache.commons.logging.LogFactory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class Pk2MmMap {

    private static final LOGGER = LogFactory.getLog(this)

    private int zeroPk;

    private Integer[] mmsArray;

    public Pk2MmMap(@NotNull final Project project, @NotNull final Bounds bounds) {
        final RailWay mappingWay = project.mappingWay()
        if (mappingWay == null) {
            this.zeroPk = 0;
            this.mmsArray = new Integer[0];
            return
        }

        if (bounds.leftBound == bounds.rightBound) {
            this.zeroPk = 0;
            this.mmsArray = new Integer[0];
            return
        }

        def processingTime = -System.currentTimeMillis()
        def irregularPicketDataSet = IrregularPicketDataSet.findByRailWay(mappingWay)
        final Set<IrregularPicketDataEntry> irregularPickets = irregularPicketDataSet.values
        if (!irregularPicketDataSet || !irregularPickets) {
            this.zeroPk = 0;
            this.mmsArray = new Integer[0];
            return
        }

        this.zeroPk = irregularPickets.first().toPk() - 1

        def startPk = this.zeroPk
        double offset = 0.0
        def list = new ArrayList<Integer>()
        for (IrregularPicketDataEntry irrPk in irregularPickets) {
            final int ipk = irrPk.toPk()

            for (int pk = startPk; pk <= ipk; pk++) {
                list.add(pk * Location.MM_IN_PK + (int) (offset * Location.MM_IN_METER))
            }

            startPk = ipk + 1
            offset += (irrPk.doubleValue - Location.METER_IN_PK)
        }
        list.add(startPk * Location.MM_IN_PK + (int) (offset * Location.MM_IN_METER))

        this.mmsArray = list.toArray(new Integer[list.size()]);

        processingTime += System.currentTimeMillis()
        LOGGER.debug("Initialized pk2mm in $processingTime msec [name='${mappingWay.label}'; id=${mappingWay.id}]")
    }

    public Pk2MmMap(int zeroPicket, @NotNull List<Integer> mms) {
        this.zeroPk = zeroPicket;
        this.mmsArray = new Integer[mms.size()];
        this.mmsArray = mms.toArray(this.mmsArray);
    }

    public long getAt(int pk) {
        if (pk < zeroPk || mmsArray.length == 0) {
            return pk * Location.MM_IN_PK;
        }

        Integer idx = pk - zeroPk;
        if (idx >= mmsArray.length) {
            return mmsArray[mmsArray.length - 1] + (idx + 1 - mmsArray.length) * Location.MM_IN_PK;
        }

        return mmsArray[idx];
    }

    public int size() {
        return mmsArray.length;
    }

    public long getAt(@NotNull final Location location) {
        return getAt(location.toPk()) + location.getPlus();
    }
}
