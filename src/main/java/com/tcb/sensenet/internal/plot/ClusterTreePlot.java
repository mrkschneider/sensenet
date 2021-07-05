package com.tcb.sensenet.internal.plot;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.jfree.chart.plot.ValueMarker;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.data.NamespaceUtil;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;

public class ClusterTreePlot extends LinePlot  {

	private String title;
	private String yLabel;
	private List<Double> yValues;

	public ClusterTreePlot(
			List<Double> yValues,
			String title, String yLabel){
		this.yValues = yValues;
		this.title = title;
		this.yLabel = yLabel;
		
		super.init();
		
		this.chart.removeLegend();
	}
	
	@Override
	public String getPlotTitle() {
		return title;
	}

	@Override
	public String getPlotSubTitle() {
		return "";
	}

	@Override
	public String getXLabel() {
		return "Cluster count";
	}

	@Override
	public String getYLabel() {
		return NamespaceUtil.removeNamespacePrefix(yLabel);
	}

	@Override
	public void plot() throws InvalidColumnException {
		List<Double> xValues = IntStream.range(1,yValues.size()+1)
				.asDoubleStream()
				.boxed()
				.collect(ImmutableList.toImmutableList());
		plotValues(xValues,yValues,Color.BLACK,getDefaultStroke(),"");
	}

}
