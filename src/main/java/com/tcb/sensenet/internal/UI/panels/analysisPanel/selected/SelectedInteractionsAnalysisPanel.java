package com.tcb.sensenet.internal.UI.panels.analysisPanel.selected;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.tcb.sensenet.internal.UI.state.ExclusiveForNormalTimeline;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.plot.LinePlotType;
import com.tcb.sensenet.internal.selection.InvalidSelectionException;
import com.tcb.sensenet.internal.selection.SingleEdgeSelection;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;


public class SelectedInteractionsAnalysisPanel extends DefaultPanel {

	private AppGlobals appGlobals;
	
	private JButton plotTimelineButton;
	
	@ExclusiveForNormalTimeline
	private JButton plotBlockedErrorButton;
	@ExclusiveForNormalTimeline
	private JButton plotAutocorrelationButton;
	@ExclusiveForNormalTimeline
	private JButton correlationButton;
	@ExclusiveForNormalTimeline
	private JButton divergenceButton;

	public SelectedInteractionsAnalysisPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		addTimelinePlotButton(p);
		addCorrelationButton(p);
		addAutocorrelationsPlotButton(p);
		addStandardBlockedErrorPlotButton(p);
		//addDivergenceButton(p);
				
		this.add(p);
		
		addDummyPanel();
	
	}
	
		
	private void addTimelinePlotButton(JPanel target){
		String buttonName = "Timeline";
		
		JButton button = new JButton(buttonName);
		button.addActionListener(e -> new TimelineDialog(appGlobals).setVisible(true));
		target.add(button);
		plotTimelineButton = button;
	}
	
	private void addStandardBlockedErrorPlotButton(JPanel target){
		String buttonName = "Blocked error";
				
		JButton button = new JButton(buttonName);
		button.addActionListener(e -> new BlockedErrorDialog(appGlobals).setVisible(true));
		target.add(button);
		plotBlockedErrorButton = button;
	}
	
	private void addAutocorrelationsPlotButton(JPanel target){
		String buttonName = "Autocorrelation";
			
		JButton button = new JButton(buttonName);
		button.addActionListener(e -> new AutocorrelationErrorDialog(appGlobals).setVisible(true));
		target.add(button);
		plotAutocorrelationButton = button;
	}
	
	private void addCorrelationButton(JPanel target){
		JButton button = new JButton("Correlation");
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new CorrelationTaskDialog(appGlobals).setVisible(true);
			}
		};
		button.addActionListener(listener);
		target.add(button);
		correlationButton = button;
	}
	
	private void addDivergenceButton(JPanel target) {
		JButton button = new JButton("Divergence");
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EdgeDivergenceTaskDialog(appGlobals).setVisible(true);
			}
		};
		button.addActionListener(listener);
		target.add(button);
		divergenceButton = button;
	}
	
	
	
	
	
}
