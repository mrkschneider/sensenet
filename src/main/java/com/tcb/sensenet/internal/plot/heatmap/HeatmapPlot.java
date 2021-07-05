package com.tcb.sensenet.internal.plot.heatmap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.chart.title.PaintScaleLegend;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYZDataset;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;

import com.tcb.sensenet.internal.data.NamespaceUtil;
import com.tcb.sensenet.internal.plot.Plot;
import com.tcb.sensenet.internal.plot.color.ColorScale;

public abstract class HeatmapPlot extends Plot {
	
	private XYZDataset dataset;
	private ColorScale colorScale;
	private String xLabel;
	private String yLabel;
	private String zLabel;
	
	protected JFreeChart chart;

	public HeatmapPlot(
			XYZDataset dataset,
			ColorScale colorScale,
			String xLabel,
			String yLabel,
			String zLabel){
		this.dataset = dataset;	
		this.colorScale = colorScale;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.zLabel = zLabel;
		this.chart = createChart();
		
		this.setLayout(new GridLayout());
	}
	
	private JFreeChart createChart(){
        XYPlot plot = createPlot();
		PaintScale paintScale = new PaintScaleImpl(colorScale);
	    
		setRenderer(plot,paintScale);
	    JFreeChart chart = new JFreeChart(getPlotTitle(),
	            JFreeChart.DEFAULT_TITLE_FONT, plot, false);
	    chart.setBackgroundPaint(Color.WHITE);
	    addLegend(chart, paintScale);
	   
	    return chart;
	}
	
	private XYPlot createPlot(){
		NumberAxis xAxis = new NumberAxis(xLabel);
        NumberAxis yAxis = new NumberAxis(yLabel);
        double minX = DatasetUtilities.findMinimumDomainValue(dataset).doubleValue();
        double maxX = DatasetUtilities.findMaximumDomainValue(dataset).doubleValue();
        double minY = DatasetUtilities.findMinimumRangeValue(dataset).doubleValue();
        double maxY = DatasetUtilities.findMaximumRangeValue(dataset).doubleValue();
        xAxis.setRange(new Range(minX,maxX));
        yAxis.setRange(new Range(minY,maxY));
        xAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);
		return plot;
	}
	
	private void setRenderer(XYPlot plot, PaintScale paintScale){
		XYBlockRenderer renderer = new XYBlockRenderer();
		renderer.setPaintScale(paintScale);
		renderer.setBlockHeight(1.0);
	    renderer.setBlockWidth(1.0);
	    plot.setRenderer(renderer);
	}
	
	private void addLegend(JFreeChart chart, PaintScale paintScale){
		NumberAxis scaleAxis = new NumberAxis(zLabel);
	    scaleAxis.setAxisLinePaint(Color.WHITE);
	    scaleAxis.setTickMarkPaint(Color.WHITE);
	    PaintScaleLegend legend = new PaintScaleLegend(paintScale, scaleAxis);
	    legend.setAxisLocation(AxisLocation.TOP_OR_RIGHT);
	    legend.setPadding(new RectangleInsets(10, 10, 10, 10));
	    legend.setStripWidth(15);
	    legend.setPosition(RectangleEdge.RIGHT);
	    legend.setBackgroundPaint(Color.WHITE);
	    chart.addSubtitle(legend);
	}
	
	@Override
	public String getXLabel() {
		return NamespaceUtil.removeNamespacePrefix(xLabel);
	}

	@Override
	public String getYLabel() {
		return NamespaceUtil.removeNamespacePrefix(yLabel);
	}
}
