package com.tcb.sensenet.internal.task.cli.degree;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.aggregation.aggregators.table.DegreeWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EdgeCorrelationFactorsWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeNormalizationMode;
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
import com.tcb.sensenet.internal.task.export.table.ExportNodeTableTask;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;
import com.tcb.sensenet.internal.task.path.centrality.factories.WeightedNodeCentralityTaskFactory;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;



public class WeightedDegreeTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="degree method")
	public String degreeMethod;
	
	@Tunable(description="weight column")
	public String weightColumnName;
	
	@Tunable(description="negative weight mode")
	public String negativeWeightMode;
	
	@Tunable(description="normalization mode")
	public String normalizationMode = WeightedDegreeNormalizationMode.NONE.name();
	
	public WeightedDegreeTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(degreeMethod, "degreeMethod");
		NullUtil.requireNonNull(weightColumnName, "weightColumnName");
		NullUtil.requireNonNull(negativeWeightMode, "negativeWeightMode");
				
		TaskIterator it = new TaskIterator();
				
		WeightedDegreeMode weightedDegreeEnum = EnumUtil.valueOfCLI(degreeMethod,
				WeightedDegreeMode.class);
		NegativeValuesMode negativeWeightModeEnum = EnumUtil.valueOfCLI(negativeWeightMode, 
				NegativeValuesMode.class);
		WeightedDegreeNormalizationMode normalizationModeEnum = EnumUtil.valueOfCLI(normalizationMode,
				WeightedDegreeNormalizationMode.class);
		
		RowWriter rowWriter = new DegreeWriter();
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		
		WeightedDegreeTaskConfig config = WeightedDegreeTaskConfig.create(
				network, weightColumnName, rowWriter, weightedDegreeEnum, negativeWeightModeEnum, 
				normalizationModeEnum
				);
		
		it.append(
				new WeightedDegreeTaskFactory(appGlobals).createTaskIterator(config));
		return it;
	}

}
