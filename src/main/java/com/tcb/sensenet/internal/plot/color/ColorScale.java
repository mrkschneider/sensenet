package com.tcb.sensenet.internal.plot.color;

import java.awt.Color;

public interface ColorScale {
	public Double getMinValue();
	public Double getMaxValue();
	public Color getColor(Double value);
}
