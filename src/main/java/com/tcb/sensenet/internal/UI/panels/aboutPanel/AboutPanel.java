package com.tcb.sensenet.internal.UI.panels.aboutPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import com.tcb.sensenet.internal.UI.util.DefaultPanel;

public class AboutPanel extends JFrame {
	
	public AboutPanel(){
		addAbout();
		this.setLocationRelativeTo(null);
		this.pack();
	}
	
	private void addAbout() {
		this.setTitle("About SenseNet");
		
		JTextPane p = new JTextPane();
		p.setEditable(false);
		p.setContentType("text/html");
		p.setPreferredSize(new Dimension(500,700));
		
		p.setText("<h1>Copyright notice</h1>"
				+ "<p>SenseNet: Analysis and visualization of networks generated from ensembles of molecular structures"
				+ "<p>Copyright (C) 2020 Markus Schneider</p>"
				+ "<p>This program is free software: you can redistribute it"
				+ " and/or modify it under the terms of the GNU Lesser General Public License"
				+ " as published by the Free Software Foundation, either version 3 of the License"
				+ ", or (at your option) any later version.</p>"
				+ "<p>This program is distributed in the hope that it will be useful, "
				+ "but WITHOUT ANY WARRANTY; without even the implied warranty of "
				+ "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the "
				+ "GNU Lesser General Public License for more details.</p>"
				+ "<p>You should have received a copy of the GNU Lesser General Public License"
				+ " along with this program. If not, see http://www.gnu.org/licenses.</p>"
				+ "<h1>Libraries</h1>"
				+ "<p>The following third-party libraries are used in this program</p>"
				+ "<ul >"
				+ "<li> RTree <a href=\"https://github.com/conversant/rtree\">https://github.com/conversant/rtree</a> <it>Apache 2.0 License</it></li>"
				+ "<li> Google AutoValue <a href=\"https://github.com/google/auto/\">https://github.com/google/auto/</a> <it>Apache 2.0 License</it></li>"
				+ "<li> Google Error Prone <a href=\"https://github.com/google/error-prone\">https://github.com/google/error-prone</a> <it>Apache 2.0 License</it></li>"
				+ "<li> Google Guava <a href=\"https://github.com/google/guava/guava\">https://github.com/google/guava/guava</a> <it>Apache 2.0 License</it></li>"
				+ "<li> iText <a href=\"http://www.lowagie.com/iText/\">http://www.lowagie.com/iText/</a> <it>Mozilla Public License</it></li>"
				+ "<li> Nayuki FFT <a href=\"https://www.nayuki.io/page/free-small-fft-in-multiple-languages\">https://www.nayuki.io/page/free-small-fft-in-multiple-languages</a> <it>MIT License</it></li>"
				+ "<li> Apache Commons <a href=\"http://commons.apache.org\">http://commons.apache.org</a> <it>Apache 2.0 License</it></li>"
				+ "<li> JUnit <a href=\"http://junit.org\">http://junit.org</a> <it>Eclipse Public License 1.0</it></li>"
				+ "<li> ByteBuddy <a href=\"http://bytebuddy.net/byte-buddy\">http://bytebuddy.net/byte-buddy</a> <it> Apache 2.0 License</it></li>"
				+ "<li> GNU Trove <a href=\"http://trove4j.sf.net\">http://trove4j.sf.net</a> <it> GNU Lesser General Public License 2.1</it></li>"
				+ "<li> Hamcrest <a href=\"https://github.com/hamcrest/JavaHamcrest/hamcrest-core\">https://github.com/hamcrest/JavaHamcrest/hamcrest-core</a> <it>New BSD License</it></li>"
				+ "<li> JCommon <a href=\"http://www.jfree.org/jcommon/\">http://www.jfree.org/jcommon/</a> <it>GNU Lesser General Public Licence</it></li>"
				+ "<li> JFreeChart <a href=\"http://www.jfree.org/jfreechart/\">http://www.jfree.org/jfreechart/</a> <it>GNU Lesser General Public Licence</it></li>"
				+ "<li> Mockito <a href=\"http://www.mockito.org\">http://www.mockito.org</a> <it>MIT License</it></li>"
				+ "<li> Objenesis <a href=\"http://objenesis.org\">http://objenesis.org</a> <it>Apache 2.0 License</it></li>"
				+ "</ul>");
		JScrollPane sp = new JScrollPane(p);
		this.add(sp);
	}
}
