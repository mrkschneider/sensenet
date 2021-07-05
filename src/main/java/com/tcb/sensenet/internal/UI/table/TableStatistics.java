package com.tcb.sensenet.internal.UI.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.plot.histogram.HistogramPlot;
import com.tcb.mdAnalysis.statistics.StandardStatistics;
import com.tcb.common.util.Rounder;


public class TableStatistics {

	private AppGlobals appGlobals;

	public TableStatistics(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
	}
	
	public TableView createStatisticsTable(TableView table){
		
		TableModel tableModel = table.getTable().getModel();
		List<Integer> numericColumns = IntStream.range(0,tableModel.getColumnCount())
				.filter(i -> Number.class.isAssignableFrom(tableModel.getColumnClass(i)))
				.boxed()
				.collect(ImmutableList.toImmutableList());
		
		String[] tableColumnNames = new String[]{"column","mean","standard deviation"};
		Object[][] statData = new Object[numericColumns.size()][tableColumnNames.length];
					
		TableView statTable = new TableView(tableColumnNames,statData,appGlobals.fileUtil);
		
		Runnable updater = () -> {
			updateStatData(statTable,numericColumns,table);
		};
		
		updater.run();
		
		table.getTable().getRowSorter().addRowSorterListener((e) -> updater.run());
		
		return statTable;
	}
	
	private void updateStatData(TableView statTable, List<Integer> numericColumns, TableView table){
		List<Integer> visibleRows = table.getVisibleRows();
		TableModel tableModel = table.getTable().getModel();
		TableModel statTableModel = statTable.getTable().getModel();
		List<StandardStatistics> statistics = new ArrayList<>();
		List<String> columnNames = new ArrayList<>();
		
		for(Integer columnIndex:numericColumns){	
			List<Double> values = visibleRows.stream()
					.map(r -> tableModel.getValueAt(r, columnIndex))
					.filter(o -> o!=null)
					.map(o -> (Number)o)
					.map(n -> n.doubleValue())
					.collect(ImmutableList.toImmutableList());
			StandardStatistics stat = new StandardStatistics(values);
			
			columnNames.add(tableModel.getColumnName(columnIndex));
			statistics.add(stat);
		}
				
		for(int i=0;i<numericColumns.size();i++){
			String column = columnNames.get(i);
			StandardStatistics stat = statistics.get(i);
			Double avg = Rounder.round(stat.getMean(),3);
			Double std = Rounder.round(stat.getStandardDeviation(),3);
			statTableModel.setValueAt(column, i, 0);
			statTableModel.setValueAt(avg, i, 1);
			statTableModel.setValueAt(std, i, 2);
		}
	}
	
}
