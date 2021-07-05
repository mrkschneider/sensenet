package com.tcb.sensenet.internal.UI.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.cytoscape.util.swing.ColorButton;
import org.cytoscape.util.swing.FileUtil;

public class LabeledParametersPanel extends DefaultPanel {
	
	private JPanel active;
	
	public LabeledParametersPanel(){
		setNewTwoColumnsPanel();
	}
	
	@Override
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = super.getDefaultConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		c.weighty = 1.0;
		return c;
	}
		
	@Override
	public Component add(Component c){
		setNewTwoColumnsPanel();
		return super.add(c);
	}
	
	@Override
	public void add(Component c, Object constraints){
		setNewTwoColumnsPanel();
		super.add(c, constraints);
	}
	
	private void setNewTwoColumnsPanel(){
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		super.add(p);
		active = p;
	}
		
	public <T extends Component> T addParameter(String name, T comp){
		JLabel label = new JLabel(name);
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		active.add(label);
		active.add(comp);
		return comp;
	}
	
	public JTextField addTextParameter(String description, String defaultValue){
		JTextField field = new JTextField(defaultValue);
		//field.setMaximumSize(new Dimension(200,25));
		return addParameter(description,field);
	}
	
	public JCheckBox addBooleanParameter(String description, Boolean defaultValue){
		JCheckBox box = new JCheckBox();
		box.setSelected(defaultValue);
		box.setHorizontalAlignment(SwingConstants.CENTER);
		return addParameter(description,box);
	}
	
	public <T> JComboBox<T> addChoosableParameter(String description, T[] options, T defaultValue){
		JComboBox<T> box = new JComboBox<>(options);
		alignHorizontally(box);
		box.setSelectedItem(defaultValue);
		return addParameter(description,box);
	}
	
	public <T> TextComboBox<T> addTextChoosableParameter(String description, T[] options, T defaultOption, String defaultText){
		TextComboBox<T> box = new TextComboBox<>(options);
		alignHorizontally(box.getComboBox());
		box.getComboBox().setSelectedItem(defaultOption);
		box.getField().setText(defaultText);
		return addParameter(description,box);
	}
	
	private void alignHorizontally(JComboBox<?> box){
		((JLabel)box.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public FileButton addFileParameter(
			String description,
			String startFile,
			String defaultLabel,
			String fileDescription,
			String[] fileSuffixes,
			String helpText,
			FileUtil fileUtil){
		FileButton button = new FileButton(
				startFile, defaultLabel, fileDescription, fileSuffixes, helpText, fileUtil);
		return addFileParameter(description,button);
	}
	
	private FileButton addFileParameter(
			String description,
			FileButton button){
		Dimension maxSize = new Dimension(150,button.getPreferredSize().height);
		button.setMaximumSize(maxSize);
		button.setPreferredSize(maxSize);
		return addParameter(description,button);
	}
	
	public FileButton addShortFileParameter(
			String description,
			String startFile,
			String defaultLabel,
			String fileDescription,
			String[] fileSuffixes,
			String helpText,
			FileUtil fileUtil){
		FileButton button = new ShortFileButton(
				startFile, defaultLabel, fileDescription, fileSuffixes, helpText, fileUtil);
		return addFileParameter(description,button);
	}
	
	public ColorButton addColorParameter(String description, Color defaultColor){
		ColorButton b = new ColorButton(defaultColor);
		return addParameter(description,b);
	}
}
