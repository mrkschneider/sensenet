package com.tcb.sensenet.internal.UI.panels.weightPanel;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.tcb.sensenet.internal.UI.util.ColumnsShortStringRenderer;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.UI.util.TextComboBox;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.util.CutoffLimit;
import com.tcb.cytoscape.cyLib.data.Columns;

public class EdgeCutoffPanel extends JPanel {

	
	private TextComboBox<Columns> cutoffBox;
	private CutoffLimit limit = new CutoffLimit(0.0,Double.MAX_VALUE);
	private AppGlobals appGlobals;
	
	private static final String defaultCutoffFormatError =
			"Cutoff must be a positive number";
		
	private static final AppProperty cutoffColumnProperty = AppProperty.DEFAULT_TIMEFRACTION_CUTOFF_COLUMN;
	private static final AppProperty cutoffValueProperty = AppProperty.DEFAULT_TIMEFRACTION_CUTOFF;
	
	private static final Columns[] validCutoffColumns = 
		{			AppColumns.OCCURRENCE,
					AppColumns.AVERAGE_INTERACTIONS,
					AppColumns.WEIGHT,
					AppColumns.CORRELATION_FACTOR,
					AppColumns.LIFETIME,
					AppColumns.ERROR_ESTIMATE,
					AppColumns.STANDARD_DEVIATION
					};
	

	public EdgeCutoffPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		this.setLayout(new GridLayout(1,1));
		
		addCutoffPanel();
	}
	
	public void addActionListener(ActionListener listener){
		cutoffBox.getField().addActionListener(listener);
	}
	
	public void addItemListener(ItemListener listener){
		cutoffBox.getComboBox().addItemListener(listener);
	}
		
	private void addCutoffPanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		cutoffBox = p.addTextChoosableParameter(
				"Remove edges <",
				validCutoffColumns,
				AppColumns.valueOf(appGlobals.appProperties.getOrDefault(cutoffColumnProperty)),
				appGlobals.appProperties.getOrDefault(cutoffValueProperty));

		@SuppressWarnings("unchecked")
		ListCellRenderer<Columns> renderer = new ColumnsShortStringRenderer(cutoffBox.getComboBox());
		cutoffBox.getComboBox().setRenderer(renderer);
		
		this.add(p);
	}
		
	public Columns getCutoffWeightColumn(){
		Columns cutoffColumn = (Columns) cutoffBox.getComboBox().getSelectedItem();
		appGlobals.appProperties.set(cutoffColumnProperty, cutoffColumn.name());
		return cutoffColumn;
	}
	
	public Double getTimeFractionCutoff(){
		Double timeFractionCutoff = parseCutoff(cutoffBox.getField().getText());
		appGlobals.appProperties.set(cutoffValueProperty,timeFractionCutoff.toString());
		return timeFractionCutoff;
	}
	
	private Double parseCutoff(String cutoffString){
		Double timeFractionCutoff;
		try{
			timeFractionCutoff = Double.valueOf(cutoffString);
		} catch(NumberFormatException e){
			throw new IllegalArgumentException(defaultCutoffFormatError);
		}
		
		if(!limit.test(timeFractionCutoff))
			throw new IllegalArgumentException(defaultCutoffFormatError);
		
		return timeFractionCutoff;
	}
	
	public void setTimeFractionCutoff(Double cutoff){
		cutoffBox.getField().setText(cutoff.toString());
	}
	
	public void setCutoffColumn(Columns column){
		cutoffBox.getComboBox().setSelectedItem(column);
	}
	
	
}
