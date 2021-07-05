package com.tcb.sensenet.internal.structureViewer.events;

import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.events.FrameSetEvent;
import com.tcb.sensenet.internal.events.FrameSetListener;
import com.tcb.sensenet.internal.events.FrameSetRecord;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.structureViewer.StructureModel;
import com.tcb.netmap.structureViewer.StructureViewer;

public class UpdateStructureViewUponFrameSetListener extends AbstractStructureViewListener 
implements FrameSetListener {

	private AppGlobals appGlobals;

	public UpdateStructureViewUponFrameSetListener(AppGlobals appGlobals){
		super(appGlobals);
		this.appGlobals = appGlobals;
	}
	
	@Override
	public synchronized void handleEvent(FrameSetEvent e) {
		FrameSetRecord record = e.getSource();
		MetaNetwork metaNetwork = record.getMetaNetwork();
		if(!modelIsActiveInViewer(metaNetwork)) return;
		
		Integer frame = record.getFrame();
		StructureViewer viewer = appGlobals.structureViewerManager.getViewer();
		
		StructureModel model = appGlobals.structureViewerManager.getModels().get(metaNetwork);
		try{
			model.setFrame(viewer, frame);
		} catch(Exception ex){
			SingletonErrorDialog.showNonBlocking(ex);
			ex.printStackTrace();
		}
		
	}

}
