package com.tcb.sensenet.internal.UI.panels.weightPanel;


import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.ActionChangeEdgeWeightingListener;
import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.ActionSetEdgeCutoffListener;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.util.JPanelUtil;
import com.tcb.sensenet.internal.util.KeyButtonGroup;
import com.tcb.sensenet.internal.util.KeyRadioButton;
import com.tcb.cytoscape.cyLib.data.Columns;


public class WeightPanel extends DefaultPanel {

	private AppGlobals appGlobals;
	private KeyButtonGroup group;
	private EdgeCutoffPanel edgeCutoffPanel;
		
	public WeightPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.group = new KeyButtonGroup();
		
		JPanel p = new DefaultPanel(getDefaultConstraints());
		
		addWeightMethodPanel(p);
		addAggregationModeSelectionPanel(p);
		addAggregationModeControlsPanel(p);
		addEdgeCutoffPanel(p);
		
		this.add(p);
		
		this.addDummyPanel();
		
		appGlobals.stateManagers.weightPanelStateManager.register(this);		
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.weightx = 0;
		return c;
	}
	
	
	private void addEdgeCutoffPanel(Container target){
		edgeCutoffPanel = new EdgeCutoffPanel(
				appGlobals);
		ActionListener listener = new ActionSetEdgeCutoffListener(
				edgeCutoffPanel, appGlobals);
		edgeCutoffPanel.addActionListener(listener);
		edgeCutoffPanel.addItemListener((ItemListener)listener);		
		
		GridBagConstraints c = getDefaultConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.weightx = 1.0;
		
		target.add(edgeCutoffPanel, c);
	}
	
	public synchronized void setEdgeCutoff(Double newValue){
		edgeCutoffPanel.setTimeFractionCutoff(newValue);
	}
	
	public synchronized void setCutoffColumn(Columns column){
		edgeCutoffPanel.setCutoffColumn(column);
	}
	
	private void addAggregationModeSelectionPanel(Container target){
		AggregationModeSelectionPanel timepointWeightingPanel =
				new AggregationModeSelectionPanel(
				appGlobals);
		
		timepointWeightingPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
		GridBagConstraints c = getDefaultConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		target.add(timepointWeightingPanel,c);
	}
			
	public KeyButtonGroup getSelectWeightModeButtonGroup(){
		return group;
	}
				
	private KeyRadioButton addAggregateSumButton(JPanel parent){
		String buttonName = "Sum";
		String buttonKey = FrameWeightMethod.SUM.name();
		ActionListener listener = new ActionChangeEdgeWeightingListener(appGlobals,
				FrameWeightMethod.SUM);
		KeyRadioButton button = JPanelUtil.addRadioButton(parent, buttonName, buttonKey, listener);
		return button;	
	}
	
	private KeyRadioButton addAggregateResidueOccupancyButton(JPanel parent){
		String buttonName = "Occurrence";
		String buttonKey = FrameWeightMethod.OCCURRENCE.name();
		ActionListener listener = new ActionChangeEdgeWeightingListener(appGlobals,
				FrameWeightMethod.OCCURRENCE);
		KeyRadioButton button = JPanelUtil.addRadioButton(parent, buttonName, buttonKey, listener);
		return button;
	}
	
	private void addAggregationModeControlsPanel(Container target){
		AggregationModeControlsPanelMaster p = new AggregationModeControlsPanelMaster(appGlobals);
		GridBagConstraints c = getDefaultConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		target.add(p,c);
	}
	
	private void addWeightMethodPanel(Container target){
		JPanel radioPanel = new DefaultPanel();
		KeyRadioButton sumButton = addAggregateSumButton(radioPanel);
		KeyRadioButton occButton = addAggregateResidueOccupancyButton(radioPanel);
		group.add(sumButton);
		group.add(occButton);
		radioPanel.add(sumButton);
		radioPanel.add(occButton);
		target.add(radioPanel);
	}

}
