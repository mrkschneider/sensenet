package com.tcb.sensenet.internal.task.create;

import java.util.Map;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.tcb.atoms.atoms.Atom;
import com.tcb.atoms.interactions.Interaction;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.init.Initializer;
import com.tcb.sensenet.internal.init.groups.GroupsInitializer;
import com.tcb.sensenet.internal.init.network.NetworkInitializer;
import com.tcb.sensenet.internal.init.row.RootNetworkHiddenRowInitializer;
import com.tcb.sensenet.internal.init.row.RootNetworkSharedRowInitializer;
import com.tcb.sensenet.internal.init.row.NetworkSharedRowInitializer;
import com.tcb.sensenet.internal.init.table.EdgeTableInitializer;
import com.tcb.sensenet.internal.init.table.RootNetworkTableInitializer;
import com.tcb.sensenet.internal.init.table.NodeTableInitializer;
import com.tcb.sensenet.internal.init.table.RootEdgeTableInitializer;
import com.tcb.sensenet.internal.init.table.RootNodeTableInitializer;
import com.tcb.sensenet.internal.init.timeline.TimelineStoreFactory;
import com.tcb.sensenet.internal.log.LogStore;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.network.MetaNetworkFactory;
import com.tcb.sensenet.internal.meta.network.MetaNetworkFactoryImpl;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettings;
import com.tcb.sensenet.internal.meta.timeline.TimelineStore;
import com.tcb.sensenet.internal.meta.view.MetaNetworkView;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.aifgen.importer.InteractionImportData;


public class CreateMetaNetworkTask extends AbstractTask {

	private AppGlobals appGlobals;
	private ImportConfig config;
	
	public CreateMetaNetworkTask(
			ImportConfig config,
			AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.config = config;
	}

	public void run(TaskMonitor tskMon) throws Exception {
		tskMon.setStatusMessage("Importing data...");
		
		InteractionImportData importData = config.getInteractionImporter().read();
		
		tskMon.setStatusMessage("Creating network...");
				
		CyNetworkAdapter network = createNetwork();
		CyRootNetworkAdapter rootNetwork = 
				appGlobals.rootNetworkManager.getRootNetwork(network);
		
		tskMon.setStatusMessage("Writing data to tables...");
		
		initializeTables(config, importData, network,rootNetwork);
				
		BaseNetworkData baseNetworkData = 
				initializeBaseNetwork(importData,network,rootNetwork);
		initializeGroups(importData,network,rootNetwork);
				
		tskMon.setStatusMessage("Creating network tree...");
		
		MetaNetworkFactory metaNetworkFactory = new MetaNetworkFactoryImpl(rootNetwork);
		MetaNetwork metaNetwork = metaNetworkFactory.create();
		
		MetaNetworkView metaNetworkView = 
				appGlobals.metaNetworkViewFactory.createMetaNetworkView(metaNetwork);
		
		appGlobals.state.metaNetworkManager.put(network,metaNetwork);
		appGlobals.metaNetworkViewManager.put(metaNetwork, metaNetworkView);
		appGlobals.state.logManager.put(metaNetwork, new LogStore());
		appGlobals.state.networkSettingsManager.put(metaNetwork,
				createMetaNetworkSettings(config));
		initTimelines(metaNetwork, baseNetworkData);
		
		LogBuilder log = TaskLogUtil.createTaskLog(
				metaNetwork, config.getTaskLogType(), appGlobals.state.logManager);
		TaskLogUtil.startTaskLog(log, config.getTaskLogType(), metaNetwork, network, config, false);

		TaskLogUtil.finishTaskLog(log);
		}
	
	private MetaNetworkSettings createMetaNetworkSettings(ImportConfig config) {
		MetaNetworkSettings s = new MetaNetworkSettings();
		return s;
	}
	
	private void initTimelines(MetaNetwork metaNetwork, BaseNetworkData baseNetworkData){
		TimelineStoreFactory fac = new TimelineStoreFactory();
		TimelineStore timelineStore = fac.create(baseNetworkData.edges, false);
		appGlobals.state.timelineManager.put(metaNetwork, timelineStore);
	}
		
	private CyNetworkAdapter createNetwork(){
		CyNetworkAdapter network = appGlobals.networkFactory.createNetwork();
		appGlobals.networkManager.addNetwork(network);
		return network;
	}
	
	private BaseNetworkData initializeBaseNetwork(
			InteractionImportData importData,
			CyNetworkAdapter network,
			CyRootNetworkAdapter rootNetwork){

		NetworkInitializer networkInitializer = 
				new NetworkInitializer(
						rootNetwork, network,
						importData, config.getNodeGroupDefinition());
		networkInitializer.init();
		return new BaseNetworkData(
				networkInitializer.getNodes(),
				networkInitializer.getEdges());
	}
	
	private void initializeGroups(
			InteractionImportData importData,
			CyNetworkAdapter network,
			CyRootNetworkAdapter rootNetwork){
		Initializer groupsInitializer = new GroupsInitializer(
				config.getNodeGroupDefinition(),
				rootNetwork,network,
				importData);
		groupsInitializer.init();
	}
		
	private void initializeTables(
			ImportConfig importConfig,
			InteractionImportData importData,
			CyNetworkAdapter network,
			CyRootNetworkAdapter rootNetwork){
		Initializer rootNodeTableInitializer = new RootNodeTableInitializer(rootNetwork);
		Initializer nodeTableInitializer = new NodeTableInitializer(network);
		Initializer edgeTableInitializer = new EdgeTableInitializer(network);
		Initializer rootEdgeTableInitializer = new RootEdgeTableInitializer(rootNetwork);
		Initializer networkTableInitializer = new RootNetworkTableInitializer(
				rootNetwork);
		Initializer metaNetworkHiddenRowInitializer =
				new RootNetworkHiddenRowInitializer(importConfig, importData, rootNetwork);
		Initializer metaNetworkSharedRowInitializer =
				new RootNetworkSharedRowInitializer(importConfig, rootNetwork);
		Initializer networkSharedRowInitializer =
				new NetworkSharedRowInitializer(importConfig, network);

		// Order is important!
		rootNodeTableInitializer.init();
		nodeTableInitializer.init();
		rootEdgeTableInitializer.init();
		edgeTableInitializer.init();
		
		networkTableInitializer.init();
		
		metaNetworkSharedRowInitializer.init();
		metaNetworkHiddenRowInitializer.init();
		networkSharedRowInitializer.init();
	}
	
	private void initializeNetworkRows(ImportConfig importConfig, InteractionImportData importData) {
		
	}
	
	private class BaseNetworkData {
		//public Map<Atom,CyNode> nodes;
		public Map<Interaction,CyEdge> edges;
		
		public BaseNetworkData(Map<Atom,CyNode> nodes, Map<Interaction,CyEdge> edges){
			//this.nodes = nodes;
			this.edges = edges;
		}
	}
	
}

	

			
		
	


