package com.tcb.sensenet.internal.log.select;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.log.LogBuilder;

public class GlobalLogSelecter implements LogSelecter {

	private AppGlobals appGlobals;

	public GlobalLogSelecter(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public LogBuilder getLog() {
		return appGlobals.state.logManager.getGlobalLog();
	}

}
