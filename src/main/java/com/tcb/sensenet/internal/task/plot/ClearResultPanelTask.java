package com.tcb.sensenet.internal.task.plot;

import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.app.AppGlobals;

public class ClearResultPanelTask extends AbstractTask {
	private AppGlobals appGlobals;

	public ClearResultPanelTask(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void run(TaskMonitor taskMon) throws Exception {
		ResultPanel resultPanel = appGlobals.resultPanelManager.getResultPanel();
		resultPanel.clear();
	}
	
	
}
