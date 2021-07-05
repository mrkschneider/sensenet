package com.tcb.sensenet.internal.task.select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.TaskMonitor;

import com.google.common.collect.ImmutableList;
import com.tcb.cluster.Cluster;
import com.tcb.cluster.ClusterTree;
import com.tcb.cluster.Clusterer;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.TimelineWeightMethod;
import com.tcb.sensenet.internal.analysis.cluster.ClusterAnalysis;
import com.tcb.sensenet.internal.analysis.cluster.ClusteringMode;
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
import com.tcb.sensenet.internal.util.IntMapUtil;
import com.tcb.sensenet.internal.util.Sieve;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.cytoscape.cyLib.log.LogBuilder;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.cytoscape.cyLib.util.ProgressTicker;
import com.tcb.aifgen.importer.TimelineType;
import com.tcb.matrix.LabeledMatrix;
import com.tcb.matrix.LabeledSquareMatrixImpl;
import com.tcb.matrix.TriangularMatrix;
import com.tcb.common.util.Combinatorics;
import com.tcb.common.util.Tuple;


public class SelectResiduesTask extends AbstractTask {

	private volatile boolean cancelled = false;
	private SelectResiduesTaskConfig config;
	private AppGlobals appGlobals;
		
	public SelectResiduesTask(
			SelectResiduesTaskConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMon) throws Exception {
			
		CyNetworkAdapter network = appGlobals.applicationManager.getCurrentNetwork();
		
		List<CyNode> nodes = network.getNodeList();
		List<CyRowAdapter> rows = nodes.stream()
				.map(n -> network.getRow(n))
				.collect(ImmutableList.toImmutableList());
		
		rows.forEach(r -> r.set(DefaultColumns.SELECTED, false));
				
		List<CyRowAdapter> selectedRows = rows.stream()
				.filter(r -> shouldSelect(r,AppColumns.RESINDEX,
						config.getResIndices(),Integer.class))
				.filter(r -> shouldSelect(r,AppColumns.RESINDEX_LABEL,
						config.getLabelResIndices(),Integer.class))
				.filter(r -> shouldSelect(r,AppColumns.RESINSERT,
						config.getResInserts(),String.class))
				.filter(r -> shouldSelect(r,AppColumns.RESINSERT_LABEL,
						config.getLabelResInserts(),String.class))
				.collect(ImmutableList.toImmutableList());
		
		selectedRows.forEach(r -> r.set(DefaultColumns.SELECTED, true));
								
	}
	
	private <T> Boolean shouldSelect(
			CyRowAdapter row,
			Columns column, 
			Set<T> set,
			Class<T> type){
		if(set==null) return true;
		T v = row.getMaybe(column, type).orElse(null);
		return set.contains(v);				
	}
			
}
