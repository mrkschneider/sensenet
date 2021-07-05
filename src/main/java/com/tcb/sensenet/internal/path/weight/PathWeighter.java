package com.tcb.sensenet.internal.path.weight;

import com.tcb.sensenet.internal.path.Path;

public interface PathWeighter {
	public Double getWeight(Path path);
}
