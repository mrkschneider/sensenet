package com.tcb.sensenet.internal.UI.panels.analysisPanel.matrix;

import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.work.TaskIterator;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.NetworkColumnStatistics;
import com.tcb.sensenet.internal.data.rows.RowStatistics;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.plot.color.BiColorScale;
import com.tcb.sensenet.internal.plot.color.ColorScale;
import com.tcb.sensenet.internal.plot.color.TriColorScale;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.export.matrix.ExportNetworkMatrixTaskConfig;
import com.tcb.sensenet.internal.task.export.matrix.factories.ExportNetworkMatrixTaskFactory;
import com.tcb.sensenet.internal.task.plot.factories.ShowNetworkMatrixPlotFrameTaskFactory;
import com.tcb.sensenet.internal.task.plot.matrix.ShowNetworkMatrixPlotFrameTaskConfig;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyRowAdapter;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;
import com.tcb.aifgen.importer.TimelineType;

public class ShowNetworkMatrixDialog extends DefaultDialog {
	
	private JComboBox<String> weightColumnBox;
	private JComboBox<String> nodeNameColumnBox;
	private JTextField minValueBox;
	private JTextField maxValueBox;

	private AppGlobals appGlobals;
	
	private static final AppProperty defaultWeightColumnProperty = 
			AppProperty.EXPORT_NETWORK_MATRIX_DEFAULT_WEIGHT_COLUMN;
	private static final AppProperty defaultNodeNameColumnProperty = 
			AppProperty.EXPORT_NETWORK_MATRIX_DEFAULT_NODE_NAME_COLUMN;

	public ShowNetworkMatrixDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel(this);
		
		this.add(
				DialogUtil.createActionPanel(this::confirm, this::dispose),
				getDefaultDialogConstraints());

		this.pack();
	}

	private void confirm(){
		String weightColumn = (String) weightColumnBox.getSelectedItem();	
		String nodeNameColumn = (String) nodeNameColumnBox.getSelectedItem();
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
				
		ColorScale colorScale = getColorScale(metaNetwork);
		
		appGlobals.appProperties.set(defaultWeightColumnProperty, weightColumn);
		appGlobals.appProperties.set(defaultNodeNameColumnProperty, nodeNameColumn);
		
		ShowNetworkMatrixPlotFrameTaskConfig config = ShowNetworkMatrixPlotFrameTaskConfig.create(
				weightColumn, nodeNameColumn, colorScale);
		TaskIterator it = 
				new ShowNetworkMatrixPlotFrameTaskFactory(appGlobals)
				.createTaskIterator(config);
		appGlobals.taskManager.execute(it);
		this.dispose();
	}
	
	private ColorScale getColorScale(MetaNetwork metaNetwork){
		TimelineType timelineType = metaNetwork.getTimelineType();
		Double minValue = Double.valueOf(minValueBox.getText());
		Double maxValue = Double.valueOf(maxValueBox.getText());
		switch(timelineType){
		case DIFFERENCE_TIMELINE:
			ColorScale lowScale = new BiColorScale(Color.BLUE, Color.WHITE, minValue, 0.0);
			ColorScale highScale = new BiColorScale(Color.WHITE, Color.RED, 0.0, maxValue);
			return new TriColorScale(lowScale,highScale);
		case TIMELINE: 
			return new BiColorScale(Color.WHITE, Color.RED, minValue, maxValue);
		default:
			throw new IllegalArgumentException();
		}
	}
	
	private void addGeneralOptionsPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		
		String[] edgeNumericColumns = getEdgeNumericColumns(metaNetwork);
		String[] nodeIntegerColumns = getNodeIntegerColumns(metaNetwork);
				
		weightColumnBox = p.addChoosableParameter("Weight column",
				edgeNumericColumns, appGlobals.appProperties.getOrDefault(defaultWeightColumnProperty));
		nodeNameColumnBox = p.addChoosableParameter("Node index column",
				nodeIntegerColumns, appGlobals.appProperties.getOrDefault(defaultNodeNameColumnProperty));
		minValueBox = p.addTextParameter("Min value", "");
		maxValueBox = p.addTextParameter("Max value", "");
		
		weightColumnBox.addActionListener((e) -> updateTextFields());
		updateTextFields();		
		
		target.add(p);
	}
	
	private void updateTextFields(){
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		List<CyRowAdapter> rows = network.getEdgeList().stream()
				.map(e -> network.getRow(e))
				.collect(ImmutableList.toImmutableList());
		String weightColumn = (String) weightColumnBox.getSelectedItem();	
		
		DoubleSummaryStatistics stat = new RowStatistics(rows)
				.getDoubleColumnStatistics(weightColumn);
		
		TimelineType timelineType = metaNetwork.getTimelineType();
		
		Double min = stat.getMin();
		Double max = stat.getMax();
		
		switch(timelineType){
		case DIFFERENCE_TIMELINE:
			Double limit = Math.max(Math.abs(min), Math.abs(max));
			minValueBox.setText(String.valueOf(-limit));
			maxValueBox.setText(String.valueOf(limit));
			return;
		case TIMELINE:
			minValueBox.setText(String.valueOf(min));
			maxValueBox.setText(String.valueOf(max));
			return;
		default: throw new IllegalArgumentException();	
		}
	}
	
	
	private String[] getNodeIntegerColumns(MetaNetwork metaNetwork){
		NetworkColumnStatistics nodeStat = new NetworkColumnStatistics(
				metaNetwork.getRootNetwork().getSharedNodeTable());
		List<String> nodeIntColumns = nodeStat.getColumns(Integer.class);
		List<String> nodeColumns = new ArrayList<>();
		nodeColumns.addAll(nodeIntColumns);
		return nodeColumns.toArray(new String[0]);
	}
	
	private String[] getEdgeNumericColumns(MetaNetwork metaNetwork){
		NetworkColumnStatistics edgeStat = new NetworkColumnStatistics(
				metaNetwork.getRootNetwork().getSharedEdgeTable()
				);
		String[] edgeNumericColumns = edgeStat.getColumns(Double.class).stream()
				.toArray(String[]::new);
		return edgeNumericColumns;
	}
}
