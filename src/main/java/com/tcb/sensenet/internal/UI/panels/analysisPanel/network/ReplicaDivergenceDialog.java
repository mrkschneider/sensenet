package com.tcb.sensenet.internal.UI.panels.analysisPanel.network;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.DivergenceAggregator;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.AutocorrelationAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.DivergenceAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.edges.timeline.config.MetaTimelineAggregatorConfig;
import com.tcb.sensenet.internal.aggregation.aggregators.table.AutocorrelationAnalysisWriter;
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


public class ReplicaDivergenceDialog extends DefaultDialog {
	private static final long serialVersionUID = 1L;
	
	private JComboBox<FrameWeightMethod> weightMethodSelectionBox;
	private JComboBox<DivergenceMethod> divergenceMethodSelectionBox;
	private JTextField convergenceLimitBox;
	private JTextField blocksBox;

	private AppGlobals appGlobals;
	
	private static final AppProperty defaultWeightMethodProperty = 
			AppProperty.DIVERGENCE_DEFAULT_WEIGHT_METHOD;
	private static final AppProperty defaultDivergenceMethodProperty =
			AppProperty.DIVERGENCE_DEFAULT_METHOD;
	private static final AppProperty defaultConvergenceLimitProperty =
			AppProperty.DIVERGENCE_DEFAULT_CONVERGENCE_LIMIT;
	private static final AppProperty defaultBlocksProperty = 
			AppProperty.BLOCKS_DEFAULT;
	
	public ReplicaDivergenceDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
				
		addGeneralOptionsPanel();
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set divergence calculation parameters");
		this.pack();
	}
	
	private void addGeneralOptionsPanel(){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		divergenceMethodSelectionBox = p.addChoosableParameter("Divergence method",
				DivergenceMethod.values(),
				DivergenceMethod.valueOf(
						appGlobals.appProperties.getOrDefault(defaultDivergenceMethodProperty)));
		weightMethodSelectionBox = p.addChoosableParameter("Frame weight", FrameWeightMethod.values(), 
				FrameWeightMethod.valueOf(
						appGlobals.appProperties.getOrDefault(defaultWeightMethodProperty)));
		blocksBox = p.addTextParameter("Blocks", appGlobals.appProperties.getOrDefault(
				defaultBlocksProperty));
		convergenceLimitBox = p.addTextParameter("Convergence limit",
				appGlobals.appProperties.getOrDefault(defaultConvergenceLimitProperty));
						
		this.add(p);
		
	}
	
	protected void confirm() {
		DivergenceMethod divergenceMethod = (DivergenceMethod) 
				divergenceMethodSelectionBox.getSelectedItem();
		FrameWeightMethod weightMethod = (FrameWeightMethod) 
				weightMethodSelectionBox.getSelectedItem();
		Integer blocks = Integer.valueOf(blocksBox.getText());
		Double convergenceLimit = Double.valueOf(convergenceLimitBox.getText());
						
		DivergenceTaskConfig config = getTaskConfig(
				divergenceMethod, weightMethod, convergenceLimit, blocks);
		
		appGlobals.appProperties.set(defaultDivergenceMethodProperty, divergenceMethod.name());
		appGlobals.appProperties.set(defaultWeightMethodProperty, weightMethod.name());
		appGlobals.appProperties.set(defaultConvergenceLimitProperty, convergenceLimit.toString());
		appGlobals.appProperties.set(defaultBlocksProperty, blocks.toString());
		
		ObjMap results = new ObjMap();
		
		appGlobals.taskManager.execute(
				new ActionNetworkDivergenceTaskFactory(appGlobals).createTaskIterator(results,config)
				);
		this.dispose();
	}
	
	private DivergenceStrategy getDivergenceStrategy(DivergenceMethod method){
		switch(method){
		case JENSEN_SHANNON: return new JensenShannonDivergence();
		case SYMMETRICAL_KULLBACK_LEIBLER: return new SymmetricKullbackLeiblerDivergence();
		case POPULATION_SHIFT: return new PopulationShiftDivergence();
		default: throw new IllegalArgumentException();	
		}
	}
	
	private DivergenceTaskConfig
		getTaskConfig(
				DivergenceMethod divergenceMethod,
				FrameWeightMethod weightMethod,
				Double convergenceLimit,
				Integer blocks){
		DivergenceStrategy divergenceStrategy = getDivergenceStrategy(divergenceMethod);
		
		MetaTimelineAggregatorConfig aggregatorConfig = 
				new DivergenceAggregatorConfig(divergenceMethod,divergenceStrategy,
						blocks,convergenceLimit);
		RowWriter writer = new ReplicaDivergenceAnalysisWriter();
		DivergenceTaskConfig config = 
				new DivergenceTaskConfig(
						aggregatorConfig,weightMethod,writer,TaskLogType.DIVERGENCE);
		return config;
	}
	
}
