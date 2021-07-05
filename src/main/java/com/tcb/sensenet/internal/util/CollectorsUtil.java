package com.tcb.sensenet.internal.util;

import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CollectorsUtil {
	
	private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }
		
	public static <T,K,U,M extends Map<K,U>> Collector<T,?,M> toMap(
			Function<? super T,? extends K> keyMapper,
			Function<? super T,? extends U> valueMapper,
			Supplier<M> mapSupplier){
		return Collectors.toMap(keyMapper, valueMapper, throwingMerger(), mapSupplier);
	}
}
