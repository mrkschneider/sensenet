package com.tcb.sensenet.internal;

import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;
import org.cytoscape.app.event.AppsFinishedStartingListener;
import org.cytoscape.application.CyApplicationManager;
import org.cytoscape.application.events.CyShutdownListener;
import org.cytoscape.application.events.SetSelectedNetworkViewsListener;
import org.cytoscape.application.events.SetSelectedNetworksListener;
import org.cytoscape.application.swing.CyAction;
import org.cytoscape.application.swing.CySwingApplication;
import org.cytoscape.application.swing.CytoPanelComponent;
import org.cytoscape.event.CyEventHelper;
import org.cytoscape.model.CyNetworkFactory;
import org.cytoscape.model.CyNetworkManager;
import org.cytoscape.model.CyTableFactory;
import org.cytoscape.model.CyTableManager;
import org.cytoscape.model.events.AboutToRemoveEdgesListener;
import org.cytoscape.model.events.AboutToRemoveNodesListener;
import org.cytoscape.model.events.NetworkAboutToBeDestroyedListener;
import org.cytoscape.model.events.NetworkAddedListener;
import org.cytoscape.model.events.NetworkDestroyedListener;
import org.cytoscape.model.events.RowsSetListener;
import org.cytoscape.model.subnetwork.CyRootNetworkManager;
import org.cytoscape.property.CyProperty;
import org.cytoscape.service.util.AbstractCyActivator;
import org.cytoscape.session.events.SessionAboutToBeSavedListener;
import org.cytoscape.session.events.SessionLoadedListener;
import org.cytoscape.session.events.SessionSavedListener;
import org.cytoscape.task.NodeViewTaskFactory;
import org.cytoscape.task.visualize.ApplyPreferredLayoutTaskFactory;
import org.cytoscape.task.write.ExportTableTaskFactory;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.view.model.CyNetworkViewFactory;
import org.cytoscape.view.model.CyNetworkViewManager;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.work.ServiceProperties;
import org.cytoscape.work.SynchronousTaskManager;
import org.cytoscape.work.TaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskManager;
import org.osgi.framework.BundleContext;

