package com.tcb.sensenet.internal.app;

import org.cytoscape.app.event.AppsFinishedStartingEvent;
import org.cytoscape.app.event.AppsFinishedStartingListener;

import com.tcb.sensenet.internal.CyActivator;

public class AppsFinishedStartingReporter implements AppsFinishedStartingListener {

	@Override
	public void handleEvent(AppsFinishedStartingEvent e) {
		System.out.println(CyActivator.APP_NAME_SHORT + ": Ready");		
	}

}
