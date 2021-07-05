package com.tcb.sensenet.internal.structureViewer.events;

import org.cytoscape.event.CyEventHelper;

import com.tcb.netmap.structureViewer.StructureViewer;

public class StructureViewerEventRegistrar {
	
	public static void register(StructureViewer viewer, CyEventHelper eventHelper){
		new Thread(createProcessEndedEventFirer(viewer,eventHelper)).start();
	}
	
	private static Runnable createProcessEndedEventFirer(StructureViewer viewer, CyEventHelper eventHelper){
		return new Runnable(){
			@Override
			public void run() {
				while(viewer.isActive()){
					try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
				}
				eventHelper.fireEvent(
						new StructureViewerClosedEvent(viewer));				
			}
		};
	}
}
