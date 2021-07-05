package com.tcb.sensenet.internal.structureViewer.events;

import com.tcb.sensenet.internal.app.AppGlobals;

public class ClearModelsWhenStructureViewerClosedListener implements StructureViewerClosedListener {

	private AppGlobals appGlobals;

	public ClearModelsWhenStructureViewerClosedListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void handleEvent(StructureViewerClosedEvent e) {
		appGlobals.structureViewerManager.getModels().reset();		
	}

}
