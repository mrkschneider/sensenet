package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.panels.weightPanel.EdgeCutoffPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.view.factories.ChangeEdgeCutoffTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.data.Columns;

public class ActionSetEdgeCutoffListener implements ActionListener,ItemListener {

	private AppGlobals appGlobals;
	private EdgeCutoffPanel edgeCutoffPanel;

	public ActionSetEdgeCutoffListener(
			EdgeCutoffPanel edgeCutoffPanel,
			AppGlobals appGlobals
			){
		this.edgeCutoffPanel = edgeCutoffPanel;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try{
			run();
		} catch(Exception ex){
			DialogUtil.showErrorDialog(ex.getMessage());
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()==ItemEvent.SELECTED){
			run();
		}
	}
	
	private void run(){
		Double cutoff = edgeCutoffPanel.getTimeFractionCutoff();
		Columns cutoffWeightColumn = edgeCutoffPanel.getCutoffWeightColumn();

		TaskIterator it = new TaskIterator();
		it.append(
				new ChangeEdgeCutoffTaskFactory(
						cutoff, cutoffWeightColumn, appGlobals)
				.createTaskIterator());
		appGlobals.taskManager.execute(it);
	}

	
	
}
