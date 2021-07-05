package com.tcb.sensenet.internal.UI.panels.stylePanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.task.labeling.factories.ResetResidueNumberingTaskFactory;

public class ActionResetResidueNumberingListener implements ActionListener {
	private AppGlobals appGlobals;

	public ActionResetResidueNumberingListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		TaskManager taskManager = appGlobals.taskManager;
		TaskIterator tasks = new TaskIterator();
		tasks.append(new ResetResidueNumberingTaskFactory(appGlobals).createTaskIterator());
		taskManager.execute(tasks);
	}

}
