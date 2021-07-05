package com.tcb.sensenet.internal.plot.heatmap;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.cytoscape.model.CyNode;
import org.jfree.chart.ChartPanel;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;

import com.tcb.sensenet.internal.analysis.matrix.ContactMatrix;
import com.tcb.sensenet.internal.plot.color.ColorScale;

public class NetworkMatrixPlot extends HeatmapPlot {

	public static NetworkMatrixPlot create(
			double[][] matrixData,
			List<Integer> nodeLabels,
			ColorScale colorScale,
			String xyLabel,
			String valueLabel
			){
		XYZDataset dataset = createDataset(matrixData,nodeLabels);
		return new NetworkMatrixPlot(dataset, colorScale, xyLabel, valueLabel);
	}
	
	private static XYZDataset createDataset(double[][] matrixData, List<Integer> nodeLabels){
		final int size = nodeLabels.size();
		DefaultXYZDataset dataset = new DefaultXYZDataset();
		for(int i=0;i<size;i++){
			double[][] data = new double[3][size];
			for(int j=0;j<size;j++){
				data[0][j] = nodeLabels.get(i);
				data[1][j] = nodeLabels.get(j);
				data[2][j] = matrixData[i][j];
			}
			dataset.addSeries(i, data);
		}
		return dataset;
	}

	
	
	private NetworkMatrixPlot(XYZDataset dataset, ColorScale colorScale, String xyLabel, String valueLabel) {
		super(dataset, colorScale, xyLabel, xyLabel, valueLabel);
	}
	
	@Override
	public String getPlotTitle() {
		return "Network matrix";
	}

	@Override
	public String getPlotSubTitle() {
		return "";
	}

	@Override
	public void plot() throws Exception {
		ChartPanel chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
	}

	@Override
	public void exportData(File f) throws IOException {
		throw new UnsupportedOperationException();		
	}
}
