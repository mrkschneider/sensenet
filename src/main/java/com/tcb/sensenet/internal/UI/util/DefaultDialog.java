package com.tcb.sensenet.internal.UI.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public abstract class DefaultDialog extends JDialog {

	private JPanel content;
	private static final int maxWidth = 1300;
	private static final int maxHeight = 750;
	
	public DefaultDialog(){
		
		JPanel p = new JPanel();
		content = p;
		JScrollPane scrollPane = 
				new ScrollPane(p, 
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setContentPane(scrollPane);
		
		scrollPane.setBorder(new EmptyBorder(1,1,1,1));
		p.setLayout(new GridBagLayout());		
		
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	}
	
	
	
	@Override
	public Component add(Component c){
		super.add(c,getDefaultDialogConstraints());
		return c;
	}
	
	@Override
	public Container getContentPane(){
		return content;
	}
	
	protected GridBagConstraints getDefaultDialogConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridy = GridBagConstraints.RELATIVE;
		return c;
	}
	
	private class ScrollPane extends DefaultScrollPane {

		public ScrollPane(Component view, int vsbPolicy, int hspPolicy) {
			super(view, vsbPolicy, hspPolicy);
		}
		
		@Override
		public Dimension getPreferredSize(){
			Dimension prefSize = super.getPreferredSize();
			Dimension size = new Dimension();
			int width = Math.min(prefSize.width, maxWidth);
			int height = Math.min(prefSize.height, maxHeight);
			size.setSize(width, height);
			return size;
		}
		
	}

}
