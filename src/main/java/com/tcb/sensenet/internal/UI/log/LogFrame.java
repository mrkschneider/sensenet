package com.tcb.sensenet.internal.UI.log;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;
import com.tcb.sensenet.internal.UI.util.DefaultScrollPane;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.data.DefaultFileWriter;
import com.tcb.cytoscape.cyLib.log.LogBuilder;


public class LogFrame extends JFrame {

	private LogBuilder log;
	private AppGlobals appGlobals;

	public LogFrame(LogBuilder log, AppGlobals appGlobals){
		this.log = log;
		this.appGlobals = appGlobals;
		
		showLog();
		
		this.pack();
	    this.setLocationRelativeTo(null);
	}
		
	private void addCloseButton(Container target, JFrame frame){
		JButton b = new JButton("Close");
		ActionListener l = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();				
			}
		};
		b.addActionListener(l);
		target.add(b);
	}
	
	private void addSavePanel(LogBuilder log, Container target, JFrame frame){
		JPanel p = new JPanel();
		
		addSaveButton(log,p);
		addCloseButton(p,frame);
		
		GridBagConstraints c = getDefaultConstraints();
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(5,5,5,5);
		
		target.add(p,c);
	}
	
	private void addSaveButton(LogBuilder log, Container target){
		JButton b = new JButton("Save");
		ActionListener l = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					saveLog(log,target);	
				} catch(IOException ex){
					handleException(ex);
				}
								
			}
			
		};
		b.addActionListener(l);
		
		target.add(b);
	}
	
	private void showLog() {
		this.setTitle("sensenet log");
		
	    JPanel p = new DefaultPanel();
	    //p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
	    JTextArea field = new JTextArea(log.get());
	    
	    field.setEditable(false);
	    field.setMargin(new Insets(5,5,5,5));
	    
	    JPanel fieldPanel = new JPanel();
	    fieldPanel.setLayout(new GridLayout(1,1));
	    fieldPanel.add(field);
	    
	    addScrollPane(p,fieldPanel);
	    addSavePanel(log, p, this);

	    this.setContentPane(p);

	}
	
	private void addScrollPane(JPanel target, Component content){
		JScrollPane scrollPane = new DefaultScrollPane(
				content,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagConstraints c = getDefaultConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		target.add(scrollPane,c);
	}
	
	private void saveLog(LogBuilder log, Component parent) throws IOException {
		FileChooserFilter filter = 
					new FileChooserFilter(".log file", "log");
		File f = appGlobals.fileUtil.getFile(parent, "save",
					FileUtil.SAVE, Arrays.asList(filter));
		if(f==null) return;
		DefaultFileWriter writer = new DefaultFileWriter();
		
		writer.write(log.get(), f.toPath());
	}
	
	private void handleException(Exception ex){
		ex.printStackTrace();
		JOptionPane.showMessageDialog(this,ex.getMessage(),
				"Write error",JOptionPane.ERROR_MESSAGE);
	}
	
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridy = GridBagConstraints.RELATIVE;
		return c;
	}
	
	
}
