package com.tcb.sensenet.internal.UI.panels.stylePanel;

import java.awt.CardLayout;
import java.awt.Component;

public class CardLayoutAdapter extends CardLayout {
			
	@Override
    public void addLayoutComponent(Component comp, Object constraints) {
       if(constraints!=null) {
         super.addLayoutComponent(comp, constraints.toString());
       } else {
         super.addLayoutComponent(comp, constraints);
       }
    }
		
}
