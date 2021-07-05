package com.tcb.sensenet.internal.app;

import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskManager;

import com.tcb.sensenet.internal.UI.panels.resultPanel.state.ResultPanelManager;
import com.tcb.sensenet.internal.layout.NodePositionStoreManager;
import com.tcb.sensenet.internal.log.TaskLogManager;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettingsManager;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.meta.view.MetaNetworkViewFactory;
import com.tcb.sensenet.internal.meta.view.MetaNetworkViewManager;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.task.UI.factories.ApplyPreferredLayoutTaskAdapterFactory;
import com.tcb.sensenet.internal.task.export.table.factories.ExportTableTaskFactoryAdapter;
import com.tcb.sensenet.internal.util.UIStateManagers;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkFactoryAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewFactoryAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableFactoryAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableManagerAdapter;
import com.tcb.cytoscape.cyLib.util.BundleUtil;
import com.tcb.cytoscape.cyLib.util.TempUtil;

public class AppGlobals {
		
	public CyApplicationManagerAdapter applicationManager;
	public CyRootNetworkManagerAdapter rootNetworkManager;
	public TaskManager taskManager;
	public SynchronousTaskManager synTaskManager;
	public ResultPanelManager resultPanelManager;
	public CyEventHelper eventHelper;
	public CyTableManagerAdapter tableManager;
	public CyNetworkViewManagerAdapter networkViewManager;
	public VisualMappingManager visualMappingManager;
	public CyNetworkManagerAdapter networkManager;
	public FileUtil fileUtil;
	public CySwingApplication swingManager;
	public MetaTimelineFactoryManager metaTimelineFactoryManager;
	public BundleUtil bundleUtil;
	public AppProperties appProperties;
	public UIStateManagers stateManagers;
	public StructureViewerManager structureViewerManager;
	public MetaNetworkViewManager metaNetworkViewManager;
	public VisualStyleFactory visualStyleFactory;
	public VisualMappingFunctionFactory visualMapFunctionFacPassthrough;
	public VisualMappingFunctionFactory visualMapFunctionFacContinuous;
	public VisualMappingFunctionFactory visualMapFunctionFacDiscrete;
	public MetaNetworkViewFactory metaNetworkViewFactory;
	public CyTableFactoryAdapter tableFactory;
	public CyNetworkViewFactoryAdapter networkViewFactory;
	public CyNetworkFactoryAdapter networkFactory;
	public ApplyPreferredLayoutTaskAdapterFactory applyPreferredLayoutTaskAdapterFactory;
	public AppPersistentState state;
	public ExportTableTaskFactoryAdapter exportTableTaskFactory;

	public AppGlobals(
			AppPersistentState state,
			UIStateManagers uiStateManagers,
			CyApplicationManagerAdapter cyAppMgr,
			CyRootNetworkManagerAdapter rootNetworkManager,
            TaskManager tskMgr,
			SynchronousTaskManager synTaskManager,
			ResultPanelManager resultPanelManager,
			CyEventHelper eventHelper, CyTableManagerAdapter cyTableManager, 
			CyNetworkViewManagerAdapter cyNtViewMgr,
			VisualMappingManager visMgr, CyNetworkManagerAdapter cyNtMgr,
			FileUtil fileUtil, CySwingApplication swingManager, 
			MetaTimelineFactoryManager cachingMetaTimelineFactoryManager,
			StructureViewerManager structureViewerManager,
			BundleUtil bundleUtil,
			AppProperties appProperties,
			VisualStyleFactory visualStyleFac, VisualMappingFunctionFactory visualMapFunctionFacPassthrough,
			VisualMappingFunctionFactory visualMapFunctionFacContinuous,
			VisualMappingFunctionFactory visualMapFunctionFacDiscrete,
			CyTableFactoryAdapter cyTableFactory,
			CyNetworkViewFactoryAdapter cyNtViewF,
			CyNetworkFactoryAdapter cyNetworkFactory,
			ApplyPreferredLayoutTaskAdapterFactory applyPreferredLayoutTaskAdapterFactory,
			ExportTableTaskFactoryAdapter exportTableTaskFactory,
			MetaNetworkViewManager metaNetworkViewManager,
			MetaNetworkViewFactory metaNetworkViewFactory
			) {
		super();
		this.state = state;
		this.stateManagers = uiStateManagers;
		this.applicationManager = cyAppMgr;
		this.rootNetworkManager = rootNetworkManager;
		this.taskManager = tskMgr;
		this.synTaskManager = synTaskManager;
		this.resultPanelManager = resultPanelManager;
		this.eventHelper = eventHelper;
		this.tableManager = cyTableManager;
		this.networkViewManager = cyNtViewMgr;
		this.visualMappingManager = visMgr;
		this.networkManager = cyNtMgr;
		this.fileUtil = fileUtil;
		this.swingManager = swingManager;
		this.metaTimelineFactoryManager = cachingMetaTimelineFactoryManager;
		this.structureViewerManager = structureViewerManager;
		this.bundleUtil = bundleUtil;
		this.appProperties = appProperties;
		this.visualStyleFactory = visualStyleFac;
		this.visualMapFunctionFacPassthrough = visualMapFunctionFacPassthrough;
		this.visualMapFunctionFacContinuous = visualMapFunctionFacContinuous;
		this.visualMapFunctionFacDiscrete = visualMapFunctionFacDiscrete;
		this.metaNetworkViewFactory = metaNetworkViewFactory;
		this.tableFactory = cyTableFactory;
		this.networkViewFactory = cyNtViewF;
		this.networkFactory = cyNetworkFactory;
		this.metaNetworkViewManager = metaNetworkViewManager;
		this.applyPreferredLayoutTaskAdapterFactory = applyPreferredLayoutTaskAdapterFactory;
		this.exportTableTaskFactory = exportTableTaskFactory;
	}
	
}
