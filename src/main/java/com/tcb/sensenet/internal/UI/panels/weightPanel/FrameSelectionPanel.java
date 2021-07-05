package com.tcb.sensenet.internal.UI.panels.weightPanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.SelectFrameBySliderListener;
import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.SelectFrameByTextFieldListener;
import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.UpdateFrameTextFieldBySliderListener;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.UI.panel.WrapPanel;

public class FrameSelectionPanel extends JPanel {

	private AppGlobals appGlobals;
	private SelectFrameSlider frameSlider;
	private JTextField frameTextField;
	private JLabel frameTextFieldLabel;

	public FrameSelectionPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		this.setLayout(new GridBagLayout());
		JPanel framePanel = createFramePanel();
		JSlider frameSlider = createFrameSlider(frameTextField);	
		this.add(frameSlider, defaultConstraints());
		this.add(framePanel, defaultConstraints());
	}
	
	private GridBagConstraints defaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
	
	public void setSelectedFrame(Integer frame){
		frameSlider.silence();
		frameSlider.setValue(frame);
		frameTextField.setText(frame.toString());
		frameSlider.unsilence();
	}
	
	public void setSelectedFrameToAll(){
		frameSlider.silence();
		frameSlider.setValue(0);
		frameTextField.setText("All");
		frameSlider.unsilence();
	}
	
	public void setSliderBounds(Integer low, Integer high){
		frameSlider.silence();
		frameSlider.setMinimum(low);
		frameSlider.setMaximum(high);
		frameSlider.unsilence();
	}
			
	public SelectFrameSlider getTimelineSlider(){
		return frameSlider;
	}
	
	public JTextField getFrameSelectionTextField(){
		return frameTextField;
	}
	
	public JLabel getFrameSelectionTextFieldLabel(){
		return frameTextFieldLabel;
	}
	
	private JPanel createFramePanel(){
		JPanel panel = new JPanel();
		this.frameTextFieldLabel = new JLabel("Frame");
		WrapPanel<JLabel> textFieldLabel = new WrapPanel<JLabel>(frameTextFieldLabel);
		JTextField frameTextField = createFrameTextField();
		
		panel.setLayout(new GridLayout(0,2));
		panel.add(textFieldLabel);
		panel.add(frameTextField);
		return panel;
	}
	
	private JTextField createFrameTextField(){
		this.frameTextField = new JTextField();
		frameTextField.setHorizontalAlignment(JTextField.CENTER);
		ActionListener listener = new SelectFrameByTextFieldListener(
				frameTextField, appGlobals);
		
		
		frameTextField.addActionListener(listener);
		return frameTextField;
	}
		
	private JSlider createFrameSlider(JTextField frameTextField){
		this.frameSlider = new SelectFrameSlider(0,1,0);
		frameSlider.setPreferredSize(new Dimension(10,20));
		ChangeListener selectListener = new SelectFrameBySliderListener(
				frameSlider, appGlobals);
		frameSlider.addChangeListener(selectListener);
		ChangeListener updateTextFieldListener = 
				new UpdateFrameTextFieldBySliderListener(frameSlider, frameTextField, appGlobals);
		frameSlider.addChangeListener(updateTextFieldListener);
		return frameSlider;
	}
		
}
