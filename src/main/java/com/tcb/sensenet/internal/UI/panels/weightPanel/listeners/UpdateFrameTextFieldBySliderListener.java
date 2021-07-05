package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tcb.sensenet.internal.app.AppGlobals;

public class UpdateFrameTextFieldBySliderListener implements ChangeListener {

	private JSlider slider;
	private JTextField textField;
	private AppGlobals appGlobals;
	

	public UpdateFrameTextFieldBySliderListener(JSlider slider, JTextField textField,
			AppGlobals appGlobals){
		this.slider = slider;
		this.textField = textField;
		this.appGlobals = appGlobals;
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==slider){
			Integer frame = slider.getValue();
			textField.setText(frame.toString());
		}
	}

}
