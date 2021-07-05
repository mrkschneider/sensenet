package com.tcb.sensenet.internal.task.cli.diffusion;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EdgeCorrelationFactorsWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RandomWalkWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeNormalizationMode;
import com.tcb.sensenet.internal.analysis.diffusion.RandomWalkMode;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeMode;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityType;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceMode;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.CentralityNormalizationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesMode;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.CorrelationFactorsTaskFactory;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTaskConfig;
import com.tcb.sensenet.internal.task.degree.factories.WeightedDegreeTaskFactory;
import com.tcb.sensenet.internal.task.diffusion.RandomWalkTask;
import com.tcb.sensenet.internal.task.diffusion.factories.RandomWalkTaskFactory;
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



public class RandomWalkTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="random walk mode")
	public String randomWalkMode;
		
	@Tunable(description="source node column")
	public String sourceColumn;
	
	@Tunable(description="source node column value")
	public String sourceColumnValue;
	
	@Tunable(description="target node column")
	public String targetColumn;
	
	@Tunable(description="target node column value")
	public String targetColumnValue;
		
	@Tunable(description="weight column")
	public String weightColumn;
	
	@Tunable(description="max steps")
	public Integer maxSteps;
	
	@Tunable(description="restart probability")
	public Double restartProb = 0.0;
	
	@Tunable(description="number of runs")
	public Integer numRuns = 1;
	
	public RandomWalkTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(randomWalkMode, "randomWalkMode");
		NullUtil.requireNonNull(sourceColumn, "sourceColumn");
		NullUtil.requireNonNull(sourceColumnValue, "sourceColumnValue");
		NullUtil.requireNonNull(numRuns, "numRuns");
		
		TaskIterator it = new TaskIterator();
		
		RandomWalkMode randomWalkModeEnum = EnumUtil.valueOfCLI(randomWalkMode,
				RandomWalkMode.class);
			
		RowWriter rowWriter = new RandomWalkWriter();
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
	
		Long sourceSUID = getNode(network,sourceColumn,sourceColumnValue).getSUID();
		Long targetSUID = getNode(network,targetColumn,targetColumnValue).getSUID();
		
		RandomWalkTask.Config config = RandomWalkTask.Config.create(
				randomWalkModeEnum,
				sourceSUID,
				targetSUID,
				maxSteps,
				weightColumn,
				restartProb,
				numRuns, 
				rowWriter);
		
		it.append(
				new RandomWalkTaskFactory(appGlobals).createTaskIterator(config));
		return it;
	}
	
	private CyNode getNode(CyNetworkAdapter network, 
			String columnName, String columnValue){
		if(columnName==null) return null;
		return ListFilter.singleton(
				network.getNodeList().stream()
					.filter(n -> columnValue.equals(
							network.getRow(n).getRaw(columnName, String.class)
							))
					.collect(Collectors.toList()))
				.orElseThrow(
						() -> new IllegalArgumentException(
								String.format("'%s' not unique in '%s'",columnValue,columnName)));
	}

}
