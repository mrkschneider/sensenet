package com.tcb.sensenet.internal.UI.table;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;

import com.google.common.collect.ImmutableList;
import com.tcb.sensenet.internal.plot.histogram.ColumnPlot;
import com.tcb.sensenet.internal.plot.histogram.HistogramPlot;

public class TableView extends JPanel {
	
	protected String[] columnNames;
	protected Object[][] data;
	protected FileUtil fileUtil;
	protected JTable table;
	protected TableRowSorter<TableModel> sorter;

	public TableView(
			String[] columns,
			Object[][] data,
			FileUtil fileUtil){
		this.data = data;
		this.fileUtil = fileUtil;
		this.columnNames = columns;
		
        this.setOpaque(true);
        this.setLayout(new GridBagLayout());
        
        addTable();
        addSaveButton();
	    addUnselectAllButton();
	}
		
	private void addTable(){
		TableModel model = TableModelImpl.create(data,columnNames);
		table = new JTable(model);
		setTableSize();
		table.setFillsViewportHeight(true);
		//table.setPreferredScrollableViewportSize(new Dimension(10,200));
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		sorter = new TableRowSorter<TableModel>(model){
			@Override
			public void setRowFilter(RowFilter<? super TableModel, ? super Integer> filter){
				try{super.setRowFilter(filter);}catch(NullPointerException e){};
			}
		};
		
		if(data.length > 0){
			sorter.setRowFilter(null);
		}
		table.setRowSorter(sorter);

		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		add(scrollPane,c);
	}
	
	private void setTableSize(){
		Dimension dim = table.getPreferredSize();
		int width = dim.width;
		int height = dim.height;
		height = Math.min(400,height);
		table.setPreferredScrollableViewportSize(new Dimension(width,height));
	}
	
	
	private void addSaveButton(){
		ActionListener buttonListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				FileChooserFilter filter = 
						new FileChooserFilter(".csv file", "csv");
				File f = fileUtil.getFile(new JFrame(), "save",
						FileUtil.SAVE, Arrays.asList(filter));
				if(f==null) {
					//JOptionPane.showMessageDialog(TableView.this, "Could not open file", "File error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				saveTable(f);
			}
			
		};
		
		JButton button = new JButton("Save table");
		button.addActionListener(buttonListener);
		
		GridBagConstraints c = getHeaderConstraints();
		
		this.add(button,c);
	}
	
	protected GridBagConstraints getHeaderConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = GridBagConstraints.RELATIVE;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(0,0,5,5);
		return c;
	}
	
	public void saveTable(File f){
		TableCSVWriter writer = new TableCSVWriter(this);
		try{
			writer.write(f);
		} catch(IOException ex){
			ex.printStackTrace();
			JOptionPane.showMessageDialog(TableView.this,ex.getMessage(),
					"Write error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<Integer> getVisibleRows(){
		List<Integer> result = new ArrayList<Integer>();
		for(int i=0;i<data.length;i++){
			if(sorter.convertRowIndexToView(i) != -1){
				result.add(i);
			}
		}
		return result;
	}
		
	public void show(){
        JFrame frame = new JFrame("sensenet");

        frame.setContentPane(this);

        frame.pack();
        frame.setVisible(true);
	}
	
	public JTable getTable(){
		return table;
	}
	
	protected void addSelectRowListener(Consumer<Integer> rowIndexFun, Runnable resetRowsFun){
        ListSelectionModel rowSM = table.getSelectionModel();
        
        rowSM.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                //Ignore extra messages.
                if (e.getValueIsAdjusting()) return;

                int[] rowIndices = table.getSelectedRows();
                
                resetRowsFun.run();
                
                for(int iSelected:rowIndices){
                	int iRow = table.getRowSorter().convertRowIndexToModel(iSelected);
                	rowIndexFun.accept(iRow);             	
                }
               
            }
        });
	}
	
	protected void addUnselectAllButton(){
		JButton button = new JButton("Unselect all");
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				table.clearSelection();				
			}
		});
		
		GridBagConstraints c = getHeaderConstraints();
				
		this.add(button, c);
	}
	
	protected void addPlotHistogramButton(){
		JButton button = new JButton("Histogram");
		
		button.addActionListener((e) -> {
			PlotColumnHistogramDialog dialog = new PlotColumnHistogramDialog(this);
			dialog.setVisible(true);
		});
		
		GridBagConstraints c = getHeaderConstraints();
		this.add(button, c);
	}
	
	public HistogramPlot createHistogramPlot(String columnName, Integer binCount){
		if(!isNumericColumn(columnName))
			throw new IllegalArgumentException("Column does not contain numeric values");
		int colIndex = getColumnIndex(columnName);
		List<Integer> visibleRows = getVisibleRows();
		List<Double> values = visibleRows.stream()
				.map(i -> table.getModel().getValueAt(i, colIndex))
				.filter(o -> o!=null)
				.map(o -> ((Number)o).doubleValue())
				.filter(d -> !Double.isNaN(d))
				.collect(ImmutableList.toImmutableList());
		HistogramPlot plot = new ColumnPlot(columnName,values,binCount);
		plot.plot();
		return plot;
	}
	
	public String[] getColumnNames(){
		return columnNames;
	}
	
	public Integer getColumnIndex(String columnName){
		int colIndex = Arrays.asList(columnNames).indexOf(columnName);
		if(colIndex==-1) throw new IllegalArgumentException("Unknown column name");
		return colIndex;
	}
	
	public Boolean isNumericColumn(String columnName){
		int colIndex = getColumnIndex(columnName);
		Class<?> columnClass = table.getColumnClass(colIndex);
		return Number.class.isAssignableFrom(columnClass);
	}
}
