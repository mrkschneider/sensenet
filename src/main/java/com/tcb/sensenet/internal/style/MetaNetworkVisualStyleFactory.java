package com.tcb.sensenet.internal.style;

import java.awt.Color;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import org.cytoscape.event.CyEventHelper;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.presentation.property.LineTypeVisualProperty;
import org.cytoscape.view.presentation.property.NodeShapeVisualProperty;
import org.cytoscape.view.presentation.property.values.LineType;
import org.cytoscape.view.presentation.property.values.NodeShape;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.data.rows.RowStatistics;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.node.NodeType;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class MetaNetworkVisualStyleFactory {
	
	
	protected static final Double minEdgeWidth = 1.0;
	protected static final Double maxEdgeWidth = 20.0;
	
	protected static final Color nodeColor = new Color(216, 253, 255);
	protected static final Double nodeBorderWidth = 2.0;
	protected static final Color nodeBorderColor = Color.BLACK;
	protected static final Color edgeColor = Color.BLACK;
	protected static final Boolean nestedNetworkVisible = false;
	protected static final Double metanodeSize = 70.0;
	protected static final Double nodeSize = 90.0;
	protected static final Double nodeWidth = 70.0;
	protected static final Double nodeHeight = 50.0;
	
	protected VisualStyleFactory fac;
	protected VisualMappingFunctionFactory visMapFunFacPassthrough;
	protected VisualMappingFunctionFactory visMapFunFacContinuous;
	protected VisualMappingFunctionFactory visMapFunFacDiscrete;
	protected String nodeNameColumn;
	protected CyEventHelper eventHelper;
	protected MetaNetwork metaNetwork;

	private static final LineType[] lineTypes = 
		{LineTypeVisualProperty.SOLID,LineTypeVisualProperty.EQUAL_DASH,
		LineTypeVisualProperty.DOT, LineTypeVisualProperty.DASH_DOT, 
		LineTypeVisualProperty.LONG_DASH};
	
	public MetaNetworkVisualStyleFactory(
			String nodeNameColumn,
			MetaNetwork metaNetwork,
			VisualStyleFactory fac,
			CyEventHelper eventHelper,
			VisualMappingFunctionFactory visMapFunFacPassthrough,
			VisualMappingFunctionFactory visMapFunFacContinuous,
			VisualMappingFunctionFactory visMapFunFacDiscrete){
		this.nodeNameColumn = nodeNameColumn;
		this.metaNetwork = metaNetwork;
		this.fac = fac;
		this.eventHelper = eventHelper;
		this.visMapFunFacPassthrough = visMapFunFacPassthrough;
		this.visMapFunFacContinuous = visMapFunFacContinuous;
		this.visMapFunFacDiscrete = visMapFunFacDiscrete;
	}
	
	public String getStyleName(){
		return String.format(
				"sensenet style (%s)",
				metaNetwork.getSharedDataRow().get(DefaultColumns.SHARED_NAME, String.class));
	}
	
	public VisualStyle create(){
		 VisualStyle vs = fac.createVisualStyle(getStyleName());
		 setNodeLabels(vs);
		 setNodeShapes(vs);
		 setNodeSizes(vs);
		 setEdgeWidthMapping(vs);
		 setEdgeColors(vs);
		 setEdgeTooltip(vs);
		 setEdgeLineTypes(vs);
		 
		 vs.setDefaultValue(BasicVisualLexicon.NODE_FILL_COLOR, nodeColor);
		 vs.setDefaultValue(BasicVisualLexicon.NODE_BORDER_WIDTH, nodeBorderWidth);
		 vs.setDefaultValue(BasicVisualLexicon.NODE_BORDER_PAINT, nodeBorderColor);
		 vs.setDefaultValue(BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT, edgeColor);
		 vs.setDefaultValue(BasicVisualLexicon.NODE_NESTED_NETWORK_IMAGE_VISIBLE, nestedNetworkVisible);
		 
		 
		 vs.getAllVisualPropertyDependencies().stream()
		 	.filter(d -> d.getIdString().equals("nodeSizeLocked"))
		 	.forEach(d -> d.setDependency(false));

		 return vs;
	}
	
	protected void setNodeLabels(VisualStyle vs){
		 PassthroughMapping<String,String> pMapping = (PassthroughMapping<String,String>) visMapFunFacPassthrough
				 .createVisualMappingFunction(nodeNameColumn, String.class, BasicVisualLexicon.NODE_LABEL);
		 vs.addVisualMappingFunction(pMapping);
	}
	
	protected void setNodeSizes(VisualStyle vs){
		DiscreteMapping<String,Double> m = (DiscreteMapping<String,Double>) 
				visMapFunFacDiscrete.createVisualMappingFunction(
						DefaultColumns.TYPE.toString(),
						String.class,
						BasicVisualLexicon.NODE_WIDTH);
		
		m.putMapValue(NodeType.Metanode.toString(), metanodeSize);
		m.putMapValue(NodeType.Node.toString(), nodeSize);
		vs.addVisualMappingFunction(m);
		
		vs.setDefaultValue(BasicVisualLexicon.NODE_WIDTH, nodeWidth);
		vs.setDefaultValue(BasicVisualLexicon.NODE_HEIGHT, nodeHeight);
	}
	
	protected void setNodeShapes(VisualStyle vs){
		DiscreteMapping<String,NodeShape> m = (DiscreteMapping<String,NodeShape>) 
				visMapFunFacDiscrete.createVisualMappingFunction(
						DefaultColumns.TYPE.toString(),
						String.class,
						BasicVisualLexicon.NODE_SHAPE);
		
		m.putMapValue(NodeType.Metanode.toString(), NodeShapeVisualProperty.ELLIPSE);
		m.putMapValue(NodeType.Node.toString(), NodeShapeVisualProperty.ROUND_RECTANGLE);
		vs.addVisualMappingFunction(m);
	}
	
	protected void setEdgeWidthMapping(VisualStyle vs){
		BoundaryRangeValues<Double> min = new BoundaryRangeValues<>(minEdgeWidth,minEdgeWidth,minEdgeWidth);
		BoundaryRangeValues<Double> max = new BoundaryRangeValues<>(maxEdgeWidth,maxEdgeWidth,maxEdgeWidth);
		 		 
		 Double maxWeight = createMetaNetworkWeightStatistics()
				 .getMax();
		 
		 ContinuousMapping<Double,Double> cMapping = (ContinuousMapping<Double,Double>) visMapFunFacContinuous
				 .createVisualMappingFunction(AppColumns.WEIGHT.toString(), Double.class, BasicVisualLexicon.EDGE_WIDTH);
		 
		 cMapping.addPoint(0.0, min);
		 cMapping.addPoint(maxWeight, max);
		 vs.addVisualMappingFunction(cMapping);
	}
	
	protected void setEdgeTooltip(VisualStyle vs){
								
		PassthroughMapping<String, ?> mapping = (PassthroughMapping<String, ?>) visMapFunFacPassthrough
				.createVisualMappingFunction(
						AppColumns.WEIGHT.toString(),
						String.class,
						BasicVisualLexicon.EDGE_TOOLTIP);
		
		vs.addVisualMappingFunction(mapping);
	}
	
	protected void setEdgeLineTypes(VisualStyle vs){
		List<String> interactionTypes = metaNetwork.getHiddenDataRow().getList(
				AppColumns.AVAILABLE_INTERACTION_TYPES, String.class).stream()
				.sorted(String.CASE_INSENSITIVE_ORDER)
				.collect(ImmutableList.toImmutableList());
		
		DiscreteMapping<String, LineType> mapping = (DiscreteMapping<String, LineType>)
				visMapFunFacDiscrete.createVisualMappingFunction(
						DefaultColumns.SHARED_INTERACTION.toString(),
						String.class,
						BasicVisualLexicon.EDGE_LINE_TYPE);
		for(int i=0;i<interactionTypes.size();i++){
			int idx = i;
			if(idx >= lineTypes.length) idx = 0;
			LineType style = lineTypes[idx];
			String interactionType = interactionTypes.get(i);
			
			mapping.putMapValue(interactionType, style);
		}
		vs.addVisualMappingFunction(mapping);
	}
	
	protected void setEdgeColors(VisualStyle vs){
		// Do nothing
	}
		
	protected DoubleSummaryStatistics createMetaNetworkWeightStatistics(){
		List<CyRowAdapter> rows = metaNetwork.getEdges().stream()
				 .map(e -> metaNetwork.getRow(e))
				 .collect(ImmutableList.toImmutableList());
		DoubleSummaryStatistics stat = new RowStatistics(rows)
				 .getDoubleColumnStatistics(AppColumns.WEIGHT.toString());
		return stat;
	}
	
	

}
