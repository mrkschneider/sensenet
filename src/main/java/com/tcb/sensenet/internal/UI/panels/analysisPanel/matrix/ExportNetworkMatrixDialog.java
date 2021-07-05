package com.tcb.sensenet.internal.UI.panels.analysisPanel.matrix;

import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.swing.JComboBox;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;
import org.cytoscape.work.TaskIterator;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.FileButton;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.NetworkColumnStatistics;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.properties.AppProperty;
import com.tcb.sensenet.internal.task.export.matrix.ExportNetworkMatrixTaskConfig;
import com.tcb.sensenet.internal.task.export.matrix.factories.ExportNetworkMatrixTaskFactory;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.data.Columns;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class ExportNetworkMatrixDialog extends DefaultDialog {
	
	private JComboBox<String> weightColumnBox;
	private JComboBox<String> nodeNameColumnBox;
	private FileButton fileButton;
	private AppGlobals appGlobals;
	
	private static final AppProperty defaultWeightColumnProperty = 
			AppProperty.EXPORT_NETWORK_MATRIX_DEFAULT_WEIGHT_COLUMN;
	private static final AppProperty defaultNodeNameColumnProperty = 
			AppProperty.EXPORT_NETWORK_MATRIX_DEFAULT_NODE_NAME_COLUMN;

	public ExportNetworkMatrixDialog(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		addGeneralOptionsPanel(this);
		
		this.add(
				DialogUtil.createActionPanel(this::confirm, this::dispose),
				getDefaultDialogConstraints());

		this.pack();
	}

	private void confirm(){
		File f = fileButton.getMaybeFile()
				.orElseThrow(() -> new IllegalArgumentException("Need to choose an output file"));
		String weightColumn = (String) weightColumnBox.getSelectedItem();	
		String nodeNameColumn = (String) nodeNameColumnBox.getSelectedItem();
		
		appGlobals.appProperties.set(defaultWeightColumnProperty, weightColumn);
		appGlobals.appProperties.set(defaultNodeNameColumnProperty, nodeNameColumn);
		
		ExportNetworkMatrixTaskConfig config = ExportNetworkMatrixTaskConfig.create(
				f.toPath(),
				weightColumn, nodeNameColumn);
		TaskIterator it = 
				new ExportNetworkMatrixTaskFactory(appGlobals)
				.createTaskIterator(config);
		appGlobals.taskManager.execute(it);
		this.dispose();
	}
	
	private void addGeneralOptionsPanel(Container target){
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		
		String[] edgeNumericColumns = getEdgeNumericColumns(metaNetwork);
		String[] nodeNameColumns = getNodeNameColumns(metaNetwork);
		
		
		weightColumnBox = p.addChoosableParameter("Weight column",
				edgeNumericColumns, appGlobals.appProperties.getOrDefault(defaultWeightColumnProperty));
		nodeNameColumnBox = p.addChoosableParameter("Node name column",
				nodeNameColumns, appGlobals.appProperties.getOrDefault(defaultNodeNameColumnProperty));
		fileButton = p.addFileParameter("Output file",
				null, "none chosen", ".csv", new String[]{}, "", appGlobals.fileUtil);
		
		target.add(p);
	}
	
	private String[] getNodeNameColumns(MetaNetwork metaNetwork){
		NetworkColumnStatistics nodeStat = new NetworkColumnStatistics(
				metaNetwork.getRootNetwork().getSharedNodeTable());
		List<String> nodeStringColumns = nodeStat.getColumns(String.class);
		List<String> nodeIntColumns = nodeStat.getColumns(Integer.class);
		List<String> nodeColumns = new ArrayList<>();
		nodeColumns.addAll(nodeStringColumns);
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
