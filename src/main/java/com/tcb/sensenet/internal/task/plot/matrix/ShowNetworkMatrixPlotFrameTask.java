package com.tcb.sensenet.internal.task.plot.matrix;

import java.util.Comparator;
import java.util.List;

import javax.swing.JFrame;

import org.cytoscape.model.CyNode;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskMonitor;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.analysis.matrix.ContactMatrixFactory;
import com.tcb.sensenet.internal.analysis.matrix.weight.ColumnEdgeWeighter;
import com.tcb.sensenet.internal.analysis.matrix.weight.EdgeWeighter;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.plot.Plot;
import com.tcb.sensenet.internal.plot.heatmap.NetworkMatrixPlot;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;

public class ShowNetworkMatrixPlotFrameTask extends AbstractTask {

	private AppGlobals appGlobals;
	private ShowNetworkMatrixPlotFrameTaskConfig config;

	public ShowNetworkMatrixPlotFrameTask(
			ShowNetworkMatrixPlotFrameTaskConfig config,
			AppGlobals appGlobals){
		this.config = config;
		this.appGlobals = appGlobals;
	}
	
	@Override
	public void run(TaskMonitor taskMonitor) throws Exception {
		CyNetworkAdapter network = appGlobals.state.metaNetworkManager.getCurrentNetwork();
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		String indexColumn = config.getIndexColumn();
		
		Comparator<CyNode> comparator = new IndexComparator(indexColumn, network);
		List<CyNode> nodes = network.getNodeList().stream()
				.sorted(comparator)
				.collect(ImmutableList.toImmutableList());
		EdgeWeighter edgeWeighter = new ColumnEdgeWeighter(config.getWeightColumn(), metaNetwork);
		ContactMatrixFactory matrixFactory = new ContactMatrixFactory(edgeWeighter);
		ContactMatrix contactMatrix = matrixFactory.create(network);
		
		double[][] matrixData = contactMatrix.getData(nodes);
		List<Integer> nodeLabels = nodes.stream()
				.map(n -> getIndex(n,indexColumn,network))
				.collect(ImmutableList.toImmutableList());
		Plot plot = NetworkMatrixPlot.create(
				matrixData, nodeLabels,
				config.getColorScale(),
				config.getIndexColumn(),
				config.getWeightColumn());
		plot.plot();
		
		JFrame frame = new JFrame("Network matrix");
		frame.setContentPane(plot);
		frame.pack();
		frame.setVisible(true);
	}

	
	private Integer getIndex(CyNode node, String indexColumn, CyNetworkAdapter network){
		return network.getRow(node).getRawMaybe(indexColumn, Integer.class)
				.orElseThrow(
						() -> 
						new IllegalArgumentException(
								"Invalid non-integer value in column: " + indexColumn));
	}
	
	private class IndexComparator implements Comparator<CyNode> {
		
		private String indexColumn;
		private CyNetworkAdapter network;

		public IndexComparator(String indexColumn, CyNetworkAdapter network){
			this.indexColumn = indexColumn;
			this.network = network;
		}
		
		@Override
		public int compare(CyNode a, CyNode b) {
			Integer indexA = getIndex(a, indexColumn,network);
			Integer indexB = getIndex(b, indexColumn,network);
			return indexA.compareTo(indexB);
		}
		
	}

}
