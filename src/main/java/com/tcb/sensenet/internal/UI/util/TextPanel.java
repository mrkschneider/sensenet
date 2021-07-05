package com.tcb.sensenet.internal.UI.util;

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
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.cytoscape.util.swing.FileChooserFilter;
import org.cytoscape.util.swing.FileUtil;

import com.tcb.sensenet.internal.app.AppGlobals;

public class TextPanel extends JPanel {
	private AppGlobals appGlobals;
	private JTextArea field;

	public TextPanel(String text, AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		this.setLayout(new GridLayout(1,1));
		
		field = new JTextArea(text);
	    
	    field.setEditable(false);
	    field.setMargin(new Insets(5,5,5,5));
	 	    
	    GridBagConstraints c = new GridBagConstraints();
	    c.gridx = GridBagConstraints.REMAINDER;
	    c.gridy = GridBagConstraints.RELATIVE;
	    c.anchor = GridBagConstraints.FIRST_LINE_START;
	    c.weightx = 1.0;
	    
	    JPanel p = new DefaultPanel(c);
	    	    
	    addSaveButton(p);
	    p.add(field);
	    
	    this.add(p);
	}
	

	
	private void addSaveButton(Container target){
		JButton b = new JButton("Save");
		ActionListener l = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					saveContent(target);	
				} catch(IOException ex){
					handleException(ex);
				}
								
			}
			
		};
		b.addActionListener(l);
		
		target.add(b);
	}
	
	private void handleException(Exception ex){
		ex.printStackTrace();
		JOptionPane.showMessageDialog(this,ex.getMessage(),
				"Write error",JOptionPane.ERROR_MESSAGE);
	}
		
	private void saveContent(Component parent) throws IOException {
		FileChooserFilter filter = 
					new FileChooserFilter(".txt file", "txt");
		File f = appGlobals.fileUtil.getFile(parent, "save",
					FileUtil.SAVE, Arrays.asList(filter));
		if(f==null) return;
		FileOutputStream out =  new FileOutputStream(f, false);
	    BufferedWriter w = new BufferedWriter(new OutputStreamWriter(out));
	    
	    try{
	    	w.write(field.getText());
	    } finally {
	    	w.close();
	    }
	    		        
	}
	
	private void addScrollPane(Container target, Component content){
		JScrollPane scrollPane = new DefaultScrollPane(
				content,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//GridBagConstraints c = getDefaultConstraints();
		//c.fill = GridBagConstraints.BOTH;
		//c.weightx = 1.0;
		//c.weighty = 1.0;
		//target.add(scrollPane,c);
		target.add(scrollPane);
	}
}
