package com.tcb.sensenet.internal.UI.log;

import javax.swing.JComboBox;

import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.log.select.GlobalLogSelecter;
import com.tcb.sensenet.internal.log.select.LogSelecter;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.cytoscape.cyLib.log.LogBuilder;


public class GlobalLogSelectionPanel extends AbstractLogSelectionPanel {

	private AppGlobals appGlobals;
	
	public GlobalLogSelectionPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
	}
	
	@Override
	public LogBuilder getLog() {
		LogSelecter selecter = new GlobalLogSelecter(appGlobals);
		return selecter.getLog();
	}
}
