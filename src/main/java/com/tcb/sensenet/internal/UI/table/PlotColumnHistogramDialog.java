package com.tcb.sensenet.internal.UI.table;

import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.stream.Stream;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.UI.util.DefaultDialog;
import com.tcb.sensenet.internal.UI.util.LabeledParametersPanel;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.plot.histogram.HistogramPlot;
import com.tcb.sensenet.internal.util.DialogUtil;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyNetworkAdapter;
import com.tcb.cytoscape.cyLib.cytoApiWrappers.CyTableAdapter;

public class PlotColumnHistogramDialog extends DefaultDialog {

	private JComboBox<String> columnNameBox;
	private JTextField binCountBox;
	private TableView table;

	public PlotColumnHistogramDialog(
			TableView table){
		this.table = table;
				
		LabeledParametersPanel p = new LabeledParametersPanel();
		
		String[] columnChoices = Stream.of(table.getColumnNames())
				.filter(c -> table.isNumericColumn(c))
				.toArray(String[]::new);
		columnNameBox = p.addChoosableParameter("Column name", columnChoices, columnChoices[0]);
		binCountBox = p.addTextParameter("Number of bins", "10");
		
		this.add(p);
		this.add(DialogUtil.createActionPanel(() -> confirm(), () -> dispose()));
		this.pack();
	}
	
	@Override
	protected GridBagConstraints getDefaultDialogConstraints(){
		GridBagConstraints c = super.getDefaultDialogConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 1.0;
		return c;
	}
	
	private void confirm(){
		String columnName = (String) columnNameBox.getSelectedItem();
		Integer binCount = Integer.parseInt(binCountBox.getText());
		
		HistogramPlot plot = table.createHistogramPlot(columnName,binCount);
		JFrame frame = new JFrame("Histogram");
		frame.setContentPane(plot);
		frame.pack();
		this.dispose();
		frame.setVisible(true);
	}
}
