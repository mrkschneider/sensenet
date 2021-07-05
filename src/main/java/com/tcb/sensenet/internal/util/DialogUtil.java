package com.tcb.sensenet.internal.util;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogUtil {
	
	public static JPanel createOKPanel(Runnable confirmedFun){
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		JButton confirmButton = new JButton("OK");
				
		ActionListener confirmedListener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				runAndDisplayException(confirmedFun);				
			}
			
		};
				
		confirmButton.addActionListener(confirmedListener);
		
		p.add(confirmButton);

		return p;
	}
	
	public static JPanel createActionPanel(Runnable confirmedFun, Runnable cancelFun){
		JPanel p = createOKPanel(confirmedFun);
		
		JButton cancelButton = new JButton("Cancel");
				
		ActionListener canceledListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runAndDisplayException(cancelFun);	
			}
			
		};
		
		cancelButton.addActionListener(canceledListener);
		
		p.add(cancelButton);
	
		return p;
	}
			
	private static void runAndDisplayException(Runnable runnable){
		try{
			runnable.run();
		} catch(Exception ex){
			ex.printStackTrace();
			showErrorDialog(ex.getMessage());
		}	
	}
	
	public static void showErrorDialog(String message){
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
}
