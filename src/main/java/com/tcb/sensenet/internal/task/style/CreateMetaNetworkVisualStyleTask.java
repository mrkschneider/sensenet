package com.tcb.sensenet.internal.task.style;

import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.style.DifferenceMetaNetworkVisualStyleFactory;
import com.tcb.sensenet.internal.style.MetaNetworkVisualStyleFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.TimelineType;


public class CreateMetaNetworkVisualStyleTask extends AbstractTask {
	
	private static final String nodeName = AppColumns.LABEL.toString();

	private AppGlobals appGlobals;
	
	public CreateMetaNetworkVisualStyleTask(
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor tskMon) throws Exception {
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);
		TimelineType timelineType = TimelineType.valueOf(
				metaNetwork.getHiddenDataRow().get(DefaultColumns.TYPE, String.class));
		VisualStyle hBondVisStyle = createVisStyle(timelineType,metaNetwork);		
		appGlobals.visualMappingManager.setCurrentVisualStyle(hBondVisStyle);
		for(CyNetworkView view: appGlobals.networkViewManager.getNetworkViews(network)){
			appGlobals.visualMappingManager.setVisualStyle(hBondVisStyle, view);
		}
	}
	
	private MetaNetworkVisualStyleFactory createStyleFactory(
			TimelineType timelineType,MetaNetwork metaNetwork){
		switch(timelineType){
			case TIMELINE: return new MetaNetworkVisualStyleFactory(nodeName, 
				metaNetwork,
				appGlobals.visualStyleFactory, 
				appGlobals.eventHelper,
				appGlobals.visualMapFunctionFacPassthrough, appGlobals.visualMapFunctionFacContinuous,
				appGlobals.visualMapFunctionFacDiscrete);
			case DIFFERENCE_TIMELINE: return new DifferenceMetaNetworkVisualStyleFactory(nodeName,
				metaNetwork,
				appGlobals.visualStyleFactory, 
				appGlobals.eventHelper,
				appGlobals.visualMapFunctionFacPassthrough, appGlobals.visualMapFunctionFacContinuous,
				appGlobals.visualMapFunctionFacDiscrete);
			default: throw new RuntimeException("Unknown TimelineType");
		}
	}
	
	private VisualStyle createVisStyle(TimelineType timelineType, MetaNetwork metaNetwork){
		MetaNetworkVisualStyleFactory styleFactory = createStyleFactory(timelineType, metaNetwork);
		VisualStyle hBondVisStyle = styleFactory.create();
		appGlobals.visualMappingManager.addVisualStyle(hBondVisStyle);
		return hBondVisStyle;
	}
		
}
