package com.tcb.sensenet.internal.UI.panels.weightPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.tcb.sensenet.internal.app.AppColumns;
import com.tcb.sensenet.internal.app.AppGlobals;
import com.tcb.sensenet.internal.events.FrameSetEvent;
import com.tcb.sensenet.internal.events.FrameSetRecord;
import com.tcb.sensenet.internal.meta.network.MetaNetwork;
import com.tcb.sensenet.internal.task.weighting.WeightFramesetTask;

public class SelectFrameByTextFieldListener implements ActionListener {

	private JTextField field;
	private AppGlobals appGlobals;
	private static final String frameErrorHeader = "Frame selection error";
	private static final String frameFormatError = "Selected frame must be an integer";

	public SelectFrameByTextFieldListener(JTextField field, AppGlobals appGlobals){
		this.field = field;
		this.appGlobals = appGlobals;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MetaNetwork metaNetwork = appGlobals.state.metaNetworkManager.getCurrentMetaNetwork();
		Integer frame = getFrame(metaNetwork);
		checkFrameIsValid(frame,metaNetwork);
			
		FrameSetRecord record = new FrameSetRecord(metaNetwork,frame);
		
		appGlobals.eventHelper.fireEvent(new FrameSetEvent(record));
	}
	
	private Integer getFrame(MetaNetwork metaNetwork){
		Integer frame;
		try{
			frame = Integer.valueOf(field.getText());
		} catch (NumberFormatException e){
			JOptionPane.showMessageDialog(null,frameFormatError,
					frameErrorHeader,JOptionPane.ERROR_MESSAGE);
			resetFrame(metaNetwork);
			throw new IllegalArgumentException(frameFormatError);
		}
		return frame;
	}

	private void checkFrameIsValid(Integer frame, MetaNetwork metaNetwork){
		try{
			WeightFramesetTask.checkFramesAreValid(metaNetwork, Arrays.asList(frame));
		} catch(Exception e){
			JOptionPane.showMessageDialog(null,e.getMessage(),
				frameErrorHeader,JOptionPane.ERROR_MESSAGE);
			resetFrame(metaNetwork);
			throw e;
		}
	}
	
	private void resetFrame(MetaNetwork metaNetwork){
		Integer lastSelectedFrame = metaNetwork.getHiddenDataRow().get(
				AppColumns.SELECTED_FRAME, Integer.class);
		field.setText(lastSelectedFrame.toString());
	}
	
}
