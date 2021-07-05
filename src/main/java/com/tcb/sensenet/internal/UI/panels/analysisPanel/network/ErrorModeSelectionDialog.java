package com.tcb.sensenet.internal.UI.panels.analysisPanel.network;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.AutocorrelationAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.AutocorrelationAnalysisWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.ErrorMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationTimeWeightMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.task.aggregation.factories.ActionMetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.ObjMap;


public class ErrorModeSelectionDialog extends DefaultDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<FrameWeightMethod> weightMethodSelectionBox;
	private JComboBox<ErrorMethod> errorMethodSelectionBox;
	private JComboBox<ReplicaAutocorrelationTimeWeightMethod> timeMergeMethodBox;
	private JTextField blocksBox;

	private AppGlobals appGlobals;
	
	private static final AppProperty defaultReplicaAutocorrelationTimeMergeMethodProperty = 
			AppProperty.AUTOCORRELATION_REPLICA_MERGE_TIME_DEFAULT_METHOD;
	
	public ErrorModeSelectionDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		addGeneralOptionsPanel();
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set error calculation parameters");
		this.pack();
	}
	
	private void addGeneralOptionsPanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		errorMethodSelectionBox = p.addChoosableParameter("Error method", ErrorMethod.values(),
				ErrorMethod.valueOf(
						appGlobals.appProperties.getOrDefault(AppProperty.WEIGHT_ERROR_DEFAULT_METHOD)));
		weightMethodSelectionBox = p.addChoosableParameter("Frame weight", FrameWeightMethod.values(), 
				FrameWeightMethod.valueOf(
						appGlobals.appProperties.getOrDefault(AppProperty.WEIGHT_ERROR_DEFAULT_WEIGHT_METHOD)));
		timeMergeMethodBox = p.addChoosableParameter(
				"Replica weight",
				ReplicaAutocorrelationTimeWeightMethod.values(),
				appGlobals.appProperties.getEnumOrDefault(ReplicaAutocorrelationTimeWeightMethod.class,
						defaultReplicaAutocorrelationTimeMergeMethodProperty));
		blocksBox = p.addTextParameter("Blocks", appGlobals.appProperties.getOrDefault(
				AppProperty.BLOCKS_DEFAULT));
				
		this.add(p);
		
	}
	
	protected void confirm() {
		MetaTimelineAggregationTaskConfig config = getTaskConfig();
		ObjMap results = new ObjMap();
						
		appGlobals.taskManager.execute(
				new ActionMetaTimelineAggregationTaskFactory(appGlobals).createTaskIterator(results,config)
				);
		this.dispose();
	}
	
	private MetaTimelineAggregationTaskConfig getTaskConfig(){
		FrameWeightMethod weightMethod = (FrameWeightMethod) weightMethodSelectionBox.getSelectedItem();
		ErrorMethod errorMethod = (ErrorMethod) errorMethodSelectionBox.getSelectedItem();
		ReplicaAutocorrelationTimeWeightMethod mergeTimeMethod = 
				(ReplicaAutocorrelationTimeWeightMethod) timeMergeMethodBox.getSelectedItem();
		Integer blocks = Integer.valueOf(blocksBox.getText());
		
		appGlobals.appProperties.set(AppProperty.WEIGHT_ERROR_DEFAULT_METHOD, errorMethod.name());
		appGlobals.appProperties.set(AppProperty.WEIGHT_ERROR_DEFAULT_WEIGHT_METHOD, weightMethod.name());
		appGlobals.appProperties.set(AppProperty.AUTOCORRELATION_REPLICA_MERGE_TIME_DEFAULT_METHOD, mergeTimeMethod.name());
		appGlobals.appProperties.set(AppProperty.BLOCKS_DEFAULT, blocks.toString());
		
		switch(errorMethod){
		case AUTOCORRELATION: return getAutocorrelationErrorTaskConfig(
				weightMethod, mergeTimeMethod, blocks);
		default: throw new IllegalArgumentException();
		}
	}
	
	private MetaTimelineAggregationTaskConfig getAutocorrelationErrorTaskConfig(
			FrameWeightMethod weightMethod,
			ReplicaAutocorrelationTimeWeightMethod mergeTimeMethod,
			Integer blocks){
		MetaTimelineAggregatorConfig aggregatorConfig = 
				new AutocorrelationAggregatorConfig(blocks, mergeTimeMethod);
		RowWriter writer = new AutocorrelationAnalysisWriter();
		MetaTimelineAggregationTaskConfig config = 
				new MetaTimelineAggregationTaskConfig(aggregatorConfig,weightMethod,
						writer,TaskLogType.WEIGHT_ERRORS);
		return config;
	}
	
}
