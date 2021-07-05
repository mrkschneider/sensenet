package com.tcb.sensenet.internal.plot.histogram;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.data.statistics.SimpleHistogramBin;

import com.tcb.sensenet.internal.analysis.cluster.ClusterAnalysis;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;

public class ClusteringPlot extends HistogramPlot {

	private ClusterAnalysis analysis;

	public ClusteringPlot(ClusterAnalysis analysis){
		this.analysis = analysis;
		super.init(createBins(analysis));
	}
	
	@Override
	public String getPlotTitle() {
		return "Clustering";
	}

	@Override
	public String getPlotSubTitle() {
		return "";
	}

	@Override
	public String getXLabel() {
		return "cluster index";
	}

	@Override
	public String getYLabel() {
		return "count";
	}
	
	private List<SimpleHistogramBin> createBins(ClusterAnalysis analysis){
		Integer clusterCount = analysis.getClusterCount();
		List<SimpleHistogramBin> bins = new ArrayList<>();
		for(double i=-0.5;i<clusterCount-0.5;i++){
			bins.add(new SimpleHistogramBin(i,i+1,true,false));
		}
		return bins;
	}

	@Override
	public void plot() throws InvalidColumnException {
		Collection<Integer> clusterTimeline = analysis.getClusterTimeline()
				.values();
		List<Double> data = clusterTimeline.stream()
				.map(i -> i.doubleValue())
				.map(d -> d - 0.5)
				.collect(Collectors.toList());
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
	    xAxis.setLowerBound(-0.5);
	    xAxis.setUpperBound(analysis.getClusterCount()-0.5);
	    xAxis.setTickUnit(new NumberTickUnit(1));
	   
	    
		plotValues(data);		
	}

	@Override
	public void exportData(File f) throws IOException {
		throw new UnsupportedOperationException();		
	}

}
