package com.r3.dataset;

import com.r3.Project;
import com.r3.RailWay;
import com.r3.drawing.area.RepresentationRole;
import com.r3.drawing.area.StackedArea2DataSet;
import com.r3.mapping.dataset.DataSetMap;
import com.r3.tree.TreeNodeAware;

import java.util.Collection;
import java.util.SortedSet;

public interface DataSet extends TreeNodeAware {

    Project getProject();

    RailWay getRailWay();

    String getTitle();

    DataSetMetaInfo getMetaInfo();

    String getTemplate(RepresentationRole representationRole);

    SortedSet<? extends DataEntry> getValues();

    Collection<StackedArea2DataSet> getTableRows();

    DataSetMap map();
}