import com.tcb.sensenet.internal.UI.panels.analysisPanel.state.AnalysisPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.appPanel.AppPanel;
import com.tcb.sensenet.internal.UI.panels.appPanel.StartAppMenuItem;
import com.tcb.sensenet.internal.UI.panels.appPanel.StopAppMenuItem;
import com.tcb.sensenet.internal.UI.panels.appPanel.state.AppPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.appSettingsPanel.AboutMenuItem;
import com.tcb.sensenet.internal.UI.panels.appSettingsPanel.AppSettingsMenuItem;
import com.tcb.sensenet.internal.UI.panels.appSettingsPanel.ResetDefaultSettingsMenuItem;
import com.tcb.sensenet.internal.UI.panels.resultPanel.ResultPanel;
import com.tcb.sensenet.internal.UI.panels.resultPanel.state.ResultPanelManager;
import com.tcb.sensenet.internal.UI.panels.showInteractionsPanel.state.ShowInteractionsPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.state.ViewerPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.structureViewerPanel.state.ViewerStatusPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.UpdateWeightsWhenFrameSelectedListener;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.AggregationModeControlsPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.AggregationModeSelectionPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.SelectedClusterPanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.SelectedFramePanelStateManager;
import com.tcb.sensenet.internal.UI.panels.weightPanel.state.WeightPanelStateManager;
import com.tcb.sensenet.internal.UI.state.Update_UI_onNetworkChanges;
import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;
import com.tcb.sensenet.internal.analysis.cluster.ClusteringStoreManager;
import com.tcb.sensenet.internal.analysis.cluster.TreeClusteringStoreManager;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.app.AppPersistentState;
import com.tcb.sensenet.internal.app.AppsFinishedStartingReporter;
import com.tcb.sensenet.internal.cleanup.NetworkDeletedListener;
import com.tcb.sensenet.internal.events.FrameSetListener;
import com.tcb.sensenet.internal.events.NodeGroupAboutToCollapseListener;
import com.tcb.sensenet.internal.events.NodeGroupCollapsedListener;
import com.tcb.sensenet.internal.integrationTest.IntegrationTestsTaskFactory;
import com.tcb.sensenet.internal.integrationTest.SetTestDirectoryTaskFactory;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.difference.reference.WriteDifferenceMetaNetworkTestRefsTaskFactory;
import com.tcb.sensenet.internal.integrationTest.test.metanetworks.normal.reference.WriteNormalMetaNetworkTestRefsTaskFactory;
import com.tcb.sensenet.internal.layout.NodePositionStoreManager;
import com.tcb.sensenet.internal.layout.PlaceNodesWhenGroupCollapsesOrExpands;
import com.tcb.sensenet.internal.layout.SaveHeadNodePositionWhenGroupExpands;
import com.tcb.sensenet.internal.log.TaskLogManager;
import com.tcb.sensenet.internal.meta.network.MetaNetworkManager;
import com.tcb.sensenet.internal.meta.settings.MetaNetworkSettingsManager;
import com.tcb.sensenet.internal.meta.timeline.TimelineManager;
import com.tcb.sensenet.internal.meta.timeline.factories.MetaTimelineFactoryManager;
import com.tcb.sensenet.internal.meta.view.MetaNetworkViewFactory;
import com.tcb.sensenet.internal.meta.view.MetaNetworkViewManager;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.properties.PropertyReader;
import com.tcb.sensenet.internal.properties.events.SaveUIStateBeforeShutdown;
import com.tcb.sensenet.internal.selection.UpdateSelectionTimeListener;
import com.tcb.sensenet.internal.session.ClearTempFilesWhenSessionSaved;
import com.tcb.sensenet.internal.session.LoadMetaNetworkFromSession;
import com.tcb.sensenet.internal.session.SaveMetaNetworkToSession;
import com.tcb.sensenet.internal.structureViewer.StructureViewerManager;
import com.tcb.sensenet.internal.structureViewer.events.ClearModelsWhenStructureViewerClosedListener;
import com.tcb.sensenet.internal.structureViewer.events.StructureViewerClosedListener;
import com.tcb.sensenet.internal.structureViewer.events.UpdateStructureViewUponFrameSetListener;
import com.tcb.sensenet.internal.structureViewer.events.UpdateStructureViewUponNodeEdgeDeletionListener;
import com.tcb.sensenet.internal.structureViewer.events.UpdateStructureViewUponSelectionListener;
import com.tcb.sensenet.internal.task.UI.factories.ApplyPreferredLayoutTaskAdapterFactory;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.cli.correlation.factories.CorrelationFactorsTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.create.factories.ImportAifTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.create.factories.ImportAmberHbondsTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.create.factories.ImportAmberNativeContactsTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.create.factories.ImportPdbContactsTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.create.factories.ImportPdbHbondsTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.degree.factories.WeightedDegreeTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.diffusion.factories.RandomWalkTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.divergence.factories.ReplicaDivergenceTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.entropy.factories.EntropyTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.export.factories.ExportEdgeTableTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.export.factories.ExportLogTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.export.factories.ExportNodeTableTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.labeling.factories.ResetResidueIndicesTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.labeling.factories.SetCustomNodeLabelsTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.labeling.factories.SetCustomResidueIndicesTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.path.centrality.factories.WeightedNodeCentralityTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.select.factories.SelectResiduesTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.table.factories.ImportCsvTableTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.view.factories.SetActiveInteractionTypesTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.weighting.factories.ActivateClusterWeightingTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.weighting.factories.ActivateTimelineWeightingTaskFactoryCLI;
import com.tcb.sensenet.internal.task.cli.weighting.factories.ActivateTimepointWeightingTaskFactoryCLI;
import com.tcb.sensenet.internal.task.export.table.factories.ExportTableTaskFactoryAdapter;
import com.tcb.sensenet.internal.task.layout.ExpandOrCollapseNodeOnDoubleClickTaskFactory;
import com.tcb.sensenet.internal.util.UIStateManagers;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyApplicationManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkFactoryAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewFactoryAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkViewManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkManagerAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableFactoryAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableManagerAdapter;
import com.tcb.cytoscape.cyLib.task.cli.CLITaskFactory;
import com.tcb.cytoscape.cyLib.util.BundleUtil;
import com.tcb.cytoscape.cyLib.util.TempUtil;

