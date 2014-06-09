package com.r3.dataset;

import com.r3.dataset.symbol.SymbolDataEntry;
import com.r3.dataset.symbol.SymbolDistanceToWay;
import com.r3.drawing.area.RepresentationRole;
import com.r3.drawing.symbol.ConventionalSymbol;
import com.r3.drawing.symbol.SymbolIcon;
import com.r3.drawing.symbol.SymbolIconReadOnly;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: graf
 * Date: 10/15/13
 * Time: 1:36 PM
 */
public class SymbolDataEntryReadOnly extends Location implements Serializable {

    public SymbolDataEntryReadOnly(SymbolDataEntry sde) {
        this(sde.getKm(), sde.getPk(), sde.getPlus(), sde.getLabel(), sde.getSymbolValue(), sde.getDistancesToWays());
    }

    public SymbolDataEntryReadOnly(int km, int pk, int plus, String label, ConventionalSymbol symbol,
                                   Collection<SymbolDistanceToWay> distanceToWays) {
        super(km, pk, plus);
        this.label = label;
        this.name = symbol.getName();
        this.defaultIcon = new SymbolIconReadOnly(symbol.getDefaultIcon());

        Map<String, SymbolIcon> symbolIcons = symbol.getIcons();
        if (symbolIcons == null || symbolIcons.isEmpty()) {
            this.icons = Collections.emptyMap();
        } else {
            this.icons = new HashMap<String, SymbolIconReadOnly>(symbolIcons.size());
            for (Map.Entry<String, SymbolIcon> iconEntry : symbolIcons.entrySet()) {
                this.icons.put(iconEntry.getKey(), new SymbolIconReadOnly(iconEntry.getValue()));
            }
        }

        if (distanceToWays == null || distanceToWays.isEmpty()) {
            this.distanceToWays = Collections.emptyMap();
        } else {
            this.distanceToWays = new HashMap<Integer, Integer>(distanceToWays.size());
            for (SymbolDistanceToWay d : distanceToWays) {
                this.distanceToWays.put(d.getRailWay().getNumber(), d.getDistance());
            }
        }
    }

    private String name;
    private String label;
    private Map<String, SymbolIconReadOnly> icons;
    private SymbolIconReadOnly defaultIcon;
    private Map<Integer, Integer> distanceToWays;

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getTemplate(RepresentationRole representationRole) {
        return getIcon(representationRole).getTemplate();
    }

    public int getWidth(RepresentationRole representationRole) {
        return getIcon(representationRole).getWidth();
    }

    public int getHeight(RepresentationRole representationRole) {
        return getIcon(representationRole).getHeight();
    }

    private SymbolIconReadOnly getIcon(RepresentationRole representationRole) {
        if (icons.isEmpty()) {
            return defaultIcon;
        }

        SymbolIconReadOnly symbolIcon = icons.get(representationRole.name());
        return symbolIcon == null ? defaultIcon : symbolIcon;
    }

    public Map<Integer, Integer> getDistanceToWays() {
        return distanceToWays;
    }

    public String getLocationLabel(){
        return String.format("пк %d+%.2f", toPk(), 1.0 * getPlus() / Location.MM_IN_METER);
    }
}
