package com.tcb.sensenet.internal.UI.panels.analysisPanel.network;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.DivergenceAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.AutocorrelationAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.DivergenceAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.EntropyAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.AutocorrelationAnalysisWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EntropyWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.ReplicaDivergenceAnalysisWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.ErrorMethod;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.autocorrelation.replicas.ReplicaAutocorrelationTimeWeightMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceMethod;
import com.tcb.sensenet.internal.analysis.divergence.DivergenceStrategy;
import com.tcb.sensenet.internal.analysis.divergence.JensenShannonDivergence;
import com.tcb.sensenet.internal.analysis.divergence.PopulationShiftDivergence;
import com.tcb.sensenet.internal.analysis.divergence.SymmetricKullbackLeiblerDivergence;
import com.tcb.sensenet.internal.analysis.entropy.EntropyMethod;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.log.TaskLogType;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.aggregation.MetaTimelineAggregationTaskConfig;
import com.tcb.sensenet.internal.task.aggregation.factories.ActionMetaTimelineAggregationTaskFactory;
import com.tcb.sensenet.internal.task.divergence.DivergenceTaskConfig;
import com.tcb.sensenet.internal.task.divergence.factories.ActionNetworkDivergenceTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.sensenet.internal.util.ObjMap;


public class EntropyDialog extends DefaultDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<FrameWeightMethod> weightMethodSelectionBox;

	private AppGlobals appGlobals;
	
	private static final AppProperty defaultWeightMethodProperty = 
			AppProperty.ENTROPY_DEFAULT_WEIGHT_METHOD;
		
	public EntropyDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		addGeneralOptionsPanel();
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set entropy calculation parameters");
		this.pack();
	}
	
	private void addGeneralOptionsPanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
				
		weightMethodSelectionBox = p.addChoosableParameter("Frame weight", FrameWeightMethod.values(), 
				FrameWeightMethod.valueOf(
						appGlobals.appProperties.getOrDefault(defaultWeightMethodProperty)));
						
		this.add(p);
		
	}
	
	protected void confirm() {
		FrameWeightMethod weightMethod = (FrameWeightMethod) 
				weightMethodSelectionBox.getSelectedItem();
								
		MetaTimelineAggregationTaskConfig config = getTaskConfig(
				weightMethod);
		
		appGlobals.appProperties.set(defaultWeightMethodProperty, weightMethod.name());
				
		ObjMap results = new ObjMap();
		
		TaskIterator tasks = new ActionMetaTimelineAggregationTaskFactory(appGlobals)
				.createTaskIterator(results, config);
		
		appGlobals.taskManager.execute(tasks);
				
		this.dispose();
	}
		
	
	private MetaTimelineAggregationTaskConfig
		getTaskConfig(
				FrameWeightMethod weightMethod){
		EntropyMethod entropyMethod = EntropyMethod.SHANNON;
		MetaTimelineAggregatorConfig aggregatorConfig = 
				new EntropyAggregatorConfig(entropyMethod);
		RowWriter writer = new EntropyWriter();
		DivergenceTaskConfig config = 
				new DivergenceTaskConfig(
						aggregatorConfig,weightMethod,writer,TaskLogType.ENTROPY);
		return config;
	}
	
}
