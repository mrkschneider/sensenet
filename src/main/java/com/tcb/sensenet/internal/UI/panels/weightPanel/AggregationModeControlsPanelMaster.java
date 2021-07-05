package com.tcb.sensenet.internal.UI.panels.weightPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.app.AppGlobals;

public class AggregationModeControlsPanelMaster extends JPanel {

	private AppGlobals appGlobals;
	private FrameSelectionPanel frameSelectionPanel;
	private ClusterSelectionPanel clusterSelectionPanel;

	public AggregationModeControlsPanelMaster(AppGlobals appGlobals) {
		this.appGlobals = appGlobals;
				
		this.setLayout(new GridBagLayout());
		
		addFrameSelectionPanel();
		addClusterSelectionPanel();
		hideSelectionPanel();
		
		appGlobals.stateManagers.aggregationModeControlsPanelStateManager.register(this);
	}
	
	private GridBagConstraints defaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
	
	private void adjustSize(){
		this.setMaximumSize(this.getPreferredSize());
	}
	
	public void addFrameSelectionPanel(){
		FrameSelectionPanel p = new FrameSelectionPanel(appGlobals);
		appGlobals.stateManagers.selectedFramePanelStateManager.register(p);
				
		this.add(p, defaultConstraints());
		frameSelectionPanel = p;
	}
	
	public void addClusterSelectionPanel(){
		ClusterSelectionPanel p = new ClusterSelectionPanel(appGlobals);
		
		this.add(p, defaultConstraints());
		clusterSelectionPanel = p;
	}
	
	public void showPanel(TimelineWeightMethod mode){
		hideSelectionPanel();
		switch(mode){
		case AVERAGE_FRAME: break;
		case SINGLE_FRAME: frameSelectionPanel.setVisible(true); break;
		case CLUSTERS: clusterSelectionPanel.setVisible(true); break;
		default: throw new IllegalArgumentException("Unknown AggregationMode");
		}
		adjustSize();
	}
	
	public void hideSelectionPanel(){
		frameSelectionPanel.setVisible(false);
		clusterSelectionPanel.setVisible(false);
	}
	

}
