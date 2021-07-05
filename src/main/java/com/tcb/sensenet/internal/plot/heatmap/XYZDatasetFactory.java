package com.tcb.sensenet.internal.plot.heatmap;

import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYZDataset;

public class XYZDatasetFactory {

	public XYZDataset create(double[] X, double[] Y, double[] Z){
		if(X.length != Y.length || X.length != Z.length){
			throw new IllegalArgumentException("Arrays must have the same length");
		}
		final int size = X.length;
		double[][] data = new double[3][size];
		
		for(int i=0;i<size;i++){
			data[0][i] = X[i];
			data[1][i] = Y[i];
			data[2][i] = Z[i];
		}
		
		DefaultXYZDataset dataset = new DefaultXYZDataset();
		dataset.addSeries("xyz", data);
		return dataset;
	}
}
