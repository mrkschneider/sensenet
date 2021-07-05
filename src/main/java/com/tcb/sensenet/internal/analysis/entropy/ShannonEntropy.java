package com.tcb.sensenet.internal.analysis.entropy;

import com.tcb.sensenet.internal.analysis.correlation.Probabilities;
import com.tcb.sensenet.internal.util.MathLog;

public class ShannonEntropy {
	
	public double calculate(Probabilities<Integer> probs) {
		double result = 0.0d;
		for(Integer x:probs.getEvents()) {
			double p = probs.getProbability(x);
			result += p * MathLog.log2(p);
		}
		if(result != 0.)
			result = -result;
		return result;
	}
}
