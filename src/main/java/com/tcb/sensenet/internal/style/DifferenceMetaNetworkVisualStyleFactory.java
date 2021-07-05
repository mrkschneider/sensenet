package com.tcb.sensenet.internal.style;

import java.awt.Color;
import java.awt.Paint;
import java.util.DoubleSummaryStatistics;

import org.cytoscape.event.CyEventHelper;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class DifferenceMetaNetworkVisualStyleFactory extends MetaNetworkVisualStyleFactory {

	private static final Color lowColor = Color.BLUE;
	private static final Color midColor = Color.WHITE;
	private static final Color highColor = Color.RED;
	
	public DifferenceMetaNetworkVisualStyleFactory(
			String nodeNameColumn,
			MetaNetwork metaNetwork,
			VisualStyleFactory fac,
			CyEventHelper eventHelper,
			VisualMappingFunctionFactory visMapFunFacPassthrough,
			VisualMappingFunctionFactory visMapFunFacContinuous,
			VisualMappingFunctionFactory visMapFunFacDiscrete) {
		super(
				nodeNameColumn,
				metaNetwork,
				fac,
				eventHelper,
				visMapFunFacPassthrough,
				visMapFunFacContinuous,
				visMapFunFacDiscrete);
	}

	@Override
	protected void setEdgeWidthMapping(VisualStyle vs){
		BoundaryRangeValues<Double> mid = new BoundaryRangeValues<>(minEdgeWidth,minEdgeWidth,minEdgeWidth);
		BoundaryRangeValues<Double> max = new BoundaryRangeValues<>(maxEdgeWidth,maxEdgeWidth,maxEdgeWidth);
		 
		Double maxAbsoluteWeight = calculateMaxAbsWeight();
		 		 
		 ContinuousMapping<Double,Double> cMapping = (ContinuousMapping<Double, Double>)
				 visMapFunFacContinuous.createVisualMappingFunction(AppColumns.WEIGHT.toString(),
						 Double.class, BasicVisualLexicon.EDGE_WIDTH);
		 cMapping.addPoint(-maxAbsoluteWeight, max);
		 cMapping.addPoint(0.0, mid);
		 cMapping.addPoint(maxAbsoluteWeight, max);
		 vs.addVisualMappingFunction(cMapping);
	}
	
	@Override
	protected void setEdgeColors(VisualStyle vs){
		BoundaryRangeValues<Paint> lowColorRange = new BoundaryRangeValues<>(lowColor, lowColor, lowColor);
		BoundaryRangeValues<Paint> lowMidColorRange = new BoundaryRangeValues<>(lowColor, lowColor, midColor);
		BoundaryRangeValues<Paint> highMidColorRange = new BoundaryRangeValues<>(midColor, highColor, highColor);
		BoundaryRangeValues<Paint> highColorRange = new BoundaryRangeValues<>(highColor, highColor, highColor);
				
		ContinuousMapping<Double,Paint> cMapping = (ContinuousMapping<Double, Paint>) visMapFunFacContinuous
				 .createVisualMappingFunction(AppColumns.WEIGHT.toString(),
						 Double.class, BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT);
		Double maxAbsoluteWeight = calculateMaxAbsWeight();
		
		cMapping.addPoint(-maxAbsoluteWeight, lowColorRange);
		cMapping.addPoint(-0.01, lowMidColorRange);
		cMapping.addPoint(0.01, highMidColorRange);
		cMapping.addPoint(maxAbsoluteWeight, highColorRange);
		
		vs.addVisualMappingFunction(cMapping);
	}
	
	@Override
	public String getStyleName(){
		return String.format(
				"Difference sensenet style (%s)",
				metaNetwork.getSharedDataRow().get(DefaultColumns.SHARED_NAME, String.class));
	}
		
	private Double calculateMaxAbsWeight(){
		 DoubleSummaryStatistics stat = createMetaNetworkWeightStatistics();
		 Double maxWeight = stat.getMax();
		 Double minWeight = stat.getMin();
		 Double absWeight = Math.max(Math.abs(minWeight), Math.abs(maxWeight));
		 return absWeight;
	}
	

}
