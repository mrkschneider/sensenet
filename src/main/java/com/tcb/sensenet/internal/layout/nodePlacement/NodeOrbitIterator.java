package com.tcb.sensenet.internal.layout.nodePlacement;

import com.tcb.cytoscape.cyLib.util.CyclingIterator;
import com.tcb.cytoscape.cyLib.util.PiCyclingIterator;
import com.tcb.common.util.Tuple;

public class NodeOrbitIterator implements CyclingIterator<Tuple<Double,Double>>{
	
	private Double centreX;
	private Double centreY;
	private Double radius;
	private CyclingIterator<Double> angleIterator;

	public NodeOrbitIterator(Double centreX, Double centreY, Double radius, Integer cycleLength){
		this.centreX = centreX;
		this.centreY = centreY;
		this.radius = radius;
		this.angleIterator = new PiCyclingIterator(cycleLength);
	}

	@Override
	public boolean hasNext() {
		return angleIterator.hasNext();
	}
	
	@Override
	public Integer getCycleLength() {
		return angleIterator.getCycleLength();
	}

	@Override
	public Tuple<Double, Double> next() {
		Double nextAngle = angleIterator.next();
		Double nextX = centreX + rotateXOffset(nextAngle);
		Double nextY = centreY + rotateYOffset(nextAngle);
		return new Tuple<Double,Double>(nextX,nextY);
	}
	
	private Double rotateXOffset(Double angle){
		return radius * Math.cos(angle);
	}
	
	private Double rotateYOffset(Double angle){
		return radius * Math.sin(angle);
	}
	
	
	
	

}
