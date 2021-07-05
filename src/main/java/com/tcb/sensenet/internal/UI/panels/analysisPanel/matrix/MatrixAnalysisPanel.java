package com.tcb.sensenet.internal.UI.panels.analysisPanel.matrix;


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


public class MatrixAnalysisPanel extends DefaultPanel {

	private AppGlobals appGlobals;
	
	private JButton showMatrixButton;
	private JButton exportMatrixButton;
		
	
	public MatrixAnalysisPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
				
		addShowMatrixButton(p);
		addExportMatrixButton(p);
		
		this.add(p);
		
		addDummyPanel();
	}
	
	private void addShowMatrixButton(Container target){
		JButton b = new JButton("Show");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new ShowNetworkMatrixDialog(appGlobals).setVisible(true);				
			}
		});
		showMatrixButton = b;
		target.add(b);
	}
	
	private void addExportMatrixButton(Container target){
		JButton b = new JButton("Export");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new ExportNetworkMatrixDialog(appGlobals).setVisible(true);				
			}
		});
		exportMatrixButton = b;
		target.add(b);
	}
		
	
			
}