public class CyActivator extends AbstractCyActivator {
	
	public static final String APP_NAME = "tcb_SenseNet";
	public static final String APP_NAME_SHORT = "SenseNet";
	public static final String APP_NAMESPACE = "sensenet";
	public static final String APP_TEST_NAMESPACE = "sensenet-test";
	public static final String APP_COLUMN_PREFIX = "sen/";
	
	private AppProperties appProperties;
	
	@Override
	public void start(BundleContext bc) throws Exception {
		printVersion();
		
		appProperties = createOrGetProperties(bc);
				
		Boolean shouldInit = getShouldInit();
		
		if(shouldInit){
			init(bc);
			addStopAppMenu(bc);
		} else {
			addStartAppMenu(bc);
		}
				
	}
	
	private void printVersion(){
		String version = getClass().getPackage().getImplementationVersion();
		if(version!=null){
			System.out.println(String.format(
					"Starting SenseNet version %s", version));
		} else {
			System.out.println(String.format(
					"Starting SenseNet"));
		}
	}
		
	private AppProperties createOrGetProperties(BundleContext bc){
		if(appProperties!=null) return appProperties;
		PropertyReader.register(bc);
		@SuppressWarnings("unchecked")
		CyProperty<Properties> cyAppProperties = getService(bc, CyProperty.class, 
				String.format("(cyPropertyName=%s)",PropertyReader.PROPERTIES_NAME));
		return new AppProperties(cyAppProperties.getProperties());
	}
	
	public void addStartAppMenu(BundleContext bc){
		registerService(bc, new StartAppMenuItem(this, bc), CyAction.class, new Properties());
	}
	
	public void addStopAppMenu(BundleContext bc){
		registerService(bc, new StopAppMenuItem(this,bc), CyAction.class, new Properties());
	}
	
	public Boolean getShouldInit(){
		return Boolean.valueOf(
				appProperties.getOrDefault(AppProperty.AUTOSTART_APP));
	}
	
	public void setShouldInit(Boolean value){
		appProperties.set(AppProperty.AUTOSTART_APP, value.toString());
	}
	
	
	private void checkOS(){
		/*if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX){
			SingletonErrorDialog.showNonBlocking(
					new RuntimeException(
							String.format(
									"Warning: %s was not tested with Mac OSX",
									APP_NAME_SHORT)));
		}*/
	}
		
