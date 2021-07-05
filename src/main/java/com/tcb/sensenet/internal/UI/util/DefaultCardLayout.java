package com.tcb.sensenet.internal.UI.util;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultCardLayout extends CardLayout {

	@Override
	public Dimension preferredLayoutSize(Container parent){
		Component current = findCurrentComponent(parent);
		if(current == null) return super.preferredLayoutSize(parent);
		
		Double x = getLargestCardWidth(parent);
		Double y = current.getPreferredSize().getHeight();
		
		Dimension dim = new Dimension();
		dim.setSize(x, y);
		
		return dim;
	}
	
	private Component findCurrentComponent(Container parent) {
        for (Component comp : parent.getComponents()) {
            if (comp.isVisible()) {
                return comp;
            }
        }
        return null;
    }
	
	private Double getLargestCardWidth(Container parent){
		List<Dimension> cardSizes = Stream.of(parent.getComponents())
				.map(c -> c.getPreferredSize())
				.collect(Collectors.toList());
		double xMax = cardSizes.stream()
				.mapToDouble(s -> s.getWidth())
				.max().orElseGet(() -> 0.0);
		return xMax;
	}
	
}
