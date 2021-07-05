package com.tcb.sensenet.internal.task.export.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrixFactory;
import com.tcb.sensenet.internal.analysis.matrix.weight.ColumnEdgeWeighter;
import com.tcb.sensenet.internal.analysis.matrix.weight.EdgeWeighter;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.matrix.NetworkMatrixWriter;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.util.DoubleArrayPrinter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.data.DefaultColumns;

public class ExportNetworkMatrixTask extends AbstractTask {

	private AppGlobals appGlobals;
	private ExportNetworkMatrixTaskConfig config;

	
	public ExportNetworkMatrixTask(
			ExportNetworkMatrixTaskConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		
		
		EdgeWeighter edgeWeighter = new ColumnEdgeWeighter(config.getWeightColumn(),metaNetwork);
		ContactMatrixFactory matrixFactory = new ContactMatrixFactory(edgeWeighter);
		
		ContactMatrix matrix = matrixFactory.create(network);
		
		NetworkMatrixWriter writer = new NetworkMatrixWriter(
				matrix, config.getWeightColumn(), config.getNodeNameColumn(), metaNetwork, network);
		
		writer.write(config.getFilePath());

	}

}
