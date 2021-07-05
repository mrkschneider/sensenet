package com.tcb.sensenet.internal.UI.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SingletonErrorDialog {
	
	private static Set<String> openExceptions = ConcurrentHashMap.newKeySet();
	
	private SingletonErrorDialog(){};
	
	public static void show(Exception exception){
		String key = getKey(exception);
		if(openExceptions.contains(key)) return;
		openExceptions.add(key);
		JDialog dialog = createDialog(exception.getMessage());
		addClosedListener(dialog,key);
		dialog.setVisible(true);
	}
	
	public static void showNonBlocking(Exception exception){
		Runnable r = new Runnable(){
			@Override
			public void run() {
				SingletonErrorDialog.show(exception);
			}
		};
		new Thread(r).start();
	}
	
	private static String getKey(Exception exception){
		return exception.getClass().getName() + exception.getMessage();
	}
	
	private static void addClosedListener(JDialog dialog, String key){
		dialog.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e){
				openExceptions.remove(key);
			}
		});
	}
	
	private static JDialog createDialog(String errorMessage){
		JDialog dialog = new JDialog();
		dialog.setModal(false);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JOptionPane pane = new JOptionPane(
				errorMessage,
				JOptionPane.ERROR_MESSAGE,
				JOptionPane.DEFAULT_OPTION,
				null,
				new Object[]{},
				null);
		JButton b = new JButton("OK");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();				
			}	
		});
		dialog.setTitle("Error");
		dialog.setContentPane(pane);
		dialog.add(b);
		dialog.pack();
		return dialog;
	}
	
	
}
