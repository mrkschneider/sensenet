package com.tcb.sensenet.internal.UI.panels.analysisPanel.paths;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Optional;

import javax.swing.JComboBox;

import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.aggregation.aggregators.table.DoubleWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.NodeCentralityWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.NetworkColumnStatistics;
import com.tcb.sensenet.internal.path.analysis.centrality.NodeCentralityType;
import com.tcb.sensenet.internal.path.analysis.centrality.distance.EdgeDistanceMode;
import com.tcb.sensenet.internal.path.analysis.centrality.normalization.CentralityNormalizationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.accumulation.WeightAccumulationMode;
import com.tcb.sensenet.internal.path.analysis.centrality.weight.negative.NegativeValuesMode;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.path.centrality.WeightedNodeCentralityTaskConfig;
import com.tcb.sensenet.internal.task.path.centrality.factories.ActionWeightedNodeCentralityTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class WeightedNodeCentralityDialog extends DefaultDialog {
	private AppGlobals appGlobals;
		
	private JComboBox<String> weightColumnNameBox;
	private JComboBox<WeightAccumulationMode> nodeWeightModeBox;
	private JComboBox<EdgeDistanceMode> distanceModeBox;
	private JComboBox<CentralityNormalizationMode> normalizationModeBox;
	private JComboBox<NodeCentralityType> centralityTypeBox;
	private JComboBox<NegativeValuesMode> negativeWeightModeBox;
	
	private static final AppProperty defaultWeightModeProperty = 
			AppProperty.NODE_CENTRALITY_DEFAULT_WEIGHT_MODE;
	private static final AppProperty defaultDistanceModeProperty = 
			AppProperty.NODE_CENTRALITY_DEFAULT_DISTANCE_MODE;
	private static final AppProperty defaultNormalizationModeProperty = 
			AppProperty.NODE_CENTRALITY_DEFAULT_NORMALIZATION_MODE;
	private static final AppProperty defaultWeightColumnProperty =
			AppProperty.NODE_CENTRALITY_DEFAULT_WEIGHT_COLUMN;
	private static final AppProperty defaultCentralityTypeProperty =
			AppProperty.NODE_CENTRALITY_DEFAULT_TYPE;
	private static final AppProperty defaultNegativeWeightModeProperty =
			AppProperty.NODE_CENTRALITY_DEFAULT_NEGATIVE_WEIGHT_MODE;
		
	private AppProperties appProperties;
		
	public WeightedNodeCentralityDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.appProperties = appGlobals.appProperties;
		
		this.setLayout(new GridBagLayout());
		
		this.setTitle("Set node centrality parameters");
		
		addGeneralPanel(this);
		
		updateDefaultValues();
		
		this.add(
				DialogUtil.createActionPanel(this::confirm,this::dispose),
				getDefaultConstraints());

		this.pack();
	}
	
	private GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		return c;
	}
	
	private void addGeneralPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		String[] numberColumns = new NetworkColumnStatistics(network.getDefaultEdgeTable())
				.getColumns(Double.class)
				.toArray(new String[0]);
		
		centralityTypeBox = p.addChoosableParameter("Centrality type", 
				NodeCentralityType.values(), appProperties.getEnumOrDefault(
						NodeCentralityType.class, defaultCentralityTypeProperty));
		nodeWeightModeBox = p.addChoosableParameter("Multiple edges weights", WeightAccumulationMode.values(),
				appProperties.getEnumOrDefault(WeightAccumulationMode.class, defaultWeightModeProperty));
		weightColumnNameBox = p.addChoosableParameter("Weight column", numberColumns,
				appProperties.getOrDefault(defaultWeightColumnProperty));
		distanceModeBox = p.addChoosableParameter("Distance transformation", EdgeDistanceMode.values(),
				appProperties.getEnumOrDefault(EdgeDistanceMode.class, defaultDistanceModeProperty));
		negativeWeightModeBox = p.addChoosableParameter("Negative weights", NegativeValuesMode.values(),
				appProperties.getEnumOrDefault(NegativeValuesMode.class, defaultNegativeWeightModeProperty));
		normalizationModeBox = p.addChoosableParameter("Normalization", 
				CentralityNormalizationMode.values(), appProperties.getEnumOrDefault(CentralityNormalizationMode.class,
						defaultNormalizationModeProperty));
		
		ActionListener updateListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				updateDefaultValues();
			}
		};
		
		centralityTypeBox.addActionListener(updateListener);
		nodeWeightModeBox.addActionListener(updateListener);
		
		target.add(p);
	}
	
	private void updateDefaultValues(){
		NodeCentralityType centralityType = (NodeCentralityType) centralityTypeBox.getSelectedItem();
		if(centralityType.equals(NodeCentralityType.STRESS)){
				normalizationModeBox.setSelectedItem(CentralityNormalizationMode.NONE);
				normalizationModeBox.setEnabled(false);
			} else {
				normalizationModeBox.setEnabled(true);
		}
		
		WeightAccumulationMode nodeWeightMode = (WeightAccumulationMode) nodeWeightModeBox.getSelectedItem();
		if(nodeWeightMode.equals(WeightAccumulationMode.EDGE_COUNT)){
			weightColumnNameBox.setSelectedItem(null);
			weightColumnNameBox.setEnabled(false);
		} else {
			weightColumnNameBox.setEnabled(true);
			weightColumnNameBox.setSelectedItem(appProperties.getOrDefault(defaultWeightColumnProperty));
		}
	}

	
	private void confirm() {
		NodeCentralityType centralityType = (NodeCentralityType) centralityTypeBox.getSelectedItem();
		WeightAccumulationMode nodeWeightMode = (WeightAccumulationMode) nodeWeightModeBox.getSelectedItem();
		EdgeDistanceMode distanceMode = (EdgeDistanceMode) distanceModeBox.getSelectedItem();
		CentralityNormalizationMode normalizationMode = (CentralityNormalizationMode) normalizationModeBox.getSelectedItem();
		NegativeValuesMode negativeWeightMode = (NegativeValuesMode) negativeWeightModeBox.getSelectedItem();
		
		String weightColumnName = (String) weightColumnNameBox.getSelectedItem();
		if(weightColumnName!=null){
			appProperties.set(AppProperty.NODE_CENTRALITY_DEFAULT_WEIGHT_COLUMN, weightColumnName);
		}
		
		appProperties.set(AppProperty.NODE_CENTRALITY_DEFAULT_TYPE, centralityType.name());
		appProperties.set(AppProperty.NODE_CENTRALITY_DEFAULT_WEIGHT_MODE, nodeWeightMode.name());
		appProperties.set(AppProperty.NODE_CENTRALITY_DEFAULT_DISTANCE_MODE, distanceMode.name());
		appProperties.set(AppProperty.NODE_CENTRALITY_DEFAULT_NORMALIZATION_MODE, normalizationMode.name());
		appProperties.set(AppProperty.NODE_CENTRALITY_DEFAULT_NEGATIVE_WEIGHT_MODE, negativeWeightMode.name());
		
		
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		RowWriter writer = new NodeCentralityWriter();
		WeightedNodeCentralityTaskConfig config = 
				WeightedNodeCentralityTaskConfig.create(
						network, writer,
						Optional.ofNullable(weightColumnName),
						centralityType,
						nodeWeightMode,
						distanceMode, 
						normalizationMode, negativeWeightMode);
		TaskIterator it = new ActionWeightedNodeCentralityTaskFactory(appGlobals)
				.createTaskIterator(config);
		appGlobals.taskManager.execute(it);		
		this.dispose();
	}	
}
