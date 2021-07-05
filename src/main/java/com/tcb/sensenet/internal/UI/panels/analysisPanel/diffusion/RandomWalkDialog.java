package com.tcb.sensenet.internal.UI.panels.analysisPanel.diffusion;

import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.cytoscape.model.CyNode;
import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.work.TaskIterator;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.UI.util.SingletonErrorDialog;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RandomWalkWriter;
import com.tcb.sensenet.internal.aggregation.aggregators.table.RowWriter;
import com.tcb.sensenet.internal.analysis.diffusion.RandomWalkMode;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.NetworkColumnStatistics;
import com.tcb.sensenet.internal.data.rows.RowStatistics;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.plot.color.BiColorScale;
import com.tcb.sensenet.internal.plot.color.ColorScale;
import com.tcb.sensenet.internal.plot.color.TriColorScale;
import com.tcb.sensenet.internal.properties.AppProperties;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.selection.InvalidSelectionException;
import com.tcb.sensenet.internal.selection.TwoNodeSelection;
import com.tcb.sensenet.internal.task.diffusion.RandomWalkTask;
import com.tcb.sensenet.internal.task.diffusion.factories.ActionRandomWalkTaskFactory;
import com.tcb.sensenet.internal.task.export.matrix.ExportNetworkMatrixTaskConfig;
import com.tcb.sensenet.internal.task.export.matrix.factories.ExportNetworkMatrixTaskFactory;
import com.tcb.sensenet.internal.task.plot.factories.ShowNetworkMatrixPlotFrameTaskFactory;
import com.tcb.sensenet.internal.task.plot.matrix.ShowNetworkMatrixPlotFrameTaskConfig;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRootNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.TimelineType;

public class RandomWalkDialog extends DefaultDialog {
	
	private JComboBox<RandomWalkMode> walkModeBox;
	private JTextField sourceSuidBox;
	private JTextField targetSuidBox;
	private JTextField restartProbBox;
	private JTextField maxStepsBox;
	private JTextField numRunsBox;
	private JComboBox<String> weightColumnBox;

	private AppGlobals appGlobals;
	
	private static final AppProperty defaultWeightColumnProperty = 
			AppProperty.RANDOM_WALK_DEFAULT_WEIGHT_COLUMN;
	private static final AppProperty defaultRestartProbProperty =
			AppProperty.RANDOM_WALK_DEFAULT_RESTART_PROBABILITY;
	private static final AppProperty defaultMaxStepsProperty =
			AppProperty.RANDOM_WALK_DEFAULT_MAX_STEPS;
	private static final AppProperty defaultNumRunsProperty =
			AppProperty.RANDOM_WALK_DEFAULT_NUM_RUNS;
	private static final AppProperty defaultWalkModeProperty =
			AppProperty.RANDOM_WALK_DEFAULT_WALK_MODE;

	public RandomWalkDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel(this);
		
		this.add(
				DialogUtil.createActionPanel(this::confirm, this::dispose),
				getDefaultDialogConstraints());

		this.pack();
	}

	private void confirm(){
		String weightColumn = (String) weightColumnBox.getSelectedItem();
		String sourceSuidInput = sourceSuidBox.getText();
		Long sourceSuid = null;
		if(sourceSuidInput != null && !sourceSuidInput.isEmpty())
			sourceSuid = Long.parseLong(sourceSuidBox.getText());
		String targetSuidInput = targetSuidBox.getText();
		Long targetSuid = null;
		if(targetSuidInput != null && !targetSuidInput.isEmpty())
			targetSuid = Long.parseLong(targetSuidBox.getText());
				
		Integer numRuns = Integer.parseInt(numRunsBox.getText());
		Integer maxSteps = Integer.parseInt(maxStepsBox.getText());
		
		Double restartProb = Double.parseDouble(restartProbBox.getText());
		RandomWalkMode walkMode = (RandomWalkMode) walkModeBox.getSelectedItem();
				
		appGlobals.appProperties.set(defaultWeightColumnProperty, weightColumn);
		appGlobals.appProperties.set(defaultNumRunsProperty, numRuns.toString());
		appGlobals.appProperties.set(defaultWalkModeProperty, walkMode.name());
		appGlobals.appProperties.set(defaultMaxStepsProperty, maxSteps.toString());
		appGlobals.appProperties.set(defaultRestartProbProperty, restartProb.toString());
		
		RowWriter rowWriter = new RandomWalkWriter();
		
		RandomWalkTask.Config config = RandomWalkTask.Config.create(
				walkMode,sourceSuid,targetSuid,maxSteps,
				weightColumn,restartProb,numRuns,rowWriter);
		TaskIterator it = 
				new ActionRandomWalkTaskFactory(appGlobals)
				.createTaskIterator(config);
		appGlobals.taskManager.execute(it);
		this.dispose();
	}
			
	private void addGeneralOptionsPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		AppProperties appProperties = appGlobals.appProperties;
		
		String[] nodeNumericColumns = getNodeNumericColumns(metaNetwork);
		
		walkModeBox = p.addChoosableParameter("Walk mode", RandomWalkMode.values(),
				appProperties.getEnumOrDefault(RandomWalkMode.class, defaultWalkModeProperty));
		weightColumnBox = p.addChoosableParameter("Weight column",
				nodeNumericColumns, appProperties.getOrDefault(defaultWeightColumnProperty));
		sourceSuidBox = p.addTextParameter("Source node", null);
		targetSuidBox = p.addTextParameter("Target node", null);
		maxStepsBox = p.addTextParameter("Max steps", appProperties.getOrDefault(defaultMaxStepsProperty));
		restartProbBox = p.addTextParameter("Restart probability", appProperties.getOrDefault(defaultRestartProbProperty));
		numRunsBox = p.addTextParameter("Num runs", appProperties.getOrDefault(defaultNumRunsProperty));

		updateTextFields();		
		
		target.add(p);
	}
	
	private void updateTextFields(){
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		CyRootNetworkAdapter rootNetwork = appGlobals.rootNetworkManager.getRootNetwork(network);
		List<CyNode> selected = network.getNodeList().stream()
				.filter(n -> network.getRow(n).get(DefaultColumns.SELECTED, Boolean.class))
				.collect(Collectors.toList());
		int selectedSize = selected.size();
		
		try {
			if(selectedSize==1) {
				sourceSuidBox.setText(selected.get(0).getSUID().toString());
				targetSuidBox.setText(null);
			}
			else if(selectedSize==2){
				TwoNodeSelection selection = TwoNodeSelection.create(network, rootNetwork);
				sourceSuidBox.setText(selection.getFirst().getSUID().toString());
				targetSuidBox.setText(selection.getSecond().getSUID().toString());
			}
			else {
				throw new InvalidSelectionException("Must select one or two nodes");
			}
			
		} catch (InvalidSelectionException e) {
			SingletonErrorDialog.showNonBlocking(e);
			this.dispose();
		}
		
	}
			
	private String[] getNodeNumericColumns(MetaNetwork metaNetwork){
		NetworkColumnStatistics nodeStat = new NetworkColumnStatistics(
				metaNetwork.getRootNetwork().getSharedNodeTable()
				);
		String[] nodeNumericColumns = nodeStat.getColumns(Double.class).stream()
				.toArray(String[]::new);
		return nodeNumericColumns;
	}
}
