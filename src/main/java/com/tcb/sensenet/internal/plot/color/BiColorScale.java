package com.tcb.sensenet.internal.plot.color;

import java.awt.Color;
import java.awt.Paint;

public class BiColorScale implements ColorScale {
	private Color minColor;
	private Color maxColor;
	private final Double minValue;
	private final Double maxValue;
	private final double range;

	public BiColorScale(Color minColor, Color maxColor, Double minValue, Double maxValue){
		this.minColor = minColor;
		this.maxColor = maxColor;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.range = maxValue - minValue;
	}

	@Override
	public Double getMinValue() {
		return minValue;
	}

	@Override
	public Double getMaxValue() {
		return maxValue;
	}

	@Override
	public Color getColor(Double value) {
		double factor = (value - minValue) / range;
		factor = Math.max(factor, 0.0);
		factor = Math.min(factor, 1.0);
        int red = scaleColorComponent(factor, minColor.getRed(), maxColor.getRed());
        int green = scaleColorComponent(factor, minColor.getGreen(), maxColor.getGreen());
        int blue = scaleColorComponent(factor, minColor.getBlue(), maxColor.getBlue());
        return new Color(red,green,blue);
	}
	
		
	private int scaleColorComponent(double factor, int lowComponent, int highComponent){
		int colorDelta = highComponent - lowComponent;
		int scaled = lowComponent + ((int) (factor * colorDelta));
		scaled = Math.max(0, scaled);
		scaled = Math.min(255, scaled);
		return scaled;
	}
}
