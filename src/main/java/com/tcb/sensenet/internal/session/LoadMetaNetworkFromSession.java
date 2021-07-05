package com.tcb.sensenet.internal.session;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.cytoscape.session.events.SessionLoadedEvent;
import org.cytoscape.session.events.SessionLoadedListener;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.table.EdgeTableInitializer;
import com.tcb.sensenet.internal.init.table.NodeTableInitializer;
import com.tcb.sensenet.internal.init.table.RootEdgeTableInitializer;
import com.tcb.sensenet.internal.init.table.RootNetworkTableInitializer;
import com.tcb.sensenet.internal.init.table.RootNodeTableInitializer;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.serialization.MetaObjectInputStream;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettings;
import com.tcb.sensenet.internal.meta.timeline.ByteMetaTimelineImpl;
import com.tcb.sensenet.internal.meta.timeline.MetaTimeline;
import com.tcb.sensenet.internal.meta.timeline.MetaTimelineImpl;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.TimelineStore;
import com.tcb.sensenet.internal.meta.view.MetaNetworkView;
import com.tcb.sensenet.internal.meta.view.MetaNetworkViewFactory;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CySessionAdapter;
import com.tcb.cytoscape.cyLib.io.SplitFileInputStream;
import com.tcb.common.util.ListFilter;


public class LoadMetaNetworkFromSession implements SessionLoadedListener {
	
	private AppGlobals appGlobals;

	public LoadMetaNetworkFromSession(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public void handleEvent(SessionLoadedEvent e) {
		CySessionAdapter session = new CySessionAdapter(e.getLoadedSession());
		Map<String,List<File>> appFilesMap = e.getLoadedSession().getAppFileListMap();
		if(!appFilesMap.containsKey(CyActivator.APP_NAME)) return;

		List<File> appFiles = appFilesMap.get(CyActivator.APP_NAME);
		try {
				readNetworkData(session, appFiles);
		} catch(Exception ex){
				printReadMetaNetworkError(ex);
		}
		update_UI();
	}
	
	private void printReadMetaNetworkError(Exception e){
		System.out.println("Importing sensenet networks from session failed");
		e.printStackTrace();
	}
	
	
	private void readNetworkData(CySessionAdapter session, List<File> appFiles) 
					throws Exception {
		SplitFileInputStream fStream = new SplitFileInputStream(appFiles);
		MetaObjectInputStream oStream = new MetaObjectInputStream(fStream, session, appGlobals);
		
		readMetaNetworkSession(oStream);
		
		oStream.close();
		fStream.close();
	}
	
	private void readMetaNetworkSession(MetaObjectInputStream oStream) throws Exception {
		MetaNetworkSession session = (MetaNetworkSession) oStream.readObject();
		
		appGlobals.state.update(session.state);
		
		Collection<MetaNetwork> metaNetworks = appGlobals.state.metaNetworkManager.values();
	
		for(MetaNetwork metaNetwork:metaNetworks){
			CyRootNetworkAdapter rootNetwork = metaNetwork.getRootNetwork();
			initializeRootTables(rootNetwork);
			MetaNetworkView view = new MetaNetworkViewFactory(appGlobals.eventHelper,
					appGlobals.networkViewManager)
					.createMetaNetworkView(metaNetwork);
			appGlobals.metaNetworkViewManager.put(metaNetwork, view);
			updateNetworkSettings(metaNetwork);
			updateTimelineStore(metaNetwork);
		}
		
		Collection<CyNetworkAdapter> networks = appGlobals.networkManager.getNetworkSet();
		for(CyNetworkAdapter network:networks) {
			if(!appGlobals.state.metaNetworkManager.belongsToMetaNetwork(network))
				continue;
			initializeTables(network);
		}
		
		

	}
	
	private void initializeRootTables(CyRootNetworkAdapter rootNetwork) {
		new RootNodeTableInitializer(rootNetwork).init();
		new RootEdgeTableInitializer(rootNetwork).init();
		new RootNetworkTableInitializer(rootNetwork).init();
	}
	
	private void initializeTables(CyNetworkAdapter network) {
		new NodeTableInitializer(network).init();
		new EdgeTableInitializer(network).init();
	}
	
	private void updateNetworkSettings(MetaNetwork metaNetwork) {
		
	}
	
	@SuppressWarnings("deprecation")
	private void updateTimelineStore(MetaNetwork metaNetwork) {
		TimelineStore store = appGlobals.state.timelineManager.get(metaNetwork);
		Map<Long,MetaTimeline> m = store.getData();
		for(Long key:m.keySet()) {
			MetaTimeline value = m.get(key);
			if(value instanceof ByteMetaTimelineImpl) {
				value = convertByteMetatimeline((ByteMetaTimelineImpl) value);
				m.replace(key,value);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private MetaTimeline convertByteMetatimeline(ByteMetaTimelineImpl timeline) {
		byte[] data = timeline.getTimelineDeprecated();
		double[] result = new double[data.length];
		for(int i=0;i<data.length;i++) {
			result[i] = data[i];
		}
		return MetaTimelineImpl.create(result);
	}
		
	
	private void update_UI(){
		TaskIterator taskIterator = new TaskIterator();
		TaskIterator updateUI_Tasks = new UpdateUI_TaskFactory(appGlobals).createTaskIterator();
		taskIterator.append(updateUI_Tasks);
		appGlobals.taskManager.execute(taskIterator);
	}
	
}
