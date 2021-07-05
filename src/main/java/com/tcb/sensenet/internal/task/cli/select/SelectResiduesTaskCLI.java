package com.tcb.sensenet.internal.task.cli.select;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.tcb.sensenet.internal.task.select.SelectResiduesTaskConfig;
import com.tcb.sensenet.internal.task.select.factories.SelectResiduesTaskFactory;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;
import com.tcb.common.util.ListFilter;



public class SelectResiduesTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="residue indices (',' separated)")
	public String resIndices;
	
	@Tunable(description="residue label indices (',' separated)")
	public String resLabelIndices;
	
	@Tunable(description="residue inserts (',' separated)")
	public String resInserts;
	
	@Tunable(description="residue label inserts (',' separated)")
	public String resLabelInserts;
			
	public SelectResiduesTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
				
		TaskIterator it = new TaskIterator();
		
		SelectResiduesTaskConfig config = new SelectResiduesTaskConfig(
				setOrNull(parseInputFields(resIndices)),
				setOrNull(parseInputFields(resLabelIndices)),
				setOrNull(splitInputFields(resInserts)),
				setOrNull(splitInputFields(resLabelInserts)));
				
		it.append(
				new SelectResiduesTaskFactory(config,appGlobals).createTaskIterator());
		return it;
	}
	
	private List<String> splitInputFields(String s){
		if(s==null) return null;
		return Arrays.asList(s.split(","));
	}
	
	private List<Integer> parseInputFields(String str){
		List<String> inputFields = splitInputFields(str);
		if(inputFields==null) return null;
		return inputFields.stream()
				.map(s -> Integer.parseInt(s))
				.collect(ImmutableList.toImmutableList());
	}
	
	private <T> Set<T> setOrNull(Collection<T> coll){
		if(coll==null) return null;
		return new HashSet<>(coll);
	}
	

}
