package com.tcb.sensenet.internal.UI.panels.stylePanel.auto;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Paint;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import javax.swing.JTextField;

import org.cytoscape.util.swing.ColorButton;
import org.cytoscape.view.model.VisualProperty;
import org.cytoscape.view.vizmap.VisualMappingFunction;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;

import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.rows.RowStatistics;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;

public abstract class AbstractAutoColorStylePanel extends AbstractAutoStylePanel {
	
	protected abstract List<CyRowAdapter> getRows();
	protected abstract VisualProperty<Paint> getVisualProperty();
	protected abstract AppProperty getDefaultLowColorProperty();
	protected abstract AppProperty getDefaultMidColorProperty();
	protected abstract AppProperty getDefaultHighColorProperty();
	
	private ColorButton lowColorButton;
	private ColorButton midColorButton;
	private ColorButton highColorButton;

	private JTextField minBox;
	private JTextField midBox;
	private JTextField maxBox;

	public AbstractAutoColorStylePanel(
			AppGlobals appGlobals){
		super(appGlobals);
				
		LabeledParametersPanel p = new LabeledParametersPanel();
						
		minBox = p.addTextParameter("Min value", null);
		midBox = p.addTextParameter("Mid value", null);
		maxBox = p.addTextParameter("Max value", null);
		
		lowColorButton = p.addColorParameter("Low color",
				 				new Color(Integer.valueOf(
				 						appGlobals.appProperties.getOrDefault(
				 								getDefaultLowColorProperty()))));
		midColorButton = p.addColorParameter("Mid color",
							new Color(Integer.valueOf(
									appGlobals.appProperties.getOrDefault(
											getDefaultMidColorProperty()))));
		highColorButton = p.addColorParameter("High color", 
							new Color(Integer.valueOf(
									appGlobals.appProperties.getOrDefault(
											getDefaultHighColorProperty()))));
		
		this.setLayout(new GridLayout(0,1));
		this.add(p);
	}
	
	public void updateTextFields(String mappingColumn){
		DoubleSummaryStatistics stat = new RowStatistics(getRows())
				.getDoubleColumnStatistics(mappingColumn);
		
		double min = stat.getMin();
		double max = stat.getMax();
		double mid = ((max - min) / 2.0) + min;
		
		minBox.setText(String.valueOf(min));
		midBox.setText(String.valueOf(mid));
		maxBox.setText(String.valueOf(max));
	}
		
	@Override
	public VisualMappingFunction<Double,Paint> getVisualMappingFunction(String mappingColumn){
		Double min = Double.valueOf(minBox.getText());
		Double mid = Double.valueOf(midBox.getText());
		Double max = Double.valueOf(maxBox.getText());
		
		Color lowColor = lowColorButton.getColor();
		Color midColor = midColorButton.getColor();
		Color highColor = highColorButton.getColor();
				
		BoundaryRangeValues<Paint> lowColorRange = new BoundaryRangeValues<>(lowColor, lowColor, lowColor);
		BoundaryRangeValues<Paint> midColorRange = new BoundaryRangeValues<>(midColor, midColor, midColor);
		BoundaryRangeValues<Paint> highColorRange = new BoundaryRangeValues<>(highColor, highColor, highColor);
				
		ContinuousMapping<Double,Paint> mapping = 
				 (ContinuousMapping<Double,Paint>) appGlobals.visualMapFunctionFacContinuous
				 .createVisualMappingFunction(
						 mappingColumn,
						 Double.class,
						 getVisualProperty());
				
		mapping.addPoint(min, lowColorRange);
		mapping.addPoint(mid, midColorRange);
		mapping.addPoint(max, highColorRange);
		
		appGlobals.appProperties.set(getDefaultLowColorProperty(), Integer.toString(lowColor.getRGB()));
		appGlobals.appProperties.set(getDefaultMidColorProperty(), Integer.toString(midColor.getRGB()));
		appGlobals.appProperties.set(getDefaultHighColorProperty(), Integer.toString(highColor.getRGB()));
		
		return mapping;
	}
	
	
}
