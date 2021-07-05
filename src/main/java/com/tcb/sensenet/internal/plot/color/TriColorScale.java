package com.tcb.sensenet.internal.plot.color;

import java.awt.Color;

public class TriColorScale implements ColorScale {

	private ColorScale low;
	private ColorScale high;
	private Double branchPoint;

	public TriColorScale(ColorScale low, ColorScale high){
		this.low = low;
		this.high = high;
		this.branchPoint = low.getMaxValue();
		verifyInput();
	}
	
	private void verifyInput(){
		if(!low.getMaxValue().equals(high.getMinValue())) 
			throw new IllegalArgumentException("Color scales must have matching min and max values");
	}
	
	@Override
	public Double getMinValue() {
		return low.getMinValue();
	}

	@Override
	public Double getMaxValue() {
		return high.getMaxValue();
	}

	@Override
	public Color getColor(Double value) {
		if(value < branchPoint) return low.getColor(value);
		else return high.getColor(value);
	}

}
