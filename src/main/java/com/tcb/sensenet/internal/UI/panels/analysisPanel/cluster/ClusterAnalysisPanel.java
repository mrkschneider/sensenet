package com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster;


import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.single.RunClusteringDialog;
import com.tcb.sensenet.internal.UI.panels.analysisPanel.cluster.tree.RunTreeClusteringDialog;
import com.tcb.sensenet.internal.UI.state.ExclusiveForNormalTimeline;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.plot.factories.ShowClusterAnalysisTaskFactory;


public class ClusterAnalysisPanel extends DefaultPanel {

	private AppGlobals appGlobals;
	@ExclusiveForNormalTimeline
	private JButton runSingleClusteringButton;
	@ExclusiveForNormalTimeline
	private JButton runTreeClusteringButton;
	@ExclusiveForNormalTimeline
	private JButton showResultsButton;
		
	
	public ClusterAnalysisPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
				
		addClusterTimelineButton(p);
		//addClusterTreeButton(p);
		//addClusterAnalysisPlotButton(p);
		
		this.add(p);
		addDummyPanel();
	}
		
	private void addClusterTimelineButton(JPanel row){
		String buttonName = "Cluster";
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new RunClusteringDialog(appGlobals);
				dialog.setVisible(true);
			}
		};
		
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		runSingleClusteringButton = button;
		row.add(button);
	}
	
	private void addClusterTreeButton(Container target){
		JButton b = new JButton("Tree clustering");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new RunTreeClusteringDialog(appGlobals);
				dialog.setVisible(true);
			}
		});
		target.add(b);
		runTreeClusteringButton = b;
	}
	
	private void addClusterAnalysisPlotButton(JPanel row){
		String buttonName = "Show results";
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TaskIterator plotTasks = 
						new ShowClusterAnalysisTaskFactory(appGlobals)
						.createTaskIterator();
				appGlobals.taskManager.execute(plotTasks);		
			}
		};
		
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		row.add(button);
		showResultsButton = button;
	}
	
	public JButton getRunSingleClusteringButton(){
		return runSingleClusteringButton;
	}
	
	public JButton getShowResultsButton(){
		return showResultsButton;
	}
	
	public JButton getRunTreeClusteringButton(){
		return runTreeClusteringButton;
	}
			
}
