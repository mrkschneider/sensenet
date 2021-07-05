package com.tcb.sensenet.internal.task.cli.table;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;
import org.cytoscape.task.TableTaskFactory;
import org.cytoscape.work.TaskIterator;
import org.cytoscape.work.Tunable;

import com.tcb.sensenet.internal.UI.table.TableType;
import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EdgeCorrelationFactorsWriter;
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
import com.tcb.sensenet.internal.task.table.ImportCsvTableTask;
import com.tcb.sensenet.internal.task.table.factories.ImportCsvTableTaskFactory;
import com.tcb.sensenet.internal.util.EnumUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;
import com.tcb.cytoscape.cyLib.util.NullUtil;
import com.tcb.aifgen.importer.InteractionImporter;
import com.tcb.aifgen.importer.aifImporter.AifImporter;
import com.tcb.common.util.ListFilter;



public class ImportCsvTableTaskCLI extends AbstractWrappedTask {
	
	@Tunable(description="csv path")
	public String csvPath;
		
	@Tunable(description="key column")
	public String keyColumn;
	
	@Tunable(description="table type")
	public String tableType;
		
	public ImportCsvTableTaskCLI(AppGlobals appGlobals) {
		super(appGlobals);
	}
	
	@Override
	public TaskIterator createWrappedTasks() {
		NullUtil.requireNonNull(csvPath, "csvPath");
		NullUtil.requireNonNull(keyColumn, "keyColumn");
		
		TaskIterator it = new TaskIterator();
		
		TableType tableTypeEnum = EnumUtil.valueOfCLI(tableType,
				TableType.class);
				
		ImportCsvTableTask.Config config = ImportCsvTableTask.Config.create(
				csvPath,keyColumn,tableTypeEnum);
		
		it.append(
				new ImportCsvTableTaskFactory(appGlobals).createTaskIterator(config));
		return it;
	}	

}
