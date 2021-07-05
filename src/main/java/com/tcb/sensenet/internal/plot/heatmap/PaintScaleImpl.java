package com.tcb.sensenet.internal.plot.heatmap;


import java.awt.Color;
import java.awt.Paint;

import org.jfree.chart.renderer.PaintScale;

import com.tcb.sensenet.internal.plot.color.ColorScale;

public class PaintScaleImpl implements PaintScale {

	private ColorScale colorScale;
		
	public PaintScaleImpl(ColorScale colorScale){
		this.colorScale = colorScale;
	}
	
	@Override
	public double getLowerBound() {
		return colorScale.getMinValue();
	}

	@Override
	public double getUpperBound() {
		return colorScale.getMaxValue();
	}

	@Override
	public Paint getPaint(double value) {
      return colorScale.getColor(value);
	}
}
