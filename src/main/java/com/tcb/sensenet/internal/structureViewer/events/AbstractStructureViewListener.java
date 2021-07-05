package com.tcb.sensenet.internal.structureViewer.events;

import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;

public abstract class AbstractStructureViewListener {

	private AppGlobals appGlobals;

	public AbstractStructureViewListener(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	protected Boolean modelIsActiveInViewer(MetaNetwork metaNetwork){
		return appGlobals.structureViewerManager.hasActiveViewer()
				&& appGlobals.structureViewerManager.getModels().containsKey(metaNetwork);
	}
}
