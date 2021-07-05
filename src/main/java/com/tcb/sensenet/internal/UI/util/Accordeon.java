package com.tcb.sensenet.internal.UI.util;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.tcb.sensenet.internal.util.JPanelUtil;

public class Accordeon extends JPanel {
	
	private JButton button;
	private JPanel content;
	
	private Boolean open;
	
	private static final String openPrefix = "\u2212";
	private static final String closedPrefix = "\u002B";
	
	private String title;
	private Font normalFont;
	
	public Accordeon(String title, JPanel content, Boolean startsOpen){
		this.normalFont = getFont().deriveFont(Font.BOLD);
		this.title = title;
		this.open = startsOpen;
				
		this.setLayout(new GridBagLayout());
		
		addButton(this);
		addContentPanel(this, content);
		update();		
	}
	
	@Override
	public void setEnabled(boolean enable){
		// Do nothing
		// Dirty hack to prevent disabling by the state manager
		// TODO find a better way
		
	}
	
	public Accordeon(String title, JPanel content){
		this(title,content,false);
	}
	
	public Boolean isOpen(){
		return open;
	}
	
	public void toggle(){
		open = !open;
		update();
	}
	
	public void setOpen(Boolean open){
		if(open.equals(this.open)) return;
		toggle();
	}
		
	public JPanel getContentPanel(){
		return content;
	}
	
	protected void addButton(Container target){
		JButton button = new JButton(""){
			@Override
            public JToolTip createToolTip() {
                return new CustomToolTip(this);    	
                };
            @Override
            public void setEnabled(boolean enabled){
            	// Do nothing
            	// Dirty hack to prevent disabling by the state manager
        		// TODO find a better way
            }
        };
        this.button = button;
        
		//button.setContentAreaFilled(false);
		button.setToolTipText("Click to collapse/expand");
		//button.setMargin(new Insets(0,0,0,0));
		//button.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		button.setBackground(Color.LIGHT_GRAY);
		button.setHorizontalAlignment(SwingConstants.LEFT);
		
		MouseAdapter mouseListener = new MouseAdapter() {
	          @Override
	          public void mouseEntered(MouseEvent m) {
	        	 if(!button.isEnabled()) return;
	             button.setFont(getUnderlinedFont());
	          }
	          @Override
	          public void mouseExited(MouseEvent m) {
	        	 button.setFont(normalFont);
	          }
		};
		button.addMouseListener(mouseListener);
		button.setFont(normalFont);
				
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				toggle();
			}
			
		});
		
		JPanel p = new DefaultPanel();
		p.setBorder(BorderFactory.createEmptyBorder(2,0,2,0));
		p.add(button, getDefaultConstraints());
		
		target.add(p, getDefaultConstraints());
		
	}
	
	private void addContentPanel(Container target, JPanel content){
		this.content = content;

		JPanelUtil.setBorders(content, "", 5, 5, 5, 5);
		
		GridBagConstraints c = getDefaultConstraints();
		c.insets = new Insets(0, 3, 0, 3);
				
		target.add(content, c);
	}
	
	private Font getUnderlinedFont(){
		HashMap<TextAttribute, Object> textAttrMap = new HashMap<TextAttribute, Object>();
		textAttrMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		return normalFont.deriveFont(textAttrMap);
	}
	
	protected void update(){
		content.setVisible(open);
		updateTitle();
		updateParent();
	}
	
	protected void updateParent(){
		Window ancestor = SwingUtilities.getWindowAncestor(this);
		if(ancestor==null) return;
		ancestor.revalidate();
		//ancestor.pack();
	}
	
	protected void updateTitle(){
		String prefix = "";
		if(open) prefix = openPrefix;
		else prefix = closedPrefix;
		button.setText(prefix + " " + title);
	}
	
	protected GridBagConstraints getDefaultConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		//c.weighty = 1.0;
		//c.anchor = GridBagConstraints.FIRST_LINE_START;
		return c;
	}
		
	private class CustomToolTip extends JToolTip{
		/* Hack to fix Nimbus L&F bug regarding tooltips on disabled components.
		 * Somehow, tooltip colors are fine when we use this empty class.
		 * I will not even pretend to understand what is going on. It just works.
		 */
		public CustomToolTip(JComponent component){
			
		}
	}
	
}
