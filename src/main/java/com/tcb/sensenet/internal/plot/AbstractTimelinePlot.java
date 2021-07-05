package com.tcb.sensenet.internal.plot;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import com.tcb.sensenet.internal.util.ListUtil;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;

public abstract class AbstractTimelinePlot extends LinePlot {
	
	protected abstract Map<Integer,? extends Number> createTimeline();
	protected abstract String getLineName();
	
	private static final int maxPlotValues = 50;
		
	@Override
	public void plot() throws InvalidColumnException {
		Map<Integer,? extends Number> timeline = createTimeline();
				
		List<Integer> X = timeline.keySet().stream()
				.sorted()
				.collect(Collectors.toList());
		List<Double> Y = X.stream()
				.map(x -> timeline.get(x))
				.map(y -> y.doubleValue())
				.collect(Collectors.toList());
		
		NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
		
		DoubleSummaryStatistics stat = Y.stream()
				.collect(Collectors.summarizingDouble(d -> d));
		
		Double lowerBound = getLowerBound();
		if(lowerBound==null) lowerBound = stat.getMin()-0.1;
		
		Double upperBound = getUpperBound();
		if(upperBound==null) upperBound = stat.getMax()+0.1;
				
		yAxis.setLowerBound(lowerBound);
		yAxis.setUpperBound(upperBound);
		yAxis.setTickUnit(new NumberTickUnit(getYTickInterval()));
		
		int plotStep = (int)Math.ceil(
				((float)X.size()) / maxPlotValues);
		
		X = ListUtil.getEveryNth(X,plotStep);
		Y = ListUtil.getEveryNth(Y,plotStep);
		
		plotValues(X,Y,colors[0],getDefaultStroke(),getLineName());
	}
	
	@Override
	protected XYLineAndShapeRenderer createRenderer(){
		XYLineAndShapeRenderer renderer = super.createRenderer();
		renderer.setBaseShapesVisible(true);
		return renderer;
	}
	
	protected Double getYTickInterval() {
		return 1.0;
	}
	
	protected Double getLowerBound() {
		return null;
	}
	
	protected Double getUpperBound() {
		return null;
	}
}
