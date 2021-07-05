package com.tcb.sensenet.internal.task.cli.path.centrality;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.NodeCentralityWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityType;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceMode;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.CentralityNormalizationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesMode;
import com.tcb.sensenet.internal.task.cli.AbstractWrappedTask;
import com.tcb.sensenet.internal.task.export.table.ExportNodeTableTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;
import com.tcb.sensenet.internal.task.path.centrality.factories.WeightedNodeCentralityTaskFactory;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;



public class WeightedNodeCentralityTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="weight column")
	public String weightColumnName;
	
	@Tunable(description="centrality Type")
	public String centralityType;
	
	@Tunable(description="multiple edge weight mode")
	public String multiEdgeWeightMode;
	
	@Tunable(description="distance mode")
	public  String distanceMode;
	
	@Tunable(description="normalization mode")
	public  String normalizationMode;
	
	@Tunable(description="negative weight mode")
	public String negativeWeightMode;
	
	public WeightedNodeCentralityTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(centralityType, "centralityType");
		NullUtil.requireNonNull(multiEdgeWeightMode, "multiEdgeWeightMode");
		NullUtil.requireNonNull(distanceMode, "distanceMode");
		NullUtil.requireNonNull(normalizationMode, "normalizationMode");
		NullUtil.requireNonNull(negativeWeightMode, "negativeWeightMode");
		
		TaskIterator it = new TaskIterator();
		
		NodeCentralityType centralityTypeEnum = EnumUtil.valueOfCLI(centralityType, NodeCentralityType.class);
		WeightAccumulationMode nodeWeightModeEnum = EnumUtil.valueOfCLI(multiEdgeWeightMode, WeightAccumulationMode.class);
		EdgeDistanceMode distanceModeEnum = EnumUtil.valueOfCLI(distanceMode, EdgeDistanceMode.class);
		CentralityNormalizationMode normalizationModeEnum = EnumUtil.valueOfCLI(normalizationMode, CentralityNormalizationMode.class);
		NegativeValuesMode negativeWeightModeEnum = EnumUtil.valueOfCLI(negativeWeightMode, NegativeValuesMode.class);
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		RowWriter tableWriter = new NodeCentralityWriter();
		
		WeightedNodeCentralityTaskConfig config = WeightedNodeCentralityTaskConfig.create(
				network, tableWriter,
				Optional.ofNullable(weightColumnName), centralityTypeEnum,
				nodeWeightModeEnum, distanceModeEnum, normalizationModeEnum, negativeWeightModeEnum);
		it.append(
				new WeightedNodeCentralityTaskFactory(appGlobals).createTaskIterator(config));
		return it;
	}

}
