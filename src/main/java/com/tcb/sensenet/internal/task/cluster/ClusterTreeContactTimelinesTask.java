package com.tcb.sensenet.internal.task.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.cytoscape.model.CyEdge;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Cluster;
import com.tcb.cluster.ClusterTree;
import com.tcb.cluster.Clusterer;
import com.tcb.cluster.TreeClusterer;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterAnalysis;
import com.tcb.sensenet.internal.analysis.cluster.ClusteringMode;
import com.tcb.sensenet.internal.analysis.cluster.ClusteringSelecter;
import com.tcb.sensenet.internal.analysis.cluster.NestedListSerializer;
import com.tcb.sensenet.internal.analysis.matrix.ContactDistanceMatrixFactory;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.analysis.matrix.TimeFrameContactMatrixFactory;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogUtil;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.meta.timeline.factories.NetworkMetaTimelineFactory;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateAverageFrameWeightingTaskFactory;
import com.tcb.sensenet.internal.util.CancellableRunner;
import com.tcb.sensenet.internal.util.Sieve;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.cytoscape.cyLib.util.ProgressTicker;
import com.tcb.aifgen.importer.TimelineType;
import com.tcb.matrix.LabeledMatrix;
import com.tcb.matrix.TriangularMatrix;
import com.tcb.common.util.Combinatorics;
import com.tcb.common.util.Tuple;


public class ClusterTreeContactTimelinesTask extends AbstractTask {

	private volatile boolean cancelled = false;
	private TreeClusteringConfig config;
	private AppGlobals appGlobals;
		
	public ClusterTreeContactTimelinesTask(
			TreeClusteringConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
				
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);

		verifyState(metaNetwork);
				
		LogBuilder log = TaskLogUtil.createTaskLog(metaNetwork, config.getTaskLogType(), 
				appGlobals.state.logManager);

		TaskLogUtil.startTaskLog(log, config.getTaskLogType(), metaNetwork, network, config);
		
		verifyInput(metaNetwork);
		if(cancelled) return;
						
		taskMon.setStatusMessage("Creating contact matrices");
				
		Map<Integer,ContactMatrix> contactMatrices = 
				createContactMatrices(network, log);
				
		taskMon.setStatusMessage("Clustering");
				
		ClusterTree clusterTree = cluster(taskMon, contactMatrices);
		List<List<Cluster>> clusterings = getClusters(clusterTree);
		
		ClusteringSelecter selecter = config.getClusteringSelectionMethod()
				.getSelecter();
		List<Cluster> selectedCluster = selecter.select(clusterings);
		
		writeClusteringResults(clusterings,selectedCluster, metaNetwork);
		
		TaskLogUtil.finishTaskLog(log);
	}
	
	protected void verifyState(MetaNetwork metaNetwork){
		
	}
		
	protected ClusterTree cluster(TaskMonitor taskMonitor,
			Map<Integer,ContactMatrix> contactMatrices) throws Exception {
		LabeledMatrix<String> distances = getDistances(contactMatrices);
		
		TreeClusterer clusterer = config.getClustererFactory().create(distances);
					
		ClusterTree clusterTree = CancellableRunner.run(
				() -> clusterer.cluster(),
				() -> cancelled == true,
				() -> clusterer.cancel());
		
		return clusterTree;
	}
	
	protected LabeledMatrix<String> getDistances(Map<Integer,ContactMatrix> contactMatrices){
		ContactDistanceMatrixFactory fac = new ContactDistanceMatrixFactory();
		LabeledMatrix<String> distances = CancellableRunner.run(
				() -> fac.calcDistances(contactMatrices),
				() -> cancelled == true,
				() -> fac.cancel());
		return distances;
	}
	
	private List<List<Cluster>> getClusters(ClusterTree clusterTree){
		ImmutableList.Builder<List<Cluster>> result = ImmutableList.builder();
		for(int i=1;i<=clusterTree.getDataPointCount();i++){
			List<Cluster> clusters = clusterTree.getClusters(i);
			result.add(clusters);
		}
		return result.build();
	}
				
	protected void verifyInput(MetaNetwork metaNetwork){
		Integer sieve = config.getSieve();
		NullUtil.requireNonNull(sieve, "Sieve step");

		if(sieve < 1) throw new IllegalArgumentException("Sieve step must be >= 1");
		Integer timelineLength = metaNetwork.getHiddenDataRow().get(
				AppColumns.TIMELINE_LENGTH, Integer.class);
		if(sieve > timelineLength) 
			throw new IllegalArgumentException(
					"Sieve step must be smaller than timeline length");
	}
			
	protected Map<Integer,ContactMatrix> createContactMatrices(CyNetworkAdapter network, LogBuilder log){
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.get(network);
		
		TimelineType timelineType = metaNetwork.getTimelineType();
		NetworkMetaTimelineFactory metaTimelineFactory = appGlobals.metaTimelineFactoryManager
				.getFactory(config.getWeightMethod(),timelineType);
		
		List<CyEdge> edges = network.getEdgeList();
		if(edges.size()==0) throw new IllegalArgumentException("No edges present for clustering");
		
		log.write(String.format("Clustering %d edges.", edges.size()));
		
		Map<Integer,ContactMatrix> contactMatrices = new TimeFrameContactMatrixFactory(
				config.getSieve(), metaNetwork, metaTimelineFactory)
				.createTimelineMatrices(network);
		
		log.write(String.format("Clustering %d time frames.", contactMatrices.size()));
		log.writeEmptyLine();
		
		return contactMatrices;
	}
	
	protected void writeClusteringResults(
			List<List<Cluster>> clusters,
			List<Cluster> selectedClustering,
			MetaNetwork metaNetwork){
		appGlobals.state.treeClusteringStoreManager.putOrReplace(metaNetwork, clusters);
		appGlobals.state.clusteringStoreManager.putOrReplace(metaNetwork, selectedClustering);
		metaNetwork.getHiddenDataRow().set(AppColumns.CLUSTERING_MODE, ClusteringMode.TREE.name());
	}
				
	@Override
	public void cancel(){
		cancelled = true;
	}
	
}
