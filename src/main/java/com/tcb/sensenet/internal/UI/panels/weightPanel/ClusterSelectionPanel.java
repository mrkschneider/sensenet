package com.tcb.sensenet.internal.UI.panels.weightPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.tcb.sensenet.internal.UI.panels.weightPanel.listeners.SelectClusterListener;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.cytoscape.cyLib.UI.panel.WrapPanel;

public class ClusterSelectionPanel extends JPanel {

	private AppGlobals appGlobals;
	private JSlider frameSlider;
	private JTextField frameTextField;
	
	private JComboBox<Integer> clusterSelectionBox;

	public ClusterSelectionPanel(AppGlobals appGlobals){
		this.appGlobals = appGlobals;
		
		this.setLayout(new GridBagLayout());
				
		addClusterPanel();
		//addFramePanel();
		
		this.appGlobals.stateManagers.selectedClusterPanelStateManager.register(this);
		
	}
	
	public JComboBox<Integer> getClusterSelectionBox(){
		return clusterSelectionBox;
	}
	
	public JTextField getFrameTextField(){
		return frameTextField;
	}
	
	private GridBagConstraints defaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		return c;
	}
	
	private void addClusterPanel(){
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Cluster");
		WrapPanel<JLabel> textFieldLabel = new WrapPanel<JLabel>(label);
				
		panel.setLayout(new GridLayout(0,2));
		panel.add(textFieldLabel);
		addClusterSelectionBox(panel);
		
		this.add(panel, defaultConstraints());
	}
	
	private void addClusterSelectionBox(JPanel panel){
		JComboBox<Integer> box = new JComboBox<Integer>();
		ItemListener listener = new SelectClusterListener(box,appGlobals);
		box.addItemListener(listener);
		((JLabel)box.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(box);
		this.clusterSelectionBox = box;
	}
		
	public void setSelectedFrame(Integer frame){
		frameSlider.setValue(frame);
		frameTextField.setText(frame.toString());
	}
		
}
