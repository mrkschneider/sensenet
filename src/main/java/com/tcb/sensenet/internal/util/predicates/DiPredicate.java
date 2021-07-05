package com.tcb.sensenet.internal.util.predicates;

public interface DiPredicate<T,U> {
	public boolean test(T a, U b);
}
