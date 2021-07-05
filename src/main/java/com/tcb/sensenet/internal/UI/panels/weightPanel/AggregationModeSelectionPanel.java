package com.tcb.sensenet.internal.UI.panels.weightPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.ActionActivateClusterWeightingListener;
import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.ActionActivateTimelineWeightingListener;
import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.ActionActivateTimepointWeightingListener;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.util.KeyButtonGroup;
import com.tcb.sensenet.internal.util.KeyRadioButton;

public class AggregationModeSelectionPanel extends JPanel {

	private AppGlobals appGlobals;
		
	private KeyButtonGroup buttonGroup;
	private KeyRadioButton timelineAggregationCheckbox;
	private KeyRadioButton timepointAggregationCheckBox;
	private KeyRadioButton clusterSelectionCheckbox;

	public AggregationModeSelectionPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.buttonGroup = new KeyButtonGroup();
		
		this.setLayout(new GridBagLayout());
		
		addTimelineAggregationButton();
		addTimepointAggregationButton();
		addClusterSelectionButton();
				
		appGlobals.stateManagers.aggregationModeSelectionPanelStateManager.register(this);
	}
		
	
	public KeyButtonGroup getAggregationModeButtonGroup(){
		return buttonGroup;
	}
	
	public KeyRadioButton getTimepointAggregationButton() {
		return timepointAggregationCheckBox;
	}
	
	public KeyRadioButton getClusterAggregationButton(){
		return clusterSelectionCheckbox;
	}
	
	public KeyRadioButton getTimelineAggregationButton(){
		return timelineAggregationCheckbox;
	}
		
	private void addTimelineAggregationButton(){
		KeyRadioButton box = new KeyRadioButton("Average weight", TimelineWeightMethod.AVERAGE_FRAME.name());
		
		ActionListener listener = new ActionActivateTimelineWeightingListener(
				appGlobals);
		box.addActionListener(listener);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.LINE_START;
		
		buttonGroup.add(box);
		this.add(box, c);
		this.timelineAggregationCheckbox = box;
	}
		
	private void addTimepointAggregationButton(){
		this.timepointAggregationCheckBox = new KeyRadioButton(
				"Single frame", TimelineWeightMethod.SINGLE_FRAME.name());
		ActionListener listener = new ActionActivateTimepointWeightingListener(
				appGlobals);
		timepointAggregationCheckBox.addActionListener(listener);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.LINE_START;
		
		buttonGroup.add(timepointAggregationCheckBox);
		this.add(timepointAggregationCheckBox, c);
	}
	
	private void addClusterSelectionButton(){
		this.clusterSelectionCheckbox = new KeyRadioButton("Clusters",
				TimelineWeightMethod.CLUSTERS.name());
		ActionListener listener = new ActionActivateClusterWeightingListener(
				appGlobals);
		clusterSelectionCheckbox.addActionListener(listener);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.anchor = GridBagConstraints.LINE_START;
		
		buttonGroup.add(clusterSelectionCheckbox);
		this.add(clusterSelectionCheckbox, c);
	}
	
	
	

	
}
