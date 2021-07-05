package com.tcb.sensenet.internal.UI.panels.analysisPanel.network;

import java.awt.Container;

import javax.swing.JComboBox;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.table.DegreeWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.EdgeCorrelationFactorsWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.aggregation.methods.timeline.FrameWeightMethod;
import com.tcb.sensenet.internal.analysis.correlation.EdgeCorrelationMethod;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeMode;
import com.tcb.sensenet.internal.analysis.degree.WeightedDegreeNormalizationMode;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.NetworkColumnStatistics;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesMode;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.correlation.CorrelationFactorsTaskConfig;
import com.tcb.sensenet.internal.task.correlation.factories.ActionCorrelationFactorsTaskFactory;
import com.tcb.sensenet.internal.task.degree.WeightedDegreeTaskConfig;
import com.tcb.sensenet.internal.task.degree.factories.ActionWeightedDegreeTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;


public class WeightedDegreeDialog extends DefaultDialog {
	private JComboBox<WeightedDegreeMode> weightedDegreeModeBox;
	private JComboBox<NegativeValuesMode> negativeValuesModeBox;
	private JComboBox<WeightedDegreeNormalizationMode> normalizationModeBox;
	private JComboBox<String> edgeWeightColumnBox;

	private AppGlobals appGlobals;
	
	private static final AppProperty defaultWeightedDegreeMethodProperty =
			AppProperty.WEIGHTED_DEGREE_DEFAULT_METHOD;
	private static final AppProperty defaultNegativeValuesMethodProperty =
			AppProperty.WEIGHTED_DEGREE_DEFAULT_NEGATIVE_WEIGHT_MODE;
	private static final AppProperty defaultWeightColumnProperty =
			AppProperty.WEIGHTED_DEGREE_DEFAULT_EDGE_WEIGHT_COLUMN;
	private static final AppProperty defaultNormalizationModeProperty =
			AppProperty.WEIGHTED_DEGREE_DEFAULT_NORMALIZATION_MODE;
	

	public WeightedDegreeDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel(this);
		this.add(DialogUtil.createActionPanel(this::confirm, this::dispose));
		
		this.setTitle("Set weighted degree parameters");
		this.pack();
	}
	
	private void addGeneralOptionsPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		String[] numberColumns = new NetworkColumnStatistics(network.getDefaultEdgeTable())
				.getColumns(Double.class)
				.toArray(new String[0]);
		
		weightedDegreeModeBox =
				p.addChoosableParameter(
						"Degree weight",
						WeightedDegreeMode.values(),
						appGlobals.appProperties.getEnumOrDefault(
								WeightedDegreeMode.class,
								defaultWeightedDegreeMethodProperty));
		edgeWeightColumnBox =
				p.addChoosableParameter(
						"Weight column",
						numberColumns,
						appGlobals.appProperties.getOrDefault(defaultWeightColumnProperty));
		negativeValuesModeBox = 
				p.addChoosableParameter(
						"Negative weights", 
						NegativeValuesMode.values(),
						appGlobals.appProperties.getEnumOrDefault(
								NegativeValuesMode.class, defaultNegativeValuesMethodProperty));
		
		normalizationModeBox =
				p.addChoosableParameter(
						"Normalization",
						WeightedDegreeNormalizationMode.values(),
						appGlobals.appProperties.getEnumOrDefault(
								WeightedDegreeNormalizationMode.class, defaultNormalizationModeProperty));
				
		target.add(p);
	}
	
	protected void confirm() {
		WeightedDegreeTaskConfig config = getTaskConfig();
				
		appGlobals.taskManager.execute(
				new ActionWeightedDegreeTaskFactory(appGlobals).createTaskIterator(config)
				);
		this.dispose();
	}
	
	private WeightedDegreeTaskConfig getTaskConfig(){
		WeightedDegreeMode weightMethod = (WeightedDegreeMode) weightedDegreeModeBox.getSelectedItem();
		NegativeValuesMode negativeWeightMode = (NegativeValuesMode) negativeValuesModeBox.getSelectedItem();
		WeightedDegreeNormalizationMode normalizationMode = (WeightedDegreeNormalizationMode)
				normalizationModeBox.getSelectedItem();
		String weightColumn = (String) edgeWeightColumnBox.getSelectedItem();
		
		appGlobals.appProperties.set(defaultWeightedDegreeMethodProperty, weightMethod.name());
		appGlobals.appProperties.set(defaultNegativeValuesMethodProperty, negativeWeightMode.name());
		appGlobals.appProperties.set(defaultWeightColumnProperty, weightColumn);
		appGlobals.appProperties.set(defaultNormalizationModeProperty, normalizationMode.name());
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		
		RowWriter rowWriter = new DegreeWriter();
		return WeightedDegreeTaskConfig.create(network, weightColumn, rowWriter, weightMethod,
				negativeWeightMode, normalizationMode
				);
	}
		
}
