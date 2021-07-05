package com.tcb.sensenet.internal.plot;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.CyActivator;
import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.util.JPanelUtil;

public abstract class Plot extends DefaultPanel {

	public abstract String getPlotTitle();
	public abstract String getPlotSubTitle();
	public abstract String getXLabel();
	public abstract String getYLabel();
	public abstract void plot() throws Exception;
	public abstract void exportData(File f) throws IOException;
	
	public Plot(){
		this.setLayout(new GridBagLayout());
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		return c;
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		return new Dimension(400,d.height);
	}
	
	public void addExportButton(FileUtil fileUtil){
		
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					FileChooserFilter filter = new FileChooserFilter(".txt file", "txt");
					File f = fileUtil.getFile(Plot.this, "save",
							FileUtil.SAVE, Arrays.asList(filter));
					Plot.this.exportData(f);	
				} catch(IOException ex){
					throw new RuntimeException(ex);
				}			
			}
		};
		
		JButton b = new JButton("Export data");
		b.addActionListener(listener);
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.insets = new Insets(2,2,2,2);
		this.add(b,c);
	}
	

}
