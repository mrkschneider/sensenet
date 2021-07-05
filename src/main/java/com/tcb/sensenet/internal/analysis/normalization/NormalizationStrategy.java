package com.tcb.sensenet.internal.analysis.normalization;

import java.util.List;
import java.util.stream.DoubleStream;

import com.google.common.collect.ImmutableList;

public interface NormalizationStrategy {
	public void normalize(double[] ds);
}
