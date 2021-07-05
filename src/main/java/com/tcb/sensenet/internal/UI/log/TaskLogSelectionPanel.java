package com.tcb.sensenet.internal.UI.log;

import javax.swing.JComboBox;

import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.LogStore;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.log.select.LogSelecter;
import com.tcb.sensenet.internal.log.select.TaskLogSelecter;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.log.LogBuilder;


public class TaskLogSelectionPanel extends AbstractLogSelectionPanel {

	private AppGlobals appGlobals;
	private JComboBox<TaskLogType> logTypeBox;
	
	
	public TaskLogSelectionPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		TaskLogType defaultLogType = TaskLogType.valueOf(
				appGlobals.appProperties.getOrDefault(AppProperty.TASK_LOG_VIEW_TYPE_DEFAULT));
		
		logTypeBox = 
				p.addChoosableParameter("Log type", TaskLogType.values(), defaultLogType);
		
		this.add(p);
	}
	
	@Override
	public LogBuilder getLog() {
		TaskLogType logType = (TaskLogType) logTypeBox.getSelectedItem();
		
		appGlobals.appProperties.set(AppProperty.TASK_LOG_VIEW_TYPE_DEFAULT, logType.name());
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		
		LogSelecter selecter = new TaskLogSelecter(logType,metaNetwork,appGlobals);
				
		return selecter.getLog();
	}
}