	public void init(BundleContext bc) throws Exception {
		checkOS();
				
		CyEventHelper eventHelper = getService(bc,CyEventHelper.class);
		
		MetaNetworkViewManager metaNetworkViewManager = new MetaNetworkViewManager();
		VisualMappingManager visMapManager = getService(bc,VisualMappingManager.class);
		CyNetworkManagerAdapter cyNtMgr = new CyNetworkManagerAdapter(getService(bc,CyNetworkManager.class));
		CyTableManagerAdapter cyTableManager = new CyTableManagerAdapter(getService(bc,CyTableManager.class));
		CyApplicationManagerAdapter cyAppManager = new CyApplicationManagerAdapter(getService(bc, CyApplicationManager.class));
		CyNetworkViewManagerAdapter cyNetworkViewManager = new CyNetworkViewManagerAdapter(getService(bc,CyNetworkViewManager.class));
		CyNetworkViewFactoryAdapter cyNetworkViewFactory = new CyNetworkViewFactoryAdapter(getService(bc, CyNetworkViewFactory.class));
		CyRootNetworkManagerAdapter cyRootNetworkManager = new CyRootNetworkManagerAdapter(getService(bc, CyRootNetworkManager.class));
		MetaNetworkManager metaNetworkManager = new MetaNetworkManager(cyAppManager,cyRootNetworkManager);
		ResultPanelManager resultPanelManager = new ResultPanelManager();
		NodePositionStoreManager nodePositionManager = new NodePositionStoreManager();
		ClusteringStoreManager clusteringStoreManager = new ClusteringStoreManager();
		TreeClusteringStoreManager treeClusteringStoreManager = new TreeClusteringStoreManager();
		TaskManager tskMgr = getService(bc,TaskManager.class);
		SynchronousTaskManager synTaskManager = getService(bc,SynchronousTaskManager.class);
		TempUtil tempUtil = new TempUtil(APP_NAME + "_session");
		
		CySwingApplication swingManager = getService(bc, CySwingApplication.class);
		MetaNetworkSettingsManager networkSettingsManager = new MetaNetworkSettingsManager();
		TimelineManager  timelineManager = new TimelineManager();
		StructureViewerManager structureViewerManager = new StructureViewerManager();
		
		WeightPanelStateManager weightPanelStateManager = new WeightPanelStateManager(metaNetworkManager);
		MetaTimelineFactoryManager metaTimelineFactoryManager = new MetaTimelineFactoryManager(timelineManager);
		AggregationModeSelectionPanelStateManager timepointAggregationPanelStateManager = new AggregationModeSelectionPanelStateManager(metaNetworkManager);
		ShowInteractionsPanelStateManager showInteractionsPanelStateManager = new ShowInteractionsPanelStateManager(metaNetworkManager);
		SelectedFramePanelStateManager selectedFramePanelStateManager = new SelectedFramePanelStateManager(metaNetworkManager);
		AggregationModeControlsPanelStateManager aggregationModeControlPanelStateManager = new AggregationModeControlsPanelStateManager(metaNetworkManager);
		SelectedClusterPanelStateManager selectedClusterPanelStateManager = new SelectedClusterPanelStateManager(metaNetworkManager, clusteringStoreManager);
		ViewerPanelStateManager viewerPanelStateManager = new ViewerPanelStateManager(metaNetworkManager,structureViewerManager);
		ViewerStatusPanelStateManager viewerStatusPanelStateManager = new ViewerStatusPanelStateManager(metaNetworkManager, structureViewerManager);
		AppPanelStateManager appPanelStateManager = new AppPanelStateManager(metaNetworkManager);
		AnalysisPanelStateManager analysisPanelStateManager = new AnalysisPanelStateManager(metaNetworkManager);
		TaskLogManager logManager = new TaskLogManager();

		
		BundleUtil bundleUtil = new BundleUtil(bc);	
		
		MetaNetworkViewFactory metaNetworkViewFactory =  new MetaNetworkViewFactory(eventHelper, cyNetworkViewManager);
		CyNetworkFactoryAdapter cyNetworkFactory = new CyNetworkFactoryAdapter(getService(bc,CyNetworkFactory.class));
		CyTableFactoryAdapter cyTblF = new CyTableFactoryAdapter(getService(bc, CyTableFactory.class));
		VisualStyleFactory visualStyleFactoryServiceRef = getService(bc,VisualStyleFactory.class);
		VisualMappingFunctionFactory vmfFactoryC = getService(bc,VisualMappingFunctionFactory.class, "(mapping.type=continuous)");
		VisualMappingFunctionFactory vmfFactoryD = getService(bc,VisualMappingFunctionFactory.class, "(mapping.type=discrete)");
		VisualMappingFunctionFactory vmfFactoryP = getService(bc,VisualMappingFunctionFactory.class, "(mapping.type=passthrough)");
		ApplyPreferredLayoutTaskAdapterFactory applyPreferredLayoutTaskFactory = new ApplyPreferredLayoutTaskAdapterFactory(
				getService(bc,ApplyPreferredLayoutTaskFactory.class),cyAppManager,cyNetworkViewManager, tskMgr);
		ExportTableTaskFactoryAdapter exportTableTaskFactory = new ExportTableTaskFactoryAdapter(
				getService(bc,ExportTableTaskFactory.class));
		
		FileUtil fileUtil = getService(bc,FileUtil.class);
	
		AppPersistentState appState = new AppPersistentState(
				metaNetworkManager, networkSettingsManager, nodePositionManager,
				timelineManager, logManager, clusteringStoreManager, treeClusteringStoreManager);
		
		UIStateManagers stateManagers = new UIStateManagers(
				weightPanelStateManager,
				timepointAggregationPanelStateManager,
				showInteractionsPanelStateManager,
				selectedFramePanelStateManager,
				selectedClusterPanelStateManager,
				aggregationModeControlPanelStateManager,
				viewerPanelStateManager,
				viewerStatusPanelStateManager,
				appPanelStateManager,
				analysisPanelStateManager);
		AppGlobals appGlobals = new AppGlobals(
				appState, stateManagers,
				cyAppManager, cyRootNetworkManager,
				tskMgr, synTaskManager,
				resultPanelManager, eventHelper,
				cyTableManager, cyNetworkViewManager,
				visMapManager, cyNtMgr,
				fileUtil, swingManager,
				metaTimelineFactoryManager, structureViewerManager,
				bundleUtil, appProperties,
				visualStyleFactoryServiceRef,
				vmfFactoryP, vmfFactoryC, vmfFactoryD,
				cyTblF, cyNetworkViewFactory,
				cyNetworkFactory, applyPreferredLayoutTaskFactory,
				exportTableTaskFactory,
				metaNetworkViewManager,
				metaNetworkViewFactory
				);
				
		Update_UI_onNetworkChanges appStartOrNetworkChangedListener = new Update_UI_onNetworkChanges(appGlobals);
		SessionAboutToBeSavedListener saveMetaNetworkToSession = new SaveMetaNetworkToSession(appGlobals, tempUtil);
		SessionSavedListener clearTempFilesWhenSessionSaved = new ClearTempFilesWhenSessionSaved(tempUtil);
		SessionLoadedListener loadMetaNetworkFromSession = new LoadMetaNetworkFromSession(appGlobals);
		NetworkDeletedListener networkDeletedListener = new NetworkDeletedListener(appGlobals);
		RowsSetListener updateStructureViewUponSelectionListener = new UpdateStructureViewUponSelectionListener(appGlobals);
		RowsSetListener updateSelectionTimeListener = new UpdateSelectionTimeListener(appGlobals);
		CyShutdownListener saveUIStateBeforeShutdownListener = new SaveUIStateBeforeShutdown(appGlobals);
		FrameSetListener updateWeightsWhenFrameSelectedListener = new UpdateWeightsWhenFrameSelectedListener(appGlobals);
		FrameSetListener updateStructureViewWhenFrameSetListener = new UpdateStructureViewUponFrameSetListener(appGlobals);
		StructureViewerClosedListener clearModelsWhenStructureViewerClosedListener = new ClearModelsWhenStructureViewerClosedListener(appGlobals);
		AboutToRemoveEdgesListener hideResiduesAndInteractionsWhenEdgesDeletedListener = new UpdateStructureViewUponNodeEdgeDeletionListener(appGlobals); 
		AppsFinishedStartingListener appsFinishedStartingReporter = new AppsFinishedStartingReporter();
		
		registerService(bc,appStartOrNetworkChangedListener,NetworkAddedListener.class,new Properties());
		registerService(bc,appStartOrNetworkChangedListener,NetworkDestroyedListener.class,new Properties());
		registerService(bc,appStartOrNetworkChangedListener,SetSelectedNetworksListener.class,new Properties());
		registerService(bc,appStartOrNetworkChangedListener,SetSelectedNetworkViewsListener.class,new Properties());
		registerService(bc,appStartOrNetworkChangedListener,AppsFinishedStartingListener.class, new Properties());
		registerService(bc,appStartOrNetworkChangedListener,StructureViewerClosedListener.class, new Properties());
		registerService(bc,saveMetaNetworkToSession,SessionAboutToBeSavedListener.class, new Properties());
		registerService(bc,clearTempFilesWhenSessionSaved,SessionSavedListener.class, new Properties());
		registerService(bc,loadMetaNetworkFromSession,SessionLoadedListener.class,new Properties());
		registerService(bc,networkDeletedListener,NetworkAboutToBeDestroyedListener.class,new Properties());
		registerService(bc,updateStructureViewUponSelectionListener,RowsSetListener.class,new Properties());
		registerService(bc,updateSelectionTimeListener,RowsSetListener.class,new Properties());
		registerService(bc,saveUIStateBeforeShutdownListener,CyShutdownListener.class,new Properties());
		registerService(bc,updateWeightsWhenFrameSelectedListener,FrameSetListener.class,new Properties());
		registerService(bc,updateStructureViewWhenFrameSetListener,FrameSetListener.class,new Properties());
		registerService(bc,clearModelsWhenStructureViewerClosedListener,StructureViewerClosedListener.class, new Properties());
		registerService(bc,hideResiduesAndInteractionsWhenEdgesDeletedListener,AboutToRemoveEdgesListener.class);
		registerService(bc,hideResiduesAndInteractionsWhenEdgesDeletedListener,AboutToRemoveNodesListener.class);
		registerService(bc,appsFinishedStartingReporter,AppsFinishedStartingListener.class);
		
		NodeGroupCollapsedListener metaNodeCollapsedListener = new PlaceNodesWhenGroupCollapsesOrExpands(appGlobals);
		NodeGroupAboutToCollapseListener metaNodeAboutToCollapseListener = new SaveHeadNodePositionWhenGroupExpands(appGlobals);
		
		registerService(bc,metaNodeCollapsedListener,NodeGroupCollapsedListener.class, new Properties());
		registerService(bc,metaNodeAboutToCollapseListener,NodeGroupAboutToCollapseListener.class,new Properties());
						
		AppPanel myPanel = new AppPanel(appGlobals);
		registerService(bc,myPanel,CytoPanelComponent.class, new Properties());
		ResultPanel resultPanel = new ResultPanel(appGlobals);
		resultPanelManager.addActivationHook(
				() -> registerService(bc,resultPanel,CytoPanelComponent.class, new Properties()));
		AppSettingsMenuItem appSettingsMenu = new AppSettingsMenuItem(appGlobals);
		registerService(bc,appSettingsMenu,CyAction.class, new Properties());
		ResetDefaultSettingsMenuItem resetDefaultSettingsMenu = new ResetDefaultSettingsMenuItem(appGlobals);
		registerService(bc,resetDefaultSettingsMenu,CyAction.class, new Properties());
		AboutMenuItem aboutMenu = new AboutMenuItem(appGlobals);
		registerService(bc,aboutMenu,CyAction.class, new Properties());
		
		registerService(bc,metaNetworkManager,MetaNetworkManager.class,new Properties());
		
		
		
		
		NodeViewTaskFactory doubleClickListener = new ExpandOrCollapseNodeOnDoubleClickTaskFactory(appGlobals);
		Properties doubleClickProperties = new Properties();
		doubleClickProperties.setProperty(ServiceProperties.PREFERRED_ACTION, "OPEN");
		doubleClickProperties.setProperty(ServiceProperties.TITLE, "Expand/Collapse Group");
		registerService(bc, doubleClickListener, NodeViewTaskFactory.class, doubleClickProperties);
		
		registerTestFactories(bc,appGlobals);
		registerCLITaskFactories(bc, appGlobals);
		
		TaskIterator updateUI_Tasks = new UpdateUI_TaskFactory(appGlobals).createTaskIterator();
		tskMgr.execute(updateUI_Tasks);
	}
	
