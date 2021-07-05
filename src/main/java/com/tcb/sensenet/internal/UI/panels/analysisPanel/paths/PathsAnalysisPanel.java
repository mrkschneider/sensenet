package com.tcb.sensenet.internal.UI.panels.analysisPanel.paths;


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


public class PathsAnalysisPanel extends DefaultPanel {

	private AppGlobals appGlobals;

		
	public PathsAnalysisPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		addSearchShortestPathsButton(p);
		addSearchFixedLengthPathsButton(p);
		
		
		this.add(p);
		
		addDummyPanel();
	}
		
	private void addSearchShortestPathsButton(Container target){
		String buttonName = "Shortest paths";
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
					CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
					TwoNodeSelection selection = TwoNodeSelection.create(network,rootNetwork);
					CyNode source = selection.getFirst();
					CyNode target = selection.getSecond();
					PathSearcher searcher = new ShortestPathSearcher(network);
					SearchPathsTaskConfig config = 
							SearchPathsTaskConfig.create(
									source,target,network,searcher);
					ShowPathsAnalysisTaskFactory fac = 
							new ShowPathsAnalysisTaskFactory(appGlobals);
					appGlobals.taskManager.execute(fac.createTaskIterator(config));
				} catch(Exception ex){
					SingletonErrorDialog.show(ex);
					ex.printStackTrace();
				}
				
			}
		};
		
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		target.add(button);
	}
	
	private void addSearchFixedLengthPathsButton(Container target){
		String buttonName = "Suboptimal paths";
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new SearchFixedLengthPathsDialog(appGlobals)
					.setVisible(true);
			}
		};
		
		JButton button = new JButton(buttonName);
		button.addActionListener(listener);
		target.add(button);
	}
	
	
			
}
