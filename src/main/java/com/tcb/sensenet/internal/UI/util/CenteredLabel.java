package com.tcb.sensenet.internal.UI.util;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CenteredLabel {
	public static JLabel create(String text){
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
}
