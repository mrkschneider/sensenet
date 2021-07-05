package com.tcb.sensenet.internal.UI.panels.analysisPanel.network;


import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.panels.analysisPanel.network.correlation.CorrelationFactorsDialog;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.paths.WeightedNodeCentralityDialog;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.selected.TimelineListSelectionDialog;
import com.tcb.sensenet.internal.UI.state.ExclusiveForNormalTimeline;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.LifetimeAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.LifetimeWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.task.aggregation.factories.ActionMetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.util.ObjMap;


public class NetworkInteractionsAnalysisPanel extends DefaultPanel {

	private AppGlobals appGlobals;
	
	private JButton aggregateTimelineButton;
	
	private JButton degreeButton;
	
	@ExclusiveForNormalTimeline
	private JButton aggregateLifetimeButton;
	@ExclusiveForNormalTimeline
	private JButton aggregateAutocorrelationErrorButton;
	@ExclusiveForNormalTimeline
	private JButton correlationFactorsButton;
	@ExclusiveForNormalTimeline
	private JButton entropyButton;
	
	private JButton nodeCentralityButton;
	
	@ExclusiveForNormalTimeline
	private JButton replicaDivergenceButton;
	
	public NetworkInteractionsAnalysisPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		addAggregateTimelineListButton(p);
		addDegreeButton(p);
		addNodeCentralityButton(p);
		addCreateCorrelationNetworkButton(p);
		addAggregateLifetimeButton(p);
		addAggregateAutocorrelationErrorButton(p);
		addEntropyButton(p);
		//addReplicaDivergenceButton(p);
				
		this.add(p);

		addDummyPanel();
	}
		
	private void addAggregateTimelineListButton(JPanel row){
		String buttonName = "Timeline";
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new TimelineListSelectionDialog(appGlobals);
				dialog.setVisible(true);
			}
			
		};
		
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		row.add(button);
		aggregateTimelineButton = button;
	}
					
	private void addAggregateLifetimeButton(JPanel row) {
		String buttonName = "Lifetime";
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new LifetimeDialog(appGlobals);
				dialog.setVisible(true);
			}
			
		};
				
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		row.add(button);
		aggregateLifetimeButton = button;
	}
	
	private void addAggregateAutocorrelationErrorButton(JPanel row) {
		String buttonName = "Weight error";
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new ErrorModeSelectionDialog(appGlobals);
				dialog.setVisible(true);
			}
			
		};
		
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		row.add(button);
		aggregateAutocorrelationErrorButton = button;
	}
	
	private void addCreateCorrelationNetworkButton(Container target){
		String buttonName = "Correlation";
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new CorrelationFactorsDialog(appGlobals);
				dialog.setVisible(true);
			}
			
		};
		
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		target.add(button);
		correlationFactorsButton = button;
	}
	
	private void addDegreeButton(Container target){
		JButton b = new JButton("Degree");
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WeightedDegreeDialog(appGlobals).setVisible(true);		
			}
			
		});
		target.add(b);
		degreeButton = b;
	}	
	
	private void addNodeCentralityButton(Container target){
		JButton b = new JButton("Centrality");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new WeightedNodeCentralityDialog(appGlobals)
				.setVisible(true);
			}
		});
		target.add(b);
		nodeCentralityButton = b;
	}
	
	private void addReplicaDivergenceButton(JPanel row) {
		String buttonName = "Divergence";
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new ReplicaDivergenceDialog(appGlobals);
				dialog.setVisible(true);
			}
			
		};
		
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		row.add(button);
		replicaDivergenceButton = button;
	}
	
	private void addEntropyButton(JPanel row) {
		String buttonName = "Entropy";
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new EntropyDialog(appGlobals);
				dialog.setVisible(true);
			}
		};
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		row.add(button);
		entropyButton = button;
	}
}
