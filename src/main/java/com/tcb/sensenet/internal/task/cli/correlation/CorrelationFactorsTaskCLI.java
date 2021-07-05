package com.tcb.sensenet.internal.task.cli.correlation;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EdgeCorrelationFactorsWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.map.edge.EdgeMappingMethod;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityType;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceMode;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.CentralityNormalizationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationMode;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.CorrelationFactorsTaskFactory;
import com.tcb.sensenet.internal.task.export.table.ExportNodeTableTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;
import com.tcb.sensenet.internal.task.path.centrality.factories.WeightedNodeCentralityTaskFactory;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;
import com.tcb.common.util.ListFilter;



public class CorrelationFactorsTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="correlation method")
	public String correlationMethod;
	
	@Tunable(description="frame weight")
	public String weightMethod;
	
	@Tunable(description="reference network name")
	public String refNetworkName;
	
	@Tunable(description="edge mapping method")
	public String mapMethod;
		
	public CorrelationFactorsTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(correlationMethod, "correlationMethod");
		NullUtil.requireNonNull(weightMethod, "weightMethod");
		
		TaskIterator it = new TaskIterator();
				
		EdgeCorrelationMethod correlationMethodEnum = EnumUtil.valueOfCLI(correlationMethod,
				EdgeCorrelationMethod.class);
		FrameWeightMethod weightMethodEnum = EnumUtil.valueOfCLI(weightMethod, 
				FrameWeightMethod.class);
		Optional<EdgeMappingMethod> edgeMapMethodEnum = getEdgeMappingMethod(); 
		Optional<MetaNetwork> refMetaNetwork = getRefMetaNetwork();
		
		RowWriter edgeTableWriter = new EdgeCorrelationFactorsWriter();
		
		CorrelationFactorsTaskConfig config = CorrelationFactorsTaskConfig.create(
				correlationMethodEnum, weightMethodEnum, refMetaNetwork, edgeMapMethodEnum, edgeTableWriter);
		it.append(
				new CorrelationFactorsTaskFactory(appGlobals).createTaskIterator(config));
		return it;
	}
	
	private Optional<MetaNetwork> getRefMetaNetwork(){
		if(refNetworkName==null) return Optional.empty();
		MetaNetwork refNetwork = ListFilter.singleton(
				appGlobals.state.metaNetworkManager.values().stream()
				.filter(n -> refNetworkName.equals(n.getName()))
				.collect(ImmutableList.toImmutableList())).get();
		return Optional.of(refNetwork);
	}
	
	private Optional<EdgeMappingMethod> getEdgeMappingMethod(){
		if(mapMethod==null) return Optional.empty();
		return Optional.of(
				EnumUtil.valueOfCLI(mapMethod, EdgeMappingMethod.class));
	}

}
