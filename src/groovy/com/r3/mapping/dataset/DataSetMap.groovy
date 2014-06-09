package com.r3.mapping.dataset

import com.r3.dataset.DataEntry
import com.r3.dataset.DataSet
import com.r3.mapping.DataItem
import com.r3.mapping.DataMappingService
import org.apache.commons.logging.LogFactory

import javax.validation.constraints.NotNull
import java.util.concurrent.ConcurrentHashMap

public class DataSetMap implements Iterable<DataItem>, Serializable {

    private static final LOGGER = LogFactory.getLog(this)

    private Long id

    private List<DataItem> values

    private Map<Long, String> observers = new ConcurrentHashMap<Long, String>()

    protected transient DataMappingService mappingService

    DataSetMap(@NotNull final DataSet dataSet,
               @NotNull final DataMappingService mappingService) {
        def processingTime = -System.currentTimeMillis()
        this.mappingService = mappingService
        this.id = dataSet.id
        this.values = map(dataSet)
        processingTime += System.currentTimeMillis()
        LOGGER.debug("Mapped data set in $processingTime msec: \t[${dataSet.title}; id=${dataSet.id}]")
    }

    protected List<DataItem> map(DataSet dataSet) {
        final Set<DataEntry> dataEntries = dataSet.values

        if (!dataEntries) {
            return Collections.emptyList()
        }

        def pk2mm = mappingService.mapPk2mm(dataSet.project)
        def result = new ArrayList<DataItem>(dataEntries.size())
        for (DataEntry pk in dataEntries) {
            result.add(new DataItem(pk2mm[pk], pk.getValues()))
        }

        return Collections.unmodifiableList(result)
    }

    void addObserver(final Long id, final String title) {
        if (this.id != id) {
            observers.put(id, title)
        }
    }

    Map<Long, String> getObservers() {
        return Collections.unmodifiableMap(observers);
    }

    Iterator<DataItem> iterator() {
        return values.iterator();
    }

    List<DataItem> getValues() {
        return values;
    }

    boolean isEmpty() {
        return values.isEmpty();
    }

    protected subscribeTo(DataSet dataSet) {
        mappingService.map(dataSet).addObserver(id, dataSet.title)
    }
}
