package com.tcb.sensenet.internal.UI.util;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.tcb.cytoscape.cyLib.data.Columns;

public class ColumnsShortStringRenderer extends BasicComboBoxRenderer {
		
		private JComboBox<Columns> parent;

		public ColumnsShortStringRenderer(JComboBox<Columns> parent){
			this.parent = parent;
			setHorizontalAlignment(SwingConstants.CENTER);
		}
		
	    @Override
	    public Component getListCellRendererComponent(
	    		JList list, Object value, int index,
	    		boolean isSelected, boolean cellHasFocus) {
	        if (value instanceof Columns) {
	            value = ((Columns)value).toShortString();
	        }   
	        setEnabled(parent.isEnabled());
	    	    
	        return super.getListCellRendererComponent(
	        		list, value, index,
	        		isSelected, cellHasFocus);

	    }
}

