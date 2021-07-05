package com.tcb.sensenet.internal.layout.nodePlacement;

import java.awt.geom.Point2D;
import java.util.List;

import com.tcb.cytoscape.cyLib.cytoApiWrappers.LayoutNodeAdapter;
import com.tcb.cytoscape.cyLib.util.CyclingIterator;
import com.tcb.common.util.Tuple;

public class SubNodeOrbitPlacer {
	
	private List<LayoutNodeAdapter> subNodes;
	private Double radius;
	private Point2D headCoordinates;

	public SubNodeOrbitPlacer(Point2D headCoordinates, List<LayoutNodeAdapter> subNodes, Double radius){
		this.headCoordinates = headCoordinates;
		this.subNodes = subNodes;
		this.radius = radius;
	}

	public void place(){
		Double headX = headCoordinates.getX();
		Double headY = headCoordinates.getY();
		Integer cycleLength = subNodes.size();
		CyclingIterator<Tuple<Double,Double>> orbitIterator = new NodeOrbitIterator(headX,headY,radius,cycleLength);
		for(LayoutNodeAdapter subNode:subNodes){
			Tuple<Double,Double> nextCoordinates = orbitIterator.next();
			subNode.setX(nextCoordinates.one());
			subNode.setY(nextCoordinates.two());
			subNode.moveToLocation();
		}
		
	}
	
}
