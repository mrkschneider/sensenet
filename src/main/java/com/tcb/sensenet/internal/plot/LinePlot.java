package com.tcb.sensenet.internal.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;

import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;

public abstract class LinePlot extends Plot {
	
	public abstract String getPlotTitle();
	public abstract String getPlotSubTitle();
	public abstract String getXLabel();
	public abstract String getYLabel();
	public abstract void plot() throws InvalidColumnException;
	
	protected JFreeChart chart;
	protected  XYPlot plot;
	protected Paint[] colors = ChartColor.createDefaultPaintArray();
	
	private int datasetCounter = 0;
		
	public static final float defaultStrokeWidth = 2.0f;
	
	public LinePlot(){
	}
	
	protected void init(){
		this.chart = ChartFactory.createXYLineChart(
				getPlotTitle(),
				getXLabel(),
				getYLabel(),
				null,
				PlotOrientation.VERTICAL, true, false, false);
		this.plot = (XYPlot)chart.getPlot();
		
		chart.addSubtitle(new TextTitle(getPlotSubTitle()));
		plot.setBackgroundPaint(Color.WHITE);
		
		  
		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.BOTTOM);
		legend.setPadding(5, 5, 5, 5);
		
		//chart.removeLegend();
		//setUpLegend();
				
		ChartPanel chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		
	}
				
	protected XYPlot getPlot(){
		return plot;
	}
	
	protected XYLineAndShapeRenderer createRenderer(){
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setBaseStroke(getDefaultStroke());
		renderer.setBaseShapesVisible(false);
		renderer.setDrawSeriesLineAsPath(true);
		return renderer;
	}
	
	protected synchronized void plotValues(
			List<? extends Number> X,
			List<? extends Number> Y,
			Paint color,
			Stroke stroke,
			String name) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries points = new XYSeries(name);

		for(int i=0;i<X.size();i++){
			double x = X.get(i).doubleValue();
			double y = Y.get(i).doubleValue();
			points.add(x, y);
		}

		dataset.addSeries(points);
		
		XYLineAndShapeRenderer renderer = createRenderer();
	
		renderer.setSeriesPaint(0, color);
				
		renderer.setBaseStroke(stroke);
		renderer.setSeriesStroke(0, stroke);
		
		
		plot.setDataset(datasetCounter, dataset);
		plot.setRenderer(datasetCounter, renderer);
		
		datasetCounter++;
	}
	
	public void addVline(Double x, Paint color, Stroke stroke){
		ValueMarker marker = new ValueMarker(x);
		marker.setPaint(color);
		marker.setStroke(stroke);
		plot.addDomainMarker(marker);
	}
	
	public void addHline(Double y, Paint color, Stroke stroke){
		ValueMarker marker = new ValueMarker(y);
		marker.setPaint(color);
		marker.setStroke(stroke);
		plot.addRangeMarker(marker);
	}
	
	protected void addToLegend(String label, Shape shape, Paint color, Stroke stroke){
		LegendItemCollection chartLegend = plot.getLegendItems();
		chartLegend.add(new LegendItem(label, null, null, null, shape, stroke, color));
		plot.setFixedLegendItems(chartLegend);
	}
	
	public Stroke getDashedStroke(){
		Stroke stroke = new BasicStroke(
		        defaultStrokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
		        1.0f, new float[] {5.0f, 5.0f}, 0.0f);
		return stroke;
	}
	
	public Stroke getDefaultStroke(){
		return new BasicStroke(defaultStrokeWidth);
	}
	
	
	@Override
	public void exportData(File f) throws IOException {
		XYWriter writer = new XYWriter(f);
		for(int i=0;i<plot.getDatasetCount();i++){
			XYSeriesCollection dataset = (XYSeriesCollection) plot.getDataset(i);
			for(Object s:dataset.getSeries()){
				XYSeries series = (XYSeries)s;
				writer.writeComment(series.getKey().toString());
				for(Object d:series.getItems()){
					XYDataItem item = (XYDataItem) d;
					writer.writeXY(item.getXValue(), item.getYValue());
				}
			}
		}
		
		writer.close();
	}
				
}
