package com.tcb.sensenet.internal.UI.panels.analysisPanel.diffusion;


import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.cytoscape.model.CyNode;

import com.tcb.sensenet.internal.UI.state.ExclusiveForNormalTimeline;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.path.PathSearcher;
import com.tcb.sensenet.internal.path.ShortestPathSearcher;
import com.tcb.sensenet.internal.selection.TwoNodeSelection;
import com.tcb.sensenet.internal.task.path.search.SearchPathsTaskConfig;
import com.tcb.sensenet.internal.task.path.search.factories.ShowPathsAnalysisTaskFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;


public class DiffusionAnalysisPanel extends DefaultPanel {

	private AppGlobals appGlobals;
	
	private JButton randomWalkButton;
	
	public DiffusionAnalysisPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
				
		addRandomWalkButton(p);
		
		this.add(p);
		
		addDummyPanel();
	}
	
	private void addRandomWalkButton(Container target){
		JButton b = new JButton("Random walk");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new RandomWalkDialog(appGlobals).setVisible(true);				
			}
		});
		randomWalkButton = b;
		target.add(b);
	}
				
}