	private void registerTestFactories(BundleContext bc, AppGlobals appGlobals){
		String testsOn = appGlobals.appProperties.getOrDefault(AppProperty.TESTS_ON);
		if(Boolean.parseBoolean(testsOn) == false) return;
		
		WriteNormalMetaNetworkTestRefsTaskFactory writeNormalIntegrationTestRefsTaskFactory = new WriteNormalMetaNetworkTestRefsTaskFactory(bc.getBundle(), appGlobals);
		registerService(bc,writeNormalIntegrationTestRefsTaskFactory, TaskFactory.class, WriteNormalMetaNetworkTestRefsTaskFactory.getProperties());
		WriteDifferenceMetaNetworkTestRefsTaskFactory writeDifferenceIntegrationTestRefsTaskFactory = new WriteDifferenceMetaNetworkTestRefsTaskFactory(bc.getBundle(), appGlobals);
		registerService(bc,writeDifferenceIntegrationTestRefsTaskFactory, TaskFactory.class, WriteDifferenceMetaNetworkTestRefsTaskFactory.getProperties());
		
		IntegrationTestsTaskFactory integrationTestsFactory = new IntegrationTestsTaskFactory(bc.getBundle(), appGlobals);
		registerService(bc,integrationTestsFactory,TaskFactory.class, IntegrationTestsTaskFactory.getProperties());
		
		SetTestDirectoryTaskFactory setTestDirectoryTaskFactory = new SetTestDirectoryTaskFactory(appGlobals);
		registerService(bc,setTestDirectoryTaskFactory,TaskFactory.class, SetTestDirectoryTaskFactory.getProperties());
	}
		
