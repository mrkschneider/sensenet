package com.tcb.sensenet.internal.plot.histogram;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.SimpleHistogramBin;

import com.tcb.sensenet.internal.analysis.cluster.ClusterAnalysis;
import com.tcb.sensenet.internal.data.NamespaceUtil;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;
import com.tcb.mdAnalysis.statistics.StandardStatistics;

public class ColumnPlot extends HistogramPlot {

	private List<Double> values;
	private Integer binCount;
	private String columnName;

	public ColumnPlot(String columnName, List<Double> values, Integer binCount){
		this.columnName = columnName;
		this.values = values;
		this.binCount = binCount;
		super.init(createBins(values,binCount));
	}
	
	@Override
	public String getPlotTitle() {
		return "";
	}

	@Override
	public String getPlotSubTitle() {
		return "";
	}

	@Override
	public String getXLabel() {
		return NamespaceUtil.removeNamespacePrefix(columnName);
	}

	@Override
	public String getYLabel() {
		return "count";
	}
	
	private List<SimpleHistogramBin> createBins(List<Double> values, Integer binCount){
		List<SimpleHistogramBin> bins = new ArrayList<>();
		double max = Collections.max(values);
		double min = Collections.min(values);
		double delta = getDelta(min,max,binCount);
		for(double i=min;i<max;i+=delta){
			bins.add(new SimpleHistogramBin(i,i+delta,true,false));
		}
		if(bins.size()==0) return bins;
		int lastIdx = bins.size() - 1;
		SimpleHistogramBin last = bins.get(lastIdx);
		bins.set(lastIdx, new SimpleHistogramBin(last.getLowerBound(),max,true,true));
		return bins;
	}

	@Override
	public void plot() throws InvalidColumnException {
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
		double max = Collections.max(values);
		double min = Collections.min(values);
		double delta = getDelta(min,max,binCount);

	    xAxis.setLowerBound(min-0.5*delta);
	    xAxis.setUpperBound(max+1.5*delta);
	    
		plotValues(values);	
		
		double mean = new StandardStatistics(values).getMean();
		
		addVline(mean, Color.blue, getDashedStroke());
				
		addToLegend("Mean", 
				new XYLineAndShapeRenderer().getLegendLine(), Color.blue,  getDashedStroke());
	}

	private double getDelta(double min, double max, int binCount){
		return (max - min) / binCount;
	}
	
	@Override
	public void exportData(File f) throws IOException {
		throw new UnsupportedOperationException();		
	}

}
