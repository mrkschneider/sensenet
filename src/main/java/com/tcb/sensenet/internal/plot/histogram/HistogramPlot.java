package com.tcb.sensenet.internal.plot.histogram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.util.List;

import org.cytoscape.util.swing.FileUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.statistics.SimpleHistogramBin;
import org.jfree.data.statistics.SimpleHistogramDataset;
import org.jfree.ui.RectangleEdge;

import com.tcb.sensenet.internal.plot.Plot;
import com.tcb.cytoscape.cyLib.errors.InvalidColumnException;

public abstract class HistogramPlot extends Plot {
	
	public abstract String getPlotTitle();
	public abstract String getPlotSubTitle();
	public abstract String getXLabel();
	public abstract String getYLabel();
	public abstract void plot() throws InvalidColumnException;
	
	public static final float defaultStrokeWidth = 3.0f;
	
	protected JFreeChart chart;
	protected SimpleHistogramDataset dataset;
	protected XYPlot plot;
	
	public HistogramPlot(){
	}
	
	protected void init(List<SimpleHistogramBin> bins){
		this.dataset = new SimpleHistogramDataset("");
		dataset.setAdjustForBinSize(false);
		this.chart = ChartFactory.createHistogram( 
				getPlotTitle(), getXLabel(), getYLabel(), 
                dataset, PlotOrientation.VERTICAL,
                true, false, false);	
		this.plot = (XYPlot) chart.getPlot();
		
		plot.setBackgroundPaint(Color.WHITE);
		for(SimpleHistogramBin bin:bins){
			dataset.addBin(bin);
		}
				
		chart.addSubtitle(new TextTitle(getPlotSubTitle()));
                
		setPlotStyle();

		ChartPanel chartPanel = new ChartPanel(chart);
		this.add(chartPanel);
		
	}
	
	protected void setPlotStyle(){
		XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true); //draw bar outlines
        renderer.setBarPainter(new StandardXYBarPainter()); //make bars flat paint
        
		plot.setDomainZeroBaselineVisible(false);
		plot.setRangeZeroBaselineVisible(false);
		plot.setFixedLegendItems(new LegendItemCollection());
        //chart.removeLegend();
        
		LegendTitle legend = chart.getLegend();
		legend.setPosition(RectangleEdge.BOTTOM);
		legend.setPadding(5, 5, 5, 5);
		
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	}
	
	public Stroke getDashedStroke(){
		Stroke stroke = new BasicStroke(
		        defaultStrokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
		        1.0f, new float[] {5.0f, 5.0f}, 0.0f);
		return stroke;
	}
	
	protected void addToLegend(String label, Shape shape, Paint color, Stroke stroke){
		LegendItemCollection chartLegend = plot.getLegendItems();
		chartLegend.add(new LegendItem(label, null, null, null, shape, stroke, color));
		plot.setFixedLegendItems(chartLegend);
	}
	
	public Stroke getDefaultStroke(){
		return new BasicStroke(defaultStrokeWidth);
	}
	
	public void addVline(Double x, Paint color, Stroke stroke){
		ValueMarker marker = new ValueMarker(x);
		marker.setPaint(color);
		marker.setStroke(stroke);
		plot.addDomainMarker(marker);
	}
		
	protected void plotValues(List<Double> values) {	
		for(Double v:values){
			dataset.addObservation(v);
		}
	}
	
	@Override
	public void addExportButton(FileUtil fileUtil){
		// Do nothing
	}
	
}
