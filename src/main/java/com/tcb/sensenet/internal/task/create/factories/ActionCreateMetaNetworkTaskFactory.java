package com.tcb.sensenet.internal.task.create.factories;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.TimelineWeightAnalysis;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.TimelineWeightAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.AverageWeightWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.init.ImportConfig;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.task.UI.factories.UpdateUI_TaskFactory;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregateTask;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.task.factoryInterfaces.ImportTaskFactory;
import com.tcb.sensenet.internal.task.labeling.factories.ResetResidueNumberingTaskFactory;
import com.tcb.sensenet.internal.task.meta.factories.CollapseMetaNetworkTaskFactory;
import com.tcb.sensenet.internal.task.protectNetwork.ProtectNetworkTaskFactory;
import com.tcb.sensenet.internal.task.style.factories.CreateMetaNetworkVisualStyleTaskFactory;
import com.tcb.sensenet.internal.task.style.factories.UpdateVisualStyleTaskFactory;
import com.tcb.sensenet.internal.task.view.factories.UpdateActiveEdgesTaskFactory;
import com.tcb.sensenet.internal.task.weighting.factories.ActivateAverageFrameWeightingTaskFactory;
import com.tcb.sensenet.internal.util.ObjMap;

public class ActionCreateMetaNetworkTaskFactory implements ImportTaskFactory {
	
	private AppGlobals appGlobals;

	public ActionCreateMetaNetworkTaskFactory(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}

	@Override
	public TaskIterator createTaskIterator(ImportConfig config) {
		TaskIterator updateVisStyleTasks = new UpdateVisualStyleTaskFactory(appGlobals).createTaskIterator();
		TaskIterator update_UITasks = new UpdateUI_TaskFactory(appGlobals).createTaskIterator();
		TaskIterator createTasks = new CreateMetaNetworkTaskFactory(appGlobals).createTaskIterator(config);
		TaskIterator protectTasks = new ProtectNetworkTaskFactory(appGlobals).createTaskIterator();
		TaskIterator createViewTasks = new CreateMetaNetworkViewTaskFactory(appGlobals).createTaskIterator();
		TaskIterator collapseMetaNetworkTasks = new CollapseMetaNetworkTaskFactory(appGlobals).createTaskIterator();
		TaskIterator applyPreferredLayoutTask = appGlobals.applyPreferredLayoutTaskAdapterFactory.createTaskIterator();
		TaskIterator aggregateTasks = getAggregationTasks();
		TaskIterator updateShownEdgesTasks = new UpdateActiveEdgesTaskFactory(appGlobals)
				.createTaskIterator();
		TaskIterator createVisStyleTasks = new CreateMetaNetworkVisualStyleTaskFactory(appGlobals).createTaskIterator();
		TaskIterator setStandardNodeLabelsTasks = new ResetResidueNumberingTaskFactory(appGlobals).createTaskIterator();
		
		TaskIterator tskIt = new TaskIterator();

		tskIt.append(createTasks);
		tskIt.append(protectTasks);
		tskIt.append(collapseMetaNetworkTasks);
		tskIt.append(aggregateTasks);
		tskIt.append(updateShownEdgesTasks);
		tskIt.append(createViewTasks);
		
		if(config.shouldCreateVisualStyle()){
			tskIt.append(createVisStyleTasks);
		}
		
		tskIt.append(updateVisStyleTasks);
		tskIt.append(setStandardNodeLabelsTasks);
		tskIt.append(update_UITasks);
		tskIt.append(applyPreferredLayoutTask);
								
		return tskIt;
	}
	
	private TaskIterator getAggregationTasks(){
		TaskIterator aggregateTasks = new TaskIterator();
	
		MetaTimelineAggregatorConfig aggregatorConfig =
				new TimelineWeightAggregatorConfig();
		RowWriter sumWriter =
				new AverageWeightWriter(AppColumns.AVERAGE_INTERACTIONS);
		RowWriter occWriter =
				new AverageWeightWriter(AppColumns.OCCURRENCE);
		MetaTimelineAggregationTaskConfig sumConfig =
				new MetaTimelineAggregationTaskConfig(
						aggregatorConfig,
						FrameWeightMethod.SUM,
						sumWriter,
						TaskLogType.WEIGHT);
		MetaTimelineAggregationTaskConfig occConfig =
				new MetaTimelineAggregationTaskConfig(
						aggregatorConfig,
						FrameWeightMethod.OCCURRENCE,
						occWriter,
						TaskLogType.WEIGHT);
		
		aggregateTasks.append(
				new ActivateAverageFrameWeightingTaskFactory(appGlobals, 
						FrameWeightMethod.SUM).createTaskIterator()
				);
		
		ObjMap results = new ObjMap();		
		aggregateTasks.append(
				new MetaTimelineAggregateTask(
						results,
						appGlobals.state.metaNetworkManager,
						appGlobals.metaTimelineFactoryManager,
						appGlobals.state.logManager,
						sumConfig));
		
		aggregateTasks.append(
				new MetaTimelineAggregateTask(
						results,
						appGlobals.state.metaNetworkManager, appGlobals.metaTimelineFactoryManager,
						appGlobals.state.logManager,
						occConfig));
				
		return aggregateTasks;
	}
		
	
}