	private void registerCLITaskFactories(BundleContext bc, 
			AppGlobals appGlobals){
		CLITaskFactory cliImportMatTaskFactory = 
				new ImportAifTaskFactoryCLI(appGlobals);
		CLITaskFactory cliImportAmberHbondsTaskFactory = 
				new ImportAmberHbondsTaskFactoryCLI(appGlobals);
		CLITaskFactory cliImportAmberContactsTaskFactory = 
				new ImportAmberNativeContactsTaskFactoryCLI(appGlobals);
		CLITaskFactory cliImportPdbHbondsTaskFactory = 
				new ImportPdbHbondsTaskFactoryCLI(appGlobals);
		CLITaskFactory cliImportPdbContactsTaskFactory = 
				new ImportPdbContactsTaskFactoryCLI(appGlobals);
		CLITaskFactory cliActivateClusterWeightingTaskFactory = 
				new ActivateClusterWeightingTaskFactoryCLI(appGlobals);
		CLITaskFactory cliActivateTimelineWeightingTaskFactory = 
				new ActivateTimelineWeightingTaskFactoryCLI(appGlobals);
		CLITaskFactory cliActivateTimepointWeightingTaskFactory = 
				new ActivateTimepointWeightingTaskFactoryCLI(appGlobals);
		CLITaskFactory cliSetCustomNodeLabelsTaskFactory =
				new SetCustomNodeLabelsTaskFactoryCLI(appGlobals);
		//CLITaskFactory cliSetStandardNodeLabelsTaskFactory =
		//		new ResetResidueIndicesTaskFactoryCLI(appGlobals);
		CLITaskFactory cliSetCustomResidueIndicesTaskFactory =
				new SetCustomResidueIndicesTaskFactoryCLI(appGlobals);
		CLITaskFactory cliExportNodeTableTaskFactory =
				new ExportNodeTableTaskFactoryCLI(appGlobals);
		CLITaskFactory cliExportEdgeTableTaskFactory =
				new ExportEdgeTableTaskFactoryCLI(appGlobals);
		CLITaskFactory cliExportLogTaskFactory =
				new ExportLogTaskFactoryCLI(appGlobals);
		CLITaskFactory cliWeightedNodeCentralityTaskFactory =
				new WeightedNodeCentralityTaskFactoryCLI(appGlobals);
		CLITaskFactory cliCorrelationFactorsTaskFactory =
				new CorrelationFactorsTaskFactoryCLI(appGlobals);
		CLITaskFactory cliWeightedDegreeTaskFactory =
				new WeightedDegreeTaskFactoryCLI(appGlobals);
		CLITaskFactory cliSetActiveInteractionTypesTaskFactory =
				new SetActiveInteractionTypesTaskFactoryCLI(appGlobals);
		CLITaskFactory cliSelectResiduesTaskFactory =
				new SelectResiduesTaskFactoryCLI(appGlobals);
		CLITaskFactory cliRandomWalkTaskFactory = 
				new RandomWalkTaskFactoryCLI(appGlobals);
		CLITaskFactory cliImportCsvTableTaskFactory =
				new ImportCsvTableTaskFactoryCLI(appGlobals);
		CLITaskFactory cliReplicaDivergenceTaskFactory =
				 new ReplicaDivergenceTaskFactoryCLI(appGlobals);
		CLITaskFactory cliEntropyTaskFactory =
				new EntropyTaskFactoryCLI(appGlobals);
		
		registerCLITaskFactory(bc, cliImportMatTaskFactory);
		registerCLITaskFactory(bc, cliImportAmberHbondsTaskFactory);
		registerCLITaskFactory(bc, cliImportAmberContactsTaskFactory);
		registerCLITaskFactory(bc, cliImportPdbHbondsTaskFactory);
		registerCLITaskFactory(bc, cliImportPdbContactsTaskFactory);
		registerCLITaskFactory(bc, cliActivateClusterWeightingTaskFactory);
		registerCLITaskFactory(bc, cliActivateTimelineWeightingTaskFactory);
		registerCLITaskFactory(bc, cliActivateTimepointWeightingTaskFactory);
		registerCLITaskFactory(bc, cliSetCustomNodeLabelsTaskFactory);
		//registerCLITaskFactory(bc, cliSetStandardNodeLabelsTaskFactory);
		registerCLITaskFactory(bc, cliSetCustomResidueIndicesTaskFactory);
		registerCLITaskFactory(bc, cliExportNodeTableTaskFactory);
		registerCLITaskFactory(bc, cliExportEdgeTableTaskFactory);
		registerCLITaskFactory(bc, cliExportLogTaskFactory);
		registerCLITaskFactory(bc, cliWeightedNodeCentralityTaskFactory);
		registerCLITaskFactory(bc, cliCorrelationFactorsTaskFactory);
		registerCLITaskFactory(bc, cliWeightedDegreeTaskFactory);
		registerCLITaskFactory(bc, cliSetActiveInteractionTypesTaskFactory);
		registerCLITaskFactory(bc, cliSelectResiduesTaskFactory);
		//registerCLITaskFactory(bc, cliRandomWalkTaskFactory);
		registerCLITaskFactory(bc, cliImportCsvTableTaskFactory);
		//registerCLITaskFactory(bc, cliReplicaDivergenceTaskFactory);
		registerCLITaskFactory(bc, cliEntropyTaskFactory);
	}
	
	private void registerCLITaskFactory(BundleContext bc, CLITaskFactory fac){
		registerAllServices(bc, fac,fac.getProperties());
	}
	
	
	
}
