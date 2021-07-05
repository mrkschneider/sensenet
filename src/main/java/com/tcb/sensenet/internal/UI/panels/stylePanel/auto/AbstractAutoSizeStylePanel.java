package com.tcb.sensenet.internal.UI.panels.stylePanel.auto;

import java.awt.GridLayout;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import javax.swing.JTextField;

import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;

import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.rows.RowStatistics;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public abstract class AbstractAutoSizeStylePanel extends AbstractAutoStylePanel {
	
	protected abstract List<CyRowAdapter> getRows();
	protected abstract VisualProperty<Double> getVisualProperty();
	protected abstract AppProperty getDefaultMinSizeProperty();
	protected abstract AppProperty getDefaultMaxSizeProperty();
	
	private JTextField minBox;
	private JTextField maxBox;

	private JTextField minWidthBox;
	private JTextField maxWidthBox;
		
	public AbstractAutoSizeStylePanel(
			AppGlobals appGlobals){
		super(appGlobals);
		
		LabeledParametersPanel p = new LabeledParametersPanel();
						
		minBox = p.addTextParameter("Min value", null);
		maxBox = p.addTextParameter("Max value", null);
		
		minWidthBox = p.addTextParameter("Min width",
				appGlobals.appProperties.getOrDefault(
				getDefaultMinSizeProperty()));
		maxWidthBox = p.addTextParameter("Max width", appGlobals.appProperties.getOrDefault(
				getDefaultMaxSizeProperty()));
		
		this.setLayout(new GridLayout(0,1));
		this.add(p);
	}
	
	public void updateTextFields(String mappingColumn){
		DoubleSummaryStatistics stat = new RowStatistics(getRows())
				.getDoubleColumnStatistics(mappingColumn);
		
		double min = stat.getMin();
		double max = stat.getMax();
		
		minBox.setText(String.valueOf(min));
		maxBox.setText(String.valueOf(max));
	}
		
	@Override
	public VisualMappingFunction<Double,Double> getVisualMappingFunction(String mappingColumn){
		Double min = Double.valueOf(minBox.getText());
		Double max = Double.valueOf(maxBox.getText());
		
		Double minWidth = Double.valueOf(minWidthBox.getText());
		Double maxWidth = Double.valueOf(maxWidthBox.getText());
						
		BoundaryRangeValues<Double> minWidthRange = new BoundaryRangeValues<>(minWidth,minWidth,minWidth);
		BoundaryRangeValues<Double> maxWidthRange = new BoundaryRangeValues<>(maxWidth,maxWidth,maxWidth);
		
		ContinuousMapping<Double,Double> mapping = 
				 (ContinuousMapping<Double,Double>) appGlobals.visualMapFunctionFacContinuous
				 .createVisualMappingFunction(
						 mappingColumn,
						 Double.class,
						 getVisualProperty());
				
		mapping.addPoint(min, minWidthRange);
		mapping.addPoint(max, maxWidthRange);
		
		appGlobals.appProperties.set(getDefaultMinSizeProperty(), minWidth.toString());
		appGlobals.appProperties.set(getDefaultMaxSizeProperty(), maxWidth.toString());
		
		return mapping;
	}
	
	
}